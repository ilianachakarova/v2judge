package judgev2.demo.service;

import judgev2.demo.domain.model.service.ExerciseServiceModel;

import java.util.List;

public interface ExerciseService {
    ExerciseServiceModel addExercise(ExerciseServiceModel model);
    List<ExerciseServiceModel>findAllExercises();
    ExerciseServiceModel findExerciseById(String id);
    ExerciseServiceModel findExerciseByName(String name);

    List<String> findAllExerciseNames();
}
