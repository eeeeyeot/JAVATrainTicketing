package view_component;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.im.InputContext;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import openAPI.TrainAPI;

@SuppressWarnings("serial")
public class AutoSuggestPanel extends JPanel{
	private JTextField tf;
	private JComboBox<String> combo;
	private Vector<String> v;
	
	public AutoSuggestPanel(){
		v = TrainAPI.getInstance().getStationNames();
		setLayout(new GridBagLayout());
		Font font = new Font("맑은 고딕", Font.BOLD, 12); 
		combo = new JComboBox<String>(v);
		combo.setEditable(true);
		combo.setFont(font);
		combo.setBackground(Color.WHITE);
		
		tf = (JTextField) combo.getEditor().getEditorComponent();
		tf.setFont(font);

		tf.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				InputContext ctx = getInputContext();
				Character.Subset[] subset = {Character.UnicodeBlock.HANGUL_SYLLABLES};
				ctx.setCharacterSubsets(subset);
			}
		});
		
		tf.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				String text = tf.getText();
				if(text.length() != 0) {
					combo.setModel(getComboBoxModel(v, text));
					combo.showPopup();
					tf.setText(text);
					if(text.length() > 0)
						tf.moveCaretPosition(text.length() - 1);
				}
			}
		});
		
		add(combo);
		tf.setText("");
	}
	
	private DefaultComboBoxModel<String> getComboBoxModel(Vector<String> v, String text){
		DefaultComboBoxModel<String> m = new DefaultComboBoxModel<String>();
		for(String s : v) {
			if(s.startsWith(text)) m.addElement(s);
		}
		
		return m;
	}
	
	public String getComboBoxText() {
		System.out.println("getComboBoxText : " + tf.getText());
		return tf.getText();
	}
	
	public void setDefault(String station) {
		tf.setText(station);
	}
}
