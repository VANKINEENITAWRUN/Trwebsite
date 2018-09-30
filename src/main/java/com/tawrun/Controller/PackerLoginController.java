package com.tawrun.Controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

import com.tawrun.Repository.ImageRepository;
import com.tawrun.Repository.OrderRepository;
import com.tawrun.Repository.PaymentRepository;
import com.tawrun.Repository.TransactionRepository;
import com.tawrun.Services.PackerServices;
import com.tawrun.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@RequestMapping("/packer")
public class PackerLoginController {


	@Autowired
	private PackerServices packerServices;
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private TransactionRepository transactionRepository;


	@RequestMapping(value={"/packer/login","/packer/"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("packer_login");
		return modelAndView;
	}


	@RequestMapping(value="/packer/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		Packer packer = new Packer();

		Image image =new Image();
		modelAndView.addObject( "image", image);
		modelAndView.addObject("packer", packer);
		modelAndView.setViewName("packer_registration");
		return modelAndView;
	}

	@RequestMapping(value = "/packer/registration", method = RequestMethod.POST)
	public ModelAndView createNewCustomer( @Valid @ModelAttribute("packer") Packer packer,
										   BindingResult bindingResult ,@Valid @RequestParam("file") MultipartFile file)
			throws IOException {
		ModelAndView modelAndView = new ModelAndView();
		Packer customerExists = packerServices.findPackerByEmail(packer.getEmail());
		if (customerExists != null) {
			bindingResult
					.rejectValue("email", "error.customer",
								 "There is already a customer registered with the email provided");
		}
		System.out.println("File:" + file.getName());
		System.out.println("ContentType:" + file.getContentType());
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("packer_registration");
			System.out.print(bindingResult.hasErrors());
			List<FieldError> errors = bindingResult.getFieldErrors();
			for ( FieldError error : errors ) {
				System.out.println (error.getObjectName() + " - " + error.getDefaultMessage());
			}
		} else {



			packerServices.savePacker(packer,file);
			modelAndView.addObject( "image",new Image() );
			modelAndView.addObject("successMessage", "Customer has been registered successfully");
			modelAndView.addObject("customer", new Packer());
			modelAndView.setViewName("packer_registration");

		}
		return modelAndView;
	}

	@RequestMapping(value="/packer/home", method = RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Packer packer = packerServices.findPackerByEmail(auth.getName());
		ArrayList<Order> orders = new ArrayList<Order>( orderRepository.findAll() ) ;
////		orders.get( 0 ).getPacker();
//
//		System.out.print( orders.size()+" packer");
		Quote q= new Quote();

		// date, amount, transaction_id
		List<Payments> payments=paymentRepository.findByPackerId( packer.getId() );


		// date, amount, packer_id, quote_id
		List<Transaction> transactions = transactionRepository.findByPackerId( packer.getId() );

		// Convert to PayInfo list
		// PayInfo contains amount, to, date
		List<PayInfo> payInfos = new ArrayList<>();

		// Logic
		for(Payments p: payments)
			payInfos.add(p.toPayInfo());

		// Logic
		for(Transaction t: transactions)
			payInfos.add(t.toPayInfo());

		modelAndView.addObject( "quote", q);
		modelAndView.addObject( "packer", packer);

		modelAndView.addObject( "payinfo", payInfos);

		modelAndView.addObject( "flag",	"packer" );
		modelAndView.addObject( "orders",orders );
		modelAndView.addObject("customerName", "Welcome " + packer.getCompany_name()  + " (" + packer.getEmail() + ")");

		modelAndView.setViewName("packer_home");
		return modelAndView;
	}


}
