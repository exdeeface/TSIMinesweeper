package org.example;

import java.util.Objects;
import java.util.Scanner;

public class GameInstance {
    Grid grid;

    boolean gameStarted = false;
    boolean gameWon = false;

    Scanner scanner = new Scanner(System.in);

    GameInstance(int difficulty) {
        grid = new Grid(difficulty);
    }

    void start() {
        gameStarted = true;

        while (gameStarted) {

            grid.printGrid();
            playerTurn();

            if (grid.checkGridState()) {
                gameStarted = false;
                gameWon = true;
            }
        }

        if (gameWon) {
            System.out.println("Congratulations!");
        } else {
            System.out.println("Better luck next time!");
        }
    }


    boolean checkForBomb(Coordinate c) {
        if (grid.matrix.get(c.x).get(c.y).hasBomb()) {
            System.out.println("Uh oh.");
            return false;
        } else {
            System.out.println("Spot clear.");
            grid.matrix.get(c.x).get(c.y).setRevealed(true);
            return true;
        }
    }

    Coordinate getPlayerInput() {
        int x = -1;
        int y = -1;

        boolean validX = false;
        while (!validX) {
            validX = true;
            System.out.print("\nPlease enter the value of the row Coordinate:\n    >> ");
            x = scanner.nextInt()-1;
            if (x > grid.size-1 || x < 0) {
                System.out.println("Input exceeds bounds. Please enter numbers between 01 and " + grid.size + "Try again.");
                validX = false;
            }
        }

        boolean validY = false;
        while (!validY) {
            validY = true;
            System.out.print("\nPlease enter the value of the column Coordinate:\n    >> ");
            y = scanner.nextInt()-1;
            if (y > grid.size-1 || y < 0) {
                System.out.println("Input exceeds bounds. Please enter numbers between 01 and " + grid.size + "Try again.");
                validY = false;
            }
        }

        return new Coordinate(x, y);
    }

    void playerTurn() {
        String inputChoice = "";

        boolean validInput = false;
        while (!validInput) {
            validInput = true;
            System.out.print("Select Action: \n    'F': Place/Remove Flag \n    'R': Reveal Tile\n        >> ");
            inputChoice = scanner.next();
            if (!Objects.equals(inputChoice, "F") && !Objects.equals(inputChoice, "R") && (!Objects.equals(inputChoice, "f") && !Objects.equals(inputChoice, "r"))) {
                validInput = false;
            }
        }

        Coordinate c = getPlayerInput();
        if (inputChoice.equals("f") || inputChoice.equals("F")) {
            grid.matrix.get(c.x).get(c.y).setFlag();
        } else if (inputChoice.equals("r") || inputChoice.equals("R")) {
            if (!checkForBomb(c)) {
                System.out.println("Bomb found at (" + c.x + ", " + c.y + ")");
                gameStarted = false;
            }
        }
    }
}
