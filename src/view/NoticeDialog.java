package view;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import util.ScreenUtil;
import javax.swing.JPanel;
import java.awt.Color;

@SuppressWarnings("serial")
public class NoticeDialog extends JDialog {

	public NoticeDialog(String info, Component parent) {
		super((Frame) parent, "알림", true);
		getContentPane().setBackground(Color.WHITE);
		setForeground(Color.WHITE);
		getContentPane().setForeground(Color.WHITE);
		setSize(250, 150);
		setLocation(ScreenUtil.getCurrentCenter((Window)parent, this));
		GridLayout gridLayout = new GridLayout();
		gridLayout.setColumns(1);
		gridLayout.setRows(0);
		getContentPane().setLayout(gridLayout);
		
		Font font = new Font("맑은 고딕", Font.BOLD, 12);

		JLabel msg = new JLabel(info, JLabel.CENTER);
		msg.setFont(font);
		msg.validate();
		getContentPane().add(msg);
		
		JButton ok = new JButton("확인");
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setForeground(Color.WHITE);
		panel.setLayout(new FlowLayout());
		getContentPane().add(panel);
		panel.add(ok);
		
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		ok.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					dispose();
				}
			}
		});
			
		
		setVisible(true);
	}
}
