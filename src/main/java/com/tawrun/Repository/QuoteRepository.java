package com.tawrun.Repository;


import java.util.List;

import com.tawrun.model.Order;
import com.tawrun.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("quoteRepository")
public interface QuoteRepository  extends JpaRepository<Quote, Long> {

	@Query(value = "select count(e)>0 from Quote e where e.packer_id=?2 and e.order_id=?1" ,nativeQuery = true)
	boolean alreadyQuoted( Long order_id, int packer_id);



	List<Quote> findByOrderId(Long id);
}
