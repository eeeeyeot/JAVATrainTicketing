package view;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@SuppressWarnings("serial")
public class MyJTextField extends JTextField implements DocumentListener{
	
	MyJTextField(String title){
		super(title);
	}
	
	public void insertUpdate(DocumentEvent e) {
		
	}

	public void removeUpdate(DocumentEvent e) {
	}

	public void changedUpdate(DocumentEvent e) {
	}
}