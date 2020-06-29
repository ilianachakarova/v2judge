package judgev2.demo.service;

import judgev2.demo.domain.entity.Role;
import judgev2.demo.domain.entity.User;
import judgev2.demo.domain.model.service.RoleServiceModel;
import judgev2.demo.domain.model.service.UserServiceModel;
import judgev2.demo.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, ModelMapper modelMapper, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;

        this.modelMapper = modelMapper;
        this.encoder = encoder;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException("No such user"));
    }

    @Override
    public UserServiceModel registerUser(UserServiceModel userServiceModel) {
        if (this.userRepository.count() == 0) {
            userServiceModel.setAuthorities(new LinkedHashSet<>());
            userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_ADMIN"));
        } else {
            userServiceModel.setAuthorities(new LinkedHashSet<>());
            userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
        }
        User user = this.modelMapper.map(userServiceModel,User.class);
        user.setPassword(encoder.encode(userServiceModel.getPassword()));
        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
    }

    @Override
    public List<UserServiceModel> findAllUsers() {
        return this.userRepository.findAll().stream().map(user ->
                this.modelMapper.map(user,UserServiceModel.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void setUserRole(RoleServiceModel roleServiceModel, String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
        Role newRole = this.modelMapper.map(roleServiceModel, Role.class);
        user.setAuthorities(Set.of(newRole));
    }

    @Override
    public UserServiceModel findUserByUsername(String username) {
        User user =  this.userRepository.findByUsername(username).orElse(null);
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public Long getUsersCount() {
        return this.userRepository.count();
    }
}
