package com.tawrun.Controller;

import java.util.ArrayList;

import javax.validation.Valid;

import com.tawrun.Repository.CustomerRepository;
import com.tawrun.Repository.OrderRepository;
import com.tawrun.model.Customer;
import com.tawrun.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import static com.tawrun.Controller.PackerHomeController.getModelAndView;

@Controller
@RequestMapping("/user")
public class OrderController {

	@Autowired
	private OrderRepository orderRepository;
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
	public ModelAndView completeDetails(@PathVariable Long id){
		return getModelAndView( id,"user", orderRepository );
	}


}
