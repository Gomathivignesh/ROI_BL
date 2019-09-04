package com.roi.bl.controller;

import com.roi.bl.dao.PaymentDAO;
import com.roi.bl.dao.UserDAO;
import com.roi.bl.model.Payments;
import com.roi.bl.model.User;
import com.roi.bl.util.EmailUtil;
import com.roi.bl.util.ResponseUtil;
import com.roi.bl.util.Security.CommonUtil;
import com.roi.bl.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminUserController {

    @Autowired
    UserDAO userDAO;

    @Autowired
    PaymentDAO paymentDAO;

    @RequestMapping(value = "/getInactiveusers", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getInactiveUsers(){
        return userDAO.getUserByStatus(Status.INACTIVE);
    }


    @RequestMapping(value = "/PaymentRequest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil sendPaymentRequestEmail(String email) throws MessagingException {
       ResponseUtil responseUtil =null;
       try {
           //[TODO] need to add expiry date
           String userSecret = CommonUtil.encryptData(email);
           EmailUtil.sendEmail(email, userSecret, "PAYMENT_REQUEST");
            responseUtil = new ResponseUtil();
            responseUtil.setMessage("Email Sent to user");
            responseUtil.setStatusCode("200");
            return responseUtil;
       }catch (Exception e){
           responseUtil.setMessage("Error in sending email");
           responseUtil.setStatusCode("500");
           return responseUtil;
       }


    }

    @RequestMapping(value = "/getInactivePayments", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Payments> getInactivePayments(){
        List<Payments> payments = paymentDAO.getPaymentsbyStatus(Status.INACTIVE);
        return payments;
    }







}
