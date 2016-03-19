package se.liu.ida.emiha868.tetris.collision;


import se.liu.ida.emiha868.tetris.board.Board;
import se.liu.ida.emiha868.tetris.Poly;
import se.liu.ida.emiha868.tetris.SquareType;

public class DefaultCollisionHandler implements CollisionHandler
{
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
    public void changeBoard(Board board){
	board.insertFallenBlock();
    }

    @Override
    public String getDescription(){
	return "Default";
    }
}
