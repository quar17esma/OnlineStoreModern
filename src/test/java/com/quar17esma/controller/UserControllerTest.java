package com.quar17esma.controller;

import com.quar17esma.configuration.AppConfig;
import com.quar17esma.enums.UserProfileType;
import com.quar17esma.model.User;
import com.quar17esma.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    @Mock
    private AuthenticationTrustResolver authenticationTrustResolver;

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
        return User.builder()
                .id(10L)
                .email("test@gmail.com")
                .firstName("John")
                .lastName("Smith")
                .password("password")
                .userProfileType(UserProfileType.USER)
                .build();
    }

    @Test
    public void loginPage() throws Exception {
        when(authenticationTrustResolver.isAnonymous(any())).thenReturn(true);

        mockMvc.perform(
                get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(forwardedUrl("/WEB-INF/views/templates/login.html"));
    }

    @Test
    public void loginPageAuthenticationIsNotAnonymous() throws Exception {
        when(authenticationTrustResolver.isAnonymous(any())).thenReturn(false);

        mockMvc.perform(
                get("/login"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/goods/list"));
    }

    @Test
    public void newUser() throws Exception {
        mockMvc.perform(
                get("/new_user"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"))
                .andExpect(forwardedUrl("/WEB-INF/views/templates/registration.html"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", hasProperty("id", is(nullValue()))))
                .andExpect(model().attribute("user", hasProperty("firstName", is(nullValue()))))
                .andExpect(model().attribute("user", hasProperty("lastName", is(nullValue()))))
                .andExpect(model().attribute("user", hasProperty("email", is(nullValue()))))
                .andExpect(model().attribute("user", hasProperty("userProfileType", is(UserProfileType.USER))));
    }

    @Test
    public void saveUser() throws Exception {
        User user = createTestUser();
        user.setId(null);
        when(userService.isEmailBusy(user.getEmail())).thenReturn(false);
        when(messageSourceMock.getMessage(matches("success.user.register"), any(), any()))
                .thenReturn("Test success message");
        doReturn(user.getEmail()).when(controller).getPrincipal();


        mockMvc.perform(
                post("/new_user")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("firstName", user.getFirstName())
                        .param("lastName", user.getLastName())
                        .param("email", user.getEmail())
                        .param("password", user.getPassword()))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(forwardedUrl("/WEB-INF/views/templates/login.html"))
                .andExpect(model().attributeHasNoErrors("user"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("successRegistrationMessage"))
                .andExpect(model().attribute("loggedinuser", is(user.getEmail())));
        verify(userService, times(1)).save(user);
    }

    @Test
    public void saveUserValidationFail() throws Exception {
        User user = createTestUserWrongData();

        mockMvc.perform(
                post("/new_user")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("firstName", user.getFirstName())
                        .param("lastName", user.getLastName())
                        .param("email", user.getEmail())
                        .param("password", user.getPassword()))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"))
                .andExpect(forwardedUrl("/WEB-INF/views/templates/registration.html"))
                .andExpect(model().attributeHasFieldErrors("user", "firstName"))
                .andExpect(model().attributeHasFieldErrors("user", "lastName"))
                .andExpect(model().attributeHasFieldErrors("user", "email"))
                .andExpect(model().attributeHasFieldErrors("user", "password"))
                .andExpect(model().attributeErrorCount("user", 4))
                .andExpect(model().attributeDoesNotExist("successRegistrationMessage"))
                .andExpect(model().attributeDoesNotExist("loggedinuser"));
        verify(userService, never()).isEmailBusy(anyString());
        verify(userService, never()).save(user);
    }

    private User createTestUserWrongData() {
        return User.builder()
                .id(null)
                .email("testgmail.com")
                .firstName(StringUtils.repeat("a", 51))
                .lastName(StringUtils.repeat("a", 51))
                .password(StringUtils.repeat("a", 101))
                .userProfileType(UserProfileType.USER)
                .build();
    }

    @Test
    public void saveUserEmailBusyFail() throws Exception {
        User user = createTestUser();
        user.setId(null);
        when(userService.isEmailBusy(user.getEmail())).thenReturn(true);
        when(messageSourceMock.getMessage(matches("error.busy.email"), any(), any()))
                .thenReturn("Test error message");

        mockMvc.perform(
                post("/new_user")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("firstName", user.getFirstName())
                        .param("lastName", user.getLastName())
                        .param("email", user.getEmail())
                        .param("password", user.getPassword()))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"))
                .andExpect(forwardedUrl("/WEB-INF/views/templates/registration.html"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeHasFieldErrors("user", "email"))
                .andExpect(model().attributeErrorCount("user", 1))
                .andExpect(model().attributeDoesNotExist("successRegistrationMessage"))
                .andExpect(model().attributeDoesNotExist("loggedinuser"));
        verify(userService, times(1)).isEmailBusy(user.getEmail());
        verify(userService, never()).save(user);
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

}