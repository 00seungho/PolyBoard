package aikopo.ac.kr.polyboard.repository;

import aikopo.ac.kr.polyboard.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Long countByBoard_Id(Long id);

    @Modifying
    @Query("delete from Reply r where r.board.id =:id")
    void deleteByBno(Long id);
}
