
package com.shopping.orderService.orderservice.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class MailService {
    public String sendMail(String toEmail,String emailText) throws FileNotFoundException, IOException {
        
        Properties props = loadingDataFromProps();

        String username = props.getProperty("mail.smtp.user");
        String password = props.getProperty("mail.smtp.password");

        Session session = createSession(props, username, password);
        
        try {
            sendEmail(session,toEmail,emailText);
            return "Email sent successfully.";
        } catch (MessagingException e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
        
        return null;
    }

	private Properties loadingDataFromProps() throws IOException {
		Properties props = new Properties();
        try (InputStream input = MailService.class.getClassLoader().getResourceAsStream("email.properties");) {
           props.load(input);
        }
		return props;
	}

	private Session createSession(Properties props, String username, String password) {
		Session session = Session.getInstance(props,
        	      new javax.mail.Authenticator() {
        	         protected PasswordAuthentication getPasswordAuthentication() {
        	            return new PasswordAuthentication(username, password);
        	         }
        	      });
		return session;
	}

	private void sendEmail(Session session,String toEmailID,String emailText) throws MessagingException, AddressException {
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("sender_email_address"));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmailID));
		message.setSubject("Test Email");
		message.setText(emailText);
		Transport.send(message);
	}
}
