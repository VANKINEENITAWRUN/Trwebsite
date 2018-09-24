package com.tawrun.model;


import java.util.Date;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity
@Table(name = "orders")
public class Order  {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_id")
	private Long id;

	@Column(name = "dateofShifting")
	@Future(message="Only the Future is valid")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateofshifting;

	@ElementCollection
	@CollectionTable(
			name = "shifting_Items",
			joinColumns = @JoinColumn(name = "order_id")
	)
	private List<String> shifting_items;
	@ElementCollection
	@CollectionTable(
			name = "services",
			joinColumns = @JoinColumn(name = "order_id")
	)
	private List<String> services;


	@Column(name = "loading_floorno")
	@Range(min = 0l, message = " provide your floor number")
	private int loading_floorno;

	@Column(name = "unloading_floorno")
	@Range(min = 0l, message = "Please  provide your floor number")
	private int unloading_floorno;

	@Column(name = "unloading_location")
	@NotBlank(message = "*Please provide your location")
	private String unloading_location;

	@Column(name = "loading_location")
	@NotBlank(message = "*Please provide your location")
	private String loading_location;

	@Column(name = "unloading_city")
	@NotBlank(message = "*Please provide your location")
	private String unloading_city;

	@Column(name = "loading_city")
	@NotBlank(message = "*Please provide your location")
	private String loading_city;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Customer customer;


}
