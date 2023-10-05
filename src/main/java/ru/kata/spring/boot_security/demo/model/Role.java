package ru.kata.spring.boot_security.demo.model;


import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String authority;

    public int getId() {
        return id;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public Role(String authority) {
        this.authority = authority;
    }

    public Role() {
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
