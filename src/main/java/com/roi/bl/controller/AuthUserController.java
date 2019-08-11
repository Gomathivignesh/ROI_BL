package com.roi.bl.controller;

import com.roi.bl.data.RefferalData;
import com.roi.bl.util.EmailUtil;
import com.roi.bl.util.ResponseUtil;
import com.roi.bl.util.Security.AES;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/user")
public class AuthUserController {



    @RequestMapping(value = "/referUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil referUser(@RequestBody RefferalData refferalData) {
        ResponseUtil responseUtil = new ResponseUtil();
        try{

            String data = "ROI-TamilNadu"+ refferalData.getUser().getEmail() + new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(new Date());
            System.out.println(data);
            String encryptedData = AES.encryptionUtil(data);
            System.out.println(encryptedData);
            EmailUtil.sendEmail(refferalData.getToUserEmail(), encryptedData);
            responseUtil.setStatusCode("200");
            responseUtil.setMessage("Email Sent");
            return responseUtil;
        }catch(Exception e){
            e.printStackTrace();
            responseUtil.setStatusCode("500");
            responseUtil.setMessage("error in getting response");
            return responseUtil;
        }
    }
}
