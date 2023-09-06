package io.pig.game;

public abstract class Game {
	
	private static final int STATE_UPDATE_ITERATIONS = 1000;
	
	private final GameStateMap states = new GameStateMap(this);
	
	public Game() {
		addStartStates();
		updateStates();
	}
	
	public abstract void addStartStates();
	public abstract GameState createState(GameStateAttribute<?>[] stateAttributes);
	
	public void addState(GameStateAttribute<?>... stateAttributes) {
		state(stateAttributes);
	}
	
	private void updateStates() {
		for(int i = 0; i < STATE_UPDATE_ITERATIONS; i++) {
			
			states.forEach(GameState::updateWinningProbability);
		}
	}
	
	public GameState state(GameStateAttribute<?>... stateAttributes) {
		return states.getState(stateAttributes);
	}
	
}
