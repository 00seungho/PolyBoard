package aikopo.ac.kr.polyboard.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class BoardDTO {
    private Long id;
    private String title;
    private String content;
    private String writerName;
    private String writerEmail;

    private Integer likes;
    private Integer disLikes;

    private Boolean allNotice;
    private Boolean departmentNotice;

    private LocalDateTime regDate;
    private LocalDateTime modDate;

    private String writerMajor;
    private String boardMajor;

    private int replyCount;
}
