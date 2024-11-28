package aikopo.ac.kr.polyboard.repository;

import aikopo.ac.kr.polyboard.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MajorRepository extends JpaRepository<Major, Integer> {
    Optional<Major> findByName(String name);
    boolean existsByName(String name); // 이름 존재 여부 확인

}
