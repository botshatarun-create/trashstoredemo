package trashstore;

public class TrashStoreApplication {
    
}
package trashstore;

import jakarta.persistence.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
public class TrashStoreApplication {
    public static void main(String[] args) { SpringApplication.run(TrashStoreApplication.class, args); }

    @Bean
    public CommandLineRunner loadData(CustomerRepo cr, ProductRepo pr) {
        return args -> {
            cr.save(new Customer("Eco Warrior", 0));
            pr.save(new Product("Reusable Bottle", 50));
            pr.save(new Product("Bamboo Brush", 20));
        };
    }
}

@Entity class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) public Long id;
    public String name; public int points;
    public Customer() {} public Customer(String n, int p) { name = n; points = p; }
}

@Entity class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) public Long id;
    public String name; public int cost;
    public Product() {} public Product(String n, int c) { name = n; cost = c; }
}

interface CustomerRepo extends JpaRepository<Customer, Long> {}
interface ProductRepo extends JpaRepository<Product, Long> {}

@RestController @RequestMapping("/api")
class StoreController {
    private final CustomerRepo cr; private final ProductRepo pr;
    public StoreController(CustomerRepo cr, ProductRepo pr) { this.cr = cr; this.pr = pr; }

    @PostMapping("/deposit")
    public Customer deposit(@RequestParam Long customerId, @RequestParam String type, @RequestParam double weight) {
        Customer c = cr.findById(customerId).orElseThrow();
        c.points += type.equalsIgnoreCase("PLASTIC") ? (weight * 10) : (weight * 5);
        return cr.save(c);
    }

    @PostMapping("/buy")
    public String buy(@RequestParam Long customerId, @RequestParam Long productId) {
        Customer c = cr.findById(customerId).orElseThrow();
        Product p = pr.findById(productId).orElseThrow();
        if (c.points >= p.cost) { c.points -= p.cost; cr.save(c); return "Bought " + p.name + "!"; }
        return "Not enough points!";
    }
}