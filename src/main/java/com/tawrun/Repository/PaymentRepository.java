package com.tawrun.Repository;


import com.tawrun.model.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("paymentRepository")
public interface PaymentRepository extends JpaRepository<Payments, Long> {

}