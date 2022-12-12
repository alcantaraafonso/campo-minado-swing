package br.com.beganinha.campominado;

import br.com.beganinha.campominado.model.Board;
import br.com.beganinha.campominado.view.BoardConsole;

public class Application {
	
	public static void main(String[] args) {
		Board board = new Board(6, 6, 3);
		
		new BoardConsole(board);
		
	}

}
