package com.tawrun.Controller;


import com.tawrun.Repository.OrderRepository;
import com.tawrun.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/packer")
public class PackerHomeController {

	@Autowired
	private OrderRepository orderRepository;

	@RequestMapping(value="/order/{id}", method = RequestMethod.GET)
	public ModelAndView completeDetails(@PathVariable Long id){
		return getModelAndView( id, orderRepository );
	}

	static ModelAndView getModelAndView(
			@PathVariable Long id,
			OrderRepository orderRepository) {
		ModelAndView view = new ModelAndView();
		Order order= orderRepository.findById( id ).get();
		view.addObject("order", order);
		view.setViewName("complete_loadDetails");
		return view;
	}

}
