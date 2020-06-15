package judgev2.demo.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller

public class HomeController extends BaseController{
    @GetMapping("/")
    @PreAuthorize("isAnonymous()")
    public ModelAndView index(){
        return super.view("index");
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")

    public ModelAndView home(Principal principal, ModelAndView modelAndView){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        modelAndView.addObject("username", username);
        return super.view("home",modelAndView);
    }
}
