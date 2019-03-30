package com.leoleo.film.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Slf4j
public class MailUtil {
    @Autowired
    public static String sendMail(String username,String type) throws Exception {
    // Create a Properties object to contain connection configuration information.
        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");

        // Create a Session object to represent a mail session with the specified properties.
        Session session = Session.getDefaultInstance(props);

        // Create a message with the specified information.
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("info@facefoto.com","facefoto"));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(username));
        String emailcode = "" + (int)((Math.random() * 9 + 1) * 100000);
        if (type.equals("register")) {
            msg.setSubject("Facefoto会员邮箱认证/Facefoto會員電郵地址認證/verification for Facefoto member email address");
            String content = "您好！您正在Facefoto进行邮箱验证，您的验证码是：{" + emailcode + "}" + "\n\n" + "如有问题，请咨询电邮客服：info@facefoto.com，感谢您的支持！" +
                    "\n\n\n\n\n" + "您好！您正在Facefoto進行電郵地址驗證，您的驗證碼是：{" + emailcode + "}" + "\n\n" + "如有問題，請諮詢電郵客服：info@facefoto.com，感謝您的支持！" +
                    "\n\n\n\n\n" + "Hello!You are verifying your email address on Facefoto. Your verification code is: {" + emailcode + "}" + "\n\n" +
                    "If you have any questions, please contact us at info@facefoto.com, thank you for your support!";
            msg.setText(content);
//        msg.setContent(content,"text/html");

            // Add a configuration set header. Comment or delete the
            // next line if you are not using a configuration set
            // msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);

            // Create a transport.
        }
        if (type.equals("forget")){
            msg.setSubject("Facefoto会员重设密码认证/Facefoto會員重設密碼認證/verification for Facefoto member reset password");
            String content ="您好！您正在Facefoto进行重设密码的邮箱验证，您的验证码是：{" + emailcode + "}" + "\n\n" + "如有问题，请咨询电邮客服：info@facefoto.com，感谢您的支持！" +
                    "\n\n\n\n\n" + "您正在Facefoto進行重設密碼的電郵地址驗證，您的驗證碼是：{" + emailcode + "}" + "\n\n" + "如有問題，請諮詢電郵客服：info@facefoto.com，感謝您的支持！" +
                    "\n\n\n\n\n" + "Hello!You are verifying your email address to reset password on Facefoto. Your verification code is:{" + emailcode + "}" + "\n\n" +
                    "If you have any questions, please contact us at info@facefoto.com, thank you for your support!";
            msg.setText(content);
        }
        Transport transport = session.getTransport();

        // Send the message.
        try
        {
            log.warn("Sending...");

            // Connect to Amazon SES using the SMTP username and password you specified above.
            transport.connect("email-smtp.us-west-2.amazonaws.com", "AKIAJ6QIM2L3TFV7RUYQ", "AlSr+eNOCnm7XMWRY19eQRRO6dWyuI/LavDGP3cU0OAo");

            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
            log.warn("Email sent!");
        }
        catch (Exception ex) {
            log.warn("The email was not sent.");
            ex.printStackTrace();
            log.error("Error message: " + ex.getMessage());
        }
        finally
        {
            // Close and terminate the connection.
            transport.close();
        }

        return  emailcode;
    }

}
