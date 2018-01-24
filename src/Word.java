import java.util.HashSet;
import java.util.Set;

public class Word {
    private Set<Character> guessedChars;
    private String originalWord;

    public Word(String originalWord) {
        this.originalWord = originalWord;
        guessedChars = new HashSet<>();
    }

    private static Set<Character> unguessableChars = new HashSet<>();
    {
        unguessableChars.add(' ');
        unguessableChars.add('"');
        unguessableChars.add('.');
        unguessableChars.add(',');
        unguessableChars.add('@');
    }

    public GuessResult guess(String guess) {
        if (guess.length() == 1) {
            char c = guess.charAt(0);

            if (unguessableChars.contains(c)) {
                return GuessResult.UNGUESSABLE_CHAR;
            }

            if (guessedChars.contains(c)) {
                return GuessResult.CHAR_ALREADY_GUESSED;
            }

            guessedChars.add(c);

            if (originalWord.contains(c + "")) {
                Set<Character> allChars = new HashSet<>(guessedChars);
                allChars.addAll(unguessableChars);

                for (int i = 0; i < originalWord.length(); i++) {
                    if (!allChars.contains(originalWord.charAt(i))) {
                        return GuessResult.CORRECT_GUESS;
                    }
                }

                return GuessResult.COMPLETED_WORD;
            }

            return GuessResult.INCORRECT_GUESS;
        }

        if (originalWord.equals(guess)) {
            return GuessResult.COMPLETED_WORD;
        }

        return GuessResult.INCORRECT_GUESS;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < originalWord.length(); i++) {
            char c = originalWord.charAt(i);

            if (unguessableChars.contains(c) ||
                guessedChars.contains(c)) {
                builder.append(c);
            } else {
                builder.append("_");
            }

        }

        return builder.toString();
    }

}
