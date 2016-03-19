package se.liu.ida.emiha868.tetris;


public class Poly{
    private boolean fallingStatus = true;

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

    public void setFallingStatus(final boolean fallingStatus) {
  	this.fallingStatus = fallingStatus;
      }

      public boolean isFalling() {
  	return fallingStatus;
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
