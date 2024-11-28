package aikopo.ac.kr.polyboard.service.service.Imp;

import aikopo.ac.kr.polyboard.dto.BoardDTO;
import aikopo.ac.kr.polyboard.dto.MajorListDTO;
import aikopo.ac.kr.polyboard.dto.PageRequestDTO;
import aikopo.ac.kr.polyboard.dto.PageResultDTO;
import aikopo.ac.kr.polyboard.entity.Board;
import aikopo.ac.kr.polyboard.entity.Major;
import aikopo.ac.kr.polyboard.entity.Member;
import aikopo.ac.kr.polyboard.repository.BoardRepository;
import aikopo.ac.kr.polyboard.repository.MajorRepository;
import aikopo.ac.kr.polyboard.repository.MemberRepository;
import aikopo.ac.kr.polyboard.repository.ReplyRepository;
import aikopo.ac.kr.polyboard.service.service.Interface.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImp implements BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final MajorRepository majorRepository;
    private final ReplyRepository replyRepository;

    @Override
    public String register(BoardDTO dto) {
        Board board = dtoToEntity(dto);
        boardRepository.save(board);
        return board.getTitle();
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getDepartmentList(PageRequestDTO pageRequestDTO) {

        if (pageRequestDTO.getCategory() != null && !pageRequestDTO.getCategory().isEmpty() && !majorRepository.existsByName(pageRequestDTO.getCategory())) {
            if (pageRequestDTO.getCategory().equals("핫글")){
                return getHotBoard(pageRequestDTO);
            }
            throw new IllegalArgumentException("존재하지 않는 게시판입니다.");
        }
        List<Board> allNotices = boardRepository.findByAllNoticeTrueOrderByIdDesc();
        List<Board> departmentNotices = boardRepository.findByDepartmentNoticeAndMajorName(pageRequestDTO.getCategory());
        List<Board> majorBoardLists = boardRepository.findByMajorNameExcludingNotices(pageRequestDTO.getCategory());

        // 2. 리스트 합치기
        List<Board> combinedList = new ArrayList<>();
        combinedList.addAll(allNotices);
        combinedList.addAll(departmentNotices);
        combinedList.addAll(majorBoardLists);

        // 3. Object[]로 변환 (단순히 Board만 포함한 배열 생성)
        List<Object[]> objectArrayList = combinedList.stream()
                .map(board -> new Object[]{board}) // Object[]로 변환
                .collect(Collectors.toList());


        int page = pageRequestDTO.getPage() - 1; // JPA Pageable은 0-based index
        int size = pageRequestDTO.getSize();
        int start = page * size;
        int end = Math.min((start + size), combinedList.size()); // 끝 인덱스 계산
        List<Object[]> pagedList = objectArrayList.subList(start, end);
        Page<Object[]> pageResult = new PageImpl<>(pagedList, PageRequest.of(page, size), objectArrayList.size());

        // 5. 변환 함수 정의
        Function<Object[], BoardDTO> fn = (en -> entityToDTO((Board) en[0]));

        return new PageResultDTO<>(pageResult, fn); // 변환 함수 전달


    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getHotBoard(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(0, 100); // 첫 번째 페이지, 100개씩
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusDays(7);
        List<Board> allNotices = boardRepository.findByLikesInLastWeek(oneWeekAgo,pageable);
        List<Object[]> objectArrayList = allNotices.stream()
                .map(board -> new Object[]{board}) // Object[]로 변환
                .collect(Collectors.toList());

        int page = pageRequestDTO.getPage() - 1; // JPA Pageable은 0-based index
        int size = pageRequestDTO.getSize();
        int start = page * size;
        int end = Math.min((start + size), objectArrayList.size()); // 끝 인덱스 계산
        List<Object[]> pagedList = objectArrayList.subList(start, end);
        Page<Object[]> pageResult = new PageImpl<>(pagedList, PageRequest.of(page, size), objectArrayList.size());

        Function<Object[], BoardDTO> fn = (en -> entityToDTO((Board) en[0]));

        return new PageResultDTO<>(pageResult, fn); // 변환 함수 전달
    }

    @Override
    public BoardDTO get(Long id) {
        Optional<Board> result = boardRepository.findById(id);
        Board board = result.orElseThrow(() -> new IllegalArgumentException("Board not found with id: " + id));
//        Member member = board.getMember();
//        Long replyCount = replyRepository.countByBoard_Id(id);
        BoardDTO dto = entityToDTO(board);
        return dto;
    }

    @Override
    public List<MajorListDTO> getMajorList() {
        List<Major> allMajors = majorRepository.findAll();
        List<MajorListDTO> majorListDTOList = allMajors.stream()
                .map(this::entityToDTO) // entityToDto 메서드 적용
                .collect(Collectors.toList());
        return majorListDTOList; // 반환 추가
    }

    @Override
    @Transactional
    public void removeWithReplies(Long id) {
        replyRepository.deleteById(id);
//        원글삭제
        boardRepository.deleteById(id);
    }

    @Override
    public void modify(BoardDTO boardDTO) {
        Board board = boardRepository.getReferenceById(boardDTO.getId());
        Optional<Major> optionalMajor = majorRepository.findByName(boardDTO.getBoardMajor());
        Major major = optionalMajor.orElseThrow(() -> new IllegalArgumentException("Major not found with BoardMajor: " + boardDTO.getBoardMajor()));

        board.changeTitle(boardDTO.getTitle());
        board.changeContent(boardDTO.getContent());
        board.changeMajor(major);

        boardRepository.save(board);
    }

    public BoardDTO entityToDTO(Board board){
        Member member = board.getMember();
        Long replyCount = replyRepository.countByBoard_Id(board.getId());
        BoardDTO boardDTO = BoardDTO.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getNickname())
                .replyCount(replyCount.intValue())
                .writerMajor(member.getMajor().getName())
                .boardMajor(board.getMajor().getName())
                .disLikes(board.getDisLikes())
                .likes(board.getLikes())
                .allNotice(board.getAllNotice())
                .departmentNotice(board.getDepartmentNotice())
                .build();
        return boardDTO;
    }

    public Board dtoToEntity(BoardDTO dto){
        Optional<Member> optionalMember = memberRepository.findByEmail(dto.getWriterEmail());
        Member member = optionalMember.orElseThrow(() -> new IllegalArgumentException("Member not found with email: " + dto.getWriterEmail()));

        Optional<Major> optionalMajor = majorRepository.findByName(dto.getBoardMajor());
        Major major = optionalMajor.orElseThrow(() -> new IllegalArgumentException("Major not found with BoardMajor: " + dto.getBoardMajor()));

        Board board= Board.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .likes(dto.getLikes())
                .disLikes(dto.getDisLikes())
                .allNotice(dto.getAllNotice())
                .departmentNotice(dto.getDepartmentNotice())
                .member(member)
                .major(major)
                .build();
        return board;
    }

    public MajorListDTO entityToDTO(Major major){
        MajorListDTO majorListDTO =MajorListDTO.builder()
                .id(major.getId())
                .name(major.getName())
                .build();
        return majorListDTO;
    }




}
