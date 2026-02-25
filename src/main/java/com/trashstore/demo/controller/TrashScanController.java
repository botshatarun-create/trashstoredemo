package com.trashstore.demo.controller;

import com.trashstore.demo.model.User;
import com.trashstore.demo.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.Map;

@Controller
@RequestMapping("/trash-scan")
public class TrashScanController {

    // Trash items the user can "scan" to add balance
    private static final Map<String, BigDecimal> TRASH_TYPES = Map.of(
            "plastic_bottle", new BigDecimal("0.5"),
            "glass_bottle", new BigDecimal("1.0"),
            "aluminium_can", new BigDecimal("0.3"),
            "cardboard_box", new BigDecimal("2.0"),
            "old_newspaper", new BigDecimal("0.8"),
            "electronic_part", new BigDecimal("5.0"),
            "metal_scrap", new BigDecimal("3.0"),
            "wood_plank", new BigDecimal("4.0"));

    private final UserRepository userRepo;

    public TrashScanController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public String trashScanPage(Model model, Authentication auth) {
        User user = userRepo.findByUsername(auth.getName()).orElseThrow();
        model.addAttribute("user", user);
        model.addAttribute("trashTypes", TRASH_TYPES);
        return "trash-scan";
    }

    @PostMapping("/submit")
    public String submitScan(@RequestParam String trashType,
            @RequestParam(defaultValue = "1") int quantity,
            Authentication auth,
            RedirectAttributes redirectAttributes) {
        if (quantity < 1 || quantity > 100) {
            redirectAttributes.addFlashAttribute("error", "Quantity must be between 1 and 100.");
            return "redirect:/trash-scan";
        }

        BigDecimal kgPerUnit = TRASH_TYPES.get(trashType);
        if (kgPerUnit == null) {
            redirectAttributes.addFlashAttribute("error", "Unknown trash type.");
            return "redirect:/trash-scan";
        }

        BigDecimal earned = kgPerUnit.multiply(new BigDecimal(quantity));
        User user = userRepo.findByUsername(auth.getName()).orElseThrow();
        user.setTrashBalance(user.getTrashBalance().add(earned));
        userRepo.save(user);

        redirectAttributes.addFlashAttribute("success",
                "Scanned " + quantity + " x " + trashType.replace("_", " ") +
                        " and earned " + earned + "kg of trash! New balance: " + user.getTrashBalance() + "kg.");
        return "redirect:/trash-scan";
    }
}
