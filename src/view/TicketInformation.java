package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@SuppressWarnings("serial")
public class TicketInformation extends JPanel {
	
	private JLabel[] labels = new JLabel[4];
	
	public static void main(String[] args) {
		TicketInformation t = new TicketInformation();
		
		JFrame frame = new JFrame();
		frame.setBounds(0, 0, 1000, 700);
		frame.setLayout(new FlowLayout());
		frame.add(t);
		frame.setVisible(true);
		
		
	}
	
	public TicketInformation() {
		setLayout(new GridLayout(1, 0, 0, 0));
		
		TitledBorder titled = new TitledBorder(new LineBorder(Color.black, 2));
		Font font = new Font("맑은 고딕", Font.BOLD, 20);
		
		for(JLabel l : labels) {
			l = new JLabel();
			l.setFont(font);
			add(l);
			l.setBorder(titled);
		}
	}
	
	public void setLocation(String dep, String arr) {
		labels[0].setText(dep + " → " + arr);
	}
	
	public void setDate(String date) {
		if(date == null) return;
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmm");
		
		try {
			StringBuilder sb = new StringBuilder(); 
			cal.setTime(sf.parse(date));
			
			sb.append(cal.get(Calendar.YEAR)).append("년 ").append(cal.get(Calendar.MONTH) + 1).append("월 ").append(cal.get(Calendar.DAY_OF_MONTH)).append("일");
			
			labels[1].setText(sb.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setTime(String time) {
		if(time == null) return;
		
		Calendar cal = Calendar.getInstance();
		
		try {
			StringBuilder sb = new StringBuilder();
			
			sb.append(cal.get(Calendar.HOUR_OF_DAY)).append("시 ").append(cal.get(Calendar.MINUTE)).append("분");
			
			labels[2].setText(sb.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setPersonnel(int count) {
		if(count == 0) return;
		
		labels[3].setText(count + "명");
	}
}















