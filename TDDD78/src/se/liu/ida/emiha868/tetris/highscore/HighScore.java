package se.liu.ida.emiha868.tetris.highscore;

/**
 * HighScore object consisting of a name of a player and his/her score
 */
public class HighScore {
    private int highscore;
    private String playername;
    public HighScore(final String playername,final int highscore) {
	this.highscore = highscore;
	this.playername = playername;
    }

    public int getHighscore() {
	return highscore;
    }

    @Override
    public String toString(){return playername + ":  " + highscore;}




/* The commeneted below is there to remind me of what the Strategy Pattern Design actually is,
below implementation of sorting works but is not sutiable for maintenance since if Highscorelist suddenly wants to
reverse sort, then we can use the new ScoreComparator and tell it to reverse sort, below implementation would only just allow
one kind of sorting, with scorecomparator it is different.
 */
//    @Override
//    public int compareTo(HighScore other) {
//       	if(highscore > other.highscore){
//	   return -1;
//       	}else if(highscore == other.highscore){
//	   return 0;
//       	}else{
//	   return 1;
//       	}
//    }
}


