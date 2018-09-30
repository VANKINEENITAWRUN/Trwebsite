package com.tawrun.Repository;

import java.util.List;

import com.tawrun.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository("transactionRepository")
public interface TransactionRepository extends JpaRepository<Transaction, Long> {


	@Query(value="select * from (select * from quote q where q.packer_id=?1 ) as foo natural join Transactions ",nativeQuery = true)
	List<Transaction> findByPackerId(int id);

	@Query(value="select sum(*) from (select * from quote q where q.packer_id=?1 ) as foo natural join Transactions ",nativeQuery = true)
	int sumOfAmount(int packerid);


}
