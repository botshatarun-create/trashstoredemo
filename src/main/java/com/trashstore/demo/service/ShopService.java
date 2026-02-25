package com.trashstore.demo.service;

import com.trashstore.demo.model.*;
import com.trashstore.demo.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ShopService {

    private final ItemRepository itemRepo;
    private final UserRepository userRepo;
    private final CartRepository cartRepo;
    private final CartItemRepository cartItemRepo;

    public ShopService(ItemRepository itemRepo, UserRepository userRepo,
            CartRepository cartRepo, CartItemRepository cartItemRepo) {
        this.itemRepo = itemRepo;
        this.userRepo = userRepo;
        this.cartRepo = cartRepo;
        this.cartItemRepo = cartItemRepo;
    }

    public Cart getOrCreateCart(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return cartRepo.findByUser(user).orElseGet(() -> {
            Cart cart = new Cart(user);
            return cartRepo.save(cart);
        });
    }

    public Cart addToCart(Long userId, Long itemId, int quantity) {
        Cart cart = getOrCreateCart(userId);
        Item item = itemRepo.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found"));

        if (item.getStockQuantity() < quantity) {
            throw new RuntimeException("Not enough stock");
        }

        // Check if item already in cart — if so, increase quantity
        Optional<CartItem> existing = cart.getItems().stream()
                .filter(ci -> ci.getItem().getId().equals(itemId))
                .findFirst();

        if (existing.isPresent()) {
            CartItem ci = existing.get();
            ci.setQuantity(ci.getQuantity() + quantity);
            cartItemRepo.save(ci);
        } else {
            CartItem ci = new CartItem(cart, item, quantity);
            cart.getItems().add(ci);
            cartItemRepo.save(ci);
        }

        return cartRepo.save(cart);
    }

    public Cart removeFromCart(Long userId, Long cartItemId) {
        Cart cart = getOrCreateCart(userId);
        cart.getItems().removeIf(ci -> ci.getId().equals(cartItemId));
        return cartRepo.save(cart);
    }

    public Cart updateQuantity(Long userId, Long cartItemId, int newQuantity) {
        Cart cart = getOrCreateCart(userId);
        cart.getItems().stream()
                .filter(ci -> ci.getId().equals(cartItemId))
                .findFirst()
                .ifPresent(ci -> {
                    if (newQuantity <= 0) {
                        cart.getItems().remove(ci);
                    } else {
                        ci.setQuantity(newQuantity);
                        cartItemRepo.save(ci);
                    }
                });
        return cartRepo.save(cart);
    }
}
