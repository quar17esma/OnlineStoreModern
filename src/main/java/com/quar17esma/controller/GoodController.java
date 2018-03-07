package com.quar17esma.controller;

import com.quar17esma.model.Good;
import com.quar17esma.model.Order;
import com.quar17esma.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@Controller
@SessionAttributes("loggedInUser")
@RequestMapping("/")
public class GoodController {
    private static final int DEFAULT_QUANTITY_FOR_ORDERED_GOOD = 1;

    @Autowired
    private UserController userController;

    @Autowired
    private GoodService goodService;

    @Autowired
    private MessageSource messageSource;

    @ModelAttribute("loggedInUser")
    public String getLoggedInUser () {
        return userController.getPrincipal();
    }

    /**
     * List all existing Goods.
     */
    @RequestMapping(value = {"/", "/list"}, method = RequestMethod.GET)
    public String listGoods(ModelMap model) {

        List<Good> goods = goodService.findAll();
        model.addAttribute("goods", goods);

        return "allGoods";
    }

    /**
     * This method will provide the medium to add a new good.
     */
    @RequestMapping(value = {"/newgood"}, method = RequestMethod.GET)
    public String newGood(ModelMap model) {
        Good good = new Good();
        model.addAttribute("good", good);
        model.addAttribute("edit", false);
        return "editGood";
    }

    /**
     * This method will be called on form submission, handling POST request for
     * saving user in database. It also validates the good input
     */
    @RequestMapping(value = {"/newgood"}, method = RequestMethod.POST)
    public String saveNewGood(@Valid Good good, BindingResult result,
                           ModelMap model, Locale locale) {

        if (result.hasErrors()) {
            return "editGood";
        }

        goodService.save(good);

        model.addAttribute("success",
                messageSource.getMessage("success.good.added",
                        new Object[]{good.getName()},
                        locale));

        return "successPage";
    }

    /**
     * This method will provide the medium to buy a good.
     */
    @RequestMapping(value = {"/buy-good-{goodId}"}, method = RequestMethod.GET)
    public String addGood(@PathVariable Long goodId, ModelMap model) {
        Good good = goodService.findById(goodId);
        good.setQuantity(DEFAULT_QUANTITY_FOR_ORDERED_GOOD);
        model.addAttribute("good", good);

        return "buyNow";
    }

    /**
     * Adds good to order
     */
    @RequestMapping(value = {"/buy-good-{goodId}"}, method = RequestMethod.POST)
    public String addGoodToOrder(@Valid Good good, BindingResult result,
                                 HttpSession httpSession, ModelMap model, Locale locale) {
        if (result.hasErrors()) {
            return "buyNow";
        }

        Order order = (Order) httpSession.getAttribute("order");
        if (order == null) {
            order = new Order();
            order.setUser(userController.getUser());
            httpSession.setAttribute("order", order);
        }

        int orderedQuantity = good.getQuantity();
        goodService.addGoodToOrder(order, good.getId(), orderedQuantity);

        String goodName = goodService.findById(good.getId()).getName();
        model.addAttribute("success",
                messageSource.getMessage("success.good.ordered",
                        new Object[]{goodName, orderedQuantity},
                        locale));

        return "successPage";
    }

    /**
     * This method will provide the medium to edit a good.
     */
    @RequestMapping(value = {"/edit-good-{goodId}"}, method = RequestMethod.GET)
    public String editGood(@PathVariable Long goodId, ModelMap model) {
        Good good = goodService.findById(goodId);
        model.addAttribute("good", good);
        model.addAttribute("edit", true);

        return "editGood";
    }

    /**
     * Edit Good.
     */
    @RequestMapping(value = {"/edit-good-{goodId}"}, method = RequestMethod.POST)
    public String saveEditedGood(@Valid Good good, BindingResult result,
                                 ModelMap model, Locale locale) {
        if (result.hasErrors()) {
            return "editGood";
        }

        goodService.save(good);

        model.addAttribute("success",
                messageSource.getMessage("success.good.edited",
                        new Object[]{good.getName()},
                        locale));

        return "successPage";
    }

    @RequestMapping(value = "/imageController/{goodId}")
    @ResponseBody
    public byte[] getGoodPicById(@PathVariable long goodId)  {
        Good good = goodService.findById(goodId);
        return good.getProfilePic();
    }
}
