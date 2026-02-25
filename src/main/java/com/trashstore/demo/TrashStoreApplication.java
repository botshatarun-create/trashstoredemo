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
                        // Curated Unsplash photos — matched specifically to each product
                        itemRepo.save(new Item("Vintage Glass Bottle",
                                        "A beautifully aged glass bottle from the 80s. Perfect for art projects or decoration.",
                                        new BigDecimal("5.0"), 20,
                                        "https://images.unsplash.com/photo-1558618066-fcd25c85cd64?w=400&h=300&fit=crop&auto=format",
                                        "Glassware"));

                        itemRepo.save(new Item("Retro CRT Monitor",
                                        "A chunky CRT monitor from the early 2000s. Still powers on! Great for retro gaming.",
                                        new BigDecimal("35.0"), 5,
                                        "https://images.unsplash.com/photo-1547394765-185e1e68f34e?w=400&h=300&fit=crop&auto=format",
                                        "Electronics"));

                        itemRepo.save(new Item("Scrap Metal Sculpture",
                                        "Handcrafted sculpture made entirely from salvaged scrap metal. Each piece is unique.",
                                        new BigDecimal("50.0"), 3,
                                        "https://images.unsplash.com/photo-1558591710-4b4a1ae0f0b9?w=400&h=300&fit=crop&auto=format",
                                        "Art"));

                        itemRepo.save(new Item("Broken Vinyl Records Bundle",
                                        "Pack of 10 broken vinyl records. Perfect for mosaics, wall art, or retro decor.",
                                        new BigDecimal("8.0"), 15,
                                        "https://images.unsplash.com/photo-1603048588665-791ca8aea617?w=400&h=300&fit=crop&auto=format",
                                        "Music"));

                        itemRepo.save(new Item("Rusty Bicycle Bell",
                                        "Classic brass bicycle bell with beautiful rust patina. Rings with a soulful 'ding'.",
                                        new BigDecimal("3.0"), 30,
                                        "https://images.unsplash.com/photo-1571068316344-75bc76f77890?w=400&h=300&fit=crop&auto=format",
                                        "Parts"));

                        itemRepo.save(new Item("Old Rotary Phone",
                                        "A fully intact rotary telephone from the 1960s. Functional and incredibly stylish.",
                                        new BigDecimal("28.0"), 8,
                                        "https://images.unsplash.com/photo-1574620023453-d0c44bcb9fa5?w=400&h=300&fit=crop&auto=format",
                                        "Electronics"));

                        itemRepo.save(new Item("Wooden Pallet Set",
                                        "3 sturdy reclaimed wooden pallets. Perfect for DIY furniture projects.",
                                        new BigDecimal("15.0"), 12,
                                        "https://images.unsplash.com/photo-1601598851547-4302969d0614?w=400&h=300&fit=crop&auto=format",
                                        "Wood"));

                        itemRepo.save(new Item("Circuit Board Art",
                                        "Salvaged motherboard repurposed into wall art. Mounted and ready to hang.",
                                        new BigDecimal("22.0"), 7,
                                        "https://images.unsplash.com/photo-1518770660439-4636190af475?w=400&h=300&fit=crop&auto=format",
                                        "Electronics"));

                        itemRepo.save(new Item("Antique Typewriter",
                                        "1950s mechanical typewriter. All keys functional. A writer's dream conversation piece.",
                                        new BigDecimal("60.0"), 2,
                                        "https://images.unsplash.com/photo-1588412079929-790b9f593d8e?w=400&h=300&fit=crop&auto=format",
                                        "Office"));

                        itemRepo.save(new Item("Glass Jar Collection",
                                        "Assorted vintage glass jars of various sizes. Clean and ready for your projects.",
                                        new BigDecimal("4.0"), 50,
                                        "https://images.unsplash.com/photo-1584589167171-541ce45f1eea?w=400&h=300&fit=crop&auto=format",
                                        "Glassware"));

                        itemRepo.save(new Item("Vintage Camera",
                                        "Film camera from the 70s. Still takes photos! Comes with unexposed film roll.",
                                        new BigDecimal("45.0"), 4,
                                        "https://images.unsplash.com/photo-1516035069371-29a1b244cc32?w=400&h=300&fit=crop&auto=format",
                                        "Electronics"));

                        itemRepo.save(new Item("Reclaimed Wood Shelf",
                                        "Beautiful weathered wood shelf, handmade from reclaimed lumber. Rustic charm guaranteed.",
                                        new BigDecimal("30.0"), 6,
                                        "https://images.unsplash.com/photo-1555041469-a586c61ea9bc?w=400&h=300&fit=crop&auto=format",
                                        "Wood"));

                        // Demo admin/owner user — starts at 0 balance
                        User admin = new User("trashmaster", "trash@store.com", BigDecimal.ZERO);
                        admin.setPassword(passwordEncoder.encode("trash123"));
                        admin.setRole("ADMIN");
                        userRepo.save(admin);
                };
        }
}
