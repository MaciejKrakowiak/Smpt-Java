import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Smtp {

    private static Properties setProp(String host, int port)
    {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        return properties;
    }

    private static Authenticator setAuth(String from, String password)
    {
        return new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        };
    }

    private static Session setSession(Properties prop, Authenticator auth)
    {
        return Session.getInstance(prop,auth);
    }

    private static void sendingMessage(Session session, String from, String to)
    {
        try {

            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            message.setSubject("Zadanie 5 z javax.mail 240719");

            message.setText("Wiadomość potwierdzająca działanie zadania 5 z wykorzystaniem javax.mail");

            Transport.send(message);

            System.out.println("Message sent successfully");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        String from = "smtp.test2115@gmail.com";

        String password = "nhij slma llix cavh";

        String to = "bartosz.sakowicz@gmail.com";

        String host = "smtp.gmail.com";

        int port = 587;

        Properties prop = setProp(host,port);

        Authenticator auth = setAuth(from,password);

        Session session = setSession(prop,auth);

        sendingMessage(session,from,to);
    }
}
