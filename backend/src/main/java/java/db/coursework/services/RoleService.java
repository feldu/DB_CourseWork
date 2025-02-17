package java.db.coursework.services;

import db.coursework.entities.Role;
import db.coursework.exception.DataNotFoundException;
import db.coursework.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(String name) {
        return roleRepository.findByName(name).orElseThrow(() -> new DataNotFoundException("Role with such name not found"));
    }
}
