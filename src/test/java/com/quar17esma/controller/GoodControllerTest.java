package com.quar17esma.controller;

import com.quar17esma.configuration.AppConfig;
import com.quar17esma.model.Good;
import com.quar17esma.service.GoodService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class GoodControllerTest {
    private MockMvc mockMvc;

    @Mock
    private GoodService goodServiceMock;

    @Mock
    private UserController userControllerMock;

    @Mock
    private MessageSource messageSourceMock;

    @InjectMocks
    private GoodController controller;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");

        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setValidator(validator)
                .setViewResolvers(viewResolver)
                .build();

        when(userControllerMock.getPrincipal()).thenReturn("johnny");
    }

    //    @Ignore
    @Test
    public void listGoods() throws Exception {

        Good firstGood = Good.builder()
                .id(1L)
                .name("Lorem")
                .description("Lorem ipsum")
                .price(200L)
                .quantity(34)
                .build();

        Good secondGood = Good.builder()
                .id(2L)
                .name("ipsum")
                .description("Lorem ipsum")
                .price(500L)
                .quantity(23)
                .build();

        when(goodServiceMock.findAll()).thenReturn(Arrays.asList(firstGood, secondGood));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("allGoods"))
                .andExpect(forwardedUrl("/WEB-INF/views/allGoods.jsp"))
                .andExpect(model().attribute("goods", hasSize(2)))
                .andExpect(model().attribute("goods", hasItem(
                        allOf(
                                hasProperty("id", is(1L)),
                                hasProperty("description", is("Lorem ipsum")),
                                hasProperty("name", is("Lorem")),
                                hasProperty("price", is(200L)),
                                hasProperty("quantity", is(34))
                        )
                )))
                .andExpect(model().attribute("goods", hasItem(
                        allOf(
                                hasProperty("id", is(2L)),
                                hasProperty("description", is("Lorem ipsum")),
                                hasProperty("name", is("ipsum")),
                                hasProperty("price", is(500L)),
                                hasProperty("quantity", is(23))
                        )
                )));
        verify(goodServiceMock, times(1)).findAll();
        verifyNoMoreInteractions(goodServiceMock);
    }

    //    @Ignore
    @Test
    public void saveNewGoodValidationFail() throws Exception {

        String name = StringUtils.repeat("a", 101);
        String description = StringUtils.repeat("a", 1001);
        Long price = -1L;
        Integer quantity = -1;
        Long id = 0L;

        mockMvc.perform(post("/new-good")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", name)
                .param("description", description)
                .param("price", String.valueOf(price))
                .param("quantity", String.valueOf(quantity))
                .requestAttr("good", new Good())
        )
                .andExpect(status().isOk())
                .andExpect(view().name("editGood"))
                .andExpect(forwardedUrl("/WEB-INF/views/editGood.jsp"))
                .andExpect(model().attributeHasFieldErrors("good", "name"))
                .andExpect(model().attributeHasFieldErrors("good", "description"))
                .andExpect(model().attributeHasFieldErrors("good", "price"))
                .andExpect(model().attributeHasFieldErrors("good", "quantity"))
                .andExpect(model().attributeErrorCount("good", 4))
                .andExpect(model().attribute("good", hasProperty("id", is(id))))
                .andExpect(model().attribute("good", hasProperty("name", is(name))))
                .andExpect(model().attribute("good", hasProperty("description", is(description))))
                .andExpect(model().attribute("good", hasProperty("price", is(price))))
                .andExpect(model().attribute("good", hasProperty("quantity", is(quantity))));

        verifyZeroInteractions(goodServiceMock);
        verifyZeroInteractions(messageSourceMock);
    }

    //    @Ignore
    @Test
    public void saveNewGoodSuccess() throws Exception {

        String name = StringUtils.repeat("a", 62);
        String description = StringUtils.repeat("a", 524);
        Long price = 1200L;
        Integer quantity = 35;
        Long id = 0L;
        Good good = new Good();

        mockMvc.perform(post("/new-good")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", name)
                .param("description", description)
                .param("price", String.valueOf(price))
                .param("quantity", String.valueOf(quantity))
                .requestAttr("good", good)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("successPage"))
                .andExpect(forwardedUrl("/WEB-INF/views/successPage.jsp"))
                .andExpect(model().attributeHasNoErrors("good"))
                .andExpect(model().attribute("good", hasProperty("id", is(id))))
                .andExpect(model().attribute("good", hasProperty("name", is(name))))
                .andExpect(model().attribute("good", hasProperty("description", is(description))))
                .andExpect(model().attribute("good", hasProperty("price", is(price))))
                .andExpect(model().attribute("good", hasProperty("quantity", is(quantity))));

        verify(goodServiceMock, times(1)).save(good);
        verifyNoMoreInteractions(goodServiceMock);
        verify(messageSourceMock, times(1))
                .getMessage(matches("success.good.added"), any(), any());
        verifyNoMoreInteractions(messageSourceMock);
    }

    @Test
    public void buyGoodEntityNotFoundException() throws Exception {
        Long goodId = 13L;
        when(goodServiceMock.findById(goodId)).thenThrow(new EntityNotFoundException());
        when(messageSourceMock.getMessage(matches("fail.good.find"), any(), any()))
                .thenReturn("Test fail message");

        mockMvc.perform(get("/buy-good-{goodId}", goodId)
                .requestAttr("goodId", goodId)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("failPage"))
                .andExpect(forwardedUrl("/WEB-INF/views/failPage.jsp"))
                .andExpect(model().attributeExists("failMessage"))
                .andExpect(model().attributeDoesNotExist("good"));

        verify(goodServiceMock, times(1)).findById(goodId);
        verifyNoMoreInteractions(goodServiceMock);
        verify(messageSourceMock, times(1))
                .getMessage(matches("fail.good.find"), any(), any());
        verifyNoMoreInteractions(messageSourceMock);
    }
}