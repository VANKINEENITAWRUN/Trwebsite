package com.tawrun.Repository;

import com.tawrun.model.Customer;
import com.tawrun.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


@Repository("imageRepository")
public interface ImageRepository extends JpaRepository<Image, Long> {

	@Query(value="select * from image i where i.image_id=?1",nativeQuery = true)
	Image findByPackerId(int id);
}
