import java.io.BufferedReader;
import java.io.PrintWriter;

public class OnlinePlayer extends Player {

  private BufferedReader in;
  private PrintWriter out;

  public OnlinePlayer(String name, BufferedReader in, PrintWriter out) {
    super(name);
    this.in = in;
    this.out = out;
  }

  public void send(String message) {
    out.println(message);
  }

}
