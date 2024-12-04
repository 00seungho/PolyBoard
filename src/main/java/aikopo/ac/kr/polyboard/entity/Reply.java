package aikopo.ac.kr.polyboard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Reply extends BaseTime{
    @Id
    @GeneratedValue
    private Long id;

    private String content;

    @ManyToOne()
    @JoinColumn(name = "board_id", referencedColumnName = "id")
    private Board board;

    @ManyToOne()
    @JoinColumn(referencedColumnName = "id")
    private Member member;

    public void changeContent(String content) {
        this.content = content;
    }
}
