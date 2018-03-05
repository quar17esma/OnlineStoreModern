package com.quar17esma.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Entity
@Table(name = "GOOD")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"profilePic", "description"})
@Builder
public class Good implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
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
    @Column(name="PROFILE_PIC")
    private byte[] profilePic;

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
