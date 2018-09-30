package com.tawrun.Controller;

import com.tawrun.Repository.PaymentRepository;
import com.tawrun.Services.PackerServices;
import com.tawrun.Services.PayPalClient;
import com.tawrun.model.Packer;
import com.tawrun.model.Payments;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/packer/paypal")
public class PayPalController {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PackerServices packerServices;


    @Autowired
    private final PayPalClient payPalClient;
    @Autowired
    PayPalController(PayPalClient payPalClient){
        this.payPalClient = payPalClient;
    }

    @PostMapping(value = "/make/payment")
	@ResponseBody
    public Map<String, Object> makePayment(@RequestParam("sum") String sum){
        return payPalClient.createPayment(sum);
    }

    @PostMapping(value = "/complete/payment")
	@ResponseBody
    public Map<String, Object> completePayment(HttpServletRequest request){

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("status", "success");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Packer packer = packerServices.findPackerByEmail( auth.getName());
        Payments payments =payPalClient.completePayment( request);
        if(payments==null){
             response.put( "status","failed" );
             return response;
        }
        payments.setPacker( packer  );

        paymentRepository.save( payments );

        return response;

    }
}