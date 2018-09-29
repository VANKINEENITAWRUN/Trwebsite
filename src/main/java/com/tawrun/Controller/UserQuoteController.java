package com.tawrun.Controller;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.tawrun.Repository.ImageRepository;
import com.tawrun.Repository.OrderRepository;
import com.tawrun.Repository.QuoteRepository;
import com.tawrun.Repository.ReviewRepository;
import com.tawrun.Services.PackerServices;
import com.tawrun.Utils.ResourceNotFoundException;
import com.tawrun.model.Image;
import com.tawrun.model.Order;
import com.tawrun.model.Packer;
import com.tawrun.model.Quote;
import com.tawrun.model.Reviews;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
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
@RequestMapping(("/user"))
@Transactional()
public class UserQuoteController {


	@Autowired
	private QuoteRepository quoteRepository;

	@Autowired
	private PackerServices packerServices;


	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private ImageRepository imageRepository;

	@RequestMapping(value="/quotes/{id}", method = RequestMethod.GET)
	public ModelAndView getQuotes(@PathVariable Long id){

		ModelAndView modelAndView = new ModelAndView();
		ArrayList<Quote> quotes= new ArrayList<>(quoteRepository.findByOrderId( id ));
		modelAndView.addObject("quotes", quotes);
		modelAndView.setViewName("available_quotes");
		return modelAndView;
	}

	@RequestMapping(value="/quote/confirm", method= RequestMethod.POST,
			 consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> acceptQuote(@RequestBody Map<String, String> request) {

		System.out.println(request);
		Map<String, Object> response = new HashMap<String, Object>();
		int packer_id=Integer.parseInt( request.get( "packerid" ) ) ;
		Long order_id=Long.parseLong( request.get( "orderid" ) ) ;

		Order order= orderRepository.findById( order_id )
				.orElseThrow(() -> new ResourceNotFoundException( "Order", "id", order_id));


		if(order.getPacker()!=null){
			order.setPacker( packerServices.findPackerById( packer_id ) );

			orderRepository.save( order );

			response.put( "sucess","Your Details will be Shared with Packer");


		}else{
			response.put( "sucess","Already accepted Other Quote");

		}


		System.out.println(response);
		return response;

	}

	@RequestMapping(value = "/packer/{id}",method = RequestMethod.GET)
	public ModelAndView showReviews(@PathVariable int id){

		ModelAndView modelAndView = new ModelAndView();
		ArrayList<Reviews> reviews=new ArrayList<>( reviewRepository.findByPackerId( id ));
		Packer packer =packerServices.findPackerById( id );
		modelAndView.addObject("reviews", reviews);
		modelAndView.addObject("packer", packer);
		modelAndView.setViewName("review");
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
