package judgev2.demo.web.controller;

import judgev2.demo.domain.model.binding.UserAddBindingModel;
import judgev2.demo.domain.model.service.UserServiceModel;
import judgev2.demo.service.HomeworkService;
import judgev2.demo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final HomeworkService homeworkService;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper, HomeworkService homeworkService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.homeworkService = homeworkService;
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView register(@ModelAttribute("model")
                                         UserAddBindingModel model, BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 ModelAndView modelAndView
    ) {
        modelAndView.addObject("userAddBindingModel", model);
        return super.view("register", modelAndView);
    }

    @GetMapping("/profile/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView profile(@PathVariable("id") String id, HttpSession session, Principal principal, ModelAndView modelAndView) {
        UserServiceModel userServiceModel = this.userService.findUserByUsername(principal.getName());
        modelAndView.addObject("user", userServiceModel);
        return modelAndView;
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView login() {

        return super.view("login");
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(
            @Valid @ModelAttribute("model") UserAddBindingModel model,
            BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("model", model);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.model", bindingResult);
            return super.redirect("/users/register");
        } else {

            this.userService.registerUser(this.modelMapper.map(model, UserServiceModel.class));
            return super.view("/users/login");
        }

    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView profile(ModelAndView modelAndView, Principal principal) {
        String username = principal.getName();
        UserServiceModel userServiceModel = this.userService.findUserByUsername(username);
        modelAndView.addObject("user", userServiceModel);

        modelAndView.addObject("homeworkList", this.homeworkService.findAllHomeworkNamesByUser(userServiceModel));
        return super.view("profile", modelAndView);
    }

}
