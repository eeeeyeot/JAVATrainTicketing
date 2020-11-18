package view;

import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

import openAPI.TrainAPI;

public class Test2 extends JFrame
{
	JTextField tf;
	JComboBox<String> combo = new JComboBox<String>();
	
	Test2(){
		tf = (JTextField)combo.getEditor().getEditorComponent();
		combo.setEditable(true);
		
		add(combo);
		setSize(300, 300);
	}
	
    public static void main(String[] args)
    {
        new Test2().setVisible(true);
    }
}

