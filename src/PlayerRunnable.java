import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

//Player thread responsible for responding to player inputs.
public class PlayerRunnable implements Runnable {

  private Socket socket;
  private MultiplayerGame game;
  private String name;
  private PrintWriter out;
  private BufferedReader in;

  public PlayerRunnable(Socket socket, MultiplayerGame game) {
    this.socket = socket;
    this.game = game;
  }

  @Override
  public void run() {
    try {
      out = new PrintWriter(socket.getOutputStream(), true);
      in = new BufferedReader(
          new InputStreamReader(socket.getInputStream()));

      out.println("Please enter your name!");
      name = in.readLine();
      out.println(name + "! Please wait while more players join...");
      game.createPlayer(name, in, out);

      String clientInput;
      while ((clientInput = in.readLine()) != null) {
        //not sure if locking the whole game up is the best way to go about it
        //as it kind of defeats the purpose of multi threading.
        synchronized (game) {
          respondToInput(clientInput);
        }
      }
    } catch (IOException e) {
      return;
    }
  }

  private void respondToInput(String input) {
    switch (game.getState()) {
      case FINDING_PLAYERS:
        out.println(name + "! Please wait while more players join...");
        break;
      case SETTING_WORD:
        if (name.equals(game.getLeaderName())) {
          game.setWord(input);
          break;
        }
        out.println("Please wait for " + game.getLeaderName() + " to choose a word!");
      case GUESSING_WORD:
        game.makeGuess(name, input);
        break;
      case GAME_OVER:
        if (input.equals("Y")) {
          game.newGame();
          break;
        } else if (input.equals("N")) {
          game.broadcast("Bye!");
          System.exit(1);
        } else {
          out.println(input + " is an invalid answer");
        }
        return;
      default:
        return;
    }
  }
}
