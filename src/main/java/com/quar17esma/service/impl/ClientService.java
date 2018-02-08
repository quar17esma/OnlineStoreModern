package com.quar17esma.service.impl;

import com.quar17esma.dao.ClientRepository;
import com.quar17esma.model.Client;
import com.quar17esma.model.User;
import com.quar17esma.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class ClientService implements IClientService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ClientRepository repository;

    @Autowired
    private UserService userService;

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
        User user = userService.findByEmail(email);
        return user.getClient();
    }

    @Override
    public List<Client> findClientsWithUnpaidOrders() {
        return repository.findClientsWithUnpaidOrders();
    }

    @Override
    public void blockClientById(Long clientId) {
        Optional<Client> client = repository.findById(clientId);
        if (client.isPresent()) {
            client.get().setIsInBlackList(true);
            repository.save(client.get());
        }
    }
}
