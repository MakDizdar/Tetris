package se.liu.ida.emiha868.tetris;

/**
 * The very core of Types for board and Poly that represent a brick.
 */
public enum SquareType
{
    //IDEA complains about standard naming conventions, but can be ignored

    /**
     * On the Board, represented as
     * The I squaretype represents the bricks of the tetromino that looks like a vertical line
     */
    I,

    /**
     * On the board, represented as the Blue colored brick
    *The J SquareType represents the bricks of the tetromino that looks like a J
    */
    J,

    /**
     *On board, represented as the Orange colored brick
    *The L SquareType represents the bricks of a mirrored J tetromino.
    */

    L,

    /**
     * Represent the Yellow colored brick
     *The O SquareType represents the bricks of the tetrimno that looks like a square.
    */

    O,

    /**
     * Represent the Green colored brick.
     *S squaretype is the bricks of the tetromino that looks like a mirrored Z.
     */

    S,

    /**
     *Represent the Violet colored brick
     *T squaretype are the type that represent the brick of the T looking tetromino
     *
    */

    T,

    /**
    *Represents the red colored brick
     * Z SquareType are the type that represent the brick of the Z looking tetromino
     * that is the mirrored version of J-SquareType
    */

    Z,

    /**
    *Represents an empty hole on board, one square that is EMPTY
     * is represented as gray color.
    */
    EMPTY,

    /**
    *This is the squareType that used for creating a 2 bricks width frame around the board.
     * This is used for collisionHandling.
    */
    /**
     * This is the squareType that used for creating a 2 bricks width frame around the board.
     * This is used for collisionHandling.
    */
    OUTSIDE
}
