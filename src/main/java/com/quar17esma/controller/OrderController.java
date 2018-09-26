package com.quar17esma.controller;

import com.quar17esma.model.Order;
import com.quar17esma.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/")
public class OrderController {
    @Autowired
    private UserController userController;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MessageSource messageSource;

    @ModelAttribute("localDateTimeFormat")
    public DateTimeFormatter getLocalDateTimeFormat () {
        return DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
    }

    @RequestMapping(value = {"/cart"}, method = RequestMethod.GET)
    public String cart(ModelMap model, HttpSession httpSession) {
        Order order = (Order) httpSession.getAttribute("order");
        model.addAttribute("order", order);

        return "cart";
    }

    @RequestMapping(value = {"/cart"}, method = RequestMethod.POST)
    public String confirmOrderFromCart(HttpSession httpSession, ModelMap model, Locale locale) {
        Order order = (Order) httpSession.getAttribute("order");
        orderService.confirmOrder(order.getId());
        httpSession.removeAttribute("order");
        model.addAttribute("success",
                messageSource.getMessage("success.order.confirm", new Object[] {}, locale));

        return "successPage";
    }

    @RequestMapping(value = {"/myOrders"}, method = RequestMethod.GET)
    public String myOrders(ModelMap model) {
        Long userId = userController.getUser().getId();
        List<Order> orders = orderService.findAllByUserIdFetchOrderedGoods(userId);
        model.addAttribute("orders", orders);

        return "myOrders";
    }

    @RequestMapping(value = {"/myOrders/pay-{orderId}"}, method = RequestMethod.GET)
    public String payOrder(@PathVariable("orderId") Long orderId, ModelMap model, Locale locale) {
        orderService.payOrder(orderId);
        model.addAttribute("success",
                messageSource.getMessage("success.order.pay", new Object[] {}, locale));

        return "successPage";
    }

    @RequestMapping(value = {"/myOrders/confirm-{orderId}"}, method = RequestMethod.GET)
    public String confirmOrderById(@PathVariable("orderId") Long orderId, ModelMap model, Locale locale) {
        orderService.confirmOrder(orderId);
        model.addAttribute("success",
                messageSource.getMessage("success.order.confirm", new Object[] {}, locale));

        return "successPage";
    }
}
