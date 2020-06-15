package judgev2.demo.service;

import judgev2.demo.domain.entity.Exercise;
import judgev2.demo.domain.entity.Homework;
import judgev2.demo.domain.entity.User;
import judgev2.demo.domain.model.binding.HomeworkAddBindingModel;
import judgev2.demo.domain.model.service.HomeworkServiceModel;
import judgev2.demo.repository.ExerciseRepository;
import judgev2.demo.repository.HomeworkRepository;
import judgev2.demo.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Transactional
@Service
public class HomeworkServiceImpl implements HomeworkService{
    private final HomeworkRepository homeworkRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ExerciseService exerciseService;
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;
    @Autowired
    public HomeworkServiceImpl(HomeworkRepository homeworkRepository, UserService userService, ModelMapper modelMapper, ExerciseService exerciseService, ExerciseRepository exerciseRepository, UserRepository userRepository) {
        this.homeworkRepository = homeworkRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.exerciseService = exerciseService;
        this.exerciseRepository = exerciseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<HomeworkServiceModel> findAllHomework() {

        return this.homeworkRepository.findAll().stream()
                .map(h->this.modelMapper.map(h,HomeworkServiceModel.class)).collect(Collectors.toList());
    }

    @Override

    public HomeworkServiceModel addHomework(HomeworkAddBindingModel homeworkServiceModel, String username) {
        User user = this.modelMapper.map(this.userService.loadUserByUsername(username),User.class);
        if(user==null){
            user = this.userRepository.save(user);
        }
        Exercise exercise = this.modelMapper.map(this.exerciseService.
                findExerciseByName(homeworkServiceModel.getExercise()),Exercise.class);
        if(exercise==null){
            exercise = this.exerciseRepository.save(exercise);
        }
        Homework homework = this.modelMapper.map(homeworkServiceModel,Homework.class);
        //homework.setGitAddress(homeworkServiceModel.getGitAddress());
        homework.setExercise(exercise);
        homework.setAddedOn(LocalDateTime.now());
        homework.setAuthor(user);
       this.homeworkRepository.saveAndFlush(homework);
        return this.modelMapper.map(homework, HomeworkServiceModel.class);
    }
}
