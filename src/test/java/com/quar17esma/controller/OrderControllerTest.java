package com.quar17esma.controller;

import com.quar17esma.configuration.AppConfig;
import com.quar17esma.enums.OrderStatus;
import com.quar17esma.enums.UserProfileType;
import com.quar17esma.model.Order;
import com.quar17esma.model.User;
import com.quar17esma.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class OrderControllerTest {
    private MockMvc mockMvc;

    @Mock
    private OrderService orderServiceMock;
    @Mock
    private UserController userControllerMock;
    @Mock
    private MessageSource messageSourceMock;

    @InjectMocks
    private OrderController controller;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/templates/");
        viewResolver.setSuffix(".html");

        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setValidator(validator)
                .setViewResolvers(viewResolver)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void cart() throws Exception {
        Order order = createTestOrder();

        mockMvc.perform(
                get("/orders/cart")
                        .sessionAttr("order", order))
                .andExpect(status().isOk())
                .andExpect(view().name("cart"))
                .andExpect(forwardedUrl("/WEB-INF/views/templates/cart.html"))
                .andExpect(model().attribute("order", is(order)));
    }

    private Order createTestOrder() {
        return Order.builder()
                    .id(13L)
                    .orderedAt(LocalDateTime.now())
                    .status(OrderStatus.NEW)
                    .user(createTestUser())
                    .build();
    }

    private User createTestUser() {
        return  User.builder()
                .id(10L)
                .email("test@gmail.com")
                .firstName("John")
                .lastName("Smith")
                .userProfileType(UserProfileType.USER)
                .build();
    }

    @Test
    public void confirmOrderFromCart() throws Exception {
        Order order = createTestOrder();
        when(messageSourceMock.getMessage(matches("success.order.confirm"), any(), any()))
                .thenReturn("Test success message");

        mockMvc.perform(
                get("/orders/cart/confirm")
                        .sessionAttr("order", order))
                .andExpect(status().isOk())
                .andExpect(view().name("message"))
                .andExpect(forwardedUrl("/WEB-INF/views/templates/message.html"))
                .andExpect(model().attributeExists("successMessage"))
                .andExpect(model().attributeDoesNotExist("order"));
        verify(orderServiceMock, times(1)).confirmOrder(order);
    }

    @Test
    public void myOrders() throws Exception {
        User user = createTestUser();
        List<Order> orders = new ArrayList<>();
        when(userControllerMock.getUser()).thenReturn(user);
        when(orderServiceMock.findAllByUserIdFetchOrderedGoods(user.getId())).thenReturn(orders);

        mockMvc.perform(
                get("/orders/myOrders"))
                .andExpect(status().isOk())
                .andExpect(view().name("myOrders"))
                .andExpect(forwardedUrl("/WEB-INF/views/templates/myOrders.html"))
                .andExpect(model().attribute("orders", is(orders)));
        verify(userControllerMock, times(1)).getUser();
        verify(orderServiceMock, times(1)).findAllByUserIdFetchOrderedGoods(user.getId());
    }

    @Test
    public void payOrder() throws Exception {
        Long orderId = 13L;
        when(messageSourceMock.getMessage(matches("success.order.pay"), any(), any()))
                .thenReturn("Test success message");

        mockMvc.perform(
                get("/orders/myOrders/pay-{orderId}", orderId))
                .andExpect(status().isOk())
                .andExpect(view().name("message"))
                .andExpect(forwardedUrl("/WEB-INF/views/templates/message.html"))
                .andExpect(model().attributeExists("successMessage"));
        verify(orderServiceMock, times(1)).payOrder(orderId);
    }

    @Test
    public void cancelOrder() throws Exception {
        Order order = createTestOrder();
        order.setStatus(OrderStatus.CONFIRMED);

        when(orderServiceMock.findById(order.getId())).thenReturn(order);
        when(userControllerMock.getUser()).thenReturn(order.getUser());
        when(messageSourceMock.getMessage(matches("success.order.cancel"), any(), any()))
                .thenReturn("Test success message");

        mockMvc.perform(
                get("/orders/myOrders/cancel-{orderId}", order.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("message"))
                .andExpect(forwardedUrl("/WEB-INF/views/templates/message.html"))
                .andExpect(model().attributeExists("successMessage"))
                .andExpect(model().attributeDoesNotExist("failMessage"));
        verify(orderServiceMock, times(1)).cancelOrder(order.getId());
    }

    @Test
    public void cancelOrderWrongUser() throws Exception {
    }

    @Test
    public void cancelOrderWrongStatus() throws Exception {
        Order order = createTestOrder();
        order.setStatus(OrderStatus.NEW);

        when(orderServiceMock.findById(order.getId())).thenReturn(order);
        when(userControllerMock.getUser()).thenReturn(order.getUser());
        when(messageSourceMock.getMessage(matches("success.order.cancel"), any(), any()))
                .thenReturn("Test success message");
        when(messageSourceMock.getMessage(matches("fail.order.cancel"), any(), any()))
                .thenReturn("Test fail message");

        mockMvc.perform(
                get("/orders/myOrders/cancel-{orderId}", order.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("message"))
                .andExpect(forwardedUrl("/WEB-INF/views/templates/message.html"))
                .andExpect(model().attributeDoesNotExist("successMessage"))
                .andExpect(model().attributeExists("failMessage"));
        verify(orderServiceMock, never()).cancelOrder(order.getId());
    }

    @Test
    public void confirmOrderById() throws Exception {
        Long orderId = 13L;
        when(messageSourceMock.getMessage(matches("success.order.confirm"), any(), any()))
                .thenReturn("Test success message");

        mockMvc.perform(
                get("/orders/myOrders/confirm-{orderId}", orderId))
                .andExpect(status().isOk())
                .andExpect(view().name("message"))
                .andExpect(forwardedUrl("/WEB-INF/views/templates/message.html"))
                .andExpect(model().attributeExists("successMessage"));
        verify(orderServiceMock, times(1)).confirmOrder(orderId);
    }

}