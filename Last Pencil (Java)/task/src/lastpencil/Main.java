package lastpencil;

import java.util.Random;
import java.util.Scanner;

public class Main {

    private final static int BOT_IDX = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // get amount of pencils
        System.out.println("How many pencils would you like to use:");
        int pencilsNumber = acquirePencilsNumber(scanner);

        // define players, Jack is always a bot
        String[] players = {"John", "Jack"};

        // determine who's starting
        System.out.printf("Who will be the first (%s, %s):%n", players[0], players[1]);
        String whoStartsName = acquireWhoStarts(scanner, players);

        // determine an index of the player who's moving first
        int movingPlayerIdx = players[0].equals(whoStartsName) ? 0 : 1;
        System.out.println("|".repeat(pencilsNumber));

        startGame(scanner, pencilsNumber, players, movingPlayerIdx);

        scanner.close();
    }

    private static String acquireWhoStarts(Scanner scanner, String[] players) {
        String whoStartsName;
        while (true) {
            whoStartsName = scanner.nextLine();
            if (players[0].equals(whoStartsName) || players[1].equals(whoStartsName)) {
                return whoStartsName;
            }
            System.out.println("Choose between 'John' and 'Jack'");
        }
    }

    private static void startGame(Scanner scanner, int pencilsNumber, String[] players, int movingPlayerIdx) {
        // Finish when all pencils are taken
        while (pencilsNumber > 0) {
            System.out.printf("%s's turn!%n", players[movingPlayerIdx]);
            int takePencils;

            // Determine who's moving and get number of taken pencils by the player
            if (movingPlayerIdx == BOT_IDX) {
                takePencils = getBotMove(pencilsNumber);
            } else {
                takePencils = getUserMove(scanner, pencilsNumber);
            }

            // Subtract taken pencils and determine the next move player or winner
            pencilsNumber -= takePencils;
            movingPlayerIdx = nextPlayer(movingPlayerIdx);

            // Show the left pencils
            if (pencilsNumber > 0) {
                System.out.println("|".repeat(pencilsNumber));
            }
        }
        // Game finished
        System.out.printf("%s won!", players[movingPlayerIdx]);
    }

    private static int getBotMove(int pencilsNumber) {
        int move;

        if (pencilsNumber == 1) {
            move = 1;
        } else {
            move = pencilsNumber % 4 - 1;
            if (move == -1) {
                move = 3;
            }
            if (move == 0) {
                move = new Random().nextInt(2) + 1;
            }
        }
        System.out.println(move);
        return move;
    }

    private static int getUserMove(Scanner scanner, int pencilsNumber) {
        int takePencils;
        while (true) {
            try {
                takePencils = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                takePencils = -1;
            }
            if (takePencils < 1 || takePencils > 3) {
                System.out.println("Possible values: '1', '2' or '3'");
                continue;
            }
            if (takePencils > pencilsNumber) {
                System.out.println("Too many pencils were taken");
                continue;
            }
            return takePencils;
        }
    }

    private static int acquirePencilsNumber(Scanner scanner) {
        int pencilsNumber = -1;
        while (true) {
            try {
                pencilsNumber = Integer.parseInt(scanner.nextLine());

                if (pencilsNumber > 0) {
                    return pencilsNumber;
                } else if (pencilsNumber == 0) {
                    System.out.println("The number of pencils should be positive");
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println("The number of pencils should be numeric");
            }
        }
    }

    private static int nextPlayer(int currentIdx) {
        if (currentIdx > 1)
            throw new IllegalArgumentException("The index can't be grater than 2, as there can be only 2 players.");

        return (1 + currentIdx) % 2;
    }
}
