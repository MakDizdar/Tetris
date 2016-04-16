package se.liu.ida.emiha868.tetris.gui;

import se.liu.ida.emiha868.tetris.board.Board;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The main frame of tetris that consist of component that repaint themselves
 * and a menu bar.
 */
public class TetrisFrame extends JFrame
{
    private final Board board;
    public TetrisFrame(Board board) {
	super("Tetris");
	this.board = board;
	this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	this.createMenu();
	final TetrisComponent tetrisarea =new TetrisComponent(board);
	final ScoreComponent score = new ScoreComponent(board);
	board.addBoardListener(tetrisarea);
	board.addBoardListener(score);
	this.setLayout(new BorderLayout());
	this.add(score,BorderLayout.NORTH);
	this.add(tetrisarea, BorderLayout.CENTER);
	this.pack();
	this.setVisible(true);

    }

    public void createMenu() {
           final JMenu fileMenu = new JMenu("File");
           final JMenuItem quit = new JMenuItem("Quit");
	   final JMenuItem restart = new JMenuItem("Restart");
           fileMenu.add(quit);
	   fileMenu.add(restart);
           quit.addActionListener(new ActionListener()
	   {
	       public void actionPerformed(ActionEvent e) {
		   System.exit(0);
	       }
	   });
	   restart.addActionListener(new ActionListener(){
	       public void actionPerformed(ActionEvent e){
		   JOptionPane.showMessageDialog(null, "RESTARTING", "alert", JOptionPane.WARNING_MESSAGE);
		   board.restart();
	       }
	   });
           final JMenuBar menubar = new JMenuBar();
           menubar.add(fileMenu);
           this.setJMenuBar(menubar);

    }
}