package main;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import openAPI.TrainAPI;

@SuppressWarnings("unused")
public class TrainAPITest {
	public static void main(String[] args) {
		
		JFrame f = new JFrame();
		f.setSize(300, 300);
		f.setLayout(new GridLayout(1, 0));
		
		JButton b1 = new JButton("b2");
		
		f.add(b1);
		
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("111111");
			}
		});
		
		removeListener(b1);
		
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("22222");
			}
		});
		
		
		
		f.setVisible(true);
	}
	
	static void removeListener(JButton b1) {
		for(ActionListener al : b1.getActionListeners())
			b1.removeActionListener(al);
	}
}

