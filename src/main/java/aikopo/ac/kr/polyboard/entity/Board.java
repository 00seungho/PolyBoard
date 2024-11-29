package aikopo.ac.kr.polyboard.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Board extends BaseTime{
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String content;

    private Integer likes;
    private Integer disLikes;

    private Boolean allNotice;
    private Boolean departmentNotice;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Member member;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Major major;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Reply reply;

    public void changeTitle(String title){
        this.title = title;
    }

    public void changeMajor(Major major){
        this.major = major;
    }

    public void changeContent(String content){
        this.content = content;
    }

}
