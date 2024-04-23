import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerComm {
    public String host;
    public int port;
    public String msg;
    public String endmsg = "\r\n.\r\n";

    public Socket clientSocket;

    public ServerComm(String host, int port, String msg) {
        this.host = host;
        this.port = port;
        this.msg = msg;
    }

    public void startSocket() throws IOException {
        this.clientSocket = new Socket(host, port);
    }

    public void startTLS() throws IOException {
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(), true);

        String recv = inFromServer.readLine();
        System.out.println(recv);


        String command = "HELO localhost\r\n";
        outToServer.print(command);
        outToServer.flush();
        recv = inFromServer.readLine();
        System.out.println(recv);


        command = "STARTTLS\r\n";
        outToServer.print(command);
        outToServer.flush();
        recv = inFromServer.readLine();

        System.out.println(recv);
    }

    public SSLSocket startSSL() throws IOException {
        SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        return (SSLSocket) sslSocketFactory.createSocket(clientSocket, clientSocket.getInetAddress().getHostAddress(), clientSocket.getPort(), true);
    }

    public void auth(BufferedReader inFromServer, PrintWriter outToServer, String email, String password) throws IOException {
        outToServer.print("AUTH LOGIN \r\n");
        outToServer.flush();
        String recv = inFromServer.readLine();
        System.out.println(recv);


        outToServer.print(email);
        outToServer.flush();
        recv = inFromServer.readLine();
        System.out.println(recv);

        outToServer.print(password);
        outToServer.flush();
        recv = inFromServer.readLine();
        System.out.println(recv);
    }

    public void sendingMail(BufferedReader inFromServer, PrintWriter outToServer, String em, String recem) throws IOException {
        outToServer.print("MAIL FROM: <" + em + ">\r\n");
        outToServer.flush();
        String recv = inFromServer.readLine();
        System.out.println(recv);

        outToServer.print("RCPT TO: <" + recem + ">\r\n");
        outToServer.flush();
        recv = inFromServer.readLine();
        System.out.println(recv);

        outToServer.print("DATA\r\n");
        outToServer.flush();
        recv = inFromServer.readLine();
        System.out.println(recv);

        outToServer.print("Subject: Zadanie 5 bez javax.mail 240719 \r\n");
        outToServer.print("To: " + recem + "\r\n");
        outToServer.print(msg);
        outToServer.print(endmsg);
        outToServer.flush();
        recv = inFromServer.readLine();
        System.out.println(recv);
    }

    public void closingComm(BufferedReader inFromServer, PrintWriter outToServer) throws IOException {
        outToServer.print("QUIT\r\n");
        outToServer.flush();
        String recv = inFromServer.readLine();
        System.out.println(recv);
        clientSocket.close();
    }
}