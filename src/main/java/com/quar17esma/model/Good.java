package com.quar17esma.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Entity
@Table(name = "GOOD")
public class Good implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private long id;

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @Column(name = "DESCRIPTION", nullable = false, length = 1000)
    private String description;

    @Min(0)
    @Column(name = "PRICE", nullable = false)
    private long price;

    @Min(0)
    @Column(name = "QUANTITY", nullable = false)
    private int quantity;

    public Good() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Good{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }

    public static class Builder {
        private Good good;

        public Builder() {
            this.good = new Good();
        }

        public Good build() {
            return good;
        }

        public Builder setId(final long id) {
            good.setId(id);
            return this;
        }

        public Builder setName(final String name) {
            good.setName(name);
            return this;
        }

        public Builder setDescription(final String description) {
            good.setDescription(description);
            return this;
        }

        public Builder setPrice(final long price) {
            good.setPrice(price);
            return this;
        }

        public Builder setQuantity(final int quantity) {
            good.setQuantity(quantity);
            return this;
        }
    }
}
