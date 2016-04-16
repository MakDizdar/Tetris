package se.liu.ida.emiha868.tetris.highscore;

import java.util.Comparator;

/**
 * ScoreComparator is used for sorting the list of highscores in a chronological order
 */
public class ScoreComparator implements Comparator<HighScore>
{
    public int compare(HighScore o1, HighScore o2) {
       	if(o1.getHighscore() > o2.getHighscore()){
	   return -1;
       	}else if(o1.getHighscore() == o2.getHighscore()){
	   return 0;
       	}else{
	   return 1;
       	}
    }
}
