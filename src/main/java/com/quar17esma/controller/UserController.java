package com.quar17esma.controller;

import com.quar17esma.model.User;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/")
@ComponentScan({"com.quar17esma.security"})
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;
    private AuthenticationTrustResolver authenticationTrustResolver;

    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        User user = userService.findByEmail(getPrincipal());
        model.addAttribute("userFirstName", user.getFirstName());
        return "accessDenied";
    }

    /**
     * This method returns the principal[user-name] of logged-in user.
     */
    public String getPrincipal() {
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
     * This method returns logged-in user.
     */
    public User getUser() {
        return userService.findByEmail(getPrincipal());
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
            return "redirect:/goods/list";
        }
    }

    /**
     * This method returns true if users is already authenticated [logged-in], else false.
     */
    private boolean isCurrentAuthenticationAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authenticationTrustResolver.isAnonymous(authentication);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            persistentTokenBasedRememberMeServices.logout(request, response, auth);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        httpSession.invalidate();

        return "redirect:/login?logout";
    }

    @RequestMapping(value = {"/new_user"}, method = RequestMethod.GET)
    public String newUser(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);

        return "registration";
    }

    @RequestMapping(value = {"/new_user"}, method = RequestMethod.POST)
    public String saveUser(@Valid User user, BindingResult result, ModelMap model, Locale locale) {
        if (result.hasErrors()) {
            return "registration";
        } else if (isEmailBusy(user)) {
            FieldError emailError = new FieldError("user", "email",
                    messageSource.getMessage("error.busy.email", new String[]{user.getEmail()}, locale));
            result.addError(emailError);
            return "registration";
        }
        userService.save(user);
        model.addAttribute("successRegistrationMessage",
                messageSource.getMessage("success.user.register", new String[]{user.getFirstName(), user.getLastName()}, locale));
        model.addAttribute("loggedinuser", getPrincipal());

        return "login";
    }

    private boolean isEmailBusy(@Valid User user) {
        return userService.isEmailBusy(user.getEmail());
    }

    @RequestMapping(value = {"/contact"}, method = RequestMethod.GET)
    public String showContactForm() {
        return "contact";
    }

    @RequestMapping(value = {"/stores"}, method = RequestMethod.GET)
    public String showStores() {
        return "stores";
    }

    @Autowired
    public void setAuthenticationTrustResolver(AuthenticationTrustResolver authenticationTrustResolver) {
        this.authenticationTrustResolver = authenticationTrustResolver;
    }
}
