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
    @Mock
    private HttpSession sessionMock;

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
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        when(userControllerMock.getPrincipal()).thenReturn("johnny");
    }

    //    @Ignore
    @Test
    public void listGoods() throws Exception {
        List<Good> goods = createDummyGoodsList();

        when(goodServiceMock.findAll()).thenReturn(goods);

        mockMvc.perform(get("/goods/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("allGoods"))
                .andExpect(forwardedUrl("/WEB-INF/views/allGoods.jsp"))
                .andExpect(model().attribute("goods", hasSize(goods.size())))
                .andExpect(model().attribute("goods", goods));
        verify(goodServiceMock, times(1)).findAll();
        verifyNoMoreInteractions(goodServiceMock);
    }

    private List<Good> createDummyGoodsList() {
        List<Good> goods = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            goods.add(
                    Good.builder()
                            .id(i)
                            .build()
            );
        }

        return goods;
    }

    //    @Ignore
    @Test
    public void showNewGoodForm() throws Exception {
        mockMvc.perform(get("/goods/new-good"))
                .andExpect(status().isOk())
                .andExpect(view().name("editGood"))
                .andExpect(forwardedUrl("/WEB-INF/views/editGood.jsp"))
                .andExpect(model().attribute("good", instanceOf(Good.class)))
                .andExpect(model().attribute("good", hasProperty("id", is(0L))))
                .andExpect(model().attribute("edit", false));
    }

    //    @Ignore
    @Test
    public void saveNewGoodValidationFail() throws Exception {
        String name = StringUtils.repeat("a", 101);
        String description = StringUtils.repeat("a", 1001);
        Long price = -1L;
        Integer quantity = -1;
        Long id = 0L;

        mockMvc.perform(
                post("/goods/new-good")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", name)
                        .param("description", description)
                        .param("price", String.valueOf(price))
                        .param("quantity", String.valueOf(quantity)))
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
    public void saveNewGood() throws Exception {
        Long id = 0L;
        String name = "Samsung s9";
        String description = "Very expensive phone";
        Long price = 1200L;
        Integer quantity = 35;
        Good good = Good.builder()
                .id(id)
                .name(name)
                .description(description)
                .price(price)
                .quantity(quantity)
                .build();

        mockMvc.perform(
                post("/goods/new-good")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", name)
                        .param("description", description)
                        .param("price", String.valueOf(price))
                        .param("quantity", String.valueOf(quantity)))
                .andExpect(status().isOk())
                .andExpect(view().name("successPage"))
                .andExpect(forwardedUrl("/WEB-INF/views/successPage.jsp"))
                .andExpect(model().attributeHasNoErrors("good"))
                .andExpect(model().attribute("good", is(good)));

        verify(goodServiceMock, times(1)).save(good);
        verifyNoMoreInteractions(goodServiceMock);
        verify(messageSourceMock, times(1))
                .getMessage(matches("success.good.added"), any(), any());
        verifyNoMoreInteractions(messageSourceMock);
    }

    //    @Ignore
    @Test
    public void showBuyGoodForm() throws Exception {
        Long goodId = 13L;
        Good good = Good.builder()
                .id(goodId)
                .build();
        when(goodServiceMock.findById(goodId)).thenReturn(good);

        mockMvc.perform(get("/goods/buy-good-{goodId}", goodId))
                .andExpect(status().isOk())
                .andExpect(view().name("buyNow"))
                .andExpect(forwardedUrl("/WEB-INF/views/buyNow.jsp"))
                .andExpect(model().attribute("good", is(good)));

        verify(goodServiceMock, times(1)).findById(goodId);
    }

    //    @Ignore
    @Test
    public void showBuyGoodFormEntityNotFoundException() throws Exception {
        Long goodId = 13L;
        when(goodServiceMock.findById(goodId)).thenThrow(new EntityNotFoundException());
        when(messageSourceMock.getMessage(matches("fail.good.find"), any(), any()))
                .thenReturn("Test fail message");

        mockMvc.perform(get("/goods/buy-good-{goodId}", goodId))
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

    //    @Ignore
    @Test
    public void addGoodToCart() throws Exception {
        Long goodId = 13L;
        Good good = Good.builder()
                .id(goodId)
                .name("Samsung s9")
                .build();
        int orderedQuantity = 5;
        Order order = new Order();
        when(sessionMock.getAttribute("order")).thenReturn(order);
        when(goodServiceMock.findById(goodId)).thenReturn(good);
        when(messageSourceMock.getMessage(matches("success.good.ordered"), any(), any()))
                .thenReturn("Test success message");

        mockMvc.perform(
                post("/goods/buy-good-{goodId}", goodId)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("orderedQuantity", String.valueOf(orderedQuantity)))
                .andExpect(status().isOk())
                .andExpect(view().name("successPage"))
                .andExpect(forwardedUrl("/WEB-INF/views/successPage.jsp"))
                .andExpect(model().attributeExists("success"))
                .andExpect(model().attributeDoesNotExist("errorNotEnoughGood"));
        verify(goodServiceMock, times(1)).addGoodToCart(order, goodId, orderedQuantity);
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
        when(sessionMock.getAttribute("order")).thenReturn(order);
        when(goodServiceMock.findById(goodId)).thenReturn(good);
        when(messageSourceMock.getMessage(matches("not.enough.good"), any(), any()))
                .thenReturn("Test error message");
        doThrow(new NotEnoughGoodException(goodId)).when(goodServiceMock).addGoodToCart(order, goodId, orderedQuantity);


        mockMvc.perform(
                post("/goods/buy-good-{goodId}", goodId)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("orderedQuantity", String.valueOf(orderedQuantity)))
                .andExpect(status().isOk())
                .andExpect(view().name("buyNow"))
                .andExpect(forwardedUrl("/WEB-INF/views/buyNow.jsp"))
                .andExpect(model().attributeExists("errorNotEnoughGood"))
                .andExpect(model().attribute("good", is(good)))
                .andExpect(model().attributeDoesNotExist("success"));
        verify(goodServiceMock, times(1)).addGoodToCart(order, goodId, orderedQuantity);
    }

    //    @Ignore
    @Test
    public void showEditGoodForm() throws Exception {
        Long goodId = 13L;
        Good good = Good.builder()
                .id(goodId)
                .build();
        when(goodServiceMock.findById(goodId)).thenReturn(good);

        mockMvc.perform(get("/goods/edit-good-{goodId}", goodId))
                .andExpect(status().isOk())
                .andExpect(view().name("editGood"))
                .andExpect(forwardedUrl("/WEB-INF/views/editGood.jsp"))
                .andExpect(model().attribute("good", is(good)))
                .andExpect(model().attribute("edit", true));
        verify(goodServiceMock, times(1)).findById(goodId);
    }
}