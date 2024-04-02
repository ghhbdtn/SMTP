import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.Scanner;

public class SimpleEmailClient {
    public static void main(String[] args) throws MessagingException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter sender email address: ");
        String username = scanner.nextLine();

        System.out.print("Enter application-specific password: ");
        String password = scanner.nextLine();

        System.out.print("Enter recipient email address: ");
        String toAddress = scanner.nextLine();

        System.out.print("Enter email subject: ");
        String subject = scanner.nextLine();

        System.out.print("Enter email body text: ");
        String bodyText = scanner.nextLine();

        String host = "smtp.gmail.com";
        String port = "587";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };

        Session session = Session.getInstance(props, auth);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
            message.setSubject(subject);
            message.setText(bodyText);

            InputStream imageStream = getResourcePath("weird-cat-pictures-gpxfomxfs1l1fj97.jpg");
            DataSource source = new ByteArrayDataSource(imageStream, "image/jpg");
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(bodyText);
            BodyPart imagePart = new MimeBodyPart();
            imagePart.setHeader("Content-ID", "<image>");
            imagePart.setDataHandler(new DataHandler(source));
            imagePart.setFileName("cat.jpg");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(imagePart);

            message.setContent(multipart);
            Transport.send(message);


            System.out.println("The letter was sent successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static InputStream getResourcePath(String resourceName) {
        return SimpleEmailClient.class.getClassLoader().getResourceAsStream(resourceName);
    }
}