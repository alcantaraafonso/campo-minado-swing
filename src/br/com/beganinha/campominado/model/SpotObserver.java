package br.com.beganinha.campominado.model;

@FunctionalInterface
public interface SpotObserver {
	public void eventHasOcurred(Spot spot, SpotEvent spotEvent);	
}
