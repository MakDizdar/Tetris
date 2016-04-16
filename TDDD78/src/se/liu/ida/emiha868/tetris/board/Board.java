package se.liu.ida.emiha868.tetris.board;
import javafx.util.Pair;
import se.liu.ida.emiha868.tetris.gui.BoardListener;
import se.liu.ida.emiha868.tetris.collision.*;
import se.liu.ida.emiha868.tetris.highscore.*;
import se.liu.ida.emiha868.tetris.Poly;
import se.liu.ida.emiha868.tetris.SquareType;
import se.liu.ida.emiha868.tetris.TetrinoMaker;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * This is the class that has the methods that controls the main mechanics of the game
 */
public class Board
{
    private SquareType[][] squares;
    private int height;
    private int width;
    private Poly falling;
    private int fallingX, fallingY;
    private List<BoardListener> boardlisteners;
    private CollisionHandler collisionHandler;
    private boolean gameover = false;
    private int score = 0;
    private HighScoreList highscorelist = HighScoreList.getInstance();
    /*DirOfPolyMovement is there so that HeavyPowerup
    can use a method to decide if poly was descending when collision occured,
    implemented after demonstration
     */
    private Move dirOfPolyMovement =null;
    private final static int FRAMEOFFSET = 2;
    private final static int FRAME = 4;
    private final static int HEAVYGENERATOR =200;
    private final static int FALLTHROUGHGENERATOR=500;
    private TetrinoMaker tetrinoMaker = TetrinoMaker.getInstance();

    public Board(final int height, final int width) {
	this.height = height;
	this.width = width;
	this.falling = tetrinoMaker.getPoly();
	this.fallingX = 0;
	this.fallingY = 0;
	squares = new SquareType[height + FRAME][width + FRAME];
	for (SquareType[] row : squares) {
	    Arrays.fill(row, SquareType.OUTSIDE);
	}
	// fill squares within frame with EMPTY types
	clearBoard();
	this.boardlisteners = new ArrayList<BoardListener>();
	this.collisionHandler = new DefaultCollisionHandler();
    }

    public void clearBoard() {
	for (int row = FRAMEOFFSET; row < squares.length - FRAMEOFFSET; row++) {
	    Arrays.fill(squares[row], FRAMEOFFSET, width + FRAMEOFFSET, SquareType.EMPTY);
	}
    }
    public boolean polyWasDescending(){
    	return dirOfPolyMovement == Move.DOWN;
	}
    public int getHeight() {
	return height;
    }

    public int getWidth() {
	return width;
    }

    public Poly getFalling() {
	return falling;
    }

    public int getFallingX() {
	return fallingX;
    }

    public int getFallingY() {
	return fallingY;
    }

    public int getScore() {
	return score;
    }

    public CollisionHandler getCollisionHandler() {return collisionHandler;}

    public SquareType getSquare(int x, int y) {
	return squares[x + FRAMEOFFSET][y + FRAMEOFFSET];
    }

    public void addBoardListener(BoardListener bl) {
	boardlisteners.add(bl);
    }

    public void notifyListeners() {
	for (BoardListener listener : boardlisteners) {
	    listener.boardChanged();
	}
    }

    /*Moves the falling poly and if there is a collision, revert it. Set the falling polystatus to be fallen
    If collision occured when fallingPoly was descending call the
    changeBoard() method of the collisonHandler.
     */
    public void movePoly(Move dir) {
	int preX = fallingX;
	int preY = fallingY;
	dirOfPolyMovement= dir;
	//First comparison is there to avoid tetriscomponent to rend a fallen block by using keys during a tick
	if (falling.isFalling()){
	    fallingX += dir.deltaX;
	    fallingY += dir.deltaY;
	    if (collisionHandler.hasCollision(this)) {
		if(!polyWasDescending()) {
		    fallingX = preX;
		    fallingY = preY;
		}
		if (polyWasDescending()) {
		    fallingX -= 1;
		    insertFallenBlock();
		    falling.setFalling(false);
		}
	    }
	}
	notifyListeners();
    }
    public void rotate(boolean direction) {
	Poly preRotation = falling;
	if(falling.isFalling()){
	    falling = falling.rotate(direction);
	    if (collisionHandler.hasCollision(this)) {
		falling = preRotation;
	    }
	}
	notifyListeners();
    }

    /*Trigger method of powerups based on score,
    * spawns also a new poly.*/
    public void spawnPoly() {
	/*I know that they are magic numbers, but since they are constans that
	are used once, i did not mind naming a CONSTANT.
	*/
	if(score % FALLTHROUGHGENERATOR == 0){
	    collisionHandler=new Fallthrough();
	}
	else if(score % HEAVYGENERATOR ==0){
	    collisionHandler=new Heavy();
	}
	else{
	    collisionHandler = new DefaultCollisionHandler();
	}

	falling = tetrinoMaker.getPoly();
	fallingX = 0;
	fallingY = 0;

    }

    public void tick() {
	if(!gameover){
	    if (falling.isFalling()){
		movePoly(Move.DOWN);
	    }else{
		completeRow();
		spawnPoly();
		if (collisionHandler.hasCollision(this)){
		    endGame();
		}
	    }
	    notifyListeners();
	}
    }

    public void deleteRow(int index) {
	for (int row = index; row > FRAMEOFFSET; row--) {
	    /*IDEA wants me to use Arraycopy to copy the row above, instead of doing it
	    brick by brick*/
	    for (int column = FRAMEOFFSET; column < width + FRAMEOFFSET; column++) {
		squares[row][column] = squares[row - 1][column];
	    }
	}
    }

    /*Checks for full rows and removes them, and counts
    * how many rows has been deleted. Then call updatescore that
    * updates the score based on how many rows were eliminated during a tick*/
    public void completeRow() {
	int rowsEliminated = 0;
	for (int row = FRAMEOFFSET; row < height + FRAMEOFFSET; row++) {
	    boolean foundRow = true;
	    for (SquareType c : squares[row]) {
		if (c == SquareType.EMPTY) {
		    foundRow = false;
		}
	    }
	    if (foundRow) {
		deleteRow(row);
		rowsEliminated += 1;
	    }
	}
	updateScore(rowsEliminated);
	notifyListeners();
    }


    public void updateScore(int rowsEliminated) {
	switch (rowsEliminated) {
	    case 0:
		break;
	    case 1:
		score += 100;
		break;
	    case 2:
		score += 300;
		break;
	    case 3:
		score += 500;
		break;
	    case 4:
		score += 800;



	}
    }

    /* Fills the board within frame with empty and resets score*/
    public void restart() {
	clearBoard();
	score = 0;
	spawnPoly();
	gameover = false;
	notifyListeners();
    }

    /*Assert boolean gameover to true, ask user for input and then inserts the highscore
    * to a list of the singleton Highscore. The highscorelist is printed in the list. Later the user is asked if he wants to restart,
    * on restart, the whole board within the frame is filled with empty types by calling restart method. */
    public void endGame() {
	gameover = true;
	String playerName = JOptionPane.showInputDialog("Please enter your name");
	highscorelist.addHighscore(new HighScore(playerName, score));
	highscorelist.printHighscores();
	int reply = JOptionPane.showConfirmDialog(null, "Do you wanna restart?,", "Tetris",
						  JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	if (reply == JOptionPane.YES_OPTION) {restart();}
    }

    /*Inserts a fallen poly to the board by iterating through the polyarray and comparing its squaretypes
    * with the board, if falling's squaretype is non-EMPTY and the squares's squaretype does not equal OUTSIDE, then
    * override the squares's squaretype with the falling's squaretype. */
    public void insertFallenBlock() {
	for (int row = fallingX; row < fallingX + falling.getPolys().length; row++) {
	    for (int col = fallingY; col < fallingY + falling.getPolys()[0].length; col++) {
		if (falling.getSquare(row - fallingX, col - fallingY) != SquareType.EMPTY) {
		    if (squares[row + FRAMEOFFSET][col + FRAMEOFFSET] != SquareType.OUTSIDE) {
			squares[row + FRAMEOFFSET][col + FRAMEOFFSET] = falling.getSquare(row - fallingX, col - fallingY);
		    }
		}
	    }
	}
    }

    /*This method smashes the blocks which falling has fallen upon.
    * This method is called only when a falling poly has fallen during Heavy Powerup, in other words when a collision has occured when the
    * falling was descending this method will be called.
    * */
    public void knockDown(){
	Collection<Integer> ycords = new ArrayList<Integer>();

	for (int row = fallingX; row < fallingX + falling.getPolys().length ;row++) {
	    for (int col = fallingY; col < fallingY + falling.getPolys()[0].length; col++) {
		if (falling.getSquare(row - fallingX, col - fallingY) != SquareType.EMPTY &&
		    this.getSquare(row, col) != SquareType.EMPTY) {
		    if(this.getSquare(row, col) != SquareType.OUTSIDE){
			ycords.add(col);
		    }
		}
	    }
	}
	if(!ycords.isEmpty()){
	    for(int column : ycords){
		deleteColumn(column);
	    }
	    fallingX +=1;

	}
//	insertFallenBlock();

    }
    /* Methods below except shuffleBoard are used for the Heavy powerup which I've shamefully implemented
    *very complicated, I can fix this sooner by using just two
    *methods and cleaning up the interface of collisionhandler.*/

/*Removes a square/column/brick on the index by replacing a brick with the above one, this is only used
 * by knockdown() */
    public void deleteColumn(int index) {
	for (int row = height + 1; row > FRAMEOFFSET; row--) {
		squares[row][index+FRAMEOFFSET] = squares[row - 1][index+FRAMEOFFSET];
	}
    }

    //    index is the column, h tells the marginal to check for empty rows
    public boolean checkVerticalEmpty(int h, int index){
	for(int row = h+FRAMEOFFSET; row<height+1; row++){
		if(squares[row][index+FRAMEOFFSET] == SquareType.EMPTY){
		    return true;

		}

	}return false;
    }

    /*Uses a tuple/pair to where key is the row and value is the column, find the closest empty block by iterating from the top
    in other words, iterate from the collided brick that is given by h and index return the cordinates of the first
    found EMPTY squaretype*/
    public Pair<Integer, Integer> getClosestEmpty(int h ,int index) {
	for (int row = h + FRAMEOFFSET; row < height + 1; row++) {
	    if (squares[row][index + FRAMEOFFSET] == SquareType.EMPTY) {
		return new Pair<Integer,Integer>(row - FRAMEOFFSET, index);

	    }
	}
	throw new IllegalArgumentException("Heavy Powerup tried to collapse when it actually should not.");
    }

//    checks if all collided squares's vertical rows has an empty square, if yes return true, otherwise knock off.
    public boolean canCollapse(Iterable<Pair<Integer,Integer>> columns){
	for(Pair<Integer,Integer> block : columns){
	    if (!checkVerticalEmpty(block.getKey(), block.getValue())){
		return false;
	    }
	}
	return true;
    }

    /*
    h is the column from which a vertical row should collapse from
    The h is there to avoid column above falling to collapse aswell
    index is what verticalrow that is supposed to collapse.
    The collapse is done by ovveriding the current block with the block above until
    it has reached the block which h indicates.
    */
    public void collapseVerticalRow(int h,int index){
	for (int row = h+FRAMEOFFSET; row > FRAMEOFFSET; row--) {
			squares[row][index+FRAMEOFFSET] = squares[row - 1][index+FRAMEOFFSET];
		}

    }

/*Given the input is a list consisting of tuples that represent the cordinates
* of a brick that has collided with a falling. By iterating over these bricks, it checks
* if the verticalrows which the bricks are inside are empty, if all are empty then the
* falling can start collapsing.*/
    public void collapse(Iterable<Pair<Integer,Integer>> columns){
	for (Pair<Integer, Integer> block : columns) {
	    Pair<Integer, Integer> closestEmpty = getClosestEmpty(block.getKey(), block.getValue());
	    collapseVerticalRow(closestEmpty.getKey(), closestEmpty.getValue());
	}
    }


    /**
     * Every brick is replaced by another brick randomly
     * Not used anymore but saved for flashy effect :-)
     */
    public void shuffleBoard() {
	Random rnd = new Random();
	for (int row = 0; row <= height+FRAMEOFFSET; row++) {
	    for (int column = 0; column < width+FRAMEOFFSET; column++) {
		squares[row+FRAMEOFFSET][column+FRAMEOFFSET] = SquareType.values()[rnd.nextInt(8)];
	    }
	}
	notifyListeners();
    }
}

