package org.example;
import java.util.Vector;

public class Grid {
    boolean gridCreated = false;

    int difficulty;
    int size;
    int bombs;

    Vector<Tile> bombLocations = new Vector<>();
    Vector<Vector<Tile>> matrix = new Vector<>();

    Grid(int difficulty) {
        this.difficulty = difficulty;
        this.initGame();
    }

    void initGame() {
        this.createGrid();
        this.plantBombs();
        this.setDisplayNumbers();
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
            matrix.add(new Vector<>());
            for (int j = 0; j < size; j++) {
                matrix.get(i).add(new Tile(this, i, j));
            }
        }

        gridCreated = true;
    }

    void setDisplayNumbers() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix.get(i).get(j).checkNeighbours();
            }
        }
    }

    boolean checkGridState() {
        for (Tile tile : bombLocations) {
            if (!tile.hasFlag()) {
                return false;
            }
        }

        return true;
    }

    Tile getTileFromCoordinate(int x, int y) {
        return matrix.get(x).get(y);
    }

    void printGrid() {
        if (!gridCreated) {
            System.out.println("Need to create grid before printing.");
            return;
        }

        int sideNumbersY = 1;

        System.out.print("\n    ");
        for (int i = 0; i < this.size; i++) {
            if (sideNumbersY < 10) {
                System.out.print(" ");
            }
            System.out.print(sideNumbersY + " ");
            sideNumbersY++;
        }

        System.out.print("\n    ");
        for (int i = 0; i < this.size; i++) { System.out.print("__ "); }
        System.out.println();

        int sideNumbersX = 1;

        for (Vector<Tile> col : matrix) {
            if (sideNumbersX < 10) { System.out.print(" "); }
            System.out.print(sideNumbersX + " | ");
            sideNumbersX++;

            for (Tile tile : col) {
                if (tile.isRevealed()) {
                    if (tile.hasFlag()) {
                        System.out.print("F  ");
                    } else {
                        System.out.print(tile.getNeighbouringBombs() + "  ");
                    }
                } else {
                    System.out.print("W  ");
                }
            }

            /*
            if (tile.isRevealed()) {
                    System.out.print(tile.getNeighbouringBombs() + "  ");
                } else {
                    char x = tile.hasFlag() ? 'F' : 'W';
                    System.out.print(x + "  ");
                }
            }
             */

            System.out.println();
        }
    }

    void printGridAsIs() {
        if (!gridCreated) {
            System.out.println("Need to create grid before printing.");
            return;
        }

        int sideNumbersY = 1;

        System.out.print("\n    ");
        for (int i = 0; i < this.size; i++) {
            if (sideNumbersY < 10) { System.out.print(" "); }
            System.out.print(sideNumbersY + " ");
            sideNumbersY++;
        }

        System.out.print("\n    ");
        for (int i = 0; i < this.size; i++) { System.out.print("__ "); }
        System.out.println();

        int sideNumbersX = 1;

        for (Vector<Tile> col : matrix) {
            if (sideNumbersX < 10) { System.out.print(" "); }
            System.out.print(sideNumbersX + " | ");
            sideNumbersX++;

            for (Tile tile : col) {
                if (tile.hasBomb()) { System.out.print("B  "); }
                else { System.out.print(tile.getNeighbouringBombs() + "  "); }
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
                if (matrix.get(c.x).get(c.y).hasBomb()) {
                    isValid = false;
                } else {
                    bombLocations.add(matrix.get(c.x).get(c.y));
                    matrix.get(c.x).get(c.y).plantBomb();
                }
            }
        }
    }
}

