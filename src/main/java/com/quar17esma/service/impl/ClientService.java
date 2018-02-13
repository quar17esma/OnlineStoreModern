package com.quar17esma.service.impl;

import com.quar17esma.dao.ClientRepository;
import com.quar17esma.model.Client;
import com.quar17esma.model.UserMy;
import com.quar17esma.service.IClientService;
import com.quar17esma.service.IUserMyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("clientService")
@Transactional
public class ClientService implements IClientService {

    @Autowired
    private ClientRepository repository;

    @Autowired
    private IUserMyService userService;

    @Override
    @Transactional
    public void registerClient(Client client) {
        String email = client.getUser().getEmail();
        if (userService.existsByEmail(email)) {
//            throw new BusyEmailException("Fail to register client, email is busy",
//                    client.getName(), client.getUser().getEmail());
            throw new RuntimeException();
        }

        repository.save(client);
    }

    @Override
    @Transactional
    public Client findClientByEmail(String email) {
        UserMy userMy = userService.findByEmail(email);
        return userMy.getClient();
    }

    @Override
    public List<Client> findClientsWithUnpaidOrders() {
//        return repository.findClientsWithUnpaidOrders();
        return null;
    }

    @Override
    public void blockClientById(Long clientId) {
//        Optional<Client> client = repository.findById(clientId);
//        if (client.isPresent()) {
//            client.get().setIsInBlackList(true);
//            repository.save(client.get());
//        }
    }
}
