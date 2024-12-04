package aikopo.ac.kr.polyboard.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ReplyRequestDTO {
    private String content;
    private Long boardId;
    private String memberWriter;
}