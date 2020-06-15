package judgev2.demo.web.controller;

import judgev2.demo.domain.model.binding.ExerciseAddBindingModel;
import judgev2.demo.domain.model.service.ExerciseServiceModel;
import judgev2.demo.service.ExerciseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/exercises")
public class ExerciseController extends BaseController{

    private final ExerciseService exerciseService;
    private final ModelMapper modelMapper;

    public ExerciseController(ExerciseService exerciseService, ModelMapper modelMapper) {
        this.exerciseService = exerciseService;
        this.modelMapper = modelMapper;
    }
    @GetMapping("/add")

    public ModelAndView addExercise(ModelAndView modelAndView,
                                    @Valid @ModelAttribute ExerciseAddBindingModel exerciseAddBindingModel,
                                    BindingResult result, RedirectAttributes redirectAttributes){

        return super.view("add-exercise");
    }

    @PostMapping("/add")
    public ModelAndView confirmAddExercise(ModelAndView modelAndView,
                                           @Valid @ModelAttribute ExerciseAddBindingModel exerciseAddBindingModel,
                                           BindingResult result, RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            redirectAttributes.addFlashAttribute("exerciseAddBindingModel",exerciseAddBindingModel);
            return super.redirect("/exercises/add");
        }

        this.exerciseService.addExercise
                (this.modelMapper.map(exerciseAddBindingModel, ExerciseServiceModel.class));
        return super.redirect("/home");

    }
}
