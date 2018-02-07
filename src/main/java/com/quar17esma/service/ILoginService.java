package com.quar17esma.service;

import com.quar17esma.model.Client;

public interface ILoginService {
    Client login(String login, String password);
}
