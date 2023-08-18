package org.example;

import java.util.Random;

public class Coordinate {
    int x;
    int y;

    Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    static Coordinate getRandomCoordinate(int size) {
        Random randX = new Random();
        Random randY = new Random();

        int x = randX.nextInt(size);
        int y = randY.nextInt(size);

        return new Coordinate(x, y);
    }
}
