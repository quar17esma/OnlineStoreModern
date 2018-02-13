package com.quar17esma.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "CLIENT")
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private long id;

    @NotEmpty
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    @NotEmpty
    @Column(name = "IS_IN_BLACK_LIST", nullable = false)
    private boolean isInBlackList = false;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "client", cascade = CascadeType.ALL)
    private UserMy userMy;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    private List<Order> orders;

    public Client() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isInBlackList() {
        return isInBlackList;
    }

    public void setIsInBlackList(boolean isInBlackList) {
        this.isInBlackList = isInBlackList;
    }

    public UserMy getUser() {
        return userMy;
    }

    public void setUser(UserMy userMy) {
        this.userMy = userMy;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isInBlackList=" + isInBlackList +
                ", userMy=" + userMy +
                ", orders=" + orders +
                '}';
    }

    public static class Builder {
        private  Client client;

        public Builder() {
            this.client = new Client();
        }

        public Client build() {
            return client;
        }

        public Builder setId(final long id) {
            client.setId(id);
            return this;
        }

        public Builder setName(final String name) {
            client.setName(name);
            return this;
        }

        public Builder setIsInBlackList(final boolean isInBlackList) {
            client.setIsInBlackList(isInBlackList);
            return this;
        }

        public Builder setUser(final UserMy userMy) {
            client.setUser(userMy);
            return this;
        }

        public Builder setOrders(final List<Order> orders) {
            client.setOrders(orders);
            return this;
        }
    }
}
