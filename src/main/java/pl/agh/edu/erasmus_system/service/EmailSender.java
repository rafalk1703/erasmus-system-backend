package pl.agh.edu.erasmus_system.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Class for sending emails. Use smtp and google address to sending emails.
 * Can be used only with servers which does not require OAUTH2. In case google,
 * it is needed to set properties of account, to allow connecting non-secure app.
 * Configuration in setupSender should be same like in application.properties.
 */
@Service
public class EmailSender {

    @Value("${spring.mail.username}")
    public String email;

    @Value("${spring.mail.password}")
    public String password;

    @Value("${spring.mail.port}")
    public Integer port;

    @Value("${spring.mail.host}")
    public String host;

    private static List<EmailSender> senders;

    private JavaMailSender emailSender;

    private void sendEmail(String to, String subject, String messageBody) {
        if (emailSender == null) {
            emailSender = setupSender();
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(messageBody);
        emailSender.send(message);
    }

    /**
     * Send email to given address. Email is sent in new thread so invoking should be non blocking
     * @param to - address of receiver
     * @param subject - subject of email
     * @param messageBody - body of email
     */
    public void sendEmailTo(String to, String subject, String messageBody) {
        Thread thread = new Thread( () -> this.sendEmail(to, subject, messageBody) );
        thread.start();
    }

    private JavaMailSender setupSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(email);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        //props.put("mail.debug", "true");      //uncomment to see logs in console from sending email

        return mailSender;
    }

    public EmailSender() {
        if (senders == null) {
            senders = new LinkedList<>();
        }
        senders.add(this);
    }

    /**
     * Get instance of EmailSender, which is created by Spring
     * @return instance of properly initialized EmailSender
     */
    public static EmailSender getSender() {
        return senders.get(0);
    }
}