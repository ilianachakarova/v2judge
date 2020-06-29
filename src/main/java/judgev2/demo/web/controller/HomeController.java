package judgev2.demo.web.controller;

import judgev2.demo.domain.entity.User;
import judgev2.demo.service.CommentService;
import judgev2.demo.service.ExerciseService;
import judgev2.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller

public class HomeController extends BaseController{
    private final UserService userService;
    private final CommentService commentService;
    private final ExerciseService exerciseService;
    @Autowired
    public HomeController(UserService userService, CommentService commentService, ExerciseService exerciseService) {
        this.userService = userService;
        this.commentService = commentService;
        this.exerciseService = exerciseService;
    }

    @GetMapping("/")
//    @PreAuthorize("isAnonymous()")
    public ModelAndView index(){
        return super.view("index");
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")

    public ModelAndView home(Principal principal, ModelAndView modelAndView){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        modelAndView.addObject("username", username);
        modelAndView.addObject("total",this.userService.getUsersCount());
        modelAndView.addObject("avgGrade",this.commentService.getAvg());
        modelAndView.addObject("scoreMap",this.commentService.getScoreMap());
        modelAndView.addObject("activeEx",this.exerciseService.findAllExerciseNames());
        List<String> users =this.commentService.findUsersByScore().stream().map(User::getUsername).collect(Collectors.toList());
        modelAndView.addObject("bestStudents",users);
        return super.view("home",modelAndView);
    }
}
