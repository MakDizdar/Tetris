package se.liu.ida.emiha868.tetris.board;

/**
 * enum for movement of polys, returns the values that increments the X and Y cordinates.
 * Incrementation of X makes it fall down and incrementation of Y makes it move right and vice versa
 */
public enum Move {
    /*IntelliJ IDEA  wants me to write a javadoc, however, the point with this enum is
    that it is trivial to see what direction we want to face
     */

    DOWN(1,0), RIGHT(0,1), LEFT(0,-1);

    /**
     * X cordinate, when poly falls down, fallingX will increment with 1
     */
    public final int deltaX;
    /**
     * Y cordinate, representing sideways movement, when Poly is told to fall DOWN,
     * fallingY will obviously increment with 0
     */
    public final int deltaY;


    Move(final int deltaX, final int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }
}
