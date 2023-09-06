package io.pig.game.games;

import io.pig.game.Game;
import io.pig.game.GameState;
import io.pig.game.GameStateAction;
import io.pig.game.GameStateAttribute;

public class PigGame extends Game {
	
	private static final int INITIAL_SCORE = 0;
	private static final int WINNING_SCORE = 100;
	private static final int INITIAL_TURN_SCORE = 0;
	private static final int MINIMAL_ROLL_SCORE = 1;
	private static final int MAXIMAL_ROLL_SCORE = 6;
	private static final int PIG_OUT_ROLL_SCORE = 1;
	
	private static final int DEFAULT_ACTION_WEIGHT = 1;
	
	@Override
	public void addStartStates() {
		addState(new ScoreAttribute(INITIAL_SCORE), new OpponentScoreAttribute(INITIAL_SCORE), new TurnScoreAttribute(INITIAL_TURN_SCORE));
	}
	
	@Override
	public GameState createState(GameStateAttribute<?>[] stateAttributes) {
		return new State(this, stateAttributes);
	}
	
	public static class State extends GameState {
		
		public State(Game game, GameStateAttribute<?>[] attributes) {
			super(game, attributes);
		}
		
		@Override
		public void addActions() {
			addAction(new PassAction(this));
			addAction(new RollAction(this));
		}
		
		@Override
		public boolean winning() {
			int score = getScore();
			int turnScore = getTurnScore();
			
			return score + turnScore >= WINNING_SCORE;
		}
		
		public int getScore() {
			ScoreAttribute attribute = getAttribute(ScoreAttribute.class);
			return attribute.getValue();
		}
		
		public int getOpponentScore() {
			OpponentScoreAttribute attribute = getAttribute(OpponentScoreAttribute.class);
			return attribute.getValue();
		}
		
		public int getTurnScore() {
			TurnScoreAttribute attribute = getAttribute(TurnScoreAttribute.class);
			return attribute.getValue();
		}
		
	}
	
	public static class ScoreAttribute extends GameStateAttribute<Integer> {
		
		public ScoreAttribute(Integer score) {
			super(score);
		}
		
	}
	
	public static class OpponentScoreAttribute extends GameStateAttribute<Integer> {
		
		public OpponentScoreAttribute(Integer score) {
			super(score);
		}
		
	}
	
	public static class TurnScoreAttribute extends GameStateAttribute<Integer> {
		
		public TurnScoreAttribute(Integer score) {
			super(score);
		}
		
	}
	
	public static class PassAction extends GameStateAction {
		
		public PassAction(GameState startState) {
			super(startState);
		}
		
		@Override
		public void addTransitions() {
			State startState = (State) getStartState();
			Game game = startState.getGame();
			int score = startState.getScore();
			int opponentScore = startState.getOpponentScore();
			int turnScore = startState.getTurnScore();
			
			GameState endState = game.state(new ScoreAttribute(opponentScore), new OpponentScoreAttribute(score + turnScore), new TurnScoreAttribute(INITIAL_TURN_SCORE));
			addTransition(endState, DEFAULT_ACTION_WEIGHT, true);
		}
		
	}
	
	public static class RollAction extends GameStateAction {
		
		public RollAction(GameState startState) {
			super(startState);
		}
		
		@Override
		public void addTransitions() {
			State startState = (State) getStartState();
			Game game = startState.getGame();
			int score = startState.getScore();
			int opponentScore = startState.getOpponentScore();
			int turnScore = startState.getTurnScore();
			
			for(int rollScore = MINIMAL_ROLL_SCORE; rollScore <= MAXIMAL_ROLL_SCORE; rollScore++) {
				if(rollScore == PIG_OUT_ROLL_SCORE) {
					
					GameState endState = game.state(new ScoreAttribute(opponentScore), new OpponentScoreAttribute(score), new TurnScoreAttribute(INITIAL_TURN_SCORE));
					addTransition(endState, DEFAULT_ACTION_WEIGHT, true);
					
				} else {
					
					GameState endState = game.state(new ScoreAttribute(score), new OpponentScoreAttribute(opponentScore), new TurnScoreAttribute(turnScore + rollScore));
					addTransition(endState, DEFAULT_ACTION_WEIGHT, false);
				}
			}
		}
		
	}
	
}
