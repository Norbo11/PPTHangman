import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Game {

  public static Map<String, Integer> leaderboardToSet(List<Player> allPlayers) {
    return allPlayers.stream()
        .collect(Collectors.toMap(Player::toString, Player::getScore));
  }

  public static void main(String[] args) {
    System.out.println("Welcome to PPTHangman!");
    System.out.println("Please enter the number of players:");

    Scanner scanner;
    int numPlayers;
    String numPlayersString;

    while (true) {
      scanner = new Scanner(System.in);
      numPlayersString = scanner.nextLine();

      if (numPlayersString.length() == 0){
        System.out.println("Invalid input, please insert a number:");
      } else {
        numPlayers = Integer.parseInt(numPlayersString);
        if (numPlayers > 1) {
          break;
        } else if (numPlayers == 1) {
          System.out.println(
              "You cannot play alone! Please enter a number greater than 1:");
        } else {
          System.out.println("Please enter a number greater than 1:");
        }
      }
    }

    List<Player> allPlayers = new ArrayList<>();

    for (int i = 0; i < numPlayers; i++) {
      System.out.println("Please enter the name of player " + (i + 1) + ":");
      String playerName;
      while (true) {
        playerName = scanner.nextLine();
        if (!allPlayers.stream().map(Object::toString)
            .collect(Collectors.toList()).contains(playerName)) {
          if (playerName.length() != 0) {
            break;
          }
          System.out.println("Invalid name, please enter another name");
        } else {
          System.out.println("Please enter another name, " + playerName
              + " has already been taken:");
        }
      }

      allPlayers.add(new Player(playerName));
    }

    int currentLeaderIndex = 0;

    mainLoop:
    while (true) {

      Player leader = allPlayers.get(currentLeaderIndex);

      Word word;
      System.out.println(leader + ", you are the leader, please pick a word:");

      while (true) {
        word = new Word(scanner.nextLine());
        if (word.wordLength() != 0) {
          break;
        }
        System.out.println("Please enter a word with at least 1 letter:");
      }

      List<Player> players = new ArrayList<>(allPlayers);
      players.remove(leader);

      Session session = new Session(scanner, players, leader, word);
      session.play();

      currentLeaderIndex = (currentLeaderIndex + 1) % allPlayers.size();
      System.out.println("Current Standings: ");
      System.out.println(leaderboardToSet(allPlayers));

      System.out.println("Do you wish to start a new game? Y/N");
      String answer = scanner.nextLine();

      while (!answer.startsWith("y") && !answer.startsWith("Y")) {
        if (answer.startsWith("N") || answer.startsWith("n")) {
          break mainLoop;
        } else {
          System.out.println("Sorry, couldn't understand");
          System.out.println("Do you wish to start a new game? Y/N");
          answer = scanner.nextLine();
        }
      }

      System.out.println("Thank you for playing! These are our top players:");
      int topScore = allPlayers.get(0).getScore();
      List<Player> topPlayers = new ArrayList<>();
      for (Player p : allPlayers) {
        if (p.getScore() >= topScore) {
          topPlayers.add(p);
          topScore = p.getScore();
        }
      }
      topPlayers.forEach(p -> System.out.print(p + ", "));
      System.out.println("with " + topScore + " points!");
      System.out.println("Thanks for playing!");
    }
  }
}
