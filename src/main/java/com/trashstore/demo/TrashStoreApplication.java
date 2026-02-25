package com.trashstore.demo;

import com.trashstore.demo.model.Item;
import com.trashstore.demo.repository.ItemRepository;
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
    CommandLineRunner initData(ItemRepository repo) {
        return args -> {
            repo.save(new Item("Broken Phone", "Old phone, works intermittently", new BigDecimal("12.50")));
            repo.save(new Item("Glass Jar", "Clean glass jar good for crafts", new BigDecimal("1.25")));
            repo.save(new Item("Wood Pallet", "Sturdy pallet, needs cleaning", new BigDecimal("25.00")));
        };
    }
}
