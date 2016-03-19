package se.liu.ida.emiha868.tetris.gui;

import se.liu.ida.emiha868.tetris.Poly;
import se.liu.ida.emiha868.tetris.SquareType;
import se.liu.ida.emiha868.tetris.board.Board;
import se.liu.ida.emiha868.tetris.board.Move;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.AbstractMap;
import java.util.EnumMap;


public class TetrisComponent extends JComponent implements BoardListener
{
    public static final int SQUARE_LENGTH = 50;
    private final static int MARGIN = 2;
    public Board board;
    private AbstractMap<SquareType,Color> colormap;
    public TetrisComponent(final Board board) {
	colormap = new EnumMap<SquareType,Color>(SquareType.class);
	colormap.put(SquareType.I, Color.CYAN);
	colormap.put(SquareType.J, Color.BLUE);
	colormap.put(SquareType.L, Color.ORANGE);
	colormap.put(SquareType.O, Color.YELLOW);
	colormap.put(SquareType.S, Color.GREEN);
	colormap.put(SquareType.T, Color.MAGENTA);
	colormap.put(SquareType.Z, Color.RED);
	colormap.put(SquareType.EMPTY, new Color(220,220,220));
	this.board = board;
	this.setKeyBindings();

    }
    @Override
    public Dimension getPreferredSize(){
	return new Dimension(board.getWidth() * SQUARE_LENGTH, board.getHeight()* SQUARE_LENGTH);
    }

    @Override
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;
	g2d.setColor(Color.WHITE);
	Poly poly = board.getFalling();
    	for(int x=0; x < board.getHeight();x++){
	    for(int y=0; y < board.getWidth(); y++){
		if (onFallingPoly(x,y,board,poly) ){
		    int xpos = x -board.getFallingX();
		    int ypos = y- board.getFallingY();
		    g2d.setColor(colormap.get(poly.getSquare(xpos, ypos)));
		}else{
		    g2d.setColor(colormap.get(board.getSquare(x, y)));
		}
		g2d.fillRect(y*SQUARE_LENGTH,x*SQUARE_LENGTH,SQUARE_LENGTH-MARGIN,SQUARE_LENGTH-MARGIN);
	    }
	}
    }
    @Override
    public void boardChanged(){
	this.repaint();
    }
    public static boolean onFallingPoly(int row, int column, Board board, Poly poly){
	int xpos = row - board.getFallingX();
    	int ypos = column - board.getFallingY();
    	return ((row - board.getFallingX()>= 0) &&
    		(row- board.getFallingX() < poly.getPolys().length) &&
    		(column - board.getFallingY()>=0) &&
    		column - board.getFallingY() < poly.getPolys().length &&
    		poly.getSquare(xpos, ypos) != SquareType.EMPTY);
        }

    private Action mvRight = new AbstractAction() {
	@Override
    		    public void actionPerformed(ActionEvent e) {
	    board.movePoly(Move.RIGHT);
    		    	}
    		    };

    private Action mvLeft = new AbstractAction()
    {
	@Override public void actionPerformed(ActionEvent e) {board.movePoly(Move.LEFT);}
    };

    private Action descend = new AbstractAction(){
	@Override
	public void actionPerformed(ActionEvent e){board.movePoly(Move.DOWN);}
    };

    private Action rotateRight = new AbstractAction(){
    @Override
    public void actionPerformed(ActionEvent e){board.rotate(true);}
    };

    private Action rotateLeft = new AbstractAction(){
	@Override
	public void actionPerformed(ActionEvent e){board.rotate(false);}
    };


    public void setKeyBindings(){
	this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"),"mvLeft");
	this.getActionMap().put("mvLeft", mvLeft);

	this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"),"mvRight");
	this.getActionMap().put("mvRight",mvRight);

	this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "rotateRight");
	this.getActionMap().put("rotateRight",rotateRight);

	this.getInputMap().put(KeyStroke.getKeyStroke("L"), "rotateLeft");
	this.getActionMap().put("rotateLeft",rotateLeft);

	this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "descend");
	this.getActionMap().put("descend",descend);

    }
}