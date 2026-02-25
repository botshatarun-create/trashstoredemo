package com.trashstore.demo.controller;

import com.trashstore.demo.model.User;
import com.trashstore.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
public class AuthController {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error,
            @RequestParam(required = false) String logout,
            Model model) {
        if (error != null)
            model.addAttribute("error", "Invalid username or password.");
        if (logout != null)
            model.addAttribute("message", "You've been logged out successfully.");
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            RedirectAttributes redirectAttributes) {
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Passwords do not match.");
            return "redirect:/register";
        }
        if (userRepo.findByUsername(username).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Username already taken.");
            return "redirect:/register";
        }

        User user = new User(username, email, new BigDecimal("100.0")); // Start with 100kg trash balance
        user.setPassword(passwordEncoder.encode(password));
        userRepo.save(user);

        redirectAttributes.addFlashAttribute("success",
                "Account created! You start with 100kg of trash balance. Login now!");
        return "redirect:/login";
    }
}
