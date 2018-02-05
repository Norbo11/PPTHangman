import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MultiplayerGame {

  OnlinePlayer[] players;
  int numPlayers;
  int numConnected = 0;
  GameState state;
  private int currentLeaderIndex;
  private Word word;
  private int lives;

  public MultiplayerGame(int numPlayers) {
    this.numPlayers = numPlayers;
    this.players = new OnlinePlayer[numPlayers];
    state = GameState.FINDING_PLAYERS;
  }

  public boolean tryAddPlayer(Socket clientSocket) {
    if (numConnected < numPlayers) {
      numConnected++;
      Thread thread = new Thread(new PlayerRunnable(clientSocket, this));
      thread.start();
      return true;
    }
    return false;
  }

  public void createPlayer(String name, BufferedReader in, PrintWriter out) {
    broadcast(name + " has joined the game!");
    for (int i = 0; i < numPlayers; i++) {
      if (players[i] == null) {
        players[i] = new OnlinePlayer(name, in, out);
        break;
      }
    }
    checkReady();
  }
  public void checkReady() {
    for (int i = 0; i < numPlayers; i++) {
      if (players[i] == null) {
        return;
      }
    }
    StringBuilder sb = new StringBuilder();
    for (OnlinePlayer player : players) {
      sb.append("\n");
      sb.append(player.toString());
    }
    changeState(GameState.READY);
    broadcast("All players are ready! The players are:" + sb);
    play();
  }

  public GameState getState() {
    return state;
  }

  public void broadcast(String message) {
    for (OnlinePlayer player : players) {
      if (player != null) {
        player.send(message);
      }
    }
  }

  private void changeState(GameState state) {
    this.state = state;
  }

  private void play() {
    lives = 7;
    OnlinePlayer leader = players[currentLeaderIndex];
    broadcast("Waiting for " + leader + " to pick a word...");
    changeState(GameState.SETTING_WORD);
    leader.send(leader + ", you are the leader, please pick a word:");
  }

  public void newGame() {
    currentLeaderIndex++;
    play();
  }

  public void setWord(String word) {
    this.word = new Word(word);
    changeState(GameState.GUESSING_WORD);
    broadcast("Word has been set. Start guessing!");
  }

  private void lose() {
    changeState(GameState.GAME_OVER);
    broadcast("Game lost!\nWould you like to start a new game?");
  }

  private void win() {
    changeState(GameState.GAME_OVER);
    broadcast("Game won!\nWould you like to start a new game?");
  }

  public void makeGuess(String name, String guess) {
    if (state != GameState.GUESSING_WORD) {
      return;
    }
    broadcast(name + " just guessed: " + guess);
    switch (word.guess(guess)) {
      case CORRECT_GUESS:
        broadcast("Correct guess!\n" + word);
        break;
      case INCORRECT_GUESS:
        lives--;
        broadcast("Incorrect guess, lose a live\n" + word);
        if (lives <= 0) {
          lose();
          return;
        }
        break;
      case UNGUESSABLE_CHAR:
        broadcast("Character cannot be guessed, please pick another\n" + word);
        break;
      case COMPLETED_WORD:
        broadcast(name + " guessed the word correctly!\n" + word);
        win();
        return;
      case CHAR_ALREADY_GUESSED:
        broadcast("Character already guessed, please pick another");
        break;
    }
  }

  public String getLeaderName() {
    return players[currentLeaderIndex].toString();
  }
}
