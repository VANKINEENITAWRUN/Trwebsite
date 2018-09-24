package com.tawrun.Services;


import java.util.Arrays;
import java.util.HashSet;

import com.tawrun.Repository.PackerRepository;
import com.tawrun.model.Packer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("packerService")
public class PackerServices {

	private PackerRepository packerRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public PackerServices(PackerRepository customerRepository,
						   BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.packerRepository = customerRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public Packer findPackerByEmail(String email) {
		return packerRepository.findByEmail(email);
	}
	public Packer findPackerById(int id){
		return packerRepository.findById( id );
	}
	public void savePacker(Packer packer) {
		packer.setPassword(bCryptPasswordEncoder.encode(packer.getPassword()));


		packerRepository.save(packer);
	}
}
