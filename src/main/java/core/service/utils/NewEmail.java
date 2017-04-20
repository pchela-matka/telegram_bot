package core.service.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by astegnienko on 04.04.2017.
 */
public class NewEmail {
    public void sendEmail(String body) {
        final String user = GetProperties.properies("mxe.smtp.user");
        final String password = GetProperties.properies("mxe.smtp.password");
        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        };
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", GetProperties.properies("mail.smtp.host"));
        properties.setProperty("mail.smtp.port", GetProperties.properies("mail.smtp.port"));
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", GetProperties.properies("mail.smtp.starttls.enable"));
        Session session = Session.getInstance(properties, authenticator);
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("helpdesktest@sibis.com.ua"));
            message.setSubject("#telegrambot");
            message.setContent(body, "text/plain");
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress("astegnienko@sibis.com.ua"));
            Transport.send(message);
        } catch (MessagingException exception) {
            exception.printStackTrace();
        }
    }
}
