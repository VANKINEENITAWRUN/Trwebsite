package com.tawrun.Repository;

import com.tawrun.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("customerRepository")
public interface CustomerRepository extends JpaRepository<Customer, Long> {


	@Query(value="select * from customer c where c.email=?1",nativeQuery = true)
	Customer findByEmail(String email);

	@Query(value = "select * from customer c where c.user_id=?1",nativeQuery = true)
	Customer findById(int id);
}