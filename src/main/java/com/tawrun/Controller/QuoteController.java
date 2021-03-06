package com.tawrun.Controller;


import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Lob;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.tawrun.Repository.ImageRepository;
import com.tawrun.Repository.OrderRepository;
import com.tawrun.Repository.PaymentRepository;
import com.tawrun.Repository.QuoteRepository;
import com.tawrun.Repository.ReviewRepository;
import com.tawrun.Repository.TransactionRepository;
import com.tawrun.Services.PackerServices;
import com.tawrun.model.Image;
import com.tawrun.model.Order;
import com.tawrun.model.Packer;
import com.tawrun.model.Payments;
import com.tawrun.model.Quote;
import com.tawrun.model.Reviews;
import com.tawrun.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/packer")
@Transactional()
public class QuoteController {
	@Autowired
	private QuoteRepository quoteRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ImageRepository imageRepository;

	@Autowired
	private PackerServices packerServices;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private TransactionRepository transactionRepository;


	@RequestMapping(value="/quote/{id}", method= RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Quote createQuote(@RequestBody Quote quote, @PathVariable int id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Packer packer = packerServices.findPackerByEmail( auth.getName());

		System.out.println( " order packing_price	"+ quote.getPacking_price() );
		System.out.println( " order transportation price	"+ quote.getTransportation_price() );
		System.out.println( " order loading price	"+ quote.getLoading_price() );
		System.out.println( " order unloading price	"+ quote.getUnloading_price() );
		System.out.println( " order unpacking price		"+ quote.getUnpacking_price() );

		Order order=orderRepository.findById(id);
//TOdo make price only for selected services
		if(quoteRepository.alreadyQuoted( id,packer.getId() )) {return  null;}

		float f=0;

		float a;
		try {
			a=paymentRepository.sumOfAmount( packer.getId() );
		}catch (Exception e){
			 a=0;
		}
		float b;
		try{
			b=transactionRepository.sumOfAmount( packer.getId() );
		}catch (Exception e){
			b=0;
		}


		f=a+b;

		if(f<200) return null;
		quote.setPacker( packer );
		quote.setOrder( order);
		Quote q=quoteRepository.save( quote );
			if(q!=null){

				Transaction transaction=new Transaction()	;
				transaction.setAmount(-200 );
				transaction.setQuote( q );
				transaction.setDateofpayment( Calendar.getInstance().getTime()  );
				transactionRepository.save(transaction  );
				return q;
			}
		return null;
	}

	@RequestMapping(value = "/profile",method = RequestMethod.GET)
	public ModelAndView showReviews(){

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Packer packer = packerServices.findPackerByEmail(auth.getName());
		int id=packer.getId();

		ModelAndView modelAndView = new ModelAndView();
		ArrayList<Reviews> reviews=new ArrayList<>( reviewRepository.findByPackerId( id ));
		modelAndView.addObject("reviews", reviews);
		modelAndView.addObject("packer", packer);
		modelAndView.setViewName("Packer_profile");
		return modelAndView;
	}

	@RequestMapping(value = "/download/{id}",method = RequestMethod.GET,  produces = MediaType.IMAGE_PNG_VALUE)
	public @ResponseBody byte[] download(@PathVariable int id, HttpServletResponse response){

		Image doc= imageRepository.findByPackerId( id );
		try {
			response.setHeader("Content-Disposition", "inline;filename=\"" +doc.getName()+ "\"");
			OutputStream out = response.getOutputStream();
			response.setContentType(doc.getContentType());
			return doc.getImage();

		} catch (IOException e) {
			e.printStackTrace();
		}


		return null;
	}

}
