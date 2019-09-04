package com.roi.bl.controller;

import com.roi.bl.dao.PaymentDAO;
import com.roi.bl.dao.UserDAO;
import com.roi.bl.dao.UserReferralDAO;
import com.roi.bl.dao.UserWalletDAO;
import com.roi.bl.data.RefferalData;
import com.roi.bl.data.UserData;
import com.roi.bl.model.Payments;
import com.roi.bl.model.User;
import com.roi.bl.model.UserWallet;
import com.roi.bl.util.*;
import com.roi.bl.util.Security.AES;
import com.roi.bl.util.Security.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class AuthUserController {

    private static String UPLOADED_FOLDER = "D://temp//";

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserWalletDAO userWalletDAO;

    @Autowired
    private UserReferralDAO userReferralDAO;

    @Autowired
    private PaymentDAO paymentDAO;



    @RequestMapping(value = "/referUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil referUser(@RequestBody RefferalData refferalData) {
        ResponseUtil responseUtil = new ResponseUtil();
        try{

            String data = "ROI-TamilNadu"+ refferalData.getUser().getEmail() + new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(new Date());
            System.out.println(data);
            String encryptedData = CommonUtil.encryptData(data);
            System.out.println(encryptedData);
            EmailUtil.sendEmail(refferalData.getToUserEmail(), encryptedData, "USER_REFERRAL");
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

    @RequestMapping(value = "/activateUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseUtil activateUser(@RequestBody User user) {
        ResponseUtil responseUtil = new ResponseUtil();
        try{
            User user1 = userDAO.getUserbyEmail(user.getEmail());
            //[TODO] user activation yet to done
            if(user1!=null){
                UserWallet userWallet = new UserWallet();
                userWallet.setUserId(user1.getId());
                userWallet.setAmount(new BigDecimal(0));
                userWallet.setTransferType(TransferType.INIT.toString());
                userWallet.setUpdatedDate(new Date());
                long responseId = userWalletDAO.create(userWallet);
                if(responseId>0){
                    responseUtil.setStatusCode("200");
                    responseUtil.setMessage("user wallet activated");
                }else{
                    responseUtil.setStatusCode("500");
                    responseUtil.setMessage("Error in activating user wallet");
                }
                return responseUtil;

            }else{
                responseUtil.setStatusCode("500");
                responseUtil.setMessage("user not exist");
                return responseUtil;
            }


        }catch(Exception e){
            e.printStackTrace();
            responseUtil.setStatusCode("250");
            responseUtil.setMessage("Error in activating user wallet");
            return responseUtil;
        }


    }

    @RequestMapping(value = "/getUserDetails", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserData getUserDetails(@RequestBody User user) {
        UserData userData = new UserData();
        List<UserData> childUserDatas = new ArrayList<>();
        try{
            User user1 = userDAO.getUserbyEmail(user.getEmail());
            //[TODO] user activation yet to done
            if(user1!=null){
                userData.setUserName(user1.getName());
                userData.setReferalCount(user1.getReferalCount());
                userData.setRoleId(user1.getUserRole());
            }
            userData.setWalletBalance(userWalletDAO.getBalanceByUserId(user1.getId()));

            List<Long> childIds = userReferralDAO.getChilduserIds(user1.getId());
            if(childIds.size()>0){
                List<User> userDetails = userDAO.getChildUserDetails(childIds);
                for(User childUser: userDetails){

                    UserData childUserData = new UserData();
                    childUserData.setUserName(childUser.getName());
                    childUserData.setReferalCount(childUser.getReferalCount());
                    childUserData.setRoleId(childUser.getUserRole());
                    childUserData.setWalletBalance(userWalletDAO.getBalanceByUserId(childUser.getId()));
                    childUserDatas.add(childUserData);
                }
                userData.setChilduserIds(childUserDatas);
            }


            return userData;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }


    }
    @RequestMapping(value = "/initPayment", method = RequestMethod.POST)
    public ResponseUtil initpayment(@RequestParam("file") MultipartFile uploadfile, String email, String paymentType) {
        ResponseUtil responseUtil = new ResponseUtil();
        if (uploadfile.isEmpty()) {
            responseUtil.setStatusCode("500");
            responseUtil.setMessage("user not exist");
            return responseUtil;
        }
        try {
            String filename = uploadfile.getOriginalFilename().replace(uploadfile.getOriginalFilename(), email+"_"+paymentType+"_"+ new SimpleDateFormat("yyyyMddhhmmss").format(new Date()));
            User userdata = userDAO.getUserbyEmail(email);
            //[TODO] user activation yet to done
            if(userdata!=null){
                saveUploadedFiles(Arrays.asList(uploadfile),filename);
                Payments payments = new Payments();
                payments.setFileLocation(UPLOADED_FOLDER+ uploadfile.getOriginalFilename());
                payments.setPaymentType("ACTIVATION");
                payments.setStatus(Status.INACTIVE);
                payments.setUser(userdata);
                payments.setCreatedBy(userdata.getName());
                payments.setCreatedDate(new Date());
                paymentDAO.create(payments);
            }
            else{
                responseUtil.setStatusCode("500");
                responseUtil.setMessage("user Not exist");
                return responseUtil;
            }



        } catch (IOException e) {
            e.printStackTrace();
            responseUtil.setStatusCode("500");
            responseUtil.setMessage("saving data failed");
            return responseUtil;
        }

        responseUtil.setStatusCode("200");
        responseUtil.setMessage("Request successfully submitted for approval");
        return responseUtil;
    }

    private void saveUploadedFiles(List<MultipartFile> files, String filename) throws IOException {

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue; //next pls
            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + filename);
            Files.write(path, bytes);

        }

    }






}
