package org.example;

public class Tile {
    Grid parent;

    public Coordinate here;

    private boolean hasBomb = false;
    private boolean hasFlag = false;
    private boolean isRevealed = false;

    int neighbouringBombs = 0;

    Tile(Grid parent, int x, int y) {
        this.here = new Coordinate(x, y);
        this.parent = parent;
    }

    boolean hasBomb() { return this.hasBomb; }
    void plantBomb() { this.hasBomb = true; }

    boolean hasFlag() { return this.hasFlag; }
    void setFlag() { this.hasFlag = !this.hasFlag; }

    boolean isRevealed() { return this.isRevealed; }
    void revealTile() { this.isRevealed = true; }

    void checkNeighbours(int size) {
        for (int i = this.here.x-1; i <= this.here.x+1; i++) {
            for (int j = this.here.y-1; j <= this.here.y+1; j++) {
                if (i != this.here.x || j != this.here.y) {
                    if ((i >= 0 && i <= size-1) && (j >= 0 && j <= size-1)) {
                        if (parent.getTileFromCoordinate(i, j).hasBomb) { this.neighbouringBombs++; } }
                }
            }
        }
    }
}
