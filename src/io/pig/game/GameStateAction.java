package io.pig.game;

import java.util.LinkedList;
import java.util.List;

public abstract class GameStateAction {
	
	private static final double ALWAYS_WINNING_WINNING_PROBABILITY = 1;
	private static final double ALWAYS_LOSING_WINNING_PROBABILITY = 0;
	
	private final GameState startState;
	private final List<Transition> transitions = new LinkedList<>();
	
	public GameStateAction(GameState startState) {
		this.startState = startState;
		
		addTransitions();
	}
	
	public abstract void addTransitions();
	
	public void addTransition(GameState endState, int weight, boolean switchPlayers) {
		Transition transition = new Transition(endState, weight, switchPlayers);
		transitions.add(transition);
	}
	
	public double winningProbability() {
		double winningProbability = ALWAYS_LOSING_WINNING_PROBABILITY;
		int weightTotal = 0;
		
		for(Transition transition : transitions) {
			int weight = transition.getWeight();
			
			winningProbability += transition.winningProbability();
			weightTotal += weight;
		}
		
		return winningProbability / weightTotal;
	}
	
	public GameState getStartState() {
		return startState;
	}
	
	private static class Transition {
		
		private final GameState endState;
		private final int weight;
		private final boolean switchPlayers;
		
		public Transition(GameState endState, int weight, boolean switchPlayers) {
			this.endState = endState;
			this.weight = weight;
			this.switchPlayers = switchPlayers;
		}
		
		public double winningProbability() {
			double winningProbability = endState.getWinningProbability();
			
			if(switchPlayers) winningProbability = ALWAYS_WINNING_WINNING_PROBABILITY - winningProbability;
			return winningProbability;
		}
		
		public GameState getEndState() {
			return endState;
		}
		
		public int getWeight() {
			return weight;
		}
		
		public boolean isSwitchingPlayers() {
			return switchPlayers;
		}
		
	}
	
}
