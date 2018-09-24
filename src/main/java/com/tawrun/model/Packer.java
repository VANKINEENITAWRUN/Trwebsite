package com.tawrun.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
@Entity
@Table(name = "packer")
public class Packer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "packer_id")
	private int id;
	@Column(name = "email")
	@Email(message = "*Please provide a valid Email")
	@NotEmpty(message = "*Please provide an email")
	private String email;
	@Column(name = "password")
	@Length(min = 5, message = "*Your password must have at least 5 characters")
	@NotEmpty(message = "*Please provide your password")
	private String password;
	@Column(name = "company_name")
	@NotEmpty(message = "*Please provide your company name")
	private String name;

	@Column(name = "phone_number")
	@Size(min=10,max =10,message="*Please provide your company name")
	@NotEmpty(message = "*Please provide your company name")
	private String phonenumber;

	@Column(name="pic")
	@Lob
	@NotNull(message="Please upload your certificate")
	private byte[] pic;
}
