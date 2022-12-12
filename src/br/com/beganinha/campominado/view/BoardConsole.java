package br.com.beganinha.campominado.view;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.beganinha.campominado.exception.ExitException;
import br.com.beganinha.campominado.exception.ExplosionException;
import br.com.beganinha.campominado.model.Board;

public class BoardConsole {
	
	private Board board;
	private Scanner entrada = new Scanner(System.in);

	public BoardConsole(Board board) {
		this.board = board;
		
		runGame();
		
	}

	private void runGame() {
		try {
			
			boolean goOn = true;
			
			while (goOn) {
				runningGame();
				System.out.println("Outra partida? (S/n) ");
				
				String answer = entrada.nextLine();
				
				if ("n".equalsIgnoreCase(answer)) {
					goOn = false;
				} else {
					board.restartGame();
				}
				
			}
		} catch (ExitException e) {
			System.out.println("Tchau! ");
		} finally {
			entrada.close();
		}
		
	}

	private void runningGame() {
		try {
			
			while (!board.goalAccomplished()) {
				System.out.println(board.toString());
				String commandTyped = captureCommandTyedByUser("Digite (x, y): ");
				
				Iterator<Integer> xy = Arrays.stream(commandTyped.split(","))
					.map(e -> Integer.parseInt(e.trim())).iterator();
				
				commandTyped = captureCommandTyedByUser("1 - Abrir ou 2 - (Des)Marcar ");

				if("1".equals(commandTyped)) 
					board.openSpot(xy.next(), xy.next());
				else if ("2".equals(commandTyped)) 
					board.switchSpotMark(xy.next(), xy.next());
			
			}
			System.out.println(board.toString());
			System.out.println("Você ganhou!!!");
		} catch (ExplosionException e) {
			System.out.println(board.toString());
			System.out.println("Você perdeu !");
		}
		
	}
	
	private String captureCommandTyedByUser(String text) {
		System.out.print(text);
		String textTyped = entrada.nextLine();
		
		if("sair".equalsIgnoreCase(textTyped)) {
			throw new ExitException();
		}
		
		return textTyped;
	}
	

}
