package aikopo.ac.kr.polyboard.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class ReplyDTO {
    private Long id;
    private String content;
    private String writer;
    private Long boardId;
    private LocalDateTime regDate,modDate;
}
