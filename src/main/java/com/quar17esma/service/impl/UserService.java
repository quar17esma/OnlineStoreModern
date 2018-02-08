package com.quar17esma.service.impl;

import com.quar17esma.dao.UserRepository;
import com.quar17esma.model.Client;
import com.quar17esma.model.User;
import com.quar17esma.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class UserService implements IUserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ClientService clientService;


    @Override
    public User findByEmail(String email) {
        Optional<User> user = repository.findByEmail(email);
        if (user.isPresent()) {
            return user.get();

        } else {
            throw new RuntimeException();
        }
    }

    public boolean existsByEmail(String email){
        return repository.existsByEmail(email);
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

            Optional<User> user = repository.findByEmail(login);
            if (user.isPresent()) {
                result = user.get().getPassword().equals(password);
            }
        }

        return result;
    }
}
