package com.trashstore.demo;

import com.trashstore.demo.model.*;
import com.trashstore.demo.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@SpringBootApplication
public class TrashStoreApplication {

        public static void main(String[] args) {
                SpringApplication.run(TrashStoreApplication.class, args);
        }

        @Bean
        CommandLineRunner seedData(ItemRepository itemRepo, UserRepository userRepo, PasswordEncoder passwordEncoder) {
                return args -> {
                        // Stable images from picsum.photos using seed parameter (always same image)
                        itemRepo.save(new Item("Vintage Glass Bottle",
                                        "A beautifully aged glass bottle from the 80s. Perfect for art projects or decoration.",
                                        new BigDecimal("5.0"), 20, "https://picsum.photos/seed/bottle1/400/300",
                                        "Glassware"));

                        itemRepo.save(new Item("Retro CRT Monitor",
                                        "A chunky CRT monitor from the early 2000s. Still powers on! Great for retro gaming.",
                                        new BigDecimal("35.0"), 5, "https://picsum.photos/seed/monitor2/400/300",
                                        "Electronics"));

                        itemRepo.save(new Item("Scrap Metal Sculpture",
                                        "Handcrafted sculpture made entirely from salvaged scrap metal. Each piece is unique.",
                                        new BigDecimal("50.0"), 3, "https://picsum.photos/seed/sculpture3/400/300",
                                        "Art"));

                        itemRepo.save(new Item("Broken Vinyl Records Bundle",
                                        "Pack of 10 broken vinyl records. Perfect for mosaics, wall art, or retro decor.",
                                        new BigDecimal("8.0"), 15, "https://picsum.photos/seed/vinyl4/400/300",
                                        "Music"));

                        itemRepo.save(new Item("Rusty Bicycle Bell",
                                        "Classic brass bicycle bell with beautiful rust patina. Rings with a soulful 'ding'.",
                                        new BigDecimal("3.0"), 30, "https://picsum.photos/seed/bell5/400/300",
                                        "Parts"));

                        itemRepo.save(new Item("Old Rotary Phone",
                                        "A fully intact rotary telephone from the 1960s. Functional and incredibly stylish.",
                                        new BigDecimal("28.0"), 8, "https://picsum.photos/seed/phone6/400/300",
                                        "Electronics"));

                        itemRepo.save(new Item("Wooden Pallet Set",
                                        "3 sturdy reclaimed wooden pallets. Perfect for DIY furniture projects.",
                                        new BigDecimal("15.0"), 12, "https://picsum.photos/seed/pallet7/400/300",
                                        "Wood"));

                        itemRepo.save(new Item("Circuit Board Art",
                                        "Salvaged motherboard repurposed into wall art. Mounted and ready to hang.",
                                        new BigDecimal("22.0"), 7, "https://picsum.photos/seed/circuit8/400/300",
                                        "Electronics"));

                        itemRepo.save(new Item("Antique Typewriter",
                                        "1950s mechanical typewriter. All keys functional. A writer's dream conversation piece.",
                                        new BigDecimal("60.0"), 2, "https://picsum.photos/seed/typewriter9/400/300",
                                        "Office"));

                        itemRepo.save(new Item("Glass Jar Collection",
                                        "Assorted vintage glass jars of various sizes. Clean and ready for your projects.",
                                        new BigDecimal("4.0"), 50, "https://picsum.photos/seed/jars10/400/300",
                                        "Glassware"));

                        itemRepo.save(new Item("Vintage Camera",
                                        "Film camera from the 70s. Still takes photos! Comes with unexposed film roll.",
                                        new BigDecimal("45.0"), 4, "https://picsum.photos/seed/camera11/400/300",
                                        "Electronics"));

                        itemRepo.save(new Item("Reclaimed Wood Shelf",
                                        "Beautiful weathered wood shelf, handmade from reclaimed lumber. Rustic charm guaranteed.",
                                        new BigDecimal("30.0"), 6, "https://picsum.photos/seed/shelf12/400/300",
                                        "Wood"));

                        // Demo admin/owner user — starts at 0 balance
                        User admin = new User("trashmaster", "trash@store.com", BigDecimal.ZERO);
                        admin.setPassword(passwordEncoder.encode("trash123"));
                        admin.setRole("ADMIN");
                        userRepo.save(admin);
                };
        }
}
