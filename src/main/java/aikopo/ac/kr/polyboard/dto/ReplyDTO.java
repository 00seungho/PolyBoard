package aikopo.ac.kr.polyboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReplyDTO {
    private Long id;
    private String content;
    private String replyer;
    private Long bno;
    private LocalDateTime regDate,modDate;
}
