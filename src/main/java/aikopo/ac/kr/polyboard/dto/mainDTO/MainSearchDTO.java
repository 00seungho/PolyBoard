package aikopo.ac.kr.polyboard.dto.mainDTO;

import aikopo.ac.kr.polyboard.dto.BoardDTO;
import aikopo.ac.kr.polyboard.entity.Board;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MainSearchDTO{
    private List<BoardDTO> allNotices;
    private List<BoardDTO> hotNotices;
    private List<BoardDTO> majorBoards;
    private String majorName;
}
