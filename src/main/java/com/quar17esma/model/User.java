package com.quar17esma.model;

import com.quar17esma.enums.Role;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "USER")
public class User implements Serializable {

    @GenericGenerator(name = "generator",
            strategy = "foreign",
            parameters = @Parameter(name = "property", value = "client"))
    @Id
    @GeneratedValue(generator = "generator")
    private int id;

    @NotEmpty
    @Column(name = "EMAIL", unique = true, nullable = false, length = 50)
    private String email;

    @NotEmpty
    @Column(name = "PASSWORD", nullable = false, length = 50)
    private String password;

    @NotEmpty
    @Column(name = "ENABLED", nullable = false)
    private boolean enabled = true;

    @NotEmpty
    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false)
    private Role role = Role.USER;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Client client;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public static class Builder {
        private User user;

        public Builder() {
            this.user = new User();
        }

        public User build() {
            return user;
        }

        public Builder setId(final int id) {
            user.setId(id);
            return this;
        }

        public Builder setEmail(final String email) {
            user.setEmail(email);
            return this;
        }

        public Builder setPassword(final String password) {
            user.setPassword(password);
            return this;
        }

        public Builder setEnabled(final boolean enabled) {
            user.setEnabled(enabled);
            return this;
        }

        public Builder setRole(final Role role) {
            user.setRole(role);
            return this;
        }

        public Builder setClient(final Client client) {
            user.setClient(client);
            return this;
        }
    }
}
