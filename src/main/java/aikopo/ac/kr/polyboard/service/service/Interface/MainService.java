package aikopo.ac.kr.polyboard.service.service.Interface;

import aikopo.ac.kr.polyboard.dto.BoardDTO;
import aikopo.ac.kr.polyboard.entity.Board;

import java.util.List;

public interface MainService {

    List<BoardDTO> getNoticeList();
    List<BoardDTO> getHotBoardList();
    List<BoardDTO> getMyBoardList(String major);

}
