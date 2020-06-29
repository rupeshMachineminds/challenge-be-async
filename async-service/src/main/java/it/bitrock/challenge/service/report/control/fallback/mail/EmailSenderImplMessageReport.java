package it.bitrock.challenge.service.report.control.fallback.mail;

import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import it.bitrock.challenge.service.report.control.fallback.FallbackConsumer;
import it.bitrock.challenge.service.report.entity.MessageReport;

@ApplicationScoped
public class EmailSenderImplMessageReport implements FallbackConsumer<EmailPayload<MessageReport>> {

	@Override
	public void consume(EmailPayload<MessageReport> emailPayload) {
		try {
			sendEmail(emailPayload);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	private void sendEmail(EmailPayload<MessageReport> emailPayload) throws MessagingException {
		String from = "rupesh@gmail.com";
		String password = "xyz";
		String host = "smtpout.asia.secureserver.net";		
		
		Properties props = System.getProperties();
		props.setProperty("mail.transport.protocol", "smtps");
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.host", host);
		props.setProperty("mail.user", from);
		props.setProperty("mail.password", password);
		
		Session mailSession = Session.getDefaultInstance(props, null);
		Transport transport = mailSession.getTransport("smtps");
		MimeMessage message = new MimeMessage(mailSession);
		message.setHeader("Disposition-Notification-To", from);
	    message.setHeader("Return-Receipt-To", from);
		
		message.setSentDate(new java.util.Date());
		message.setSubject("Read receipt test");
		message.setFrom(new InternetAddress(from));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress("xyz@gmail.com"));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress("abc@gmail.com"));
		
		message.setContent(emailPayload.getPayload().getValue().getMessageValue(), "text/html");
		transport.connect(host, from, password);
		transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
		transport.close();	
		
	}
}
