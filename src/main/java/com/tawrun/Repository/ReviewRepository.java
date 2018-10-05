package com.tawrun.Repository;

import java.util.List;

import com.tawrun.model.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository("reviewRepository")
public interface ReviewRepository extends JpaRepository<Reviews, Long> {

	@Query(value = "select r.review_id,r.rating,r.review from (select * from orders q where q.packer_id=?1 ) as foo natural  join reviews r",nativeQuery = true)
	List<Reviews> findByPackerId(int id);


//	@Query(value = "insert into reviews(rating,packer_id,review) values (?1,?3,?2)",nativeQuery = true)
//	Reviews save (int rating,String review,int packer_id);


}