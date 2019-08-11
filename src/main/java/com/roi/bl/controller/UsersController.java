package com.roi.bl.controller;

import com.roi.bl.dao.UserDAO;
import com.roi.bl.dao.UserReferralDAO;
import com.roi.bl.model.User;
import com.roi.bl.model.UserReferral;
import com.roi.bl.util.ResponseUtil;
import com.roi.bl.util.Security.AES;
import com.roi.bl.util.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserReferralDAO userReferralDAO;


    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil login(@RequestBody User user) {
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            user.setPassword(AES.encryptionUtil(user.getPassword()));
            User data = userDAO.getUserforLogin(user);
            if(data!=null){
                responseUtil.setStatusCode("200");
                responseUtil.setMessage("login successfully");
            }
            else{
                responseUtil.setStatusCode("500");
                responseUtil.setMessage("Invalid Credentails");
            }
            return responseUtil;
        }catch(Exception e){
            e.printStackTrace();
            responseUtil.setStatusCode("500");
            responseUtil.setMessage("error in getting response");
            return responseUtil;
        }
    }

    @RequestMapping(value ="/signup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil signUp(@RequestBody User user) {
        ResponseUtil responseUtil = null;
        try{
            responseUtil = validateUserdata(user);
            if(responseUtil.getStatusCode().equals("500"))
                return responseUtil;
            else{

                String password = AES.encryptionUtil(user.getPassword());
                if(!password.isEmpty()&& password != null)
                    user.setPassword(password);
                else {
                    responseUtil.setStatusCode("500");
                    responseUtil.setMessage("failed to save user data");
                    return responseUtil;
                }
                if(user.getReferedBy() == null)
                    user.setUserRole(UserRole.ROOT_USER.getGetValue());
                else
                    user.setUserRole(UserRole.CHILD_USER.getGetValue());

                Long id = userDAO.create(user);
                if(user.getReferedBy()!=null){
                    User referredUser = userDAO.getUserbyEmail(user.getReferedBy());
                    if(referredUser!=null){
                        UserReferral userReferral = new UserReferral();
                        userReferral.setChilduserId(id);
                        userReferral.setParentUserIds(referredUser.getId());
                        Long resposneId = userReferralDAO.create(userReferral);
                        if(resposneId> 0){
                            userDAO.updateReferralCount(user.getReferedBy());
                        }

                    }
                }
                if(id > 0){
                    responseUtil.setStatusCode("200");
                    responseUtil.setMessage("user data saved");
                }else{
                    responseUtil.setStatusCode("500");
                    responseUtil.setMessage("failed to save user data");
                }
                return responseUtil;
            }

        }catch(Exception e){
            e.printStackTrace();
            responseUtil.setStatusCode("500");
            responseUtil.setMessage("User Already registered");
            return responseUtil;
        }
    }


    private static ResponseUtil validateUserdata(User user){

        ResponseUtil responseUtil = new ResponseUtil();
        responseUtil.setStatusCode("500");
        if(user.getName()==null || user.getName().isEmpty())
            responseUtil.setMessage("Username is required");
        else if(user.getEmail()==null || user.getEmail().isEmpty())
            responseUtil.setMessage("Email is required");
        else if(user.getAddress()==null || user.getAddress().isEmpty())
            responseUtil.setMessage("Address is required");
        else if(user.getCity()==null || user.getCity().isEmpty())
            responseUtil.setMessage("City is required");
        else if(user.getPassword()==null || user.getPassword().isEmpty())
            responseUtil.setMessage("Password is required");
        else if(user.getCountry()==null || user.getCountry().isEmpty())
            responseUtil.setMessage("Country is required");
        else if(user.getMobile()==null || user.getMobile().isEmpty())
            responseUtil.setMessage("Mobile no is required");
        else{
            responseUtil.setStatusCode("200");
            responseUtil.setMessage("user data is valid");
        }
        return responseUtil;





    }

}
