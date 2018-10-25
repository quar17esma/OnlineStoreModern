package com.quar17esma.model;

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString(exclude = {"goodPic", "description"})
@Builder
@Entity
@Table(name = "goods")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "goods")
public class Good implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Length(min = 5, max = 100)
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @NotEmpty
    @Length(min = 5, max = 1000)
    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @Min(0)
    @Column(name = "price", nullable = false)
    private long price;

    @Min(0)
    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Lob
    @Column(name="good_pic")
    private byte[] goodPic;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Good good = (Good) o;

        return id != null ? id.equals(good.id) : good.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
