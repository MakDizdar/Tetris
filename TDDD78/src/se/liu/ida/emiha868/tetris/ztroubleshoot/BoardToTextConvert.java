package se.liu.ida.emiha868.tetris.ztroubleshoot;

import se.liu.ida.emiha868.tetris.Poly;
import se.liu.ida.emiha868.tetris.SquareType;
import se.liu.ida.emiha868.tetris.board.Board;




public final class BoardToTextConvert
{
//    This class is unnecessary, I am just using it for troubleshooting.
    private BoardToTextConvert() {
    }

    public static String convertToText(Board board){
	StringBuilder boardtext = new StringBuilder();
	Poly poly=board.getFalling();
	int c =0;
	for (int row = 0; row < board.getHeight()+4; row++) {
	    for (int column = 0; column < board.getWidth()+4; column++) {
		if (onFallingPoly(row, column, board, poly)) {
		    int xpos = row - board.getFallingX();
		    int ypos = column - board.getFallingY();
		    boardtext = insertTypeToString(boardtext, poly.getSquare(xpos, ypos));
		} else {
		    boardtext = insertTypeToString(boardtext, board.getSquare(row-2, column-2));
		}
	    }
	    boardtext.append(" "+ c);
	    c+=1;
	    boardtext.append("\n");
	}
	return boardtext.toString();
    }
    private static StringBuilder insertTypeToString(StringBuilder boardtext, SquareType square){
	switch(square){
	    case EMPTY:
		boardtext.append("_ ");
		break;
	    case OUTSIDE:
		boardtext.append("W ");
		break;
	    case I:
		boardtext.append("I ");
		break;
	    case O:
		boardtext.append("O ");
		break;
	    case T:
		boardtext.append("T ");
		break;
	    case S:
		boardtext.append("S ");
		break;
	    case Z:
		boardtext.append("Z ");
		break;
	    case J:
		boardtext.append("J ");
		break;
	    case L:
		boardtext.append("L ");
		break;
	    default:
		boardtext.append("NULL ");
	}
	return boardtext;
    }
    public static boolean onFallingPoly(int row, int column, Board board, Poly poly){
    	int xpos = row- board.getFallingX();
	int ypos = column - board.getFallingY();
	return ((row - board.getFallingX()>= 0) &&
		(row- board.getFallingX() < poly.getPolys().length) &&
		(column - board.getFallingY()>=0) &&
		column - board.getFallingY() < poly.getPolys().length &&
		poly.getSquare(xpos, ypos) != SquareType.EMPTY);
    }

    public static void main(String[] args) {
  	Board board = new Board(8,8);
  	System.out.println(BoardToTextConvert.convertToText(board));
	board.deleteRow(9);
	System.out.println(BoardToTextConvert.convertToText(board));
	board.deleteRow(9);
	System.out.println(BoardToTextConvert.convertToText(board));
	board.deleteRow(7);
	System.out.println(BoardToTextConvert.convertToText(board));
	board.deleteRow(7);
	System.out.println(BoardToTextConvert.convertToText(board));
      }
}
