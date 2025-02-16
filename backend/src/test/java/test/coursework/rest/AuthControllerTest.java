package test.coursework.rest;

import db.coursework.DbCourseworkApplication;
import db.coursework.dto.UserDTO;
import db.coursework.entities.Role;
import db.coursework.entities.User;
import db.coursework.services.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import test.coursework.TestContainerStarter;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static test.coursework.constants.ApiConstants.AUTH;
import static test.coursework.constants.ApiConstants.SINGUP;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {DbCourseworkApplication.class})
@RequiredArgsConstructor
public class AuthControllerTest extends TestContainerStarter {

    @Autowired
    private MockMvc mockMvc;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = Mockito.mock(UserService.class);
    }

    @Test
    public void testRegisterUserSuccess() throws Exception {
        // Исходные данные для нового предопределителя
        UserDTO userDTO = UserDTO.builder()
                .username("testuser")
                .fullname("test user")
                .password("password")
                .role("PREDETERMINER")
                .build();

        // Подготовка мока для возвращаемых значений
        Role role = new Role("PREDETERMINER");
        when(userService.saveRoleByName("PREDETERMINER")).thenReturn(role);
        when(userService.saveUser(any(User.class))).thenReturn(true);

        // Выполнение POST-запроса
        mockMvc.perform(post(AUTH + SINGUP)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fullname\":\"Test User\",\"username\":\"testuser\",\"password\":\"password\",\"role\":\"DEFAULT_ROLE\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Пользователь зарегистрирован. Войдите :)")));
    }

    @Test
    public void testRegisterUserAlreadyExists() throws Exception {
        // Исходные данные для нового предопределителя
        UserDTO userDTO = UserDTO.builder()
                .username("testuser")
                .fullname("test user")
                .password("password")
                .role("PREDETERMINER")
                .build();

        // Подготовка мока для возвращаемых значений
        Role role = new Role("PREDETERMINER");
        when(userService.saveRoleByName("PREDETERMINER")).thenReturn(role);
        when(userService.saveUser(any(User.class))).thenReturn(false); // Пользователь уже существует

        // Выполнение POST-запроса
        mockMvc.perform(post(AUTH + SINGUP)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fullname\":\"Test User\",\"username\":\"testuser\",\"password\":\"password\",\"role\":\"DEFAULT_ROLE\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Пользователь с таким именем уже существует")));
    }
}