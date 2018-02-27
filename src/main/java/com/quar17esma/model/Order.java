package com.quar17esma.model;

import com.quar17esma.converter.LocalDateTimeConverter;
import com.quar17esma.enums.OrderStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "ORDER")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private long id;

    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "ORDERED_AT", nullable = false)
    private LocalDateTime orderedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private OrderStatus status = OrderStatus.NEW;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ElementCollection
    private Map<Good, Integer> orderedGoods = new HashMap<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(LocalDateTime orderedAt) {
        this.orderedAt = orderedAt;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setClient(User user) {
        this.user = user;
    }

    public Map<Good, Integer> getOrderedGoods() {
        return orderedGoods;
    }

    public void setOrderedGoods(Map<Good, Integer> orderedGoods) {
        this.orderedGoods = orderedGoods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        return getId() == order.getId();
    }

    @Override
    public int hashCode() {
        return (int) (getId() ^ (getId() >>> 32));
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderedAt=" + orderedAt +
                ", status=" + status +
                ", client=" + user +
                '}';
    }

    public static class Builder {
        private Order order;

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

        public Builder setOrderedAt(final LocalDateTime orderedAt) {
            order.setOrderedAt(orderedAt);
            return this;
        }

        public Builder setStatus(final OrderStatus status) {
            order.setStatus(status);
            return this;
        }

        public Builder setUser(final User user) {
            order.setClient(user);
            return this;
        }

        public Builder setGoods(final Map<Good, Integer> orderedGoods) {
            order.setOrderedGoods(orderedGoods);
            return this;
        }
    }
}
