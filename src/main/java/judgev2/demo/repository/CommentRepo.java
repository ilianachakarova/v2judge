package judgev2.demo.repository;

import judgev2.demo.domain.entity.Comment;
import judgev2.demo.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment,String> {

    @Query(value = "select c.author from Comment as c group by c.homework.author order by avg(c.score) desc ")
    List<User>findAllOrOrderByScore();
}
