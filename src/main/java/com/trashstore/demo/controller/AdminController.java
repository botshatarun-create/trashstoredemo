package com.trashstore.demo.controller;

import com.trashstore.demo.model.Order;
import com.trashstore.demo.model.User;
import com.trashstore.demo.repository.ItemRepository;
import com.trashstore.demo.repository.OrderRepository;
import com.trashstore.demo.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final OrderRepository orderRepo;
    private final UserRepository userRepo;
    private final ItemRepository itemRepo;

    public AdminController(OrderRepository orderRepo, UserRepository userRepo, ItemRepository itemRepo) {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
        this.itemRepo = itemRepo;
    }

    private User getUser(Authentication auth) {
        return userRepo.findByUsername(auth.getName()).orElseThrow();
    }

    @GetMapping
    public String dashboard(Model model, Authentication auth) {
        User user = getUser(auth);
        if (!user.isAdmin())
            return "redirect:/";

        List<Order> orders = orderRepo.findAll();
        long totalUsers = userRepo.count();
        long totalItems = itemRepo.count();
        BigDecimal totalRevenue = orders.stream()
                .map(Order::getTotalTrashPaid)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("user", user);
        model.addAttribute("orders", orders);
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("totalOrders", orders.size());
        return "admin";
    }

    @PostMapping("/orders/{id}/status")
    public String updateStatus(@PathVariable Long id,
            @RequestParam String status,
            Authentication auth,
            RedirectAttributes redirect) {
        User user = getUser(auth);
        if (!user.isAdmin())
            return "redirect:/";

        orderRepo.findById(id).ifPresent(order -> {
            order.setStatus(status.toUpperCase());
            orderRepo.save(order);
        });
        redirect.addFlashAttribute("success", "Order #" + id + " status updated to " + status);
        return "redirect:/admin";
    }
}
