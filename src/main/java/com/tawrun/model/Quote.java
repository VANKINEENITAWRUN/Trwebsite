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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity
@Table(name = "quote")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Quote {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "quote_id")
	private Long id;


	@Column(name = "loading_price")
	@Range(min = 0l, message = " provide your loading price")
	private int loading_price;

	@Column(name = "unloading_price")
	@Range(min = 0l, message = "Please  provide your unloading price")
	private int unloading_price;

	@Column(name = "packing_price")
	@NotBlank(message = "*Please provide your packing price")
	private String packing_price;

	@Column(name = "unpacking_price")
	@NotBlank(message = "*Please provide your unpacking price")
	private String unpacking_price;

	@Column(name = "transportation_price")
	@NotBlank(message = "*Please provide your transportation price")
	private String transportation_price;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "order_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Order order;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "packer_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Packer packer;



}
