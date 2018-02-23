package com.quar17esma.controller;

import com.quar17esma.model.Order;
import com.quar17esma.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/")
public class OrderController {
    @Autowired
    UserController userController;

    @Autowired
    OrderService orderService;

    /**
     * Show current Order and its Goods.
     */
    @RequestMapping(value = {"/cart"}, method = RequestMethod.GET)
    public String cart(ModelMap model, HttpSession httpSession) {

        Order order = (Order) httpSession.getAttribute("order");
        model.addAttribute("order", order);

        return "cart";
    }

    /**
     * Confirm current Order.
     */
    @RequestMapping(value = {"/cart"}, method = RequestMethod.POST)
    public String confirmOrder(HttpSession httpSession, ModelMap model) {

        Order order = (Order) httpSession.getAttribute("order");
        orderService.confirmOrder(order.getId());
        httpSession.removeAttribute("order");

        model.addAttribute("success","Your order was successfully confirmed");

        return "orderConfirmSuccess";
    }

    /**
     * Show all Orders.
     */
    @RequestMapping(value = {"/myOrders"}, method = RequestMethod.GET)
    public String myOrders(ModelMap model) {

        Long userId = userController.getUser().getId();
        List<Order> orders = orderService.findAllByClientIdFetchOrderedGoods(userId);

        model.addAttribute("orders", orders);

        return "myOrders";
    }
}
