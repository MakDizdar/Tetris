package se.liu.ida.emiha868.tetris.highscore;

import java.util.Comparator;

public class ScoreComparator implements Comparator<HighScore>
{
    public int compare(HighScore o1, HighScore o2) {
       	if(o1.highscore > o2.highscore){
	   return -1;
       	}else if(o1.highscore == o2.highscore){
	   return 0;
       	}else{
	   return 1;
       	}
    }
}
