package aikopo.ac.kr.polyboard.repository;

import aikopo.ac.kr.polyboard.entity.LikeDislike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LikeDislikeRepository extends JpaRepository<LikeDislike, Long> {
    Optional<LikeDislike> findByMemberIdAndBoardId(Long memberId, Long boardId);
    @Modifying
    @Query("delete from LikeDislike l where l.board.id =:id")
    void deleteByBoardId(Long id);
}
