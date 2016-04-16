package se.liu.ida.emiha868.tetris.collision;


import se.liu.ida.emiha868.tetris.board.Board;
import se.liu.ida.emiha868.tetris.Poly;
import se.liu.ida.emiha868.tetris.SquareType;

/**
 * This is the collisionhandling during no powerup
 */
public class DefaultCollisionHandler implements CollisionHandler
{
    /*iterates over the falling's array and compares the squaretypes with the board's squaretypes
    * and if the falling's squaretype overlaps an non-empty block it will count as an collision*/
    @Override
    public boolean hasCollision(Board board) {
	Poly falling = board.getFalling();
	int fallingX = board.getFallingX();
	int fallingY = board.getFallingY();
	for (int row = fallingX; row < fallingX + falling.getPolys().length; row++) {
	    for (int col = fallingY; col < fallingY + falling.getPolys()[0].length; col++) {
		if (falling.getSquare(row - fallingX, col - fallingY) != SquareType.EMPTY &&
		    board.getSquare(row, col) != SquareType.EMPTY) {
		    return true;
		}
	    }
	}
	return false;
    }

    @Override
    public String getDescription(){
	return "Default";
    }

/// /    public void changeBoard(Board board){
//	board.insertFallenBlock();
//    }

}
