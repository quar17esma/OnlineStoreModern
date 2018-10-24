package com.quar17esma.controller;

import com.quar17esma.configuration.AppConfig;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class UserControllerTest {
    private MockMvc mockMvc;

    @Mock
    private MessageSource messageSourceMock;

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