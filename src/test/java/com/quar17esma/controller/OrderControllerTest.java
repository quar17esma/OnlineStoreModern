package com.quar17esma.controller;

import com.quar17esma.configuration.AppConfig;
import com.quar17esma.enums.OrderStatus;
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
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.http.HttpSession;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
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
                    .user(User.builder().id(10L).email("test@gmail.com").build())
                    .build();
    }

    @Test
    public void confirmOrderFromCart() throws Exception {
    }

    @Test
    public void myOrders() throws Exception {
    }

    @Test
    public void payOrder() throws Exception {
    }

    @Test
    public void cancelOrder() throws Exception {
    }

    @Test
    public void confirmOrderById() throws Exception {
    }

}