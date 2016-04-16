package se.liu.ida.emiha868.tetris;


/**
 * A poly represents for example a tetromino that is currently falling, and it is either falling
 * or it has fallen.
 */
public class Poly{
    private boolean falling = true;

    private SquareType[][] polys;
    public Poly(final SquareType[][] polys) {
	this.polys = polys;
    }
    public SquareType[][] getPolys() {
	return polys;
    }
    public SquareType getSquare(int x,int y){
	return polys[x][y];
    }

    public void setFalling(final boolean fallingStatus) {
  	this.falling = fallingStatus;
      }
/*It is complaining on my way of naming the method isFalling, it should
 be naming fallingstatus, but for the sake of readability in the class Board i decided to have
  isFalling()*/
    public boolean isFalling() {
  	return falling;
      }

    public Poly rotate(boolean dir){
	if (dir){
	    return rotateRight();
	}else{
	    return rotateLeft();
	}

    }
    private Poly rotateRight(){
  	    	int size = polys.length;
  	        Poly newPoly = new Poly(new SquareType[size][size]);

  	        for (int r = 0; r < size; r++) {
  	            for (int c = 0; c < size; c++){
  	                newPoly.polys[c][size-1-r] = this.polys[r][c];
  	            }
  	        }

  	        return newPoly;
  	    }

    private Poly rotateLeft(){
  	    	int size = polys.length;
  	        Poly newPoly = new Poly(new SquareType[size][size]);

  	        for (int r = 0; r < size; r++) {
  	            for (int c = 0; c < size; c++){
  	                newPoly.polys[size-1-r][c] = this.polys[c][r];
  	            }
  	        }

  	        return newPoly;
  	    }
}
