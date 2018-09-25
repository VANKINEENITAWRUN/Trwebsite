package com.tawrun.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
@Table(name="image")
public class Image {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "image_id")

	private int id;

	@Lob
	@Column(name="image")
	@NotEmpty(message = "*Please provide any Certificate")
	private byte [] image ;


	@OneToOne(mappedBy = "image")
	private Packer packer;

}
