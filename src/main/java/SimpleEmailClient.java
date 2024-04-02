import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
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

            String imagePath = "src\\main\\resources\\weird-cat-pictures-gpxfomxfs1l1fj97.jpg";

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(bodyText);
            MimeBodyPart imagePart = new MimeBodyPart();
            imagePart.attachFile(imagePath);

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
}


//    String username = "ibryaevaa@gmail.com"; // адрес электронной почты
//    String password = "vmdi yrfg ykvs efsu"; // пароль приложений
//
//    String toAddress = "germionaalina@mail.ru"; // адрес получателя
