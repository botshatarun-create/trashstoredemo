package com.trashstore.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;

    @Column(precision = 10, scale = 2)
    private BigDecimal trashBalance; // amount of trash (kg) available to spend

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Cart cart;

    public User() {
    }

    public User(String username, String email, BigDecimal trashBalance) {
        this.username = username;
        this.email = email;
        this.trashBalance = trashBalance;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getTrashBalance() {
        return trashBalance;
    }

    public void setTrashBalance(BigDecimal trashBalance) {
        this.trashBalance = trashBalance;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
