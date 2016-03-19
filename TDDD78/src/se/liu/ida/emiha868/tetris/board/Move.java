package se.liu.ida.emiha868.tetris.board;

public enum Move {
    DOWN(1,0), RIGHT(0,1), LEFT(0,-1);

    public final int deltaX;
    public final int deltaY;


    Move(final int deltaX, final int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }
}
