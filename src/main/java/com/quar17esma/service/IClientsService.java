package com.quar17esma.service;

import com.quar17esma.model.Client;

import java.util.List;

public interface IClientsService {

    void registerClient(Client client);

    Client getClientByEmail(String email);

    List<Client> getClientsWithUnpaidOrders();

    void blockClientById(int clientId);
}
