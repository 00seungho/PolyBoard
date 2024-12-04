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
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "board_id"})
)
public class LikeDislike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
    private Member member; // 좋아요/싫어요를 한 사용자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", referencedColumnName = "id", nullable = false)
    private Board board; // 좋아요/싫어요를 받은 게시글

    @Column(nullable = false)
    private Boolean likeStatus; // true: 좋아요, false: 싫어요

    public void changeLikeStatus(Boolean likeStatus){
        this.likeStatus = likeStatus;
    }
}
