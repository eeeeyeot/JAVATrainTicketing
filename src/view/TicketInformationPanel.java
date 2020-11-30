package view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import database.TicketVo;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;


@SuppressWarnings("serial")
public class TicketInformationPanel extends JPanel implements ActionListener{
	
	private TicketVo ticket;
	
	private JLabel[] labels = new JLabel[4];
	private JButton detailButton = new JButton("상세보기");
	private SimpleDateFormat sf;
	Calendar cal;
	
	// 출발지 -> 목적지  / 날짜  / 시간  / 인원
	public TicketInformationPanel(TicketVo ticket) {
		setLayout(new GridLayout(1, 0, 0, 0));
		this.ticket = ticket;
		
		sf = new SimpleDateFormat("yyyyMMddHHmm");
		cal = Calendar.getInstance();
		
		TitledBorder titled = new TitledBorder(new LineBorder(Color.black, 2));
		Font font = new Font("맑은 고딕", Font.BOLD, 18);
		
		for(int i = 0; i < labels.length; i++) {
			labels[i] = new JLabel();
			labels[i].setFont(font);
			add(labels[i]);
			labels[i].setBorder(titled);
			labels[i].setHorizontalAlignment(SwingConstants.CENTER);
		}
		
		detailButton.setBorder(titled);
		detailButton.setFont(font);
		detailButton.addActionListener(this);
		add(detailButton);
		
		setLocation(ticket.getDeppland_place(), ticket.getArrpland_place());
		setDate(ticket.getDeppland_time());
		setTime();
		setPersonnel(Integer.parseInt(ticket.getPersonnel()));
	}
	
	public void setLocation(String dep, String arr) {
		labels[0].setText(dep + " → " + arr);
	}
	
	public void setDate(String date) {
		if(date == null) return;
		
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
	
	public void setTime() {
		try {
			StringBuilder sb = new StringBuilder();
			
			sb.append(String.format("%02d", cal.get(Calendar.HOUR_OF_DAY))).append("시 ").append(String.format("%02d", cal.get(Calendar.MINUTE))).append("분");
			
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

	public void actionPerformed(ActionEvent e) {
		new TicketViewMenu(ticket).setVisible(true);
	}
}















