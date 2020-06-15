package judgev2.demo.service;

import judgev2.demo.domain.model.service.RoleServiceModel;
import judgev2.demo.domain.model.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserServiceModel registerUser(UserServiceModel userServiceModel);
    List<UserServiceModel>findAllUsers();
    void setUserRole(RoleServiceModel roleServiceModel, String username);
    UserServiceModel findUserByUsername(String username);
}
