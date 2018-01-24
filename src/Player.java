public class Player {
    private String name;
    private int score;

    public Player(String name) {
        this.name = name;
    }

    public void incrementScore(int n) {
        score += n;
    }

    public void decrementScore(int n) {
        score -= n;
    }

    @Override
    public String toString() {
        return name;
    }
}
