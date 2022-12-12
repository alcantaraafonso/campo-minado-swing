package br.com.beganinha.campominado.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import br.com.beganinha.campominado.exception.ExplosionException;

public class Board {
	
	private int quantityMines;
	private int lines;
	private int columns;
	
	private final List<Spot> spots = new ArrayList<>();

	public Board(int lines, int columns, int quantityMines) {
		this.lines = lines;
		this.columns = columns;
		this.quantityMines = quantityMines;

		generateSpots();
		neighborhoodRelationship();
		putMinesOnBoard();
	}
	
	public void openSpot(int line, int column) {
		try { 
			spots.parallelStream()
				.filter(spot -> spot.getLine() == line && spot.getColumn() == column)
				.findFirst()
				.ifPresent(spot -> spot.openSpot());
		} catch (ExplosionException e) {
			spots.forEach(s -> s.setOpened(true));
			throw e;
		}
	}
	
	public void switchSpotMark(int line, int column) {
		spots.parallelStream()
			.filter(spot -> spot.getLine() == line && spot.getColumn() == column)
			.findFirst()
			.ifPresent(spot -> spot.switchSpotMark());
	}

	private void generateSpots() {
		for (int line = 0; line < lines; line++) {
			for (int column = 0; column < columns; column++) {
				spots.add(new Spot(line, column));
			}
		}
		
	}
	

	private void neighborhoodRelationship() {
		for(Spot s1: spots) {
			for(Spot s2: spots) {
				s1.addNeighbor(s2);
			}
		}
		
	}
	
	/*
	 * To put mines randomly in the board
	 */
	private void putMinesOnBoard() {
		long armedMine = 0;
		
		Predicate<Spot> mined = m -> m.isMined();
		
		do {
			//a operação de multiplicação está entre parênteses para ter preferência sobre
			//a operação de casting. Sem os parênteses, a operação de casting seria executada antes da 
			//multiplicação
			int random = (int)(Math.random() * spots.size());
			spots.get(random).putMineInTheSpot();
			
			armedMine = spots.stream()
					.filter(mined)
					.count();
		} while(armedMine < quantityMines);
		
	}
	
	public boolean goalAccomplished() {
		return spots.stream().allMatch(spot -> spot.goalAccomplished());
	}
	
	public void restartGame() {
		spots.stream()
			.forEach(spot -> spot.restart());
		putMinesOnBoard();
	}

	public String toString() {
		//Use StringBuilder sempre que houver uma necessidade grande de concactenação
		StringBuilder sb = new StringBuilder();
		
		sb.append("  ");
		for (int c = 0; c < columns; c++) {
			sb.append(" ");
			sb.append(c);
			sb.append(" ");
		}
		
		sb.append("\n");
		
		int i = 0;
		for (int line = 0; line < lines; line++) {
			sb.append(line);
			sb.append(" ");
			for (int column = 0; column < columns; column++) {
				sb.append(" ");
				sb.append(spots.get(i));
				sb.append(" ");
				i++;
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
}
