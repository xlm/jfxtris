package game;

import data_types.DropType;

/**
 * ScoreSystem
 * @author Josh Chia
 * 
 * Tetris guideline compatible scoring system.
 * Scores for combos, soft drop, hard drop and all
 * the various line clears, include those in excess of
 * 4 lines (Tetris).   
 *
 */

public class ScoreSystem {
	private final double B2B_DIFFICULT_MULTIPLIER = 3.0/2;
	private final int LEVELUP_GOAL = 10;
	private boolean difficult_clear;
	public int level, last_level_gain, score, combo, goal;
	
	private Game game;
	
	/**
	 * Configure a scoring system for a game
	 * @param lvl, starting level
	 * @param g, game being scored
	 */
	public ScoreSystem(int lvl, Game g) {
		level = (lvl > 0) ? lvl: 1;
		last_level_gain = 0;
		goal = LEVELUP_GOAL;
		difficult_clear = false;
		score = 0;
		combo = 0;
		
		game = g;
	}
	
	/**
	 * Update the score system and feedback given lines clears 
	 * @param lines, number cleared
	 */
	public void addLinesCleared(int lines) {
		game.feedback_text.setText(lineClearFeedback(lines));
		scoreLinesCleared(lines);
		updateGoal(lines);
	}
	
	/**
	 * Update the score system given soft or hard drops
	 * @param cells, soft/hard dropped
	 * @param d, drop type
	 */
	public void addCellsDropped(int cells, DropType d) {
		score += cells*d.getScoreValue();
	}
	
	/**
	 * Reset the combo
	 */
	public void resetCombo() {
		combo = 0;
	}
	
	/**
	 * Update the score given the lines cleared,
	 * setting the appropriate level, difficulty and combo multipliers.
	 * @param lines, number cleared
	 */
	private void scoreLinesCleared(int lines) {
		int base_score;
		double multiplier = (difficult_clear && isDifficultLineClear(lines)) ? B2B_DIFFICULT_MULTIPLIER: 1;
		difficult_clear = (isDifficultLineClear(lines)) ? true: false;
		
		switch (lines) {
		case 0:
			base_score = 0;
			break;
		case 1:
			base_score = 100;
			break;
		case 2:
			base_score = 300; 
			break;
		case 3:
			base_score = 500;
			break;
		case 4:
			base_score = 800;
			break;
		default:
			base_score = 1000;
			break;
		}
		
		score += base_score*level*multiplier + 50*combo*level;
		combo++;
		
		// Provide feedback to the player
		if (multiplier > 1) {
			game.feedback_text.setText("Back-2-Back Difficult!");
		}
		if (combo > 1) {
			game.feedback_text.setText("Combo " + combo + "!");
		}
	}
	
	/**
	 * Level up the player if enough lines scores,
	 * increasing gravity and score multiplier. 
	 * @param lines, number cleared
	 */
	private void updateGoal(int lines) {
		int new_target = goal - lines;
		
		if (new_target <= 0) {
			last_level_gain += 1 + Math.abs(new_target)/10;
			level += last_level_gain;
			goal = last_level_gain*10 + new_target;
		}
		else {
			goal = new_target;
		}
		
		if (last_level_gain > 0) {
			game.updateGravity(last_level_gain);
			last_level_gain = 0;
		}
	}
	
	/**
	 * Determination of what is a difficult line clear
	 * @param lines, number cleared
	 * @return. true if difficult
	 */
	private boolean isDifficultLineClear(int lines) {
		return lines>=4;
	}
	
	/**
	 * Feedback to the player for line clears
	 * @param lines, number cleared
	 * @return feedback string
	 */
	private String lineClearFeedback(int lines) {
		switch (lines) {
		case 0:
			return "";
		case 1:
			return "Single!";
		case 2: 
			return "Double!";
		case 3:
			return "Triple!";
		case 4:
			return "Tetris!";
		default:
			return "SUPER!";
		}
	}
}
