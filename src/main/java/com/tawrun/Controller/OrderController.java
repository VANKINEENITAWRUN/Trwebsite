package com.tawrun.Controller;

import java.util.ArrayList;
import java.util.Map;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tawrun.Repository.CustomerRepository;
import com.tawrun.Repository.OrderRepository;
import com.tawrun.Repository.ReviewRepository;
import com.tawrun.Services.PackerServices;
import com.tawrun.model.Customer;
import com.tawrun.model.Order;
import com.tawrun.model.Reviews;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import static com.tawrun.Controller.PackerHomeController.getModelAndView;

@Controller
@RequestMapping("/user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class OrderController {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private PackerServices packerServices;
	@Autowired
	private  CustomerRepository customerRepository;

	@RequestMapping(value="/postload", method = RequestMethod.GET)
	public ModelAndView home(){
		Order order = new Order();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("order", order);
		modelAndView.setViewName("loadDetails");
		return modelAndView;
	}

	@RequestMapping(value="/postload", method = RequestMethod.POST)
	public ModelAndView createOrder(@Valid Order order, BindingResult bindingResult){
		ModelAndView modelAndView = new ModelAndView();

		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("loadDetails");
		} else {
			order.setCustomer( customerRepository.findByEmail( SecurityContextHolder.getContext().getAuthentication().getName() ) );
			orderRepository.save(order);
			modelAndView.addObject("successMessage", "Order has been made");
			modelAndView.addObject("order", new Order());
			modelAndView.setViewName("loadDetails");
			return modelAndView;

		}
		return modelAndView;
	}



	@ModelAttribute("items_list")
	public String[] getItemsList(){
		return new String[]{
				"Double Bed","Matress","Almirah","AC","Micro Wave", "Utensils","Dinning Table",
				"Fridge","Sofa Set","Music system","Dressing Table","Scooty or BIke","Washing Machine","Table"

		};
	}

	@ModelAttribute("services_list")
	public String[] getServicesList(){
		return new String[]{
				"Loading","Packing","Transportation","Unloading","Unpacking ","Insurance"
		};
	}
	@RequestMapping(value="/order/{id}", method = RequestMethod.GET)
	public ModelAndView completeDetails(@PathVariable int id){
		return getModelAndView( id,"user", orderRepository );
	}

	@RequestMapping(value="/order/review/", method= RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Reviews createQuote(@RequestBody Map<String, String> request) {

		System.out.println( request.get( "rating" )+"	"+ request.get( "rating" )+"		");
		System.out.println( request.get( "packer" )+"	"+ request.get( "order" )+"		");

		Reviews reviews=orderRepository.findById( Integer.parseInt(request.get( "order" ))).getReview();
		if(reviews==null){
		 reviews =new Reviews();}

//		reviews.setPacker( packerServices .findPackerById( Integer.parseInt( request.get( "packer" ) ) ));
		reviews.setRating( Integer.parseInt(request.get( "rating" )) );
		reviews.setReview( request.get( "review" ) );
		Reviews r=reviewRepository.save( reviews );

		Order order=orderRepository.findById(Integer.parseInt (request.get( "order" ) ));
		order.setReview( r );
		orderRepository.save( order );
		return r;

	}
}
