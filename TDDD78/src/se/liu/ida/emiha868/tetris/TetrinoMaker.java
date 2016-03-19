package se.liu.ida.emiha868.tetris;

import java.util.Arrays;
import java.util.Random;

public final class TetrinoMaker
{
    private TetrinoMaker() {
    }

    public static int getNumberOfTypes(){
	return SquareType.values().length;
    }
    private static SquareType[][] genEmptyBlock(SquareType[][] polyArray){
	for (SquareType[] row: polyArray){
	    Arrays.fill(row, SquareType.EMPTY);
	}
	return polyArray;
    }



    public static Poly getPoly(){
	Random rnd = new Random();
	int n = rnd.nextInt(getNumberOfTypes()-2);
	switch(SquareType.values()[n]){
	    case I:
		SquareType[][] I = genEmptyBlock(new SquareType[4][4]);
		Arrays.fill(I[1],SquareType.I);
		return new Poly(I);
	    case J:
		SquareType[][] J = genEmptyBlock(new SquareType[3][3]);
		Arrays.fill(J[1],SquareType.J);
		J[0][0] = SquareType.J;
		return new Poly(J);
	    case L:
		SquareType[][] L = genEmptyBlock(new SquareType[3][3]);
		Arrays.fill(L[1],SquareType.L);
		L[0][2]= SquareType.L;
		return new Poly(L);
	    case O:
		SquareType[][] O = genEmptyBlock(new SquareType[2][2]);
		for (SquareType[] row: O){
		    Arrays.fill(row, SquareType.O);
		}
		return new Poly(O);
	    case S:
		SquareType[][] S = genEmptyBlock(new SquareType[3][3]);
		Arrays.fill(S[0],1,3,SquareType.S);
		Arrays.fill(S[1],0,2,SquareType.S);
		return new Poly(S);
	    case T:
		SquareType[][] T = genEmptyBlock(new SquareType[3][3]);
		T[0][1] = SquareType.T;
		Arrays.fill(T[1],SquareType.T);
		return new Poly(T);
	    case Z:
		SquareType[][] Z = genEmptyBlock(new SquareType[3][3]);
		Arrays.fill(Z[0],0,2,SquareType.Z);
		Arrays.fill(Z[1],1,3,SquareType.Z);
		return new Poly(Z);
	    default:
		throw new IllegalArgumentException("Invalid index: "+ n);
	    }
        }
}

