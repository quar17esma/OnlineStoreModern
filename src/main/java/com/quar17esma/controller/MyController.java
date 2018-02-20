package com.quar17esma.controller;

import com.quar17esma.model.Good;
import com.quar17esma.model.Order;
import com.quar17esma.model.User;
import com.quar17esma.service.GoodService;
import com.quar17esma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/")
@ComponentScan({"com.quar17esma.security"})
public class MyController {

    @Autowired
    GoodService goodService;

    @Autowired
    UserService userService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

    AuthenticationTrustResolver authenticationTrustResolver;

    /**
     * List all existing Goods.
     */
    @RequestMapping(value = {"/", "/list"}, method = RequestMethod.GET)
    public String listGoods(ModelMap model) {

        List<Good> goods = goodService.findAll();
        model.addAttribute("goods", goods);
        model.addAttribute("loggedinuser", getPrincipal());

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
    @RequestMapping(value = {"/buy-good-{goodId}"}, method = RequestMethod.GET)
    public String addGood(@PathVariable Long goodId, ModelMap model) {
        Good good = goodService.findById(goodId);
        model.addAttribute("good", good);

        return "buy_now";
    }

    /**
     * Adds good to order
     */
    @RequestMapping(value = {"/buy-good-{goodId}"}, method = RequestMethod.POST)
    public String addGoodToOrder(@Valid Good good, BindingResult result) {
        if (result.hasErrors()) {
            return "buy_now";
        }

        goodService.addGoodToOrder(new Order(), good.getId(), good.getQuantity());

        return "allgoods";
    }

    /**
     * This method handles Access-Denied redirect.
     */
    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());
        return "accessDenied";
    }

    /**
     * This method handles login GET requests.
     * If users is already logged-in and tries to goto login page again, will be redirected to list page.
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        if (isCurrentAuthenticationAnonymous()) {
            return "login";
        } else {
            return "redirect:/list";
        }
    }

    /**
     * This method handles logout requests.
     * Toggle the handlers if you are RememberMe functionality is useless in your app.
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            //new SecurityContextLogoutHandler().logout(request, response, auth);
            persistentTokenBasedRememberMeServices.logout(request, response, auth);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:/login?logout";
    }

    /**
     * This method returns the principal[user-name] of logged-in user.
     */
    private String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    /**
     * This method returns true if users is already authenticated [logged-in], else false.
     */
    private boolean isCurrentAuthenticationAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authenticationTrustResolver.isAnonymous(authentication);
    }

    @Autowired
    public void setAuthenticationTrustResolver(AuthenticationTrustResolver authenticationTrustResolver) {
        this.authenticationTrustResolver = authenticationTrustResolver;
    }

    /**
     * This method will provide the medium to add a new user.
     */
    @RequestMapping(value = {"/newuser"}, method = RequestMethod.GET)
    public String newUser(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);

        return "registration";
    }

    /**
     * This method will be called on form submission, handling POST request for
     * saving user in database. It also validates the user input
     */
    @RequestMapping(value = {"/newuser"}, method = RequestMethod.POST)
    public String saveUser(@Valid User user, BindingResult result,
                           ModelMap model) {

        if (result.hasErrors()) {
            return "registration";
        }

		/*
         * Preferred way to achieve uniqueness of field [sso] should be implementing custom @Unique annotation
		 * and applying it on field [sso] of Model class [User].
		 *
		 * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
		 * framework as well while still using internationalized messages.
		 *
		 */
        if (!userService.isUserSSOUnique(user.getId(), user.getSsoId())) {
            FieldError ssoError = new FieldError("user", "ssoId",
                    messageSource.getMessage("non.unique.ssoId", new String[]{user.getSsoId()}, Locale.getDefault()));
            result.addError(ssoError);
            return "registration";
        }

        userService.saveUser(user);

        model.addAttribute("success",
                "User " + user.getFirstName() + " " + user.getLastName() + " registered successfully");
        model.addAttribute("loggedinuser", getPrincipal());

        return "registrationsuccess";
    }
}
