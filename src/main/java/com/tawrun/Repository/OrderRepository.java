package com.tawrun.Repository;

import java.util.ArrayList;

import com.tawrun.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

	ArrayList<Order> findByCustomerId(int customerId);
}
