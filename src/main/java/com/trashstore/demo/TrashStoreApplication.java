package com.trashstore.demo;

import com.trashstore.demo.model.*;
import com.trashstore.demo.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class TrashStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrashStoreApplication.class, args);
    }

    @Bean
    CommandLineRunner seedData(ItemRepository itemRepo, UserRepository userRepo) {
        return args -> {
            // Seed items
            itemRepo.save(new Item("Vintage Glass Bottle",
                    "A beautifully aged glass bottle from the 80s. Perfect for art projects or decoration.",
                    new BigDecimal("5.0"), 20, "https://images.unsplash.com/photo-1612528443702-f6741f70a049?w=400",
                    "Glassware"));

            itemRepo.save(new Item("Retro CRT Monitor",
                    "A chunky CRT monitor from the early 2000s. Still powers on! Great for retro gaming.",
                    new BigDecimal("35.0"), 5, "https://images.unsplash.com/photo-1593640408182-31c228d91eac?w=400",
                    "Electronics"));

            itemRepo.save(new Item("Scrap Metal Sculpture",
                    "Handcrafted sculpture made entirely from salvaged scrap metal. Each piece is unique.",
                    new BigDecimal("50.0"), 3, "https://images.unsplash.com/photo-1581781870027-04212e06e0a7?w=400",
                    "Art"));

            itemRepo.save(new Item("Broken Vinyl Records Bundle",
                    "Pack of 10 broken vinyl records. Perfect for mosaics, wall art, or retro decor.",
                    new BigDecimal("8.0"), 15, "https://images.unsplash.com/photo-1603048588665-791ca8aea617?w=400",
                    "Music"));

            itemRepo.save(new Item("Rusty Bicycle Bell",
                    "Classic brass bicycle bell with beautiful rust patina. Rings with a soulful 'ding'.",
                    new BigDecimal("3.0"), 30, "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=400",
                    "Parts"));

            itemRepo.save(new Item("Old Rotary Phone",
                    "A fully intact rotary telephone from the 1960s. Functional and incredibly stylish.",
                    new BigDecimal("28.0"), 8, "https://images.unsplash.com/photo-1574620023453-d0c44bcb9fa5?w=400",
                    "Electronics"));

            itemRepo.save(new Item("Wooden Pallet Set",
                    "3 sturdy reclaimed wooden pallets. Perfect for DIY furniture projects.",
                    new BigDecimal("15.0"), 12, "https://images.unsplash.com/photo-1565372781813-7a5c5b8f5c6a?w=400",
                    "Wood"));

            itemRepo.save(new Item("Circuit Board Art",
                    "Salvaged motherboard repurposed into wall art. Mounted and ready to hang.",
                    new BigDecimal("22.0"), 7, "https://images.unsplash.com/photo-1518770660439-4636190af475?w=400",
                    "Electronics"));

            itemRepo.save(new Item("Antique Typewriter",
                    "1950s mechanical typewriter. All keys functional. A writer's dream conversation piece.",
                    new BigDecimal("60.0"), 2, "https://images.unsplash.com/photo-1588412079929-790b9f593d8e?w=400",
                    "Office"));

            itemRepo.save(new Item("Glass Jar Collection",
                    "Assorted vintage glass jars of various sizes. Clean and ready for your projects.",
                    new BigDecimal("4.0"), 50, "https://images.unsplash.com/photo-1558618047-3c8c76ca7d13?w=400",
                    "Glassware"));

            itemRepo.save(new Item("Vintage Camera",
                    "Film camera from the 70s. Still takes photos! Comes with unexposed film roll.",
                    new BigDecimal("45.0"), 4, "https://images.unsplash.com/photo-1516035069371-29a1b244cc32?w=400",
                    "Electronics"));

            itemRepo.save(new Item("Reclaimed Wood Shelf",
                    "Beautiful weathered wood shelf, handmade from reclaimed lumber. Rustic charm guaranteed.",
                    new BigDecimal("30.0"), 6, "https://images.unsplash.com/photo-1555041469-a586c61ea9bc?w=400",
                    "Wood"));

            // Seed default user
            User user = new User("trashmaster", "trash@store.com", new BigDecimal("500.0"));
            userRepo.save(user);
        };
    }
}
