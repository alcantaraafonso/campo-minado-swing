package br.com.beganinha.campominado.view;

import java.awt.event.MouseListener;

public interface SpotMouseClickEvent extends  MouseListener {
	
	@Override
	default void mouseClicked(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	default void mouseEntered(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	default void mouseExited(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	default void mouseReleased(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mousePressed(java.awt.event.MouseEvent e);

}
