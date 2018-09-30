package com.tawrun.model;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Range;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity
@Table(name = "Transactions")
public class Transaction {

    // TODO define it
    public Transaction(){

    }

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "transaction_id")
	private int id;

	@Column(name ="amount")
	private int amount;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "quote_id", nullable = false)
	private Quote quote;

	@Column(name = "dateofpayment")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateofpayment;


	public PayInfo toPayInfo(){

		if(amount>0)
			return new PayInfo( this.amount, "Amount refunded ", this.dateofpayment);
		else
			return new PayInfo( this.amount, "Amount debited ", this.dateofpayment);

	}
}
