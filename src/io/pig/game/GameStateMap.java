package io.pig.game;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GameStateMap {
	
	private final Game game;
	private final List<GameState> states = new ArrayList<>();
	
	public GameStateMap(Game game) {
		this.game = game;
	}
	
	public void forEach(Consumer<GameState> stateConsumer) {
		List<GameState> copiedStates = new ArrayList<>();
		copiedStates.addAll(states);
		copiedStates.forEach(stateConsumer);
	}
	
	public GameState getState(GameStateAttribute<?>[] stateAttributes) {
		for(GameState state : states) {
			
			if(state.attributesEqual(stateAttributes)) return state;
		}
		
		GameState state = game.createState(stateAttributes);
		states.add(state);
		
		if(!state.winning()) state.addActions();
		return state;
	}
	
}
