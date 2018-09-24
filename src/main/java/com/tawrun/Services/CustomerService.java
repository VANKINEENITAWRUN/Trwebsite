package com.tawrun.Services;

import java.util.Arrays;
import java.util.HashSet;

import com.tawrun.Repository.CustomerRepository;
import com.tawrun.Repository.RoleRepository;
import com.tawrun.model.Customer;
import com.tawrun.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("customerService")
public class CustomerService {

	private CustomerRepository customerRepository;
	private RoleRepository roleRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public CustomerService(CustomerRepository customerRepository,
					   RoleRepository roleRepository,
					   BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.customerRepository = customerRepository;
		this.roleRepository = roleRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public Customer findCustomerByEmail(String email) {
		return customerRepository.findByEmail(email);
	}
	public Customer findCustomerById(int id){
		return customerRepository.findById( id );
	}
	public void saveCustomer(Customer customer) {
		customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
		customer.setActive(1);
		Role customerRole = roleRepository.findByRole("ADMIN");
		customer.setRoles(new HashSet<Role>( Arrays.asList(customerRole) ));

		customerRepository.save(customer);
	}

}