package com.quar17esma.controller;

import com.quar17esma.model.Order;
import com.quar17esma.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private OrderService orderService;

    @Override
    public boolean hasPermission(Authentication authentication, Object target, Object permission) {
        throw new UnsupportedOperationException(
                "hasPermission not supported for object <" + target
                        + "> and permission <" + permission + ">");
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
                                 Object permission) {
        if ("com.quar17esma.model.Order".equals(targetType)) {
            Order order = orderService.findById((Long) targetId);
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            return order.getUser().getEmail().equals(principal.getUsername());
        }

        return false;
    }
}
