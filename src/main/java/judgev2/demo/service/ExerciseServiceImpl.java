package judgev2.demo.service;

import judgev2.demo.domain.entity.Exercise;
import judgev2.demo.domain.model.service.ExerciseServiceModel;
import judgev2.demo.repository.ExerciseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ExerciseServiceImpl implements ExerciseService{
    private final ModelMapper modelmapper;
    private final ExerciseRepository exerciseRepository;

    public ExerciseServiceImpl(ModelMapper modelmapper, ExerciseRepository exerciseRepository) {
        this.modelmapper = modelmapper;
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public ExerciseServiceModel addExercise(ExerciseServiceModel model) {
        Exercise exercise = this.modelmapper.map(model, Exercise.class);
       Exercise entity = this.exerciseRepository.save(exercise);

        return this.modelmapper.map(entity, ExerciseServiceModel.class);
    }

    @Override
    public List<ExerciseServiceModel> findAllExercises() {
        return this.exerciseRepository.findAll().stream()
                .map(exercise -> this.modelmapper.map(exercise,ExerciseServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ExerciseServiceModel findExerciseById(String id) {
        return this.modelmapper.map(this.exerciseRepository.findById(id),ExerciseServiceModel.class);
    }

    @Override
    public ExerciseServiceModel findExerciseByName(String name) {
      return this.modelmapper.map(this.exerciseRepository.findByName(name),ExerciseServiceModel.class);
    }
}
