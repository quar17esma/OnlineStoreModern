package com.quar17esma.controller;

import com.quar17esma.enums.OrderStatus;
import com.quar17esma.model.Order;
import com.quar17esma.model.User;
import com.quar17esma.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/orders")
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

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = {"/cart"}, method = RequestMethod.GET)
    public String cart(ModelMap model, HttpSession httpSession) {
        Order order = (Order) httpSession.getAttribute("order");
        model.addAttribute("order", order);

        return "cart";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = {"/cart/confirm"}, method = RequestMethod.GET)
    public String confirmOrderFromCart(HttpSession httpSession, ModelMap model, Locale locale) {
        Order order = (Order) httpSession.getAttribute("order");
        orderService.confirmOrder(order);
        httpSession.removeAttribute("order");
        model.addAttribute("successMessage",
                messageSource.getMessage("success.order.confirm", new Object[] {}, locale));

        return "message";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = {"/myOrders"}, method = RequestMethod.GET)
    public String myOrders(ModelMap model) {
        Long userId = userController.getUser().getId();
        List<Order> orders = orderService.findAllByUserIdFetchOrderedGoods(userId);
        model.addAttribute("orders", orders);

        return "myOrders";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PreAuthorize("@orderService.findById(#orderId).user.email.equals(authentication.principal.username)")
    @RequestMapping(value = {"/myOrders/pay-{orderId}"}, method = RequestMethod.GET)
    public String payOrder(@PathVariable("orderId") Long orderId, ModelMap model, Locale locale) {
        orderService.payOrder(orderId);
        model.addAttribute("successMessage",
                messageSource.getMessage("success.order.pay", new Object[] {}, locale));

        return "message";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PreAuthorize("@orderService.findById(#orderId).user.email.equals(authentication.principal.username)")
    @RequestMapping(value = {"/myOrders/cancel-{orderId}"}, method = RequestMethod.GET)
    public String cancelOrder(@PathVariable("orderId") Long orderId, ModelMap model, Locale locale) {
        Order order = orderService.findById(orderId);
        if (order.getStatus() != OrderStatus.CONFIRMED) {
            model.addAttribute("failMessage",
                    messageSource.getMessage("fail.order.cancel", new Object[] {orderId}, locale));
            return "message";
        }

        orderService.cancelOrder(orderId);
        model.addAttribute("successMessage",
                messageSource.getMessage("success.order.cancel", new Object[] {}, locale));

        return "message";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PreAuthorize("@orderService.findById(#orderId).user.email.equals(authentication.principal.username)")
    @RequestMapping(value = {"/myOrders/confirm-{orderId}"}, method = RequestMethod.GET)
    public String confirmOrderById(@PathVariable("orderId") Long orderId, ModelMap model, Locale locale) {
        orderService.confirmOrder(orderId);
        model.addAttribute("successMessage",
                messageSource.getMessage("success.order.confirm", new Object[] {}, locale));

        return "message";
    }
}
