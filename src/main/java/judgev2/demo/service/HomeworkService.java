package judgev2.demo.service;

import judgev2.demo.domain.model.binding.HomeworkAddBindingModel;
import judgev2.demo.domain.model.service.HomeworkServiceModel;

import java.util.List;

public interface HomeworkService {
    List<HomeworkServiceModel> findAllHomework();
    HomeworkServiceModel addHomework(HomeworkAddBindingModel homeworkAddBindingModel, String username);
}
