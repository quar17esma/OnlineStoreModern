package com.quar17esma.controller;

import com.quar17esma.exceptions.NotEnoughGoodException;
import com.quar17esma.model.Good;
import com.quar17esma.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@ControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private GoodService goodService;
    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(NotEnoughGoodException.class)
    public String notEnoughGoodExceptionHandler(NotEnoughGoodException e, Model model) {
        Locale locale = LocaleContextHolder.getLocale();
        Good good = goodService.findById(e.getGoodId());
        model.addAttribute("good", good);
        model.addAttribute("errorNotEnoughGood",
                messageSource.getMessage("not.enough.good", new Object[]{good.getName(), good.getQuantity()}, locale));

        return "buyNow";
    }
}
