package com.quar17esma.controller;

import com.quar17esma.configuration.AppConfig;
import com.quar17esma.enums.UserProfileType;
import com.quar17esma.model.User;
import com.quar17esma.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class CommonControllerTest {
    private MockMvc mockMvc;
    @Mock
    private UserService userService;
    @Mock
    private UserController userController;
    @InjectMocks
    private CommonController controller;

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
    public void accessDeniedPage() throws Exception {
        User user = createTestUser();
        when(userService.findByEmail(user.getEmail())).thenReturn(user);
        doReturn(user.getEmail()).when(userController).getPrincipal();

        mockMvc.perform(
                get("/Access_Denied"))
                .andExpect(status().isOk())
                .andExpect(view().name("accessDenied"))
                .andExpect(forwardedUrl("/WEB-INF/views/templates/accessDenied.html"))
                .andExpect(model().attribute("userFirstName", is(user.getFirstName())));
        verify(userService, times(1)).findByEmail(user.getEmail());
    }

    @Test
    public void showContactForm() throws Exception {
        mockMvc.perform(
                get("/contact"))
                .andExpect(status().isOk())
                .andExpect(view().name("contact"))
                .andExpect(forwardedUrl("/WEB-INF/views/templates/contact.html"));

    }

    @Test
    public void showStores() throws Exception {
        mockMvc.perform(
                get("/stores"))
                .andExpect(status().isOk())
                .andExpect(view().name("stores"))
                .andExpect(forwardedUrl("/WEB-INF/views/templates/stores.html"));
    }

    @Test
    public void showMessage() throws Exception {
        mockMvc.perform(
                get("/message"))
                .andExpect(status().isOk())
                .andExpect(view().name("message"))
                .andExpect(forwardedUrl("/WEB-INF/views/templates/message.html"));
    }

    private User createTestUser() {
        return User.builder()
                .id(10L)
                .email("test@gmail.com")
                .firstName("John")
                .lastName("Smith")
                .password("password")
                .userProfileType(UserProfileType.USER)
                .build();
    }
}