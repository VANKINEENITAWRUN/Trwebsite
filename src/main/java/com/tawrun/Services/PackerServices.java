package com.tawrun.Services;


import java.util.Arrays;
import java.util.HashSet;

import javax.validation.Valid;

import com.tawrun.Repository.ImageRepository;
import com.tawrun.Repository.PackerRepository;
import com.tawrun.Repository.RoleRepository;
import com.tawrun.model.Image;
import com.tawrun.model.Packer;
import com.tawrun.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("packerService")
public class PackerServices {

	private RoleRepository roleRepository;
	private PackerRepository packerRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	private ImageRepository imageRepository;

	@Autowired
	public PackerServices(PackerRepository customerRepository,
						   RoleRepository roleRepository,ImageRepository imageRepository,
						   BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.packerRepository = customerRepository;
		this.roleRepository=roleRepository;
		this.imageRepository=imageRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public Packer findPackerByEmail(String email) {
		return packerRepository.findByEmail(email);
	}
	public Packer findPackerById(int id){
		return packerRepository.findById( id );
	}
	public void savePacker(Packer packer, byte[] image) {
		Image image11=new Image();
		image11.setImage( image );
		Image i=imageRepository.save( image11);
//		int id =i.getId();
		packer.setImage( i );
		packer.setPassword(bCryptPasswordEncoder.encode(packer.getPassword()));
		packer.setActive(1);
		Role packerRole = roleRepository.findByRole( "PACKER");
		packer.setRoles(new HashSet<Role>( Arrays.asList(packerRole) ));


		packerRepository.save(packer);
	}
}
