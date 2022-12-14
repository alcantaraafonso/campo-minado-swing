package br.com.beganinha.campominado.view;

import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import br.com.beganinha.campominado.model.Spot;
import br.com.beganinha.campominado.model.SpotEvent;
import br.com.beganinha.campominado.model.SpotObserver;

@SuppressWarnings("serial")
public class SpotButton extends JButton implements SpotObserver, SpotMouseClickEvent {
	
	private Spot spot;
	private final Color STANDARD_BACK_GROUND = new Color(184, 184, 184);
	private final Color MARK_BACK_GROUND = new Color(8, 179, 247);
	private final Color EXPLODE_BACK_GROUND = new Color(189, 66, 68);
	private final Color GREEN_TEXT = new Color(0, 100, 0);
	
	public SpotButton(Spot spot) {
		this.spot = spot;
		
		setBackground(STANDARD_BACK_GROUND);
		setBorder(BorderFactory.createBevelBorder(0));
		setOpaque(true);
		
		addMouseListener(this);
		spot.registerObservers(this);
	}
	
	@Override
	public void eventHasOcurred(Spot spot, SpotEvent spotEvent) {
		// TODO Auto-generated method stub
		switch (spotEvent) {
		case OPEN:
			applyOpenStyle();
			break;
		case MARK:
			applyMarkStyle();
			break;
		case EXPLODE:
			applyExplodeStyle();
			break;
		default:
			applyDefaultStyle();
		}
		
		SwingUtilities.invokeLater(() -> {
			repaint();
			validate();
		});
	}

	private void applyOpenStyle() {
		
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		if (spot.isMined()) {
			setBackground(EXPLODE_BACK_GROUND);
			return;
		}
		
		setBackground(STANDARD_BACK_GROUND);
		
		switch(spot.minesInTheNeighborhood()) {
		case 1:
			setForeground(GREEN_TEXT);
			break;
		case 2: 
			setForeground(Color.BLUE);
			break;
		case 3: 
			setForeground(Color.YELLOW);
			break;
		case 4:
		case 5:
		case 6: 
			setForeground(Color.RED);
			break;
		default:
			setForeground(Color.PINK);
		}
		
		setText(!spot.safeNeighborhood() ? spot.minesInTheNeighborhood() + "" : "");
		
	}

	private void applyMarkStyle() {
		setBackground(MARK_BACK_GROUND);
		setForeground(Color.BLACK);
		setText("M");
		
	}
	private void applyExplodeStyle() {
		setBackground(EXPLODE_BACK_GROUND);
		setForeground(Color.WHITE);
		setText("X");
		
	}

	private void applyDefaultStyle() {
		setBackground(STANDARD_BACK_GROUND);
		setBorder(BorderFactory.createBevelBorder(0));
		setText("");
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getButton() == 1)
			spot.openSpot();
		else 
			spot.switchSpotMark();
		
	}

}
