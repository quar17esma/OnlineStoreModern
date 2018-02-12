package com.quar17esma.controller;

import com.quar17esma.model.Good;
import com.quar17esma.model.Order;
import com.quar17esma.service.IGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/")
public class MyController {

    @Autowired
    IGoodService goodService;

    @Autowired
    MessageSource messageSource;

    /**
     * List all existing Goods.
     */
    @RequestMapping(value = {"/", "/list"}, method = RequestMethod.GET)
    public String listGoods(ModelMap model) {

        List<Good> goods = goodService.findAll();
        model.addAttribute("goods", goods);

        return "allgoods";
    }

    /**
     * This method will provide the medium to add a new good.
     */
    @RequestMapping(value = {"/newgood"}, method = RequestMethod.GET)
    public String newGood(ModelMap model) {
        Good good = new Good();
        model.addAttribute("good", good);
        model.addAttribute("edit", false);
        return "edit_good";
    }

    /**
     * This method will be called on form submission, handling POST request for
     * saving user in database. It also validates the good input
     */
    @RequestMapping(value = {"/newgood"}, method = RequestMethod.POST)
    public String saveGood(@Valid Good good, BindingResult result) {

        if (result.hasErrors()) {
            return "edit_good";
        }

        goodService.save(good);

        return "redirect:/list";
    }

    /**
     * This method will provide the medium to buy a good.
     */
    @RequestMapping(value = { "/buy-good-{goodId}" }, method = RequestMethod.GET)
    public String addGood(@PathVariable Long goodId, ModelMap model) {
        Good good = goodService.findById(goodId);
        model.addAttribute("good", good);

        return "buy_now";
    }

    /**
     * Adds good to order
     */
    @RequestMapping(value = { "/buy-good-{goodId}" }, method = RequestMethod.POST)
    public String addGoodToOrder(@Valid Good good, BindingResult result) {
        if (result.hasErrors()) {
            return "buy_now";
        }

        goodService.addGoodToOrder(new Order(), good.getId(), good.getQuantity());

        return "allgoods";
    }
}
