package test.coursework.rest;

import db.coursework.DbCourseworkApplication;
import db.coursework.entities.Human;
import db.coursework.entities.Role;
import db.coursework.entities.User;
import db.coursework.repositories.HumanRepository;
import db.coursework.repositories.RoleRepository;
import db.coursework.repositories.UserRepository;
import db.coursework.services.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import test.coursework.TestContainerStarter;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static test.coursework.constants.ApiConstants.*;

@AutoConfigureMockMvc
@SpringBootTest(classes = {DbCourseworkApplication.class})
@RequiredArgsConstructor
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthControllerTest extends TestContainerStarter {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private Role role;
    private Human human;
    private User user;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HumanRepository humanRepository;

    @BeforeEach
    public void setup() {
        // Настройка мока Role
        role = new Role("ROLE_PREDETERMINER");

        // Настройка мока Human
        human = new Human();
        human.setId(1L);
        human.setFullname("test user");

        // Настройка мока User
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword(bCryptPasswordEncoder.encode("test"));
        user.setHuman(human);
    }

    @Test
    public void testRegisterUserSuccess() throws Exception {
        roleRepository.save(role);
        // Выполнение POST-запроса
        mockMvc.perform(post(AUTH + SINGUP)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fullname\":\"Test User\",\"username\":\"testuser\",\"password\":\"test\",\"role\":\"DEFAULT_ROLE\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Пользователь зарегистрирован. Войдите :)")));
    }

    @Test
    public void testRegisterUserAlreadyExists() throws Exception {
        roleRepository.save(role);
        userService.saveUser(user);

        // Выполнение POST-запроса
        mockMvc.perform(post(AUTH + SINGUP)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fullname\":\"Test User\",\"username\":\"testuser\",\"password\":\"test\",\"role\":\"DEFAULT_ROLE\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Пользователь с таким именем уже существует")));
    }
}