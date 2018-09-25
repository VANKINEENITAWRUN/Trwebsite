package com.tawrun.Repository;

import com.tawrun.model.Customer;
import com.tawrun.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service("imageRepository")
public interface ImageRepository extends JpaRepository<Image, Long> {
}
