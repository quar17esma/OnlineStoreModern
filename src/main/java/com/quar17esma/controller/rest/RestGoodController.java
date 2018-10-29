package com.quar17esma.controller.rest;

import com.quar17esma.model.Good;
import com.quar17esma.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/goods")
public class RestGoodController {

    @Autowired
    private GoodService goodService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Good goodById(@PathVariable long id) {
        Good good = goodService.findById(id);

        return good;
    }
}
