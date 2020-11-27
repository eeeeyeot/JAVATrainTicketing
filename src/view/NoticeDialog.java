package view;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import util.ScreenUtil;

@SuppressWarnings("serial")
public class NoticeDialog extends JDialog {

	NoticeDialog(String info, Component parent) {
		JDialog jd = new JDialog(this, "알림", true);
		jd.setSize(200, 120);
		jd.setLocation(ScreenUtil.getCurrentCenter((Window)parent, jd));
		jd.getContentPane().setLayout(new FlowLayout());

		JLabel msg = new JLabel(info, JLabel.CENTER);
		JButton ok = new JButton("확인");

		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jd.setVisible(false);
				jd.dispose();
			}
		});

		jd.getContentPane().add(msg);
		jd.getContentPane().add(ok);

		jd.setVisible(true);
	}
}
