package com.trashstore.demo.controller;

import com.trashstore.demo.model.Item;
import com.trashstore.demo.model.User;
import com.trashstore.demo.repository.ItemRepository;
import com.trashstore.demo.repository.OrderRepository;
import com.trashstore.demo.repository.UserRepository;
import com.trashstore.demo.service.ShopService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StoreController {

    private final ItemRepository itemRepo;
    private final UserRepository userRepo;
    private final ShopService shopService;
    private final OrderRepository orderRepo;

    public StoreController(ItemRepository itemRepo, UserRepository userRepo,
            ShopService shopService, OrderRepository orderRepo) {
        this.itemRepo = itemRepo;
        this.userRepo = userRepo;
        this.shopService = shopService;
        this.orderRepo = orderRepo;
    }

    private User getUser(Authentication auth) {
        return userRepo.findByUsername(auth.getName()).orElseThrow();
    }

    @GetMapping("/")
    public String home(Model model, Authentication auth) {
        User user = getUser(auth);
        model.addAttribute("user", user);
        model.addAttribute("featuredItems", itemRepo.findAll().stream().limit(4).toList());
        model.addAttribute("cart", shopService.getOrCreateCart(user.getId()));
        return "index";
    }

    @GetMapping("/shop")
    public String shop(Model model, Authentication auth,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search) {
        User user = getUser(auth);
        List<Item> items;
        if (search != null && !search.isBlank()) {
            items = itemRepo.findByNameContainingIgnoreCase(search);
        } else if (category != null && !category.isBlank()) {
            items = itemRepo.findByCategory(category);
        } else {
            items = itemRepo.findAll();
        }

        model.addAttribute("user", user);
        model.addAttribute("items", items);
        model.addAttribute("categories", itemRepo.findAll().stream()
                .map(Item::getCategory).distinct().sorted().toList());
        model.addAttribute("cart", shopService.getOrCreateCart(user.getId()));
        model.addAttribute("selectedCategory", category);
        model.addAttribute("search", search);
        return "shop";
    }

    @GetMapping("/shop/item/{id}")
    public String itemDetail(@PathVariable Long id, Model model, Authentication auth) {
        User user = getUser(auth);
        Item item = itemRepo.findById(id).orElse(null);
        if (item == null)
            return "redirect:/shop";

        List<Item> related = itemRepo.findByCategory(item.getCategory())
                .stream().filter(i -> !i.getId().equals(id)).limit(3).toList();

        model.addAttribute("user", user);
        model.addAttribute("item", item);
        model.addAttribute("relatedItems", related);
        model.addAttribute("cart", shopService.getOrCreateCart(user.getId()));
        return "item-detail";
    }

    @GetMapping("/cart")
    public String cart(Model model, Authentication auth) {
        User user = getUser(auth);
        model.addAttribute("user", user);
        model.addAttribute("cart", shopService.getOrCreateCart(user.getId()));
        return "cart";
    }

    @GetMapping("/orders")
    public String orders(Model model, Authentication auth) {
        User user = getUser(auth);
        model.addAttribute("user", user);
        model.addAttribute("cart", shopService.getOrCreateCart(user.getId()));
        return "orders";
    }

    @GetMapping("/profile")
    public String profile(Model model, Authentication auth) {
        User user = getUser(auth);
        long orderCount = orderRepo.findByUserOrderByCreatedAtDesc(user).size();
        model.addAttribute("user", user);
        model.addAttribute("orderCount", orderCount);
        model.addAttribute("cart", shopService.getOrCreateCart(user.getId()));
        return "profile";
    }
}
