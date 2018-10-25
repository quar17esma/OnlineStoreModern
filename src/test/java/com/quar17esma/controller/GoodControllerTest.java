package com.quar17esma.controller;

import com.quar17esma.configuration.AppConfig;
import com.quar17esma.exceptions.NotEnoughGoodException;
import com.quar17esma.model.Good;
import com.quar17esma.model.Order;
import com.quar17esma.service.GoodService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Ignore;
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
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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
        viewResolver.setPrefix("/WEB-INF/views/templates/");
        viewResolver.setSuffix(".html");

        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setValidator(validator)
                .setViewResolvers(viewResolver)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        when(userControllerMock.getPrincipal()).thenReturn("johnny");
    }

    @Test
    public void listGoods() throws Exception {
        List<Good> goods = createDummyGoodsList();

        when(goodServiceMock.findAll()).thenReturn(goods);

        mockMvc.perform(get("/goods/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("allGoods"))
                .andExpect(forwardedUrl("/WEB-INF/views/templates/allGoods.html"))
                .andExpect(model().attribute("goods", hasSize(goods.size())))
                .andExpect(model().attribute("goods", goods));
        verify(goodServiceMock, times(1)).findAll();
        verifyNoMoreInteractions(goodServiceMock);
    }

    private List<Good> createDummyGoodsList() {
        List<Good> goods = new ArrayList<>();
        for (long i = 1; i <= 20; i++) {
            goods.add(
                    Good.builder()
                            .id(i)
                            .build()
            );
        }

        return goods;
    }

    @Test
    public void showNewGoodForm() throws Exception {
        mockMvc.perform(get("/goods/new-good"))
                .andExpect(status().isOk())
                .andExpect(view().name("editGood"))
                .andExpect(forwardedUrl("/WEB-INF/views/templates/editGood.html"))
                .andExpect(model().attribute("good", instanceOf(Good.class)))
                .andExpect(model().attribute("good", hasProperty("id", is(nullValue()))))
                .andExpect(model().attribute("edit", false));
    }

    @Test
    public void saveNewGoodValidationFail() throws Exception {
        String name = StringUtils.repeat("a", 101);
        String description = StringUtils.repeat("a", 1001);
        Long price = -1L;
        Integer quantity = -1;
        Long id = null;

        mockMvc.perform(
                post("/goods/new-good")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", name)
                        .param("description", description)
                        .param("price", String.valueOf(price))
                        .param("quantity", String.valueOf(quantity)))
                .andExpect(status().isOk())
                .andExpect(view().name("editGood"))
                .andExpect(forwardedUrl("/WEB-INF/views/templates/editGood.html"))
                .andExpect(model().attributeHasFieldErrors("good", "name"))
                .andExpect(model().attributeHasFieldErrors("good", "description"))
                .andExpect(model().attributeHasFieldErrors("good", "price"))
                .andExpect(model().attributeHasFieldErrors("good", "quantity"))
                .andExpect(model().attributeErrorCount("good", 4))
                .andExpect(model().attribute("good", hasProperty("id", is(nullValue()))))
                .andExpect(model().attribute("good", hasProperty("name", is(name))))
                .andExpect(model().attribute("good", hasProperty("description", is(description))))
                .andExpect(model().attribute("good", hasProperty("price", is(price))))
                .andExpect(model().attribute("good", hasProperty("quantity", is(quantity))));

        verifyZeroInteractions(goodServiceMock);
        verifyZeroInteractions(messageSourceMock);
    }

    @Test
    public void saveNewGood() throws Exception {
        Good good = createTestGood();
        when(messageSourceMock.getMessage(matches("success.good.added"), any(), any()))
                .thenReturn("Test success message");

        mockMvc.perform(
                post("/goods/new-good")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", good.getName())
                        .param("description", good.getDescription())
                        .param("price", String.valueOf(good.getPrice()))
                        .param("quantity", String.valueOf(good.getQuantity())))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/message"))
                .andExpect(flash().attributeExists("successMessage"));
        verify(goodServiceMock, times(1)).save(good);
    }

    private Good createTestGood() {
        return Good.builder()
                    .id(null)
                    .name("Samsung s9")
                    .description("Very expensive phone")
                    .price(1200L)
                    .quantity(35)
                    .build();
    }

    @Test
    public void showBuyGoodForm() throws Exception {
        Good good = createTestGood();
        good.setId(13L);
        when(goodServiceMock.findById(good.getId())).thenReturn(good);

        mockMvc.perform(get("/goods/buy-good-{goodId}", good.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("buyGood"))
                .andExpect(forwardedUrl("/WEB-INF/views/templates/buyGood.html"))
                .andExpect(model().attribute("good", is(good)));

        verify(goodServiceMock, times(1)).findById(good.getId());
    }

    @Test
    public void showBuyGoodFormEntityNotFoundException() throws Exception {
        Long goodId = 13L;
        when(goodServiceMock.findById(goodId)).thenThrow(new EntityNotFoundException());
        when(messageSourceMock.getMessage(matches("fail.good.find"), any(), any()))
                .thenReturn("Test fail message");

        mockMvc.perform(get("/goods/buy-good-{goodId}", goodId))
                .andExpect(status().isOk())
                .andExpect(view().name("message"))
                .andExpect(forwardedUrl("/WEB-INF/views/templates/message.html"))
                .andExpect(model().attributeExists("failMessage"))
                .andExpect(model().attributeDoesNotExist("good"));

        verify(goodServiceMock, times(1)).findById(goodId);
        verifyNoMoreInteractions(goodServiceMock);
        verify(messageSourceMock, times(1))
                .getMessage(matches("fail.good.find"), any(), any());
        verifyNoMoreInteractions(messageSourceMock);
    }

    @Test
    public void addGoodToCart() throws Exception {
        Good good = createTestGood();
        good.setId(13L);
        int orderedQuantity = 5;
        Order order = new Order();
        when(goodServiceMock.findById(good.getId())).thenReturn(good);
        when(messageSourceMock.getMessage(matches("success.good.ordered"), any(), any()))
                .thenReturn("Test success message");

        mockMvc.perform(
                post("/goods/buy-good-{goodId}", good.getId())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("orderedQuantity", String.valueOf(orderedQuantity))
                        .sessionAttr("order", order))
                .andExpect(status().isOk())
                .andExpect(view().name("message"))
                .andExpect(forwardedUrl("/WEB-INF/views/templates/message.html"))
                .andExpect(model().attributeExists("successMessage"))
                .andExpect(model().attributeDoesNotExist("errorNotEnoughGood"));
        verify(goodServiceMock, times(1)).addGoodToCart(order, good.getId(), orderedQuantity);
    }

    @Ignore
    @Test
    public void addGoodToCartNotEnoughGoodException() throws Exception {
        Long goodId = 13L;
        Good good = Good.builder()
                .id(goodId)
                .name("Samsung s9")
                .build();
        int orderedQuantity = 5;
        Order order = new Order();
        when(goodServiceMock.findById(goodId)).thenReturn(good);
        when(messageSourceMock.getMessage(matches("not.enough.good"), any(), any()))
                .thenReturn("Test error message");
        doThrow(new NotEnoughGoodException(goodId)).when(goodServiceMock).addGoodToCart(order, goodId, orderedQuantity);


        mockMvc.perform(
                post("/goods/buy-good-{goodId}", goodId)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("orderedQuantity", String.valueOf(orderedQuantity))
                        .sessionAttr("order", order))
                .andExpect(status().isOk())
                .andExpect(view().name("buyNow"))
                .andExpect(forwardedUrl("/WEB-INF/views/templates/buyGood.html"))
                .andExpect(model().attributeExists("errorNotEnoughGood"))
                .andExpect(model().attribute("good", is(good)))
                .andExpect(model().attributeDoesNotExist("successMessage"));
        verify(goodServiceMock, times(1)).addGoodToCart(order, goodId, orderedQuantity);
    }

    @Test
    public void showEditGoodForm() throws Exception {
        Good good = createTestGood();
        good.setId(13L);
        when(goodServiceMock.findById(good.getId())).thenReturn(good);

        mockMvc.perform(get("/goods/edit-good-{goodId}", good.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("editGood"))
                .andExpect(forwardedUrl("/WEB-INF/views/templates/editGood.html"))
                .andExpect(model().attribute("good", is(good)))
                .andExpect(model().attribute("edit", true));
        verify(goodServiceMock, times(1)).findById(good.getId());
    }
}