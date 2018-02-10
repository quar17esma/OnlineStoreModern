package com.quar17esma.dao;

import com.quar17esma.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

//    @Query("SELECT c from Client c where c.orders.status = 'NEW'")
//    List<Client> findClientsWithUnpaidOrders();
}
