package se.liu.ida.emiha868.tetris.collision;

import javafx.util.Pair;
//import se.liu.ida.emiha868.tetris.ztroubleshoot.BoardToTextConvert;
import se.liu.ida.emiha868.tetris.board.Board;
import se.liu.ida.emiha868.tetris.Poly;
import se.liu.ida.emiha868.tetris.SquareType;

import java.util.ArrayList;
import java.util.List;

public class Heavy implements CollisionHandler
{



@Override public boolean hasCollision(Board board){
    	Poly falling = board.getFalling();
   	int fallingX = board.getFallingX();
   	int fallingY = board.getFallingY();
    	boolean collision =false;
    	List<Pair<Integer,Integer>> ycords = new ArrayList<Pair<Integer,Integer>>();
   	for (int row = fallingX; row < fallingX + falling.getPolys().length; row++) {
   	    for (int col = fallingY; col < fallingY + falling.getPolys()[0].length; col++) {
   		if (falling.getSquare(row - fallingX, col - fallingY) != SquareType.EMPTY &&
   		    board.getSquare(row, col) != SquareType.EMPTY){
		    collision=true;
		    if(board.getSquare(row, col) != SquareType.OUTSIDE){
			ycords.add(new Pair(row,col));
		    }
   		}
   	    }
   	}
    	if (!collision){
	    return false;
	}
    	if (ycords.isEmpty()){
	    return true;
	}
    	else if ( board.canCollapse(ycords)){
	    board.collapse(ycords);
	    return false;
	}else{
	    return true;
	}

	}
    @Override public void changeBoard(Board board){
	board.knockDown();
    }

    @Override public String getDescription(){
	return "Heavy";
    }
}
