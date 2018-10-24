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
import org.mockito.Spy;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class UserControllerTest {
    private MockMvc mockMvc;

    @Mock
    private UserService userService;
    @Mock
    private MessageSource messageSourceMock;

    @Spy
    @InjectMocks
    private UserController controller;

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
        doReturn(user.getEmail()).when(controller).getPrincipal();

        mockMvc.perform(
                get("/Access_Denied"))
                .andExpect(status().isOk())
                .andExpect(view().name("accessDenied"))
                .andExpect(forwardedUrl("/WEB-INF/views/templates/accessDenied.html"))
                .andExpect(model().attribute("userFirstName", is(user.getFirstName())));
        verify(userService, times(1)).findByEmail(user.getEmail());
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
    public void loginPage() throws Exception {
    }

    @Test
    public void logoutPage() throws Exception {
    }

    @Test
    public void newUser() throws Exception {
    }

    @Test
    public void saveUser() throws Exception {
    }

    @Test
    public void showContactForm() throws Exception {
    }

    @Test
    public void showStores() throws Exception {
    }

}