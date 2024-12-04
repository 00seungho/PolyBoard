package aikopo.ac.kr.polyboard.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class LikeDislikeDTO {
    private Long id;
    private Long boardId;
    private Long memberId;
    private Boolean likeStatus;
}
