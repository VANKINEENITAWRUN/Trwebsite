package com.tawrun.Controller;


import java.util.ArrayList;
import javax.validation.Valid;

import com.tawrun.Repository.OrderRepository;
import com.tawrun.Services.CustomerService;
import com.tawrun.Services.PackerServices;
import com.tawrun.model.Customer;
import com.tawrun.model.Order;
import com.tawrun.model.Packer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/packer")
public class PackerLoginController {


	@Autowired
	private PackerServices packerServices;
	@Autowired
	private OrderRepository orderRepository;

	@RequestMapping(value={"login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}


	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		Packer customer = new Packer();
		modelAndView.addObject("customer", customer);
		modelAndView.setViewName("packer_registration");
		return modelAndView;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewCustomer(@Valid Packer packer, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		Packer customerExists = packerServices.findPackerByEmail(packer.getEmail());
		if (customerExists != null) {
			bindingResult
					.rejectValue("email", "error.customer",
								 "There is already a customer registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			packerServices.savePacker(packer);
			modelAndView.addObject("successMessage", "Customer has been registered successfully");
			modelAndView.addObject("customer", new Packer());
			modelAndView.setViewName("packer_registration");

		}
		return modelAndView;
	}

	@RequestMapping(value="/home", method = RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Packer customer = packerServices.findPackerByEmail(auth.getName());
		ArrayList<Order> orders = new ArrayList<Order>( orderRepository.findAll() ) ;
//		System.out.print( orders.size()+"");
		modelAndView.addObject( "orders",orders );
		modelAndView.addObject("customerName", "Welcome " + customer.getName()  + " (" + customer.getEmail() + ")");
		modelAndView.setViewName("home");
		return modelAndView;
	}


}
