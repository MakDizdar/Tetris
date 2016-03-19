package se.liu.ida.emiha868.tetris.gui;

import se.liu.ida.emiha868.tetris.board.Board;

import javax.swing.*;
import java.awt.*;

public class ScoreComponent extends JComponent implements BoardListener
{
    private Board board;
    private final static int SQUARE_LENGTH = TetrisComponent.SQUARE_LENGTH;
    public ScoreComponent(Board board) {
	this.board = board;
    }

    @Override
    public Dimension getPreferredSize(){
	return new Dimension(SQUARE_LENGTH*board.getWidth(),20);
    }

    @Override
    public void paintComponent(Graphics g) {
	Graphics2D g2 = (Graphics2D) g;
	String scoretext = String.format("%-5s %5d", "Score:",board.getScore());
	String powerup = "Powerup: "+ board.getCollisionHandler().getDescription();
	g2.setColor(Color.BLACK);
	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
	g2.fillRect(0,0,SQUARE_LENGTH*board.getWidth(),SQUARE_LENGTH*board.getHeight());
	g2.setRenderingHint(
                   RenderingHints.KEY_ANTIALIASING,
                   RenderingHints.VALUE_ANTIALIAS_ON);
	g2.setFont(new Font("serif", Font.BOLD, 12));
	g2.setColor(new Color(248,248,255));
	g2.drawString(scoretext,board.getWidth()*SQUARE_LENGTH-90 , 15);
	g2.drawString(powerup, 5, 15);
    }
    @Override
    public void boardChanged(){
	this.repaint();
    }
}
