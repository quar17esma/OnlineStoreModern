package com.quar17esma.model;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Entity
@Table(name = "GOOD")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "goods")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString(exclude = {"goodPic", "description"})
@Builder
public class Good implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    @Length(min = 5, max = 100)
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @NotEmpty
    @Length(min = 5, max = 1000)
    @Column(name = "DESCRIPTION", nullable = false, length = 1000)
    private String description;

    @Min(0)
    @Column(name = "PRICE", nullable = false)
    private long price;

    @Min(0)
    @Column(name = "QUANTITY", nullable = false)
    private int quantity;

    @Lob
    @Column(name="GOOD_PIC")
    private byte[] goodPic;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Good)) return false;

        Good good = (Good) o;

        return getId() == good.getId();
    }

    @Override
    public int hashCode() {
        return (int) (getId() ^ (getId() >>> 32));
    }
}
