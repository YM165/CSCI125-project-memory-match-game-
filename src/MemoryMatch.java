import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Arrays;

public class MemoryMatch {
    public static void main(String[] args) {
        Game memoryGame = new Game();
        memoryGame.setup();
        memoryGame.play();
    }
}

class Card {
    private final String symbol;
    private boolean isMatched;
    private boolean isFaceUp;

    public Card(String symbol) {
        this.symbol = symbol;
        this.isMatched = false;
        this.isFaceUp = false;
    }

    public String getSymbol() { 
        return symbol; 
    }
    public boolean isMatched() { 
        return isMatched; 
    }
    public void setMatched(boolean matched) { 
        isMatched = matched; 
    }
    public boolean isFaceUp() { 
        return isFaceUp; 
    }
    public void setFaceUp(boolean faceUp) { 
        isFaceUp = faceUp; 
    }

    public String toString() {
        if (isMatched || isFaceUp) {
            return "[" + symbol + "]";
        } else {
            return "[ ? ]";
        }
    }
}

class Player {
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

class Game {
    private Card[][] board;
    private final int SIZE = 4;
    private Player player;
    private Scanner scanner = new Scanner(System.in);

    public void setup() {
        System.out.println("================================");
        System.out.println("   Welcome to Memory Match!");
        System.out.println("================================");
        System.out.print("Enter player name: ");
        player = new Player(scanner.nextLine());

        String[] symbols = {"A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F", "G", "G", "H", "H"};
        List<String> symbolList = Arrays.asList(symbols);
        Collections.shuffle(symbolList);

        board = new Card[SIZE][SIZE];
        int listId = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = new Card(symbolList.get(listId++));
            }
        }
    }

    public void play() {
        int totalPairs = (SIZE * SIZE) / 2;

        while (player.getScore() < totalPairs) {
            printBoard();

            // First Card Selection
            int[] firstCoord = getMove("Pick first card (row and column): ", null);
            Card card1 = board[firstCoord[0]][firstCoord[1]];
            card1.setFaceUp(true);
            printBoard();

            // Second Card Selection
            int[] secondCoord = getMove("Pick second card (row and column): ", firstCoord);
            Card card2 = board[secondCoord[0]][secondCoord[1]];
            card2.setFaceUp(true);
            printBoard();

            player.addAttempt();

            if (card1.getSymbol().equals(card2.getSymbol())) {
                System.out.println("\nIt's a Match!");
                card1.setMatched(true);
                card2.setMatched(true);
                player.addPoint();
            } else {
                System.out.println("\nIt's not a Match :( ");
                try { Thread.sleep(1500); } catch (InterruptedException e) {}
                card1.setFaceUp(false);
                card2.setFaceUp(false);
            }
            player.displayStats();
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }

        System.out.println("\nCongratulations! You found all pairs.");
        player.displayStats();
    }

    private int[] getMove(String prompt, int[] firstChoice) {
        int row, col;
        while (true) {
            System.out.print(prompt);
            try {
                row = scanner.nextInt();
                col = scanner.nextInt();
                scanner.nextLine(); // clear buffer

                if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
                    System.out.println("Out of range! Please use 0-3.");
                } else if (board[row][col].isMatched() || board[row][col].isFaceUp()) {
                    System.out.println("Card already showing or matched! Pick another.");
                } else if (firstChoice != null && row == firstChoice[0] && col == firstChoice[1]) {
                    System.out.println("You cannot pick the same card twice!");
                } else {
                    return new int[]{row, col};
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter two numbers separated by a space.");
                scanner.nextLine();
            }
        }
    }

    private void printBoard() {
        // Clear screen effect
        for (int i = 0; i < 5; i++) System.out.println();

        System.out.println("    0    1    2    3");
        System.out.println("  -------------------");
        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + " |");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(" " + board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
