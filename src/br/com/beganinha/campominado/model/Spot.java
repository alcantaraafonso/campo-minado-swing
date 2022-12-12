package br.com.beganinha.campominado.model;

import java.util.ArrayList;
import java.util.List;

import br.com.beganinha.campominado.exception.ExplosionException;

public class Spot {
	
	private boolean mined = false;
	private boolean opened = false;
	private boolean marked = false;
	private List<Spot> neighbors = new ArrayList<>();
	
	private final int line;
	private final int column;
	
	Spot(int line, int column) {
		this.line = line;
		this.column = column;
	}

	
	boolean addNeighbor(Spot neighbor) {
		boolean isDifferentLine = line != neighbor.line;
		boolean isDifferentColumn = column != neighbor.column;
		boolean diagonal = isDifferentLine && isDifferentColumn;
		
		int lineGap = Math.abs(line - neighbor.line);
		int columnGap = Math.abs(column - neighbor.column);
		int gap = columnGap + lineGap;
		
		if (gap == 1 && !diagonal) {
			neighbors.add(neighbor);
			return true;
		} else if (gap == 2 && diagonal) {
			neighbors.add(neighbor);
			return true;			
		} else { 
			return false;
		}
	}
	
	void switchSpotMark() {
		if (!opened) {
			marked = !marked;
		}
	}
	
	void putMineInTheSpot() {
		if (!mined) {
			mined = true;
		}
	}
	
	boolean openSpot() {
		//só abre se não estiver aberto e não estiver marcado
		if (!opened && !marked) {
			//marca como true
			opened = true;
			
			//Lança exception caso esteja minado
			if (mined) {
				throw new ExplosionException();
			}
			
			/*
			 * Aqui é feito um processo de recussividade, onde
			 * o método chama a sí mesmo a aporte da lista de vizinhos.
			 * O método openSpot será executado enquanto a condição do primeiro IF for verdadeira
			 * usa-se um Consumer pois não precisa de um retorno e sim de uma execução para marcar o campo como
			 * aberto
			 */
			if (safeNeighborhood()) {
				neighbors.forEach(n -> n.openSpot());
			}
			
			return true;
		}
		
		return false;
	}
	
	boolean safeNeighborhood() {
		return neighbors.stream()
				.noneMatch(n -> n.mined);
	}
	
	public boolean isMarked() {
		return marked;
	}

	public boolean isOpended() {
		return opened;
	}
	
	void setOpened(boolean opened) {
		this.opened = opened;
	}
	
	public boolean isClosed() {
		return !isOpended();
	}
	
	public boolean isMined() {
		return mined;
	}

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}
	
	boolean goalAccomplished() {
		boolean unraveled = !mined && opened;
		boolean protectedSpot = mined && marked;
		
		return unraveled || protectedSpot;
	}
	
	long minesInTheNeighborhood() {
		return neighbors.stream()
				.filter(n -> n.mined)
				.count();
	}
	
	void restart() {
		opened = false;
		mined = false;
		marked = false;
	}
	
	public String toString() {
		if (marked) { 
			return "x";
		} else if (opened && mined) {
			return "*";
		} else if (opened && minesInTheNeighborhood() > 0) { 
			return Long.toString(minesInTheNeighborhood());
		} else if (opened) {
			return " ";
		} else {
			return "?";
		}
	}
		
	
}
