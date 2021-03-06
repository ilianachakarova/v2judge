package judgev2.demo.web.controller;

import judgev2.demo.domain.model.binding.AddCommentBindingModel;
import judgev2.demo.domain.model.binding.HomeworkAddBindingModel;
import judgev2.demo.domain.model.service.ExerciseServiceModel;
import judgev2.demo.domain.model.service.HomeworkServiceModel;
import judgev2.demo.domain.model.service.UserServiceModel;
import judgev2.demo.service.CommentService;
import judgev2.demo.service.ExerciseService;
import judgev2.demo.service.HomeworkService;
import judgev2.demo.service.UserService;
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
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/homework")
public class HomeworkController extends BaseController{
    private final ExerciseService exerciseService;
    private final HomeworkService homeworkService;
    private final ModelMapper modelMapper;
    private final CommentService commentService;
    private final UserService userService;
    @Autowired
    public HomeworkController(ExerciseService exerciseService, HomeworkService homeworkService, ModelMapper modelMapper, CommentService commentService, UserService userService) {
        this.exerciseService = exerciseService;
        this.homeworkService = homeworkService;
        this.modelMapper = modelMapper;
        this.commentService = commentService;
        this.userService = userService;
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

    @GetMapping("/check")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView checkHomework(ModelAndView modelAndView){
        HomeworkServiceModel homeworkServiceModel = this.homeworkService.findOneToCheck();

        modelAndView.addObject("homework",homeworkServiceModel);
        return super.view("homework-check",modelAndView);
    }

    @PostMapping("/check")
    public String checkConfirm(@Valid @ModelAttribute("addCommentBindingModel")AddCommentBindingModel addCommentBindingModel,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("addCommentBindingModel",addCommentBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addCommentBindingModel",bindingResult);
            return "redirect:/homework/check";
        }
        String username = principal.getName();
        UserServiceModel userServiceModel = this.userService.findUserByUsername(username);
        this.commentService.saveComment(addCommentBindingModel,userServiceModel);
        return "redirect:/home";

    }


}
