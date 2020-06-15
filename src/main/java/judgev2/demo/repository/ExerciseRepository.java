package judgev2.demo.repository;

import judgev2.demo.domain.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, String> {
    Exercise findByName(String name);
}
