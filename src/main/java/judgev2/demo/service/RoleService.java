package judgev2.demo.service;

import judgev2.demo.domain.model.service.RoleServiceModel;

public interface RoleService {
    RoleServiceModel findByAuthority(String authority);
    void seedRolesInDB();
}
