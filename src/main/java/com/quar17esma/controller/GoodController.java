package com.quar17esma.controller;

import com.quar17esma.model.Good;
import com.quar17esma.model.Order;
import com.quar17esma.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    private static int currentPage = 1;
    private static int pageSize = 6;

    @ModelAttribute("loggedInUser")
    public String getLoggedInUser() {
        return userController.getPrincipal();
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = {"/", "/list"}, method = RequestMethod.GET)
    public String listGoods(ModelMap model,
                            @RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size) {
        page.ifPresent(p -> currentPage = p);
        size.ifPresent(s -> pageSize = s);

        Page<Good> goodPage = goodService.findAll(new PageRequest(currentPage - 1, pageSize));
        model.addAttribute("goods", goodPage);

        int totalPages = goodPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "allGoods";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = {"/new-good"}, method = RequestMethod.GET)
    public String showNewGoodForm(ModelMap model) {
        Good good = new Good();
        model.addAttribute("good", good);
        model.addAttribute("edit", false);

        return "editGood";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = {"/new-good"}, method = RequestMethod.POST)
    public String saveNewGood(@Valid Good good, BindingResult result, RedirectAttributes model, Locale locale) {
        if (result.hasErrors()) {
            return "editGood";
        }
        goodService.save(good);
        model.addFlashAttribute("successMessage",
                messageSource.getMessage("success.good.added", new Object[]{good.getName()}, locale));

        return "redirect:/message";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = {"/buy-good-{goodId}"}, method = RequestMethod.GET)
    public String showBuyGoodForm(@PathVariable Long goodId, ModelMap model, Locale locale) {
        Good good;
        try {
            good = goodService.findById(goodId);
        } catch (EntityNotFoundException ex) {
            model.addAttribute("failMessage",
                    messageSource.getMessage("fail.good.find", new Object[]{goodId}, locale));
            return "message";
        }
        model.addAttribute("good", good);

        return "buyGood";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = {"/buy-good-{goodId}"}, method = RequestMethod.POST)
    public String addGoodToCart(@PathVariable Long goodId,
                                @RequestParam(value = "orderedQuantity", defaultValue = "1") Integer orderedQuantity,
                                HttpSession httpSession, RedirectAttributes model, Locale locale) {
        Order cart = getOrderFromSessionOrCreate(httpSession);
        goodService.addGoodToCart(cart, goodId, orderedQuantity);
        String goodName = goodService.findById(goodId).getName();
        model.addFlashAttribute("successMessage",
                messageSource.getMessage("success.good.ordered", new Object[]{goodName, orderedQuantity}, locale));

        return "redirect:/message";
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

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = {"/edit-good-{goodId}"}, method = RequestMethod.GET)
    public String showEditGoodForm(@PathVariable Long goodId, ModelMap model) {
        Good good = goodService.findById(goodId);
        model.addAttribute("good", good);
        model.addAttribute("edit", true);

        return "editGood";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = {"/edit-good-{goodId}"}, method = RequestMethod.POST)
    public String saveEditedGood(@Valid Good good, BindingResult result, RedirectAttributes model, Locale locale) {
        if (result.hasErrors()) {
            return "editGood";
        }
        goodService.save(good);
        model.addFlashAttribute("successMessage",
                messageSource.getMessage("success.good.edited", new Object[]{good.getName()}, locale));

        return "redirect:/message";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = {"/delete-good-{goodId}"}, method = RequestMethod.GET)
    public String deleteGood(@PathVariable Long goodId, RedirectAttributes model, Locale locale) {
        Good good = goodService.findById(goodId);
        if (good == null) {
            model.addAttribute("failMessage",
                    messageSource.getMessage("fail.good.find", new Object[]{good.getId()}, locale));
            return "message";
        }
        goodService.delete(goodId);
        model.addFlashAttribute("successMessage",
                messageSource.getMessage("success.good.delete", new Object[]{good.getId()}, locale));

        return "redirect:/message";
    }

    @RequestMapping(value = "/imageController/{goodId}")
    @ResponseBody
    public byte[] getGoodPicById(@PathVariable long goodId) {
        Good good = goodService.findById(goodId);
        return good.getGoodPic();
    }
}
