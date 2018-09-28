package com.tawrun.Services;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
client secret: EEDSEjTkEe7pYE74R_7AZI-njS3bpo3P8IhP36elsFN5p26xTk4g1FEVos_3ZwZYee6drp0IOy1OpPuG
client ID: AWkwWxlmZXEEUJ8wvF5wYv71w9dr94RnM4gRPNqcqyc_8paJsov_oGcHDn1KpPd0gKsYulGHYHJZdsms
sandbox account: sushsilsharma.ss451-facilitator@gmail.com
 */

@Service
public class PayPalClient {
    String clientId = "AWkwWxlmZXEEUJ8wvF5wYv71w9dr94RnM4gRPNqcqyc_8paJsov_oGcHDn1KpPd0gKsYulGHYHJZdsms";
    String clientSecret = "EEDSEjTkEe7pYE74R_7AZI-njS3bpo3P8IhP36elsFN5p26xTk4g1FEVos_3ZwZYee6drp0IOy1OpPuG";

    public Map<String, Object> createPayment(String sum){
        Map<String, Object> response = new HashMap<String, Object>();
        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(sum);
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();

        // TODO Currently failure and success redirect URL's are the same.
        // TODO U wont the diff if its passed or not. Make them separate
        redirectUrls.setCancelUrl("http://localhost:8080/packer/home");
        redirectUrls.setReturnUrl("http://localhost:8080/packer/home");
        payment.setRedirectUrls(redirectUrls);
        Payment createdPayment;
        try {
            String redirectUrl = "";
            APIContext context = new APIContext(clientId, clientSecret, "sandbox");
            createdPayment = payment.create(context);
            if(createdPayment!=null){
                List<Links> links = createdPayment.getLinks();
                for (Links link:links) {
                    if(link.getRel().equals("approval_url")){
                        redirectUrl = link.getHref();
                        break;
                    }
                }
                response.put("status", "success");
                response.put("redirect_url", redirectUrl);
            }
        } catch (PayPalRESTException e) {
            System.out.println("Error happened during payment creation!");
        }
        return response;
    }

    public Map<String, Object> completePayment(HttpServletRequest req){
        Map<String, Object> response = new HashMap<String, Object>();
        Payment payment = new Payment();
        payment.setId(req.getParameter("paymentId"));

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(req.getParameter("PayerID"));
        try {
            APIContext context = new APIContext(clientId, clientSecret, "sandbox");
            Payment createdPayment = payment.execute(context, paymentExecution);
            if(createdPayment!=null){
                response.put("status", "success");
                response.put("payment", createdPayment);
            }
        } catch (PayPalRESTException e) {
            System.err.println(e.getDetails());
        }
        return response;
    }
}
