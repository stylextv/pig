package io.pig.game;

import java.util.ArrayList;
import java.util.List;

public abstract class GameState {
	
	private static final double ALWAYS_WINNING_WINNING_PROBABILITY = 1;
	private static final double ALWAYS_LOSING_WINNING_PROBABILITY = 0;
	
	private final Game game;
	private final GameStateAttribute<?>[] attributes;
	
	private final List<GameStateAction> actions = new ArrayList<>();
	
	private double winningProbability;
	
	public GameState(Game game, GameStateAttribute<?>[] attributes) {
		this.game = game;
		this.attributes = attributes;
	}
	
	public abstract void addActions();
	public abstract boolean winning();
	
	public void addAction(GameStateAction action) {
		actions.add(action);
	}
	
	protected void updateWinningProbability() {
		if(winning()) {
			
			winningProbability = ALWAYS_WINNING_WINNING_PROBABILITY;
			return;
		}
		
		double winningProbability = ALWAYS_LOSING_WINNING_PROBABILITY;
		
		for(GameStateAction action : actions) {
			double wp = action.winningProbability();
			winningProbability = Math.max(winningProbability, wp);
		}
		
		this.winningProbability = winningProbability;
	}
	
	public boolean attributesEqual(GameStateAttribute<?>[] attributes) {
		int length = attributes.length;
		if(length != this.attributes.length) return false;
		
		for(int i = 0; i < length; i++) {
			
			GameStateAttribute<?> attribute = attributes[i];
			GameStateAttribute<?> a = this.attributes[i];
			
			if(!a.equals(attribute)) return false;
		}
		
		return true;
	}
	
	public <T extends GameStateAttribute<?>> T getAttribute(Class<T> attributeClass) {
		for(GameStateAttribute<?> attribute : attributes) {
			
			if(attributeClass.isInstance(attribute)) return (T) attribute;
		}
		
		return null;
	}
	
	public Game getGame() {
		return game;
	}
	
	public GameStateAttribute<?>[] getAttributes() {
		return attributes;
	}
	
	public double getWinningProbability() {
		return winningProbability;
	}
	
}
