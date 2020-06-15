package judgev2.demo.repository;

import judgev2.demo.domain.entity.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework,String> {
    Homework findByGitAddress(String gitAddress);
}
