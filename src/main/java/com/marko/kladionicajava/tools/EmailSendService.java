package com.marko.kladionicajava.tools;

import com.marko.kladionicajava.entitiy.Email;
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
import java.util.List;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class EmailSendService {
    private final AppConfigService appConfigService;
    private final EmailService emailService;

    private void sendEmail(Quota quota) {

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
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            for (int i = 0; i < listEmail.size(); i++) {
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(listEmail.get(i).getEmail()));
            }
            message.setSubject("Razlika u kvoti");
            message.setText(quota.toString());
            Multipart multipart = new MimeMultipart();
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            //String filePath = "result/" + nameFileGlobal;
            // attachmentBodyPart.attachFile(filePath);
            // multipart.addBodyPart(attachmentBodyPart);
            // message.setContent(multipart);
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
