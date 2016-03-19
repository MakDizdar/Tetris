package se.liu.ida.emiha868.tetris.board;
import javafx.util.Pair;
import se.liu.ida.emiha868.tetris.gui.BoardListener;
import se.liu.ida.emiha868.tetris.ztroubleshoot.BoardToTextConvert;
import se.liu.ida.emiha868.tetris.collision.*;
import se.liu.ida.emiha868.tetris.highscore.*;
import se.liu.ida.emiha868.tetris.Poly;
import se.liu.ida.emiha868.tetris.SquareType;
import se.liu.ida.emiha868.tetris.TetrinoMaker;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
    private final static int FRAMEOFFSET = 2;
    private final static int FRAME = 4;
    private HighScoreList highscorelist = HighScoreList.getInstance();

    public Board(final int height, final int width) {
	this.height = height;
	this.width = width;
	this.falling = TetrinoMaker.getPoly();
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
	//First comparison is there to avoid tetriscomponent to rend a fallen block by using keys during a tick
	if (falling.isFalling()){
	    fallingX += dir.deltaX;
	    fallingY += dir.deltaY;
	    if (collisionHandler.hasCollision(this)) {
		fallingX = preX;
		fallingY = preY;
		if (dir == Move.DOWN) {
		    collisionHandler.changeBoard(this);
		    falling.setFallingStatus(false);
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

    public void spawnPoly() {
	if(score % 500 == 0){
	    collisionHandler=new Fallthrough();
	}
	else if(score % 200 ==0){
	    collisionHandler=new Heavy();
	}
	else{
	    collisionHandler = new DefaultCollisionHandler();
	}

	falling = TetrinoMaker.getPoly();
	fallingX = 0;
	fallingY = 0;

    }

    public void tick() {
	if (gameover) {
	    return;

	} else {
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
	    for (int column = FRAMEOFFSET; column < width + FRAMEOFFSET; column++) {
		squares[row][column] = squares[row - 1][column];
	    }
	}
    }
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
		break;


	}
    }

    public void restart() {
	clearBoard();
	score = 0;
	spawnPoly();
	gameover = false;
	notifyListeners();
    }

    public void endGame() {
	gameover = true;
	String playerName = JOptionPane.showInputDialog("Please enter your name");
	highscorelist.addHighscore(new HighScore(playerName, score));
	highscorelist.printHighscores();
	int reply = JOptionPane.showConfirmDialog(null, "Do you wanna restart?,", "Tetris",
						  JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	if (reply == JOptionPane.YES_OPTION) {restart();}
    }

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
    public void knockDown(){
	List<Integer> ycords = new ArrayList<Integer>();
	fallingX +=1;
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
	if(ycords.isEmpty()){
	    fallingX -=1;
	}else{
	    for(int column : ycords){
		deleteColumn(column);
	    }
	}
	insertFallenBlock();

    }
    /* Methods below except shuffleBoard are used for the Heavy powerup which I've shamefully implemented very complicated, I can fix this
    * sooner by using just two methods and cleaning up the interface of collisionhandler.*/


    public void deleteColumn(int index) {
	for (int row = height + 1; row > FRAMEOFFSET; row--) {
		squares[row][index+FRAMEOFFSET] = squares[row - 1][index+FRAMEOFFSET];
	}
    }

    //    index is the column, h tells the marginal to check for empty rows
    public boolean checkVerticalEmpty(int h, int index){
	for(int row = h+2; row<height+1; row++){
		if(squares[row][index+2] == SquareType.EMPTY){
		    return true;

		}

	}return false;
    }
    //uses a tuple/pair to where key is the row and value is the column, find the closest empty block by iterating from the top
    public Pair<Integer, Integer> getClosestEmpty(int h ,int index) {
	for (int row = h + FRAMEOFFSET; row < height + 1; row++) {
	    if (squares[row][index + FRAMEOFFSET] == SquareType.EMPTY) {
		return new Pair(row - FRAMEOFFSET, index);

	    }
	}
	throw new IllegalArgumentException("Heavy Powerup tried to collapse when it actually should not.");
    }
//    checks if all collided squares' vertical rows has an empty square, if yes collapse, otherwise knock off.
    public boolean canCollapse(List<Pair<Integer,Integer>> columns){
	for(Pair<Integer,Integer> block : columns){
	    if (!checkVerticalEmpty(block.getKey(), block.getValue())){
		return false;
	    }
	}
	return true;
    }
//    h is border to what the bricks above should collapse to
//    index is what column to collapse
    public void collapseVerticalRow(int h,int index){
	for (int row = h+2; row > 2; row--) {
			squares[row][index+2] = squares[row - 1][index+2];
		}

    }
    public void collapse(List<Pair<Integer,Integer>> columns){
	for (Pair<Integer, Integer> block : columns) {
	    Pair<Integer, Integer> closestEmpty = getClosestEmpty(block.getKey(), block.getValue());
	    collapseVerticalRow(closestEmpty.getKey(), closestEmpty.getValue());
	}
    }


    public void shuffleBoard() {
	Random rnd = new Random();
	for (int row = 0; row <= height+FRAMEOFFSET; row++) {
	    for (int column = 0; column < width+FRAMEOFFSET; column++) {
		squares[row+FRAMEOFFSET][column+FRAMEOFFSET] = SquareType.values()[rnd.nextInt(8)];
	    }
	}
	notifyListeners();
    }
    public void oldcomplicatedcollapse(List<Pair<Integer,Integer>> columns){
	if(columns.isEmpty()) {
	    insertFallenBlock();
	}
	else if (canCollapse(columns)) {
	    falling.setFallingStatus(true);
	    fallingX+=1;
	    for (Pair<Integer, Integer> block : columns) {
		Pair<Integer,Integer> closestEmpty= getClosestEmpty(block.getKey(),block.getValue());
		collapseVerticalRow(closestEmpty.getKey(), closestEmpty.getValue());
//		collapseVerticalRow(block.getKey()+2, block.getValue());
	    }
	}else{
	    falling.setFallingStatus(false);
	    for (Pair<Integer, Integer> block : columns) {
		deleteColumn(block.getValue());
	    }
	    fallingX+=1;
	    insertFallenBlock();
	    System.out.println(BoardToTextConvert.convertToText(this));

	}

    }
}

