import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiplayerServer {

  public static void main(String[] args) throws IOException {

    if (args.length != 2) {
      System.err.println("Usage: java MultiplayerServer <port number> <number of players>");
      System.exit(1);
    }

    int portNumber = Integer.parseInt(args[0]);
    int numPlayers = Integer.parseInt(args[1]);
    ServerSocket serverSocket = new ServerSocket(portNumber);
    MultiplayerGame game = new MultiplayerGame(numPlayers);
    System.out.println("Server created! Please connect to: "
        + InetAddress.getLocalHost().getHostName() + " at port: " + portNumber);

    while (true) {
      Socket clientSocket = serverSocket.accept();
      PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
      if (!game.tryAddPlayer(clientSocket)) {
        out.println("Room is full! Please try again later!");
        clientSocket.close();
      }
    }
  }
}
