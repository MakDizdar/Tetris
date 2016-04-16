package se.liu.ida.emiha868.tetris;


import se.liu.ida.emiha868.tetris.board.Board;
import se.liu.ida.emiha868.tetris.gui.TetrisFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;


/**
 * Main class for running the game, creates a board and a TetrisFrame
 * and then runs a timer that runs board.tick()
 */
/*This is the main method of running the tetris game.*/
public final class BoardTest
{
    private BoardTest() {

    }

    public static void main(String[] args) {
	final Board board = new Board (20,10);
	/*It wants me to assign the instance to a variable, but it is unneccesary since i just want a frame
	which I wont touch once I have initialized it*/
	new TetrisFrame(board);
	Action doOneStep = new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
			board.tick();
		    	}
		    };
	final Timer timer = new Timer(500, doOneStep);
	timer.setCoalesce(true);
	timer.start();
    }
}
