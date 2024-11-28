package aikopo.ac.kr.polyboard.repository;

import aikopo.ac.kr.polyboard.entity.Board;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    //모든 공지 찾기
    List<Board> findByAllNoticeTrueOrderByIdDesc();
    //학과 공지만 찾기
    @Query("SELECT b FROM Board b " +
            "WHERE b.departmentNotice = true AND b.major.name = :majorName " +
            "ORDER BY b.id DESC")
    List<Board> findByDepartmentNoticeAndMajorName(@Param("majorName") String majorName);

    //학과내 게시글만 찾기
    @Query("SELECT b FROM Board b " +
            "WHERE b.major.name = :majorName " +
            "AND (b.allNotice = false AND b.departmentNotice = false) " +
            "ORDER BY b.id DESC")
    List<Board> findByMajorNameExcludingNotices(@Param("majorName") String majorName);


    //전체 글중 일주일간 가장 추천수가 많고 공지사항이 아닌 게시글 10개
    @Query("SELECT b FROM Board b " +
            "WHERE b.allNotice = false AND b.departmentNotice = false " +
            "AND b.regDate >= :startDate " +
            "ORDER BY b.likes DESC")
    List<Board> findByLikesInLastWeek(@Param("startDate") LocalDateTime startDate, Pageable pageable);

    //모든 공지 중 10개만
    List<Board> findTop10ByAllNoticeTrueOrderByIdDesc();

    //모든 게시물중 10개만
    @Query("SELECT b FROM Board b " +
            "WHERE b.major.name = :majorName " +
            "AND (b.allNotice = false AND b.departmentNotice = false) " +
            "ORDER BY b.id DESC")
    List<Board> findByMajorNameExcludingNotices(@Param("majorName") String majorName, Pageable pageable);

}
