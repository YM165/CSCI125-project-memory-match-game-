public class Player {
    private String name;
    private int score;
    private int attempts;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.attempts = 0;
    }
  
    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public int getAttempts() {
        return attempts;
    }

    public void addPoint() {
        score++;
    }

    public void addAttempt() {
        attempts++;
    }

    public double getAccuracy() {
        if (attempts == 0) return 0;
        return ((double) score / attempts) * 100;
    }

    public void displayStats() {
        System.out.println("\n--- " + name + "'s Stats ---");
        System.out.println("Pairs Found: " + score);
        System.out.println("Attempts: " + attempts);
        System.out.printf("Accuracy: %.1f%%\n", getAccuracy());
    }
}
