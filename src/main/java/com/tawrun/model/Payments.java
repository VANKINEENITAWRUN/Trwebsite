package com.tawrun.model;


import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity
@Table(name = "Payments")
public class Payments {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "payment_id")
	private int id;

	@Column(name = "dateofpayment")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateofpayment;

	@Column(name = "transaction_id")
	@NotBlank(message = "*Please provide the Transaction Id")
	private String transactionId;

	@Column(name ="amount")
	@Range(min = 0l, message = " provide your price")
	private float amount;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "packer_id", nullable = false)
	private Packer packer;

	public PayInfo toPayInfo(){

		return new PayInfo( this.amount, "Amount Deposited from Paypal", this.dateofpayment);
	}


}
