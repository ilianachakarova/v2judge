package judgev2.demo.web.controller;

import judgev2.demo.domain.model.binding.RoleAddBindingModel;
import judgev2.demo.domain.model.service.RoleServiceModel;
import judgev2.demo.domain.model.service.UserServiceModel;
import judgev2.demo.service.RoleService;
import judgev2.demo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/roles")
public class RoleController extends BaseController{
    private final UserService userService;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    @Autowired
    public RoleController(UserService userService, RoleService roleService, ModelMapper modelMapper) {
        this.userService = userService;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/add")
    public ModelAndView addRole(ModelAndView modelAndView, @ModelAttribute("model")RoleAddBindingModel model){
        List<String>users = this.userService.findAllUsers().stream()
                .map(UserServiceModel::getUsername).collect(Collectors.toList());
        modelAndView.addObject("users",users);
        return super.view("add-role", modelAndView);
    }

    @PostMapping("/add")
    public ModelAndView confirmAddRole(ModelAndView modelAndView,@ModelAttribute("model")RoleAddBindingModel model){
        RoleServiceModel roleServiceModel = this.roleService.findByAuthority(model.getRole());


        this.userService.setUserRole(roleServiceModel,model.getUsername());
        return super.redirect("/home");
    }
}
