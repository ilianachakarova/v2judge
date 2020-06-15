package judgev2.demo.web.controller;

import judgev2.demo.domain.model.binding.HomeworkAddBindingModel;
import judgev2.demo.domain.model.service.ExerciseServiceModel;
import judgev2.demo.service.ExerciseService;
import judgev2.demo.service.HomeworkService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/homework")
public class HomeworkController extends BaseController{
    private final ExerciseService exerciseService;
    private final HomeworkService homeworkService;
    private final ModelMapper modelMapper;
    @Autowired
    public HomeworkController(ExerciseService exerciseService, HomeworkService homeworkService, ModelMapper modelMapper) {
        this.exerciseService = exerciseService;
        this.homeworkService = homeworkService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addHomework(ModelAndView modelAndView, @ModelAttribute("model")HomeworkAddBindingModel model){
        List<String>exercises = this.exerciseService.findAllExercises()
                .stream()
                .map(ExerciseServiceModel::getName).collect(Collectors.toList());

        modelAndView.addObject("exercises",exercises);
        return super.view("add-homework",modelAndView);
    }

    @PostMapping("/add")
    public ModelAndView confirmAddHomework(ModelAndView modelAndView,
                                           @Valid@ModelAttribute("model")HomeworkAddBindingModel model,
                                           BindingResult bindingResult, RedirectAttributes redirectAttributes
    ){
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("model",model);
            return super.redirect("/homework/add");
        }
        this.homeworkService.addHomework(model,username);
        return super.view("/home",modelAndView);
    }
}
