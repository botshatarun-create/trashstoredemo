package com.trashstore.demo.controller;

import com.trashstore.demo.model.Item;
import com.trashstore.demo.model.User;
import com.trashstore.demo.repository.ItemRepository;
import com.trashstore.demo.repository.UserRepository;
import com.trashstore.demo.service.ShopService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StoreController {

    private static final Long DEFAULT_USER_ID = 1L;

    private final ItemRepository itemRepo;
    private final UserRepository userRepo;
    private final ShopService shopService;

    public StoreController(ItemRepository itemRepo, UserRepository userRepo, ShopService shopService) {
        this.itemRepo = itemRepo;
        this.userRepo = userRepo;
        this.shopService = shopService;
    }

    private User getUser() {
        return userRepo.findById(DEFAULT_USER_ID).orElseThrow();
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("user", getUser());
        model.addAttribute("featuredItems", itemRepo.findAll().stream().limit(4).toList());
        model.addAttribute("cart", shopService.getOrCreateCart(DEFAULT_USER_ID));
        return "index";
    }

    @GetMapping("/shop")
    public String shop(Model model,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search) {
        List<Item> items;
        if (search != null && !search.isBlank()) {
            items = itemRepo.findByNameContainingIgnoreCase(search);
        } else if (category != null && !category.isBlank()) {
            items = itemRepo.findByCategory(category);
        } else {
            items = itemRepo.findAll();
        }

        model.addAttribute("user", getUser());
        model.addAttribute("items", items);
        model.addAttribute("categories", itemRepo.findAll().stream()
                .map(Item::getCategory).distinct().sorted().toList());
        model.addAttribute("cart", shopService.getOrCreateCart(DEFAULT_USER_ID));
        model.addAttribute("selectedCategory", category);
        model.addAttribute("search", search);
        return "shop";
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        model.addAttribute("user", getUser());
        model.addAttribute("cart", shopService.getOrCreateCart(DEFAULT_USER_ID));
        return "cart";
    }

    @GetMapping("/orders")
    public String orders(Model model) {
        model.addAttribute("user", getUser());
        model.addAttribute("cart", shopService.getOrCreateCart(DEFAULT_USER_ID));
        return "orders";
    }
}
