package se.liu.ida.emiha868.tetris.collision;

import javafx.util.Pair;
//import se.liu.ida.emiha868.tetris.ztroubleshoot.BoardToTextConvert;
import se.liu.ida.emiha868.tetris.board.Board;
import se.liu.ida.emiha868.tetris.Poly;
import se.liu.ida.emiha868.tetris.SquareType;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Collisionhandling during Heavy powerup. It collapses blocks if they have holes inside them,
 * and when they reach the bottom they will get smashed.
 */
public class Heavy implements CollisionHandler
{

/*ycords is a list consisting of the cordinates of a certain block in the board that has been collided
 with the falling poly. This method iterates and finds all certain non-empty and non-outside blocks that has collided with the
 falling poly and every cordinate is added as a Tuple in the ycords list. This is used for collapsing verticalrows in the board.

 The boolean collision flag is there so that the method does not return true when poly has not collided at all. Without it
 it would have entered the isEmpty()-if-statement and returned true even though a collision has not happened. I have tried to
 avoid flags, but I did not manage to do this in time.

 When collision has occured and it involves non-outisde and non-empty blocks, check all cordinates
 of collision and see if the vertical rows of these certain positions can collapse, collapse if all vertical rows has an empty block and
 return false. Otherwise return true.
 */
@Override public boolean hasCollision(Board board){
    	Poly falling = board.getFalling();
   	int fallingX = board.getFallingX();
   	int fallingY = board.getFallingY();
    	boolean collision =false;
    	Collection<Pair<Integer,Integer>> ycords = new ArrayList<Pair<Integer,Integer>>();
   	for (int row = fallingX; row < fallingX + falling.getPolys().length; row++) {
   	    for (int col = fallingY; col < fallingY + falling.getPolys()[0].length; col++) {
   		if (falling.getSquare(row - fallingX, col - fallingY) != SquareType.EMPTY &&
   		    board.getSquare(row, col) != SquareType.EMPTY){
		    collision=true;
		    if(board.getSquare(row, col) != SquareType.OUTSIDE){
			ycords.add(new Pair<Integer,Integer>(row,col));
		    }
   		}
   	    }
   	}
    	// Checks if a collision at all has happened
    	if (!collision){
	    return false;
	}
    	//if collision has occured when only OUTSIDE blocks are involved, return true.
    	else if (ycords.isEmpty()){
	    return true;
	}
	//Collapse if possible, if not; a collision has occured and bottom bricks gets smashed
    	else if ( board.canCollapse(ycords)){
	    board.collapse(ycords);
	    return false;
	}else{
	    if(board.polyWasDescending()){
		board.knockDown();
	    }
	    return true;
	}

	}

    @Override public String getDescription(){
	return "Heavy";
    }



    //    /*This function is called whenever a falling poly has fallen, and it knocks down
    //    * the bricks which the falling has fallen upon*/
    //    public void changeBoard(Board board){
    //	board.knockDown();
    //    }

}
