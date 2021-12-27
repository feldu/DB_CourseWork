package db.coursework.services;

import db.coursework.entities.Role;
import db.coursework.repositories.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RoleService {
    final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role saveRole(String name) {
        name = "ROLE_" + name.toUpperCase(); //to ROLE_NAME format
        Role roleByName = roleRepository.findByName(name);
        if (roleByName != null) {
            return roleByName;
        }
        return roleRepository.save(new Role(name));
    }
}
