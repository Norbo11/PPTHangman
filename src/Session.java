import java.util.List;
import java.util.Scanner;

public class Session {

  private static final int MAX_LIVES = 7;

  private List<Player> players;
  private int lives;
  private Word word;
  private Player leader;
  private Scanner input;

  public Session(Scanner input, List<Player> players, Player leader, Word word, int lives) {
    this.lives = lives;
    this.players = players;
    this.leader = leader;
    this.word = word;
    this.input = input;
  }

  public Session(Scanner input, List<Player> players, Player leader, Word word) {
    this(input, players, leader, word, MAX_LIVES);
  }

  public void play() {
    int currentPlayerIndex = 0;

    while (lives > 0) {
      Player currentPlayer = players.get(currentPlayerIndex);

      System.out.println(currentPlayer + ": please enter a guess");

      guessLoop:
      while (true) {
        String guess = input.nextLine();

        switch (word.guess(guess)) {
          case CORRECT_GUESS:
            System.out.println("Correct guess!");
            break guessLoop;
          case INCORRECT_GUESS:
            lives--;
            System.out.println("Incorrect guess, lose a live");
            break guessLoop;
          case UNGUESSABLE_CHAR:
            System.out.println("Character cannot be guessed, please pick another");
            break;
          case COMPLETED_WORD:
            players.forEach(p -> p.incrementScore(5));
            currentPlayer.incrementScore(5);
            leader.decrementScore(3);
            System.out.println(word);
            System.out.println(
                "Game won! 10 points to " + currentPlayer + ", 5 to others, leader loses 3");
            return;
          case CHAR_ALREADY_GUESSED:
            System.out.println("Character already guessed, please pick another");
            break;
        }
      }

      System.out.println(word);
      currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    System.out.println("No lives left, round lost");
    leader.incrementScore(10);
  }


}
