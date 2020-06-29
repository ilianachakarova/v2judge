package judgev2.demo.service;

import judgev2.demo.domain.entity.Homework;
import judgev2.demo.domain.model.binding.HomeworkAddBindingModel;
import judgev2.demo.domain.model.service.HomeworkServiceModel;
import judgev2.demo.domain.model.service.UserServiceModel;

import java.util.List;

public interface HomeworkService {
    List<HomeworkServiceModel> findAllHomework();
    HomeworkServiceModel addHomework(HomeworkAddBindingModel homeworkAddBindingModel, String username);
    List<HomeworkServiceModel> findAllByUser(UserServiceModel author);
    List<String>findAllHomeworkNamesByUser(UserServiceModel author);

    HomeworkServiceModel findOneToCheck();
    Homework findById(String id);
}
