package judgev2.demo.web.controller;

import judgev2.demo.domain.model.binding.UserAddBindingModel;
import judgev2.demo.domain.model.service.UserServiceModel;
import judgev2.demo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/users")
public class UserController extends BaseController{

    private final UserService userService;
    private final ModelMapper modelMapper;
    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView register(@Valid @ModelAttribute("userAddBindingModel")
                                             UserAddBindingModel userAddBindingModel, ModelAndView modelAndView){
        modelAndView.addObject("userAddBindingModel",userAddBindingModel);
        return super.view("register",modelAndView);
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView login(){

        return super.view("login");
    }

    @PostMapping("/register")
    public String registerConfirm(
            @Valid @ModelAttribute ("model") UserAddBindingModel userAddBindingModel,
                                        final BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(!userAddBindingModel.getPassword().equals(userAddBindingModel.getConfirmPassword())){
            redirectAttributes.addFlashAttribute("model",userAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userAddBindingModel", bindingResult);
            return "redirect:/users/register";
        }
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("model",userAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userAddBindingModel", bindingResult);
            return "redirect:/users/register";
        }else {

            this.userService.registerUser(this.modelMapper.map(userAddBindingModel, UserServiceModel.class));
            return "redirect:/users/login";
        }

    }



}
