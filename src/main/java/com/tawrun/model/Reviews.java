package com.tawrun.model;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@Entity
@Table(name = "reviews")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Reviews {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "review_id")
	private int id;



	@Column(name="rating")
	@Range(min = 0,max = 5 ,message = "please provide the Rating")
	private int rating;

	@Column(name = "review")
	@NotBlank(message = "*Please provide your Review")
	private String review;

	@JsonIgnore
	@OneToOne(mappedBy = "review")
	private Order order;

//	@JsonIgnore
//	@ManyToOne(fetch = FetchType.LAZY, optional = false)
//	@JoinColumn(name = "packer_id", nullable = false)
//	private Packer packer;


}
