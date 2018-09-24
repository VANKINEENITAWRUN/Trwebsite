package com.tawrun.Repository;

import com.tawrun.model.Packer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("packerRepository")
public interface PackerRepository extends JpaRepository<Packer, Long> {
	Packer findByEmail(String email);
	Packer findById(int id);
}