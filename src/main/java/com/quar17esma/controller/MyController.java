package com.quar17esma.controller;

import com.quar17esma.model.Good;
import com.quar17esma.service.IGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/")
public class MyController {

    @Autowired
    IGoodService service;

    @Autowired
    MessageSource messageSource;

    /*
     * List all existing Goods.
     */
    @RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
    public String listGoods(ModelMap model) {

        List<Good> goods = service.findAll();
        model.addAttribute("goods", goods);
        
        return "allgoods";
    }
}
