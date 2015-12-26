import java.io.*;
import java.net.*;
import java.util.*;

public class EchoServer {
  public static final int MAXCONN = 300;

  public static void main(String[] args) {
    ServerSocket servsock = null;
    Socket socket;
    int port;

    if (args.length == 0) {
      port = 9999;
    } else {
      port = Integer.parseInt(args[0]);
    }

    try {
      servsock = new ServerSocket(port, MAXCONN);
      System.out.println("Server Started (ip addr: " +
                         servsock.getInetAddress().getHostAddress() + " , port: " +
                         servsock.getLocalPort() + ")");

      while(true) {
        socket = servsock.accept();
        System.out.println(new Date().toString() + ": Incoming connection established from " +
                           ((InetSocketAddress)(socket.getRemoteSocketAddress())).getAddress().getHostAddress()); 
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        String line;
        String sendBack;

        while((line = in.readLine()) != null) {
          System.out.println("Received: " + line);
          if (line.charAt(0) == '.') {
            System.out.println("Close connection for " +
                               ((InetSocketAddress)(socket.getRemoteSocketAddress())).getAddress().getHostAddress());
            break;
          }

          sendBack = new Date().toString() + " : sendback from " +
                     socket.getLocalAddress().getHostAddress() + ":" + socket.getLocalPort() + " : " +
                     line;
          out.println(sendBack);
          System.out.println("Sent: " + sendBack);
        }
        socket.close();

      }
    } catch (IOException e0) {
      try {
        servsock.close();
        System.exit(1);
      } catch (IOException e1) {
        System.exit(1);
      }
    }
  }
}
    

