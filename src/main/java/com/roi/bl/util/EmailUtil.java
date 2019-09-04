package com.roi.bl.util;


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtil {

    private static Properties emailProperties;
    private static Session mailSession;
    private static MimeMessage emailMessage;


    static {
        String emailPort = "587";//gmail's smtp port

        emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.port", emailPort);
        emailProperties.put("mail.smtp.auth", "true");
        emailProperties.put("mail.smtp.starttls.enable", "true");
    }




    public static void  referralEmailMessage(String toEmailId, String referalToken) throws AddressException,
            MessagingException {

        String emailSubject = "ROI-Tamilnadu";
        String emailBody = "click here to register ROI-Tamilnadu\n  http://localhost:4200/signup?id="+ referalToken;

        mailSession = Session.getDefaultInstance(emailProperties, null);
        emailMessage = new MimeMessage(mailSession);
        emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmailId));


        emailMessage.setSubject(emailSubject);
        emailMessage.setContent(emailBody, "text/html");//for a html email
        //emailMessage.setText(emailBody);// for a text email

    }

    public static void  PaymentRequestEmail(String toEmailId, String userSecret) throws AddressException,
            MessagingException {

        String emailSubject = "ROI-Tamilnadu";
        String emailBody = "Dear User \n " +
                "Thanks for register \n"+
                "Click here  http://localhost:4200/paymentRequestid="+ userSecret + " to complete your registration by upload the payment snapshot";

        mailSession = Session.getDefaultInstance(emailProperties, null);
        emailMessage = new MimeMessage(mailSession);
        emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmailId));


        emailMessage.setSubject(emailSubject);
        emailMessage.setContent(emailBody, "text/html");//for a html email
        //emailMessage.setText(emailBody);// for a text email

    }

    public static void sendEmail(String toEmailId, String referalToken, String reason) throws MessagingException {
        if(reason.equals("USER_REFERRAL"))
            referralEmailMessage(toEmailId, referalToken);

        else if(reason.equals("PAYMENT_REQUEST"))
            referralEmailMessage(toEmailId, referalToken);

        String emailHost = "smtp.gmail.com";
        String fromUser = "mgomathivignesh";//just the id alone without @gmail.com
        String fromUserEmailPassword = "greenday22297";

        Transport transport = mailSession.getTransport("smtp");

        transport.connect(emailHost, fromUser, fromUserEmailPassword);
        transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
        transport.close();
        System.out.println("Email sent successfully.");
    }
}
