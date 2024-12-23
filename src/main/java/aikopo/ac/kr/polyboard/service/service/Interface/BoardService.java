package aikopo.ac.kr.polyboard.service.service.Interface;

import aikopo.ac.kr.polyboard.dto.*;
import aikopo.ac.kr.polyboard.entity.Board;
import aikopo.ac.kr.polyboard.entity.Member;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BoardService {
    //    새글을 등록하는 기능
    Long register(BoardDTO dto);

    //    각 게시판별 게시글 처리 로직
    PageResultDTO<BoardDTO, Object[]> getDepartmentList(PageRequestDTO pageRequestDTO);

    //    핫 글 게시판 처리 로직
    PageResultDTO<BoardDTO, Object[]> getHotBoard(PageRequestDTO pageRequestDTO);

    //   특정 게시글 하나를 조회하는 기능
    BoardDTO get(Long bno);

    //    삭제 기능
    void removeWithReplies(Long id);

    //    수정 기능
    Long modify(BoardDTO boardDTO);

    List<MajorListDTO> getMajorList();

    void addLike(LikeDislikeDTO likeDislikeDTO);
}