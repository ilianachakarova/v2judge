package judgev2.demo.service;

import judgev2.demo.domain.entity.Role;
import judgev2.demo.domain.model.service.RoleServiceModel;
import judgev2.demo.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class RoleServiceImpl implements RoleService{
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public RoleServiceModel findByAuthority(String authority) {
        return this.roleRepository.findByAndAuthority(authority)
                .map(role -> this.modelMapper.map(role,RoleServiceModel.class))
                .orElseThrow(()->new IllegalArgumentException("No such role"));
    }

    @PostConstruct
    public void init(){
        if(this.roleRepository.count()==0){
            this.seedRolesInDB();
        }
    }

    @Override
    public void seedRolesInDB() {
        Role admin = new Role();
        admin.setAuthority("ROLE_ADMIN");
        this.roleRepository.save(admin);

        Role user = new Role();
        user.setAuthority("ROLE_USER");
        this.roleRepository.save(user);
    }
}
