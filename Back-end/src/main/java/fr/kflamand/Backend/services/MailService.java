package fr.kflamand.Backend.services;

import fr.kflamand.Backend.entities.RegistrationToken;
import fr.kflamand.Backend.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:env/mail.properties")
public class MailService {

    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    @Autowired
    private Environment env;

    private final static String SEPARATOR = "------------------------------------------------\n";

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Autowired
    public JavaMailSender emailSender;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void sendSimpleMessage(String to, String subject, String text) {

        logger.info("Sending email...");

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setFrom(env.getProperty("Mail_Sender"));
        message.setSubject(subject);
        message.setText(text);

        try {
            emailSender.send(message);
            logger.info("Email Sent!");
        } catch (MailException e) {
            logger.error("Email NOT Sent! => " + e.getMessage());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String messageRegistrationMail(User user) {

        String UriValidMail = env.getProperty("API_ROOT_URI") + env.getProperty("API_RELATIVE_URI_REGISTRATION")+ user.getRegistrationToken().getToken();


        String message = "Hi " + user.getFullName() + ",\n \n This email has been sent from: " + env.getProperty("API_NAME") + "\n \n You have received this email because this email address" +
                "was used during registration for our API. If you did not register at our forums, please disregard this email. You do not need to unsubscribe or take " +
                "any further action. \n \n" + SEPARATOR + " Activation Instructions \n" + SEPARATOR + "\n Thank you for registering. We require that you \"validate\"" +
                " your registration to ensure that the email address you entered was correct. This protects against unwanted spam and malicious abuse. To activate your account, " +
                "simply click on the following link: \n \n" + UriValidMail + "\n \n (Some email client users may need to copy and paste the link into your web browser).";


        return message;
    }

    public String messageResetPassword(User user, RegistrationToken token) {

        String UriValidMail = env.getProperty("API_ROOT_URI") + env.getProperty("API_RELATIVE_URI_RESET_PASSWORD")+ token.getToken();

        String message = "Hi " + user.getFullName() +
                ",\n \n This email has been sent from: " + env.getProperty("API_NAME") +
                "\n \n You have received this email because a password recovery for the user account \"" + user.getFullName() + "\" was instigated by you on PostPlatform. \n \n" +
                SEPARATOR + "IMPORTANT! \n" + SEPARATOR +
                "If you did not request this password change, please IGNORE and DELETE this email immediately. Only continue if you wish your password to be reset! \n \n" +
                SEPARATOR + "Activation Instructions Below" + SEPARATOR +
                "Simply click on the link below and complete the rest of the form.\n \n" +
                UriValidMail ;

        return message;
    }

    public String subjectResetPassword(User user) {

        String subject = "Hi " + user.getFullName() + ". Would you reset your password for PostPlatform?";

        return subject;
    }

    public String subjectRegistrationMail(User user) {

        String subject = "Wellcome to PostPlatform, " + user.getFullName() + ".";

        return subject;
    }
}
