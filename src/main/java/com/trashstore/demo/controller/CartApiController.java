package com.trashstore.demo.controller;

import com.trashstore.demo.model.Cart;
import com.trashstore.demo.model.Order;
import com.trashstore.demo.model.User;
import com.trashstore.demo.repository.OrderRepository;
import com.trashstore.demo.repository.UserRepository;
import com.trashstore.demo.service.CheckoutService;
import com.trashstore.demo.service.ShopService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CartApiController {

    private static final Long DEFAULT_USER_ID = 1L;

    private final ShopService shopService;
    private final CheckoutService checkoutService;
    private final UserRepository userRepo;
    private final OrderRepository orderRepo;

    public CartApiController(ShopService shopService, CheckoutService checkoutService,
            UserRepository userRepo, OrderRepository orderRepo) {
        this.shopService = shopService;
        this.checkoutService = checkoutService;
        this.userRepo = userRepo;
        this.orderRepo = orderRepo;
    }

    @PostMapping("/cart/add/{itemId}")
    public ResponseEntity<Map<String, Object>> addToCart(@PathVariable Long itemId,
            @RequestParam(defaultValue = "1") int quantity) {
        Map<String, Object> response = new HashMap<>();
        try {
            Cart cart = shopService.addToCart(DEFAULT_USER_ID, itemId, quantity);
            response.put("success", true);
            response.put("cartCount", cart.getTotalItems());
            response.put("cartTotal", cart.getTotal());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/cart/remove/{cartItemId}")
    public ResponseEntity<Map<String, Object>> removeFromCart(@PathVariable Long cartItemId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Cart cart = shopService.removeFromCart(DEFAULT_USER_ID, cartItemId);
            response.put("success", true);
            response.put("cartCount", cart.getTotalItems());
            response.put("cartTotal", cart.getTotal());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/cart/update/{cartItemId}")
    public ResponseEntity<Map<String, Object>> updateCartItem(@PathVariable Long cartItemId,
            @RequestParam int quantity) {
        Map<String, Object> response = new HashMap<>();
        try {
            Cart cart = shopService.updateQuantity(DEFAULT_USER_ID, cartItemId, quantity);
            response.put("success", true);
            response.put("cartCount", cart.getTotalItems());
            response.put("cartTotal", cart.getTotal());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity<Map<String, Object>> checkout() {
        Map<String, Object> response = new HashMap<>();
        try {
            Order order = checkoutService.checkout(DEFAULT_USER_ID);
            User user = userRepo.findById(DEFAULT_USER_ID).orElseThrow();
            response.put("success", true);
            response.put("orderId", order.getId());
            response.put("totalPaid", order.getTotalTrashPaid());
            response.put("newBalance", user.getTrashBalance());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/user/balance")
    public ResponseEntity<Map<String, Object>> getBalance() {
        User user = userRepo.findById(DEFAULT_USER_ID).orElseThrow();
        Cart cart = shopService.getOrCreateCart(DEFAULT_USER_ID);
        Map<String, Object> response = new HashMap<>();
        response.put("balance", user.getTrashBalance());
        response.put("cartCount", cart.getTotalItems());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/orders")
    public ResponseEntity<?> getOrders() {
        User user = userRepo.findById(DEFAULT_USER_ID).orElseThrow();
        return ResponseEntity.ok(orderRepo.findByUserOrderByCreatedAtDesc(user));
    }
}
