package com.trashstore.demo.repository;

import com.trashstore.demo.model.Order;
import com.trashstore.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrderByCreatedAtDesc(User user);
}
