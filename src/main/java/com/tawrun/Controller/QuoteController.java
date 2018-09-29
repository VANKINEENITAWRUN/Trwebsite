package com.tawrun.Controller;


import javax.persistence.Lob;
import javax.validation.Valid;

import com.tawrun.Repository.OrderRepository;
import com.tawrun.Repository.QuoteRepository;
import com.tawrun.Services.PackerServices;
import com.tawrun.model.Order;
import com.tawrun.model.Packer;
import com.tawrun.model.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/packer")
public class QuoteController {
	@Autowired
	private QuoteRepository quoteRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private PackerServices packerServices;


	@RequestMapping(value="/quote/{id}", method= RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Quote createQuote(@RequestBody Quote quote, @PathVariable Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Packer packer = packerServices.findPackerByEmail( auth.getName());

		System.out.println( " order packing_price	"+ quote.getPacking_price() );
		System.out.println( " order transportation price	"+ quote.getTransportation_price() );
		System.out.println( " order loading price	"+ quote.getLoading_price() );
		System.out.println( " order unloading price	"+ quote.getUnloading_price() );
		System.out.println( " order unpacking price		"+ quote.getUnpacking_price() );
		Order order=orderRepository.findById(id).get();
//TOdo make price only for selected services
		if(quoteRepository.alreadyQuoted( id,packer.getId() )) {return  null;}
		quote.setPacker( packer );
		quote.setOrder( order);
		return quoteRepository.save( quote );
	}
}
