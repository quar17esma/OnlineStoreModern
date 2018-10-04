package com.quar17esma.controller;

import com.quar17esma.exceptions.NotEnoughGoodException;
import com.quar17esma.model.Good;
import com.quar17esma.model.Order;
import com.quar17esma.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@Controller
@SessionAttributes("loggedInUser")
@RequestMapping("/goods")
public class GoodController {
    @Autowired
    private UserController userController;
    @Autowired
    private GoodService goodService;
    @Autowired
    private MessageSource messageSource;

    @ModelAttribute("loggedInUser")
    public String getLoggedInUser() {
        return userController.getPrincipal();
    }

    @RequestMapping(value = {"/", "/list"}, method = RequestMethod.GET)
    public String listGoods(ModelMap model) {
        List<Good> goods = goodService.findAll();
        model.addAttribute("goods", goods);

        return "allGoods";
    }

    @RequestMapping(value = {"/new-good"}, method = RequestMethod.GET)
    public String showNewGoodForm(ModelMap model) {
        Good good = new Good();
        model.addAttribute("good", good);
        model.addAttribute("edit", false);

        return "editGood";
    }

    @RequestMapping(value = {"/new-good"}, method = RequestMethod.POST)
    public String saveNewGood(@Valid Good good, BindingResult result, ModelMap model, Locale locale) {
        if (result.hasErrors()) {
            return "editGood";
        }
        goodService.save(good);
        model.addAttribute("success",
                messageSource.getMessage("success.good.added", new Object[]{good.getName()}, locale));

        return "successPage";
    }

    @RequestMapping(value = {"/buy-good-{goodId}"}, method = RequestMethod.GET)
    public String showBuyGoodForm(@PathVariable Long goodId, ModelMap model, Locale locale) {
        Good good;
        try {
            good = goodService.findById(goodId);
        } catch (EntityNotFoundException ex) {
            model.addAttribute("failMessage",
                    messageSource.getMessage("fail.good.find", new Object[]{goodId}, locale));
            return "failPage";
        }
        model.addAttribute("good", good);

        return "buyNow";
    }

    @RequestMapping(value = {"/buy-good-{goodId}"}, method = RequestMethod.POST)
    public String addGoodToCart(@PathVariable Long goodId,
                                @RequestParam(value = "orderedQuantity", defaultValue = "1") Integer orderedQuantity,
                                HttpSession httpSession, ModelMap model, Locale locale) {
        Order cart = getOrderFromSessionOrCreate(httpSession);
        goodService.addGoodToCart(cart, goodId, orderedQuantity);
        String goodName = goodService.findById(goodId).getName();
        model.addAttribute("success",
                messageSource.getMessage("success.good.ordered", new Object[]{goodName, orderedQuantity}, locale));

        return "successPage";
    }

    private Order getOrderFromSessionOrCreate(HttpSession httpSession) {
        Order order = (Order) httpSession.getAttribute("order");
        if (order == null) {
            order = new Order();
            order.setUser(userController.getUser());
            httpSession.setAttribute("order", order);
        }
        return order;
    }

    @RequestMapping(value = {"/edit-good-{goodId}"}, method = RequestMethod.GET)
    public String showEditGoodForm(@PathVariable Long goodId, ModelMap model) {
        Good good = goodService.findById(goodId);
        model.addAttribute("good", good);
        model.addAttribute("edit", true);

        return "editGood";
    }

    @RequestMapping(value = {"/edit-good-{goodId}"}, method = RequestMethod.POST)
    public String saveEditedGood(@Valid Good good, BindingResult result, ModelMap model, Locale locale) {
        if (result.hasErrors()) {
            return "editGood";
        }
        goodService.save(good);
        model.addAttribute("success",
                messageSource.getMessage("success.good.edited", new Object[]{good.getName()}, locale));

        return "successPage";
    }

    @RequestMapping(value = {"/delete-good-{goodId}"}, method = RequestMethod.GET)
    public String deleteGood(@PathVariable Long goodId, ModelMap model, Locale locale) {
        Good good = goodService.findById(goodId);
        if (good == null) {
            model.addAttribute("failMessage",
                    messageSource.getMessage("fail.good.find", new Object[]{good.getId()}, locale));
            return "failPage";
        }
        goodService.delete(goodId);
        model.addAttribute("success",
                messageSource.getMessage("success.good.delete", new Object[]{good.getId()}, locale));

        return "editGood";
    }

    @RequestMapping(value = "/imageController/{goodId}")
    @ResponseBody
    public byte[] getGoodPicById(@PathVariable long goodId) {
        Good good = goodService.findById(goodId);
        return good.getGoodPic();
    }
}
