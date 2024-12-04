package aikopo.ac.kr.polyboard.repository;

import aikopo.ac.kr.polyboard.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Long countByBoard_Id(Long id);

    @Modifying
    @Query("delete from Reply r where r.board.id =:id")
    void deleteByBoardId(Long id);

    List<Reply> findByBoard_IdOrderById(Long id);
    @Query("SELECT r, m.nickname FROM Reply r JOIN r.member m WHERE r.board.id = :boardId ORDER BY r.id DESC")
    Page<Object[]> findRepliesWithMemberNickname(Long boardId, Pageable pageable);

}
