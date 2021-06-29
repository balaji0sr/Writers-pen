//$Id$
package com.service;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {

/*	public static void main(String[] args) throws AddressException, MessagingException {

		final String username = "balajitenth@gmail.com";
		final String password = "Aa@1779699";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.zoho.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("balajitenth@gmail.com"));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("jbalajisundar@gmail.com"));
		message.setSubject("A testing mail header !!!");
		message.setText("Dear Mail Crawler");

		Transport.send(message);
		System.out.println("Done");
	}*/
	
	public static void main(String[] args) {

        // Recipient's email ID needs to be mentioned.
        String to = "jbalajisundar@gmail.com";

        // Sender's email ID needs to be mentioned
        String from = "balajitenth@gmail.com";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties props = System.getProperties();

        // Setup mail server
//        properties.put("mail.smtp.host", host);
//        properties.put("mail.smtp.port", "587");
//        properties.put("mail.smtp.tls.enable", "true");
//        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.starttls.enable", "true");
        
		props.setProperty( "mail.smtp.host", host );
		props.setProperty( "mail.smtp.from", from );
		props.setProperty( "mail.smtp.connectiontimeout", "10000");
		props.setProperty( "mail.smtp.timeout", "10000");

		
			props.setProperty( "mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.ssl.trust", "*");
			props.put("mail.smtp.port", "587");
	        props.put("mail.smtp.auth", "true");



//			props.setProperty( "mail.smtp.port", "465");
//			props.setProperty( "mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");//No I18N
//			props.setProperty( "mail.smtp.socketFactory.port", "587");//No I18N
//			props.setProperty( "mail.smtp.socketFactory.fallback", "false");  //No I18N
//			props.setProperty( "mail.smtp.ssl.enable", "true");
		

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, "Aa@1779699");
            }
        });
        // Used to debug SMTP issues
        session.setDebug(true);
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("This is the Subject Line!");

            // Now set the actual message
            message.setText("This is actual message");

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }
}
