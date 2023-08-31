package com.marko.kladionicajava.tools;

import com.marko.kladionicajava.entitiy.Email;
import com.marko.kladionicajava.entitiy.Quotas;
import com.marko.kladionicajava.service.AppConfigService;
import com.marko.kladionicajava.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class EmailSendService {
    private final AppConfigService appConfigService;
    private final EmailService emailService;

    private void sendEmail(String title, String body) {
        String host = appConfigService.getHost();
        int port = 587;
        String username = appConfigService.getUsername();
        String password = appConfigService.getPassword();
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        List<Email> listEmail = emailService.getEmails();

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }});

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            Address[] recipients = new Address[listEmail.size()];
            for (int i = 0; i < listEmail.size(); i++) {
                recipients[i] = new InternetAddress(listEmail.get(i).getEmail());
            }
            message.setRecipients(Message.RecipientType.TO, recipients);
            message.setSubject(title);
            message.setText(body);
            // String filePath = "result/" + nameFileGlobal;
            // attachmentBodyPart.attachFile(filePath);
            // multipart.addBodyPart(attachmentBodyPart);
            // message.setContent(multipart);
            Transport.send(message);
            System.out.println("The mail was sent successfully!!!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sentQuotas(Quotas quota){
        String title = "ALARM";
        String body = "Dear Sir/Madam, \n  \n  You have received this email because you are registered " +
                "on the mailing list of the odds difference website. The program is our next game " +
                "that is favorable for the game:\n  \n" +
                "https://www.mozzartbet.com/sr/kladjenje-2018#/date/three_days?sid=1  \n" + quota.toString();
        sendEmail(title, body);
    }

}
