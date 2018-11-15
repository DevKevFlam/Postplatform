package fr.kflamand.Backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;

@Component
public class MailService {

    @Autowired
    public JavaMailSender emailSender;


    // Email Formating
    @Bean
    public SimpleMailMessage templateSimpleMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(
                "This is the test email template for your email:\n%s\n ... Ajout d'un url pour validation http ");
        return message;
    }

    // Email Sender
    // @PostConstruct
    public void sendSimpleMessage( String to, String subject, String text) {

        System.out.println("Sending email...");

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setFrom("kev.flamand.dev.test@gmail.com");
        message.setSubject(subject);
       // message.setText(text);
        message.setFrom("PostPlatform");

        try {
            emailSender.send(message);
            System.out.println("Email Sent!");
        } catch ( MailException e) {
            System.out.println(e.getMessage());
        }
    }

    public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) {

/*
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        FileSystemResource file
                = new FileSystemResource(new File(pathToAttachment));
        helper.addAttachment("Invoice", file);

        emailSender.send(message);
*/
    }

}
