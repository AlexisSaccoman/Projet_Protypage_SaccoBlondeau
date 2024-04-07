package controllers.divers;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class EmailSender {

    private static String senderEmail;
    private static String senderPassword;

    public EmailSender() {
        this.senderEmail =  "envoiautomatiqueig@gmail.com";
        this.senderPassword = "Sb123456789";
    }
//    public static void sendMail(String prof, String content) {
//
//        // Paramètres du destinataire
//        String recipientEmail = "envoiautomatiqueig@gmail.com";
//
//        // Configuration des propriétés pour la session
//        Properties properties = new Properties();
//        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.starttls.enable", "true");
//        properties.put("mail.smtp.host", "smtp.gmail.com");
//        properties.put("mail.smtp.port", "587");
//
//        // Création d'une nouvelle session avec authentification
//        Session session = Session.getInstance(properties, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(senderEmail, senderPassword);
//            }
//        });
//
//        try {
//            // Création d'un objet Message
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(senderEmail));
//            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
//            message.setSubject("Pour Mr/Mme : " + prof);
//            message.setText(content);
//
//            // Envoi du message
//            Transport.send(message);
//            System.out.println("L'e-mail a été envoyé avec succès.");
//        } catch (MessagingException e) {
//            System.out.println("Une erreur s'est produite lors de l'envoi de l'e-mail : " + e.getMessage());
//        }
//    }

    public static void sendMail(String prof, String content) throws IOException, URISyntaxException {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            URI mailto = new URI("mailto:" + prof + "univ-avignon.fr");
            desktop.mail(mailto);
        }
    }
}

