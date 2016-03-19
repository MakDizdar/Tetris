package se.liu.ida.emiha868.tetris;


import se.liu.ida.emiha868.tetris.board.Board;
import se.liu.ida.emiha868.tetris.gui.TetrisFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

public final class BoardTest
{
    //private Board board;

    private BoardTest() {

    }

    public static void main(String[] args) {
	final Board board = new Board (15,8);
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
