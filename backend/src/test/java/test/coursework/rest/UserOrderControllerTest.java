package test.coursework.rest;


import db.coursework.DbCourseworkApplication;
import db.coursework.entities.FutureJobType;
import db.coursework.entities.Human;
import db.coursework.entities.Order;
import db.coursework.entities.User;
import db.coursework.entities.enums.FutureJobTypeName;
import db.coursework.entities.enums.OrderCaste;
import db.coursework.repositories.FutureJobTypeRepository;
import db.coursework.repositories.HumanRepository;
import db.coursework.repositories.OrderRepository;
import db.coursework.services.HumanService;
import db.coursework.services.OrderService;
import db.coursework.services.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import test.coursework.TestContainerStarter;

import javax.validation.constraints.AssertTrue;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@AutoConfigureMockMvc
@SpringBootTest(classes = {DbCourseworkApplication.class})
@RequiredArgsConstructor
public class UserOrderControllerTest extends TestContainerStarter {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    private Human human;
    private User user;
    private Order order;
    private FutureJobType futureJobType;
    @Autowired
    private HumanService humanService;
    @Autowired
    private HumanRepository humanRepository;
    @Autowired
    private FutureJobTypeRepository futureJobTypeRepository;
    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    public void setup() {
        // Настройка мока Human
        human = new Human();
        human.setId(1L);
        human.setFullname("test");

        // Настройка мока User
        user = new User();
        user.setId(1L);
        user.setUsername("test");
        user.setPassword("test");
        user.setHuman(human);

        // Настройка мока FutureJobTypeName
        futureJobType = new FutureJobType(FutureJobTypeName.HIGH_TEMP);

        // Настройка мока Order
        order = new Order();
        order.setId(123L);
        order.setHuman(human);
        order.setHumanNumber(10);
        order.setCaste(OrderCaste.Alpha);
        order.setFutureJobTypes(Collections.singletonList(futureJobType));
        order.setProcessing(true);
    }

    @Test
    @WithMockUser(username = "test", roles = {"PREDETERMINER"})
    public void testAddOrder() throws Exception {
        futureJobTypeRepository.save(futureJobType);
        humanService.save(human);
        userService.saveUser(user);

        String orderDtoJson = "{\"id\":123,\"humanNumber\":10,\"caste\":\"Alpha\",\"futureJobTypes\":[\"HIGH_TEMP\"], \"isProcessing\": \"true\"}";

        mockMvc.perform(post("/user/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderDtoJson))
                .andExpect(status().isOk())
                .andExpect(result -> contains("123"));

        assertNotNull(orderRepository.findById(123L));
    }

    @Test
    @WithMockUser(username = "test_user", roles = {"PREDETERMINER"})
    public void testGetOrders() throws Exception {
        // Мокаем поведение UserService и OrderService
        when(userService.loadUserByUsername(anyString())).thenReturn(user);
        when(orderService.findAllOrdersByHuman(any(Human.class))).thenReturn(List.of(order));

        // Выполняем GET-запрос
        mockMvc.perform(get("/user/orders"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$[0].id").value(order.getId()))
                .andExpect((ResultMatcher) jsonPath("$[0].humanNumber").value(order.getHumanNumber()))
                .andExpect((ResultMatcher) jsonPath("$[0].caste").value(order.getCaste().name()))
                .andExpect((ResultMatcher) jsonPath("$[0].futureJobTypes[0]").value("HIGH_TEMP"))
                .andExpect((ResultMatcher) jsonPath("$[0].processing").value(order.isProcessing()));
    }
}