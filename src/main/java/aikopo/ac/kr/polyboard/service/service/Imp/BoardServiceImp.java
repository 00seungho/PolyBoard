package aikopo.ac.kr.polyboard.service.service.Imp;

import aikopo.ac.kr.polyboard.dto.*;
import aikopo.ac.kr.polyboard.entity.Board;
import aikopo.ac.kr.polyboard.entity.LikeDislike;
import aikopo.ac.kr.polyboard.entity.Major;
import aikopo.ac.kr.polyboard.entity.Member;
import aikopo.ac.kr.polyboard.repository.*;
import aikopo.ac.kr.polyboard.service.service.Interface.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
public class BoardServiceImp extends BaseService implements BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final MajorRepository majorRepository;
    private final ReplyRepository replyRepository;
    private final LikeDislikeRepository likeDislikeRepository;
    @Override
    public Long register(BoardDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증되지 않은 상태 처리
        if (authentication == null || !authentication.isAuthenticated() ||
                "anonymousUser".equals(authentication.getPrincipal())) {
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            String userName = ((UserDetails) principal).getUsername();
            Optional<Member> memberOptional = memberRepository.findByEmail(userName);
            Member member = memberOptional.orElseThrow(() ->
                    new IllegalArgumentException("사용자를 찾을 수 없습니다: " + userName));

            Optional<Major> majorOptional = majorRepository.findByName(dto.getBoardMajor());
            Major major = majorOptional.orElseThrow(() -> new IllegalArgumentException("보드를 찾을 수 없습니다: " + dto.getBoardMajor()));
            Board board = Board.builder()
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .member(member)
                    .major(major)
                    .likes(0)
                    .disLikes(0)
                    .departmentNotice(dto.getDepartmentNotice())
                    .allNotice(dto.getAllNotice())
                    .build();
            boardRepository.save(board);
            return board.getId();
        }
        throw new AccessDeniedException("접근 권한이 없습니다.");
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getDepartmentList(PageRequestDTO pageRequestDTO) {
        if (pageRequestDTO.getCategory() == null){
            throw new IllegalArgumentException("존재하지 않는 게시판입니다.");
        }
        if (pageRequestDTO.getCategory().isEmpty()) {
                return getAllBoards(pageRequestDTO);

        }

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

//    @Override
    public PageResultDTO<BoardDTO, Object[]> getAllBoards(PageRequestDTO pageRequestDTO) {
        List<Board> allNotices = boardRepository.findAllByOrderByIdDesc();
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
    public void addLike(LikeDislikeDTO likeDislikeDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("인증되지 않은 사용자입니다.");
        }
        Object principal = authentication.getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        Member member = memberRepository.findByEmail(userDetails.getUsername()).orElseThrow(()-> new IllegalArgumentException("회원 조회 오류"));
        Optional<LikeDislike> existing = likeDislikeRepository.findByMemberIdAndBoardId(member.getId(), likeDislikeDTO.getBoardId());
        if (existing.isPresent()) {
            // 이미 좋아요/싫어요를 누른 경우 상태를 업데이트
            LikeDislike likeDislike = existing.get();
            likeDislike.changeLikeStatus(likeDislikeDTO.getLikeStatus());
            likeDislikeRepository.save(likeDislike);
        } else {
            // 처음 누르는 경우
            LikeDislike likeDislike = LikeDislike.builder()
                    .member(member)
                    .board(Board.builder().id(likeDislikeDTO.getBoardId()).build())   // Board 객체 생성
                    .likeStatus(likeDislikeDTO.getLikeStatus())
                    .build();
            likeDislikeRepository.save(likeDislike);
        }
        boardRepository.updateLikesAndDislikes(likeDislikeDTO.getBoardId());
    }


    @Override
    @Transactional
    public void removeWithReplies(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Board not found with id: " + id));
        List<String> role = getRole();
        if(checkAuthorize(board.getMember().getEmail()) || role.contains("ROLE_ADMIN"))
        {
            likeDislikeRepository.deleteByBoardId(id);
            replyRepository.deleteByBoardId(id);
    //        원글삭제
            boardRepository.deleteById(id);
        }
    }

    @Override
    public Long modify(BoardDTO boardDTO) {
        Board board = boardRepository.getReferenceById(boardDTO.getId());
        if(checkAuthorize(board.getMember().getEmail())){
        board.changeTitle(boardDTO.getTitle());

        board.changeContent(boardDTO.getContent());

        boardRepository.save(board);
        return board.getId();
        }
        else{
            throw new AccessDeniedException("접근권한이 없습니다.");
        }
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
