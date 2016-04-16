package se.liu.ida.emiha868.tetris.highscore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A Singleton that stores the game's highscores in a list
 */
public final class HighScoreList
{
    private static final HighScoreList INSTANCE = new HighScoreList();
    private List<HighScore> highscores;
    private HighScoreList() {
	highscores = new ArrayList<HighScore>();
    }
    public static HighScoreList getInstance(){
	return INSTANCE;
    }

    public void addHighscore(HighScore score){
	highscores.add(score);
	Collections.sort(highscores, new ScoreComparator());
    }
    public void printHighscores(){
	int c = 10;
	for (HighScore score : highscores) {
	    if (c > 0) {
		System.out.println(score);
	    }
	    c -= 1;
	}
	System.out.println("----------\n");
    }
}
