import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerListener implements Runnable {

  private Socket socket;
  public static final String roomFullCode = "ROOM_FULL";

  public ServerListener(Socket socket) {
    this.socket = socket;
  }
  @Override
  public void run() {
    String serverInput;
    try {
      PrintWriter out =
          new PrintWriter(socket.getOutputStream(), true);
      BufferedReader in =
          new BufferedReader(
              new InputStreamReader(socket.getInputStream()));
      while ((serverInput = in.readLine()) != null) {
        System.out.println(serverInput);
      }
      System.err.println("Connection with server has been severed! closing...");
      System.exit(1);
    } catch (IOException e) {
      System.err.println("IOException from ServerListener: " + e);
      System.exit(1);
    }

  }
}
