package br.com.beganinha.campominado.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Board implements SpotObserver {

	private int quantityMines;
	private int lines;
	private int columns;

	private final List<Spot> spots = new ArrayList<>();
	private final List<Consumer<ResultEvent>> observers = new ArrayList<>();

	public Board(int lines, int columns, int quantityMines) {
		this.lines = lines;
		this.columns = columns;
		this.quantityMines = quantityMines;

		generateSpots();
		neighborhoodRelationship();
		putMinesOnBoard();
	}

	public void registerObsersers(Consumer<ResultEvent> observer) {
		observers.add(observer);
	}

	private void notifyObservers(boolean result) {
		observers.stream().forEach(o -> o.accept(new ResultEvent(result)));
	}

	public void openSpot(int line, int column) {

		spots.parallelStream()
			.filter(spot -> spot.getLine() == line && spot.getColumn() == column)
			.findFirst()
			.ifPresent(spot -> spot.openSpot());

	}

	private void showMines() {
		spots.stream()
			.filter(spot -> spot.isMined())
			.forEach(s -> s.setOpened(true));
	}

	public void switchSpotMark(int line, int column) {
		spots.parallelStream().filter(spot -> spot.getLine() == line && spot.getColumn() == column).findFirst()
				.ifPresent(spot -> spot.switchSpotMark());
	}

	private void generateSpots() {
		for (int line = 0; line < lines; line++) {
			for (int column = 0; column < columns; column++) {
				Spot spot = new Spot(line, column);
				spot.registerObservers(this);
				spots.add(spot);
			}
		}

	}

	private void neighborhoodRelationship() {
		for (Spot s1 : spots) {
			for (Spot s2 : spots) {
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
			// a operação de multiplicação está entre parênteses para ter preferência sobre
			// a operação de casting. Sem os parênteses, a operação de casting seria
			// executada antes da
			// multiplicação
			int random = (int) (Math.random() * spots.size());
			spots.get(random).putMineInTheSpot();

			armedMine = spots.stream().filter(mined).count();
		} while (armedMine < quantityMines);

	}

	public boolean goalAccomplished() {
		return spots.stream().allMatch(spot -> spot.goalAccomplished());
	}

	public void restartGame() {
		spots.stream().forEach(spot -> spot.restart());
		putMinesOnBoard();
	}

	@Override
	public void eventHasOcurred(Spot spot, SpotEvent spotEvent) {
		if (spotEvent == SpotEvent.EXPLODE) {
			showMines();
			notifyObservers(false);
		} else if (goalAccomplished()) {
			notifyObservers(true);
		}
	}

//	public String toString() {
//		//Use StringBuilder sempre que houver uma necessidade grande de concactenação
//		StringBuilder sb = new StringBuilder();
//		
//		sb.append("  ");
//		for (int c = 0; c < columns; c++) {
//			sb.append(" ");
//			sb.append(c);
//			sb.append(" ");
//		}
//		
//		sb.append("\n");
//		
//		int i = 0;
//		for (int line = 0; line < lines; line++) {
//			sb.append(line);
//			sb.append(" ");
//			for (int column = 0; column < columns; column++) {
//				sb.append(" ");
//				sb.append(spots.get(i));
//				sb.append(" ");
//				i++;
//			}
//			sb.append("\n");
//		}
//		
//		return sb.toString();
//	}

}
