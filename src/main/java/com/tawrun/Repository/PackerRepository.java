package com.tawrun.Repository;

import com.tawrun.model.Packer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("packerRepository")
public interface PackerRepository extends JpaRepository<Packer, Long> {

	@Query(value="select * from packer p where p.email=?1",nativeQuery = true)
	Packer findByEmail(String email);
//	@Query(value = "select * from packer p where p.packer_id=?1",nativeQuery = true)
	Packer findById(int id);
}