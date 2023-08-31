package com.marko.kladionicajava.tools;

import com.marko.kladionicajava.entitiy.Email;
import com.marko.kladionicajava.entitiy.Quotas;
import com.marko.kladionicajava.repository.EmailRepository;
import com.marko.kladionicajava.service.AppConfigService;
import com.marko.kladionicajava.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service

public class email {

    public email() {
    }


    public static void main(String[] args) {
        sendEmail("sfsfsf", "sffffffffffffffffffffffffffffffffffffffff");
    }
    static void sendEmail(String title, String body) {
        String host = "mail.lumenspei.com";
        int port = 587;
        String username = "marko.milosevic@lumenspei.com";
        String password =  "Donjev.018";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        List<String> recipientList = new ArrayList<>();
        recipientList.add("marko.milosevic2202@gmail.com");
        recipientList.add("marko71@mailinator.com");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            Address[] recipients = new Address[recipientList.size()];
            for (int i = 0; i < recipientList.size(); i++) {
                recipients[i] = new InternetAddress(recipientList.get(i));
            }
            message.setRecipients(Message.RecipientType.TO, recipients);
            message.setSubject(title);
            message.setText(body);

            // Slanje poruke
            Transport.send(message);
            System.out.println("Email je uspešno poslat.");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sentQuotas(Quotas quota){
        String title = "ALARM";
        String body = quota.toString();
        sendEmail(title, body);
    }

}
