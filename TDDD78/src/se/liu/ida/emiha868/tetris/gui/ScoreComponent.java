package se.liu.ida.emiha868.tetris.gui;

import se.liu.ida.emiha868.tetris.board.Board;

import javax.swing.*;
import java.awt.*;


/**
 * Scorecomponent is responsible of painting the text for Powerup and Score
 * and updating it by acting as a boardlistener.
 */
public class ScoreComponent extends JComponent implements BoardListener
{
    private Board board;
    private final static int SQUARE_LENGTH = TetrisComponent.SQUARE_LENGTH;
    public ScoreComponent(Board board) {
	this.board = board;
    }
    private final static int CENTER_TEXT = 15;
    private final static int FONT_SIZE =12;
    private final static int SPAN_LEFT =90;
    private final static float OPACITY = 0.7f;
    @Override
    public Dimension getPreferredSize(){
	return new Dimension(SQUARE_LENGTH*board.getWidth(),20);
    }

    @Override
    //Magic numbers, they are trivial when reading the code
    public void paintComponent(Graphics g) {
	Graphics2D g2 = (Graphics2D) g;
	String scoretext = String.format("%-5s %5d", "Score:",board.getScore());
	String powerup = "Powerup: "+ board.getCollisionHandler().getDescription();
	/*Creates a black bar at the top of tetriscomponent*/
	g2.setColor(Color.BLACK);
	//trivial magic number,
	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, OPACITY));
	g2.fillRect(0,0,SQUARE_LENGTH*board.getWidth(),SQUARE_LENGTH*board.getHeight());

	g2.setRenderingHint(
                   RenderingHints.KEY_ANTIALIASING,
                   RenderingHints.VALUE_ANTIALIAS_ON);
	g2.setFont(new Font("serif", Font.BOLD, FONT_SIZE));
	//Trivial magic number, ignore
	g2.setColor(new Color(248,248,255));

	int rightAligned =board.getWidth()*SQUARE_LENGTH-SPAN_LEFT;
	g2.drawString(scoretext, rightAligned , CENTER_TEXT);
	g2.drawString(powerup, 5, CENTER_TEXT);
    }
    @Override
    public void boardChanged(){
	this.repaint();
    }
}
