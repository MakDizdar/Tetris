package se.liu.ida.emiha868.tetris;

import java.util.Arrays;
import java.util.Random;


/**
 * TetrinoMaker is a singleton factory class that produces the certain various
 * polyminos. getPoly() method returns a Poly object that represents
 * any of the possible tetrominos that can be produced. Check the comments
 * inside the class for more details
 */
public final class TetrinoMaker
{
    private static final TetrinoMaker INSTANCE = new TetrinoMaker();
    private  TetrinoMaker() {}
    public static TetrinoMaker getInstance(){
 	return INSTANCE;
     }

    public int getNumberOfTypes(){
	return SquareType.values().length;
    }

    private SquareType[][] genEmptyBlock(SquareType[][] polyArray){
	for (SquareType[] row: polyArray){
	    Arrays.fill(row, SquareType.EMPTY);
	}
	return polyArray;
    }


/*A Poly requires an array as input for their constructor. The array
* represents a tetromino.*/
    public Poly getPoly(){
	Random rnd = new Random();
	int n = rnd.nextInt(getNumberOfTypes()-2);
	switch(SquareType.values()[n]){
	    case I:
		SquareType[][] i = genEmptyBlock(new SquareType[4][4]);
		Arrays.fill(i[1],SquareType.I);
		return new Poly(i);
	    case J:
		SquareType[][] j = genEmptyBlock(new SquareType[3][3]);
		Arrays.fill(j[1],SquareType.J);
		j[0][0] = SquareType.J;
		return new Poly(j);
	    case L:
		SquareType[][] l = genEmptyBlock(new SquareType[3][3]);
		Arrays.fill(l[1],SquareType.L);
		l[0][2]= SquareType.L;
		return new Poly(l);
	    case O:
		SquareType[][] o = genEmptyBlock(new SquareType[2][2]);
		for (SquareType[] row: o){
		    Arrays.fill(row, SquareType.O);
		}
		return new Poly(o);
	    case S:
		SquareType[][] s = genEmptyBlock(new SquareType[3][3]);
		Arrays.fill(s[0],1,3,SquareType.S);
		Arrays.fill(s[1],0,2,SquareType.S);
		return new Poly(s);
	    case T:
		SquareType[][] t = genEmptyBlock(new SquareType[3][3]);
		t[0][1] = SquareType.T;
		Arrays.fill(t[1],SquareType.T);
		return new Poly(t);
	    case Z:
		SquareType[][] z = genEmptyBlock(new SquareType[3][3]);
		Arrays.fill(z[0],0,2,SquareType.Z);
		Arrays.fill(z[1],1,3,SquareType.Z);
		return new Poly(z);
	    case EMPTY:
	    case OUTSIDE:
	    default:
		throw new IllegalArgumentException("Invalid index: "+ n);
	    }
        }
}

