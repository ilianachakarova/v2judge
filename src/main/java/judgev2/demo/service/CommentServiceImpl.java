package judgev2.demo.service;

import judgev2.demo.domain.entity.Comment;
import judgev2.demo.domain.entity.Homework;
import judgev2.demo.domain.entity.User;
import judgev2.demo.domain.model.binding.AddCommentBindingModel;
import judgev2.demo.domain.model.service.CommentServiceModel;
import judgev2.demo.domain.model.service.UserServiceModel;
import judgev2.demo.repository.CommentRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final ModelMapper modelMapper;
    private final CommentRepo commentRepo;
    private final HomeworkService homeworkService;
    private final UserService userService;
    @Autowired
    public CommentServiceImpl(ModelMapper modelMapper, CommentRepo commentRepo, HomeworkService homeworkService, UserService userService) {
        this.modelMapper = modelMapper;
        this.commentRepo = commentRepo;
        this.homeworkService = homeworkService;
        this.userService = userService;
    }

    @Override
    public CommentServiceModel saveComment(AddCommentBindingModel addCommentBindingModel, UserServiceModel userServiceModel) {
        Homework homework = this.homeworkService.findById(addCommentBindingModel.getHomeworkId());
        User user = this.modelMapper.map(userServiceModel,User.class);
        CommentServiceModel commentServiceModel = this.modelMapper.map(addCommentBindingModel,CommentServiceModel.class);
        commentServiceModel.setHomework(homework);
        commentServiceModel.setAuthor(user);
        Comment commentToSave = this.modelMapper.map(commentServiceModel,Comment.class);
        return this.modelMapper.map(this.commentRepo.save(commentToSave),CommentServiceModel.class);
    }

    @Override
    public Double getAvg() {
        return this.commentRepo.findAll()
                .stream().mapToDouble(Comment::getScore)
                .average()
                .orElse(0D);
    }

    @Override
    public HashMap<Integer, Integer> getScoreMap() {
        HashMap<Integer,Integer>map = new HashMap<>();
        this.commentRepo.findAll().stream()
                .forEach(c->{
                    int score = c.getScore();
                    map.put(score,
                            map.containsKey(score) ? map.get(score)+1 : 1);
                });
        System.out.println();
        return map;
    }

    @Override
    public List<User> findUsersByScore() {
        return this.commentRepo.findAllOrOrderByScore();
    }
}
