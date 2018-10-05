package com.tawrun.model;


import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Range;

import lombok.Data;

@Data
public class ReviewInfo {

	private String unloading_city;


	private String loading_city;


	private int rating;

	private String review;

	public ReviewInfo( String unloading_city,String loading_city,int rating,String review ){
		this.loading_city=loading_city;
		this.rating=rating;
		this.review=review;
		this.unloading_city=unloading_city;

	}
}
