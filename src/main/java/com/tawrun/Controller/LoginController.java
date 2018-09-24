package com.tawrun.Controller;


import java.util.ArrayList;
import java.util.logging.Logger;
import javax.validation.Valid;

import com.tawrun.Repository.OrderRepository;
import com.tawrun.Services.CustomerService;
import com.tawrun.model.Customer;
import com.tawrun.model.Order;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private OrderRepository orderRepository;

	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}


	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		Customer customer = new Customer();
		modelAndView.addObject("customer", customer);
		modelAndView.setViewName("registration");
		return modelAndView;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewCustomer(@Valid Customer customer, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		Customer customerExists = customerService.findCustomerByEmail(customer.getEmail());
		if (customerExists != null) {
			bindingResult
					.rejectValue("email", "error.customer",
								 "There is already a customer registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			customerService.saveCustomer(customer);
			modelAndView.addObject("successMessage", "Customer has been registered successfully");
			modelAndView.addObject("customer", new Customer());
			modelAndView.setViewName("registration");

		}
		return modelAndView;
	}

	@RequestMapping(value="/home", method = RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Customer customer = customerService.findCustomerByEmail(auth.getName());
		ArrayList<Order> orders = orderRepository.findByCustomerId(customer.getId());
		System.out.print( orders.size()+"");
		modelAndView.addObject( "orders",orders );
		modelAndView.addObject("customerName", "Welcome " + customer.getName()  + " (" + customer.getEmail() + ")");
		modelAndView.setViewName("home");
		return modelAndView;
	}


}