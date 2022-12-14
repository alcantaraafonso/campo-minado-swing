package br.com.beganinha.campominado.view;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import br.com.beganinha.campominado.model.Board;

/**
 * O JPanel é um container igual ao bootstrap
 * @author alcan
 *
 */
@SuppressWarnings("serial")
public class PanelBoard extends JPanel {
	
	public PanelBoard(Board board) {
		
		setLayout(new GridLayout(board.getLines(), board.getColumns()));
		
		board.forEachSpot(spot -> add(new SpotButton(spot)));

		board.registerObsersers(e -> {
			SwingUtilities.invokeLater(() -> {
				if (e.isWon()) 
					JOptionPane.showMessageDialog(this, "Você ganhou! :D");
				else 
					JOptionPane.showMessageDialog(this, "Você perdeu!");	
				
				board.restartGame();
			});
			
		});
		
	}
	
}
