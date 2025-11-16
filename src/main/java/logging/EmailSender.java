package logging;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailSender {

    private static final String SENDER_EMAIL = System.getenv("EMAIL_USER");
    private static final String SENDER_PASSWORD = System.getenv("EMAIL_PASS");
    private static final String RECEIVER_EMAIL = System.getenv("EMAIL_TO");

    private static final Properties props = new Properties();
    private static final Session session;

    static {
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
            }
        });
    }

    public static void sendAlert(String subject, String body) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(RECEIVER_EMAIL)
            );
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.err.println("✅ Лист успішно надіслано!");

        } catch (AuthenticationFailedException e) {
            System.err.println("❌ ПОМИЛКА: Невірний email або пароль програми!");
        } catch (MessagingException e) {
            System.err.println("❌ Не вдалося надіслати лист: " + e.getMessage());
        }
    }
}
