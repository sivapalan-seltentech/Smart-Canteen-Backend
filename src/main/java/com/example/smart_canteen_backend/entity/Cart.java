package com.example.smart_canteen_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    @JsonIgnoreProperties({
            "orders",
            "cart",
            "password"
    })
    private User user;


    @ManyToOne
    @JoinColumn(
            name = "food_id",
            nullable = false
    )
    @JsonIgnoreProperties({
            "hibernateLazyInitializer",
            "handler"
    })
    private Food food;


    private int quantity;


    public Cart() {
    }


    public Cart(
            User user,
            Food food,
            int quantity
    ) {

        this.user = user;

        this.food = food;

        this.quantity = quantity;
    }


    // ID

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    // USER

    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }


    // FOOD

    public Food getFood() {
        return food;
    }


    public void setFood(Food food) {
        this.food = food;
    }


    // QUANTITY

    public int getQuantity() {
        return quantity;
    }


    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}