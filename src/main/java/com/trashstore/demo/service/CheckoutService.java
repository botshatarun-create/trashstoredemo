package com.trashstore.demo.service;

import com.trashstore.demo.model.*;
import com.trashstore.demo.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class CheckoutService {

    private final UserRepository userRepo;
    private final CartRepository cartRepo;
    private final CartItemRepository cartItemRepo;
    private final OrderRepository orderRepo;
    private final ItemRepository itemRepo;

    public CheckoutService(UserRepository userRepo, CartRepository cartRepo,
            CartItemRepository cartItemRepo, OrderRepository orderRepo,
            ItemRepository itemRepo) {
        this.userRepo = userRepo;
        this.cartRepo = cartRepo;
        this.cartItemRepo = cartItemRepo;
        this.orderRepo = orderRepo;
        this.itemRepo = itemRepo;
    }

    public Order checkout(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartRepo.findByUser(user).orElseThrow(() -> new RuntimeException("Cart is empty"));

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        BigDecimal total = cart.getTotal();

        if (user.getTrashBalance().compareTo(total) < 0) {
            throw new RuntimeException(
                    "Insufficient trash balance! You need " + total + "kg but have " + user.getTrashBalance() + "kg");
        }

        // Deduct balance
        user.setTrashBalance(user.getTrashBalance().subtract(total));
        userRepo.save(user);

        // Create order
        Order order = new Order(user, total);
        orderRepo.save(order);

        // Create order items and reduce stock
        for (CartItem cartItem : cart.getItems()) {
            Item item = cartItem.getItem();

            if (item.getStockQuantity() < cartItem.getQuantity()) {
                throw new RuntimeException("Not enough stock for: " + item.getName());
            }

            item.setStockQuantity(item.getStockQuantity() - cartItem.getQuantity());
            itemRepo.save(item);

            OrderItem oi = new OrderItem(order, item, cartItem.getQuantity(), item.getTrashPrice());
            order.getOrderItems().add(oi);
        }
        orderRepo.save(order);

        // Clear the cart
        cart.getItems().clear();
        cartRepo.save(cart);

        return order;
    }
}
