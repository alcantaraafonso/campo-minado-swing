package br.com.beganinha.campominado.view;

import javax.swing.JFrame;

import br.com.beganinha.campominado.model.Board;

@SuppressWarnings("serial")
public class MainScreen extends JFrame{
	
	public MainScreen() {
		
		Board board = new Board(16, 30, 50);
		add(new PanelBoard(board));
		
		setTitle("Campo Minado");
		setSize(690, 438);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		new MainScreen();
	}

}
