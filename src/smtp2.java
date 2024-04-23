import javax.net.ssl.SSLSocket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Base64;

public class smtp2 {
    public static void main(String[] args) {

        String host = "smtp.gmail.com";
        int port = 587;
        String em = "smtp.test2115@gmail.com";
        String recem = "bartosz.sakowicz@gmail.com";
        String passwd = "nhijslmallixcavh";
        String msg = "\r\n Wiadomość potwierdzająca działanie zadania 5 bez wykorzystania javax.mail";

        ServerComm sc = new ServerComm(host, port, msg);
        try {
            sc.startSocket();
            sc.startTLS();

            SSLSocket sslSocket = sc.startSSL();
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
            PrintWriter outToServer = new PrintWriter(sslSocket.getOutputStream(), true);

            String email = Base64.getEncoder().encodeToString(em.getBytes()) + "\r\n";
            String password = Base64.getEncoder().encodeToString(passwd.getBytes()) + "\r\n";

            sc.auth(inFromServer, outToServer, email, password);

            sc.sendingMail(inFromServer, outToServer, em, recem);

            sc.closingComm(inFromServer, outToServer);

            System.out.println("Mail sent and connection closed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
