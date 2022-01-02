package db.coursework.services;

import db.coursework.entities.Role;
import db.coursework.entities.User;
import db.coursework.repositories.RoleRepository;
import db.coursework.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;


    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.debug("User {} not found in DB", username);
            throw new UsernameNotFoundException("User not found in DB");
        }
        log.debug("User {} found in DB", username);
        return user;
    }

    public boolean saveUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            log.debug("User {} already exists in DB", user.getUsername());
            return false;
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        log.debug("User {} saved in DB", user.getUsername());
        return true;
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
