package com.quar17esma.service;

import com.quar17esma.model.Client;
import com.quar17esma.model.UserMy;

public interface IUserMyService {
    UserMy findByEmail (String email);

    boolean existsByEmail(String email);

    Client login(String login, String password);
}
