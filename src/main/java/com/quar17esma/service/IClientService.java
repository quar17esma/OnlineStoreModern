package com.quar17esma.service;

import com.quar17esma.model.Client;

import java.util.List;

public interface IClientService {

    void registerClient(Client client);

    Client findClientByEmail(String email);

    List<Client> findClientsWithUnpaidOrders();

    void blockClientById(Long clientId);
}
