package org.example;
import java.util.Scanner;
import java.util.Vector;

//easy = 8x8 w/ 10 bombs
//medium = 12x12 w/ 15 bombs
//hard = 16x16 w/ 20 bombs
//fix bomb spawning
//for each coord, check neighbour coords
    //how to check for edges

public class Grid {
    Scanner scanner = new Scanner(System.in);

    boolean gridCreated = false;
    boolean gameStarted = false;
    boolean gameWon = false;

    int difficulty;
    int size;
    int bombs;

    Vector<Coordinate> bombLocations = new Vector<>();
    Vector<Coordinate> flagLocations = new Vector<>();
    Vector<Vector<Boolean>> grid = new Vector<Vector<Boolean>>();

    Grid(int difficulty) {
        this.difficulty = difficulty;
        this.initGame();
    }

    void initGame() {
        this.createGrid();
        this.plantBombs();
    }

    void startGame() {
        gameStarted = true;
        while (gameStarted) {
            printGrid();
            Coordinate input = playerTurn();
            if (!processTurn(input)) {
                System.out.println("Bomb clicked on. Game over.");
                gameStarted = false;
            } else {
                for (Coordinate c : flagLocations) {
                    if (!bombLocations.contains(c)) {
                        System.out.println("Nice work but more bombs to go!");
                    } else {
                        System.out.println("All bombs found. Well done.");
                        gameWon = true;
                        gameStarted = false;
                    }
                }
            }
        }
    }

    Coordinate playerTurn() {
        int x = -1;
        int y = -1;

        boolean validX = false;
        while (!validX) {
            validX = true;
            System.out.println("Please enter the x value of the coordinate:\n    ");
            x = scanner.nextInt();
            if (x > this.size-1) {
                System.out.println("Input exceeds bounds. Try again.");
                validX = false;
            }
        }

        boolean validY = false;
        while (!validY) {
            validY = true;
            System.out.println("Please enter the y value of the coordinate:\n    ");
            y = scanner.nextInt();
            if (y > this.size-1) {
                System.out.println("Input exceeds bounds. Try again.");
                validY = false;
            }
        }

        return new Coordinate(x, y);
    }

    boolean processTurn(Coordinate c) {
        if (grid.get(c.x).get(c.y)) {
            System.out.println("Bomb found.");
            return false;
        } else {
            System.out.println("Spot clear.");
            return true;
        }
    }

    void createGrid() {
        switch (this.difficulty) {
            case 0 -> {
                this.size = 8;
                this.bombs = 10;
            }

            case 1 -> {
                this.size = 12;
                this.bombs = 15;
            }

            case 2 -> {
                this.size = 16;
                this.bombs = 20;
            }

            default -> {
                System.out.println("How did you even get here?");
                System.exit(0);
            }
        }

        for (int i = 0; i < size; i++) {
            grid.add(new Vector<Boolean>());
            for (int j = 0; j < size; j++) {
                grid.get(i).add(false);
            }
        }

        gridCreated = true;
    }

    void printGrid() {
        if (!gridCreated) {
            System.out.println("Need to create grid before printing.");
            return;
        }

        System.out.println();
        for (Vector<Boolean> vector : grid) {
            for (boolean bool : vector) {
                int x = bool ? 1 : 0;
                System.out.print(x + " ");
            }
            System.out.println();
        }
    }

    void plantBombs() {
        for (int i = 0; i < 10; i++) {
            boolean isValid = false;
            while (!isValid) {
                isValid = true;
                Coordinate c = Coordinate.getRandomCoordinate(this.size);
                System.out.println("Bomb placed at (" + c.x + ", " + c.y + ").");
                if (!bombLocations.contains(c)) {
                    bombLocations.add(c);
                    grid.get(c.x).set(c.y, true);
                } else {
                    System.out.println("Duplicate found, trying again.");
                    isValid = false;
                }
            }
        }
    }
}

