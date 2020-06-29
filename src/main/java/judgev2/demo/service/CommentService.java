package judgev2.demo.service;

import judgev2.demo.domain.entity.User;
import judgev2.demo.domain.model.binding.AddCommentBindingModel;
import judgev2.demo.domain.model.service.CommentServiceModel;
import judgev2.demo.domain.model.service.UserServiceModel;

import java.util.HashMap;
import java.util.List;

public interface CommentService {
    CommentServiceModel saveComment(AddCommentBindingModel addCommentBindingModel, UserServiceModel userServiceModel);

    Double getAvg();

    HashMap<Integer,Integer> getScoreMap();

    List<User>findUsersByScore();
}
