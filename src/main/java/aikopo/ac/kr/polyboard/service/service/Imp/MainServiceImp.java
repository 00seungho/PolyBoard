package aikopo.ac.kr.polyboard.service.service.Imp;

import aikopo.ac.kr.polyboard.dto.BoardDTO;
import aikopo.ac.kr.polyboard.entity.Board;
import aikopo.ac.kr.polyboard.entity.Member;
import aikopo.ac.kr.polyboard.repository.BoardRepository;
import aikopo.ac.kr.polyboard.repository.MajorRepository;
import aikopo.ac.kr.polyboard.repository.MemberRepository;
import aikopo.ac.kr.polyboard.repository.ReplyRepository;
import aikopo.ac.kr.polyboard.service.service.Interface.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainServiceImp implements MainService {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MajorRepository majorRepository;

    public BoardDTO entityToDTO(Board board){
        Member member = board.getMember();
        Long replyCount = replyRepository.countByBoard_Id(board.getId());
        BoardDTO boardDTO = BoardDTO.builder()
                .id(board.getId())
                .title(board.getTitle())
                .regDate(board.getRegDate())
                .writerName(member.getNickname())
                .replyCount(replyCount.intValue())
                .boardMajor(board.getMajor().getName())
                .writerMajor(board.getMember().getMajor().getName())
                .likes(board.getLikes())
                .allNotice(board.getAllNotice())
                .build();
        return boardDTO;
    }

    @Override
    public List<BoardDTO> getNoticeList() {
        List<Board> allNotices = boardRepository.findTop10ByAllNoticeTrueOrderByIdDesc();
        List<BoardDTO> allNoticesDTO = allNotices.stream()
                .map(board -> entityToDTO(board))
                .collect(Collectors.toList());

        return allNoticesDTO;
    }

    @Override
    public List<BoardDTO> getHotBoardList() {
        Pageable pageable = PageRequest.of(0, 10); // 첫 번째 페이지, 10개씩
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusDays(7);
        List<Board> hotBoards = boardRepository.findByLikesInLastWeek(oneWeekAgo,pageable);
        List<BoardDTO> hotBoardsDTO = hotBoards.stream()
                .map(board -> entityToDTO(board))
                .collect(Collectors.toList());
        return hotBoardsDTO;
    }

    @Override
    public List<BoardDTO> getMyBoardList(String major) {
        if(major==null){
            List<Board> Boards = boardRepository.findTop10ByOrderByIdDesc();
            List<BoardDTO> BoardDTOs = Boards.stream()
                    .map(board -> entityToDTO(board))
                    .collect(Collectors.toList());
            return BoardDTOs;
        }

        Pageable pageable = PageRequest.of(0, 10); // 첫 번째 페이지, 10개씩
        List<Board> Boards = boardRepository.findByMajorNameExcludingNotices(major,pageable);
        List<BoardDTO> BoardDTOs = Boards.stream()
                .map(board -> entityToDTO(board))
                .collect(Collectors.toList());
            return BoardDTOs;
    }


}
