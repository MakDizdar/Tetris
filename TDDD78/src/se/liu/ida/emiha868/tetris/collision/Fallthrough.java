package se.liu.ida.emiha868.tetris.collision;

import se.liu.ida.emiha868.tetris.board.Board;
import se.liu.ida.emiha868.tetris.Poly;
import se.liu.ida.emiha868.tetris.SquareType;

/**
 * Collisionhandling during fallthrough powerup. Ignores collision with non-empty and non-outside squaretypes
 */
public class Fallthrough implements CollisionHandler
{
    /*If the falling overlaps an outside block a collision has occured, therefore it will ignore collision with
     * non-outside squaretypes which makes it go through a bunch of squares. Iteration is described in
     * DefaultCollisionHandler */
    @Override
    public boolean hasCollision(Board board) {
	Poly falling = board.getFalling();
	int fallingX = board.getFallingX();
	int fallingY = board.getFallingY();
	for (int row = fallingX; row < fallingX + falling.getPolys().length; row++) {
	    for (int col = fallingY; col < fallingY + falling.getPolys()[0].length; col++) {
		if (falling.getSquare(row - fallingX, col - fallingY) != SquareType.EMPTY &&
		    board.getSquare(row, col) == SquareType.OUTSIDE) {
		    return true;
		}
	    }
	}
	return false;
    }


    @Override
    public String getDescription(){
	return "Fallthrough";
    }


}
