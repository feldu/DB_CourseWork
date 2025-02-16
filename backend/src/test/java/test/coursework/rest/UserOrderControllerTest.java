package test.coursework.rest;


import db.coursework.DbCourseworkApplication;
import db.coursework.controllers.UserOrderController;
import db.coursework.entities.FutureJobType;
import db.coursework.entities.Human;
import db.coursework.entities.Order;
import db.coursework.entities.User;
import db.coursework.entities.enums.FutureJobTypeName;
import db.coursework.entities.enums.OrderCaste;
import db.coursework.services.OrderService;
import db.coursework.services.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import test.coursework.TestContainerStarter;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {DbCourseworkApplication.class})
@RequiredArgsConstructor
public class UserOrderControllerTest extends TestContainerStarter {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private OrderService orderService;

    private Human mockHuman;
    private User mockUser;
    private Order mockOrder;

    @BeforeEach
    public void setup() {
        // Настройка мока Human
        mockHuman = new Human();
        mockHuman.setId(1L);
        mockHuman.setFullname("test test");

        // Настройка мока User
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("test");
        mockUser.setPassword("test");
        mockUser.setHuman(mockHuman);

        // Настройка мока Order
        mockOrder = new Order();
        mockOrder.setId(1L);
        mockOrder.setHuman(mockHuman);
        mockOrder.setHumanNumber(10);
        mockOrder.setCaste(OrderCaste.Alpha);
        mockOrder.setFutureJobTypes(Collections.singletonList(new FutureJobType(FutureJobTypeName.HIGH_TEMP)));
        mockOrder.setProcessing(false);
    }

    @Test
    @WithMockUser(username = "test", roles = {"PREDETERMINER"})
    public void testAddOrder() throws Exception {
        // Мокаем поведение UserService и OrderService
        when(userService.loadUserByUsername(anyString())).thenReturn(mockUser);
        when(orderService.saveOrderFromRequest(
                any(Human.class), anyInt(), anyString(), anyList()
        )).thenReturn(mockOrder);

        String orderDtoJson = "{\"id\":1,\"humanNumber\":10,\"caste\":\"ALPHA\",\"futureJobTypes\":[\"Manager\"]}";

        // Выполняем POST-запрос
        mockMvc.perform(post("/user/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderDtoJson)) // Ваш JSON для отправки
                .andExpect(status().isOk()); // Проверяем статус ответа (200 OK)
//                .andExpect(jsonPath("$.id").value(mockOrder.getId()));
    }

    @Test
    @WithMockUser(username = "test_user", roles = {"PREDETERMINER"})
    public void testGetOrders() throws Exception {
        // Мокаем поведение UserService и OrderService
        when(userService.loadUserByUsername(anyString())).thenReturn(mockUser);
        when(orderService.findAllOrdersByHuman(any(Human.class))).thenReturn(List.of(mockOrder));

        // Выполняем GET-запрос
        mockMvc.perform(get("/user/orders"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$[0].id").value(mockOrder.getId()))
                .andExpect((ResultMatcher) jsonPath("$[0].humanNumber").value(mockOrder.getHumanNumber()))
                .andExpect((ResultMatcher) jsonPath("$[0].caste").value(mockOrder.getCaste().name()))
                .andExpect((ResultMatcher) jsonPath("$[0].futureJobTypes[0]").value("Manager"))
                .andExpect((ResultMatcher) jsonPath("$[0].processing").value(mockOrder.isProcessing()));
    }
}