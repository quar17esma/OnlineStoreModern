package com.quar17esma.model;

import com.quar17esma.enums.OrderStatus;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ORDER")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "ORDERED_AT", nullable = false)
    private Date orderedAt;


    @Enumerated
    @Column(name = "STATUS", nullable = false)
    private OrderStatus status = OrderStatus.NEW;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private Client client;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name="ID")
    private List<Good> goods;

    public Order() {
        this.goods = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(Date orderedAt) {
        this.orderedAt = orderedAt;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Good> getGoods() {
        return goods;
    }

    public void setGoods(List<Good> goods) {
        this.goods = goods;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderedAt=" + orderedAt +
                ", status=" + status +
                ", client=" + client +
                ", goods=" + goods +
                '}';
    }

    public static class Builder {
        private  Order order;

        public Builder() {
            this.order = new Order();
        }

        public Order build() {
            return order;
        }

        public Builder setId(final long id) {
            order.setId(id);
            return this;
        }

        public Builder setOrderedAt(final Date orderedAt) {
            order.setOrderedAt(orderedAt);
            return this;
        }

        public Builder setStatus(final OrderStatus status) {
            order.setStatus(status);
            return this;
        }

        public Builder setClient(final Client client) {
            order.setClient(client);
            return this;
        }

        public Builder setGoods(final List<Good> goods) {
            order.setGoods(goods);
            return this;
        }
    }
}
