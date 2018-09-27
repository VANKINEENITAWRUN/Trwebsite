package com.tawrun.Repository;

import java.util.ArrayList;

import com.tawrun.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository("orderRepository")
public interface OrderRepository extends JpaRepository<Order, Long> {

	ArrayList<Order> findByCustomerId(int customerId);
}
