package com.tawrun.Repository;


import java.util.List;

import com.tawrun.model.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("paymentRepository")
public interface PaymentRepository extends JpaRepository<Payments, Long> {


	@Query(value = "select * from Payments e where e.packer_id=?1 order by e.dateofpayment",nativeQuery =true)
	List<Payments> findByPackerId(int id);

	@Query(value = "select sum(e.amount) from Payments e where e.packer_id=?1",nativeQuery = true)
	float sumOfAmount(int id);

}