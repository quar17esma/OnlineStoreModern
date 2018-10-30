package com.quar17esma.controller.rest;

import com.quar17esma.exceptions.GoodNotFoundException;
import com.quar17esma.model.Good;
import com.quar17esma.service.GoodService;
import com.quar17esma.exceptions.MyRestError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/goods")
public class RestGoodController {

    @Autowired
    private GoodService goodService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Good goodById(@PathVariable long id) {
        Good good = goodService.findById(id);
        if (good == null) { throw new GoodNotFoundException(id); }
        return good;
    }

    @ExceptionHandler(GoodNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MyRestError goodNotFound(GoodNotFoundException e) {
        long goodId = e.getGoodId();
        return new MyRestError(404, "Good [" + goodId + "] not found");
    }
}
