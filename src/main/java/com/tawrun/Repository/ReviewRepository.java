package com.tawrun.Repository;

import java.util.List;

import com.tawrun.model.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("reviewRepository")
public interface ReviewRepository extends JpaRepository<Reviews, Long> {

	List<Reviews> findByPackerId(int id);


}