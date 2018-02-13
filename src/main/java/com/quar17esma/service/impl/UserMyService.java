package com.quar17esma.service.impl;

import com.quar17esma.dao.UserMyRepository;
import com.quar17esma.model.Client;
import com.quar17esma.model.UserMy;
import com.quar17esma.service.IClientService;
import com.quar17esma.service.IUserMyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service("userService")
@Transactional
public class UserMyService implements IUserMyService {

    @Autowired
    private UserMyRepository repository;

    @Autowired
    private IClientService clientService;


    @Override
    public UserMy findByEmail(String email) {
        Optional<UserMy> user = repository.findByEmail(email);
        if (user.isPresent()) {
            return user.get();

        } else {
            throw new RuntimeException();
        }
    }

    public boolean existsByEmail(String email){
//        return repository.existsByEmail(email);
        return false;
    }

    @Override
    @Transactional
    public Client login(String login, String password) {
        if (checkLogin(login, password)) {
            return clientService.findClientByEmail(login);
        } else {
//            throw new LoginException("Fail to login", login);
            throw new RuntimeException();
        }
    }

    private boolean checkLogin(String login, String password) {
        boolean result = false;

        if (login != null &&
                password != null &&
                !login.isEmpty() &&
                !password.isEmpty()) {

            Optional<UserMy> user = repository.findByEmail(login);
            if (user.isPresent()) {
                result = user.get().getPassword().equals(password);
            }
        }

        return result;
    }
}
