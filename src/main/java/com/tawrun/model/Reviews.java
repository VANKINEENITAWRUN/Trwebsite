package com.tawrun.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Range;

import lombok.Data;

@Data
@Entity
@Table(name = "reviews")
public class Reviews {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "review_id")
	private Long id;

	@Column(name = "unloading_city")
	@NotBlank(message = "*Please provide your location")
	private String unloading_city;

	@Column(name = "loading_city")
	@NotBlank(message = "*Please provide your location")
	private String loading_city;

	@Column(name="rating")
	@Range(min = 0,max = 5 ,message = "please provide the Rating")
	private int rating;

	@Column(name = "review")
	@NotBlank(message = "*Please provide your Review")
	private String review;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "packer_id", nullable = false)
	private Packer packer;


}
