package io.pig.game;

public abstract class GameStateAttribute<T> {
	
	private final T value;
	
	public GameStateAttribute(T value) {
		this.value = value;
	}
	
	public boolean equals(GameStateAttribute<?> attribute) {
		Class<?> clazz = this.getClass();
		if(!clazz.isInstance(attribute)) return false;
		
		Object v = attribute.getValue();
		return v.equals(value);
	}
	
	public T getValue() {
		return value;
	}
	
}
