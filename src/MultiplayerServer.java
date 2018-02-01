import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class MultiplayerServer {

  public static void main(String[] args) throws IOException {
    HashMap<Integer, String> players = new HashMap<>();
    if (args.length != 2) {
      System.out.println("Usage: java MultiplayerServer <port number> <number of players>");
      System.exit(1);
    }

    int portNumber = Integer.parseInt(args[0]);
    int numPlayers = Integer.parseInt(args[1]);

    while (true) {
      try {
        ServerSocket serverSocket =
          new ServerSocket(portNumber);
        Socket clientSocket = serverSocket.accept();
        players.put(clientSocket.getPort(), )
        Thread clientThread = new Thread(new PlayerThread());
        clientThread.start();
      } catch (IOException e) {
        System.out.println("Exception caught when trying to listen on port "
          + portNumber + " or listening for a connection");
        System.out.println(e.getMessage());
      }
    }

  }
}
