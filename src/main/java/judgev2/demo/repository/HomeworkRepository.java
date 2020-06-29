package judgev2.demo.repository;

import judgev2.demo.domain.entity.Homework;
import judgev2.demo.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework,String> {
    Homework findByGitAddress(String gitAddress);
    List<Homework>findAllByAuthor(User author);
}
