package view_component;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import constants.Constants;
import database.TicketVo;
import database.TrainDAO;
import view.MainMenu;
import view.NoticeDialog;
import view.TicketViewMenu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;


@SuppressWarnings("serial")
public class TicketInformationPanel extends JPanel implements ActionListener{
	
	private TicketVo ticket;
	private JFrame parent;
	
	private JLabel[] labels = new JLabel[4];
	private JButton detailButton = new JButton("상세보기");
	private JPopupMenu pMenu = new JPopupMenu("설정");
	
	private SimpleDateFormat sf;
	Calendar cal;
	
	// 출발지 -> 목적지  / 날짜  / 시간  / 인원
	public TicketInformationPanel(TicketVo ticket, JFrame parent) {
		setLayout(new BorderLayout());
		this.ticket = ticket;
		this.parent = parent;
		sf = new SimpleDateFormat("yyyyMMddHHmm");
		cal = Calendar.getInstance();
		TitledBorder titled = new TitledBorder(new LineBorder(new Color(47, 48, 61), 2));
		Font font = new Font("맑은 고딕", Font.BOLD, 17);
		
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(1, 0));
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(1, 0, 0, 0));
		add(centerPanel, "Center");
		
		for(int i = 0; i < labels.length; i++) {
			labels[i] = new JLabel();
			labels[i].setFont(font);
			centerPanel.add(labels[i]);
			labels[i].setBorder(titled);
			labels[i].setHorizontalAlignment(SwingConstants.CENTER);
		}
		
		JLabel ticketingTimeLabel = new JLabel("예매 날짜 : " + Constants.getDateKorean(ticket.getTicketing_day()));
		ticketingTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ticketingTimeLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		ticketingTimeLabel.setBackground(Color.white);
		northPanel.add(ticketingTimeLabel);
		northPanel.setBackground(new Color(0xE1, 0xFF, 0xFF));
		add(northPanel, "North");
		
		detailButton.setFont(font);
		detailButton.setBorder(titled);
		detailButton.setBackground(new Color(153, 204, 255));
		detailButton.addActionListener(this);
		centerPanel.add(detailButton);
		centerPanel.setBackground(new Color(0xF0, 0xFF, 0xFF));
		
		JMenuItem jmiCancel = new JMenuItem("예매 취소");
		jmiCancel.setFont(font);
		pMenu.add(jmiCancel);
		JPanel thisP = this;
		
		jmiCancel.addActionListener(this);
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(e.getModifiers() == MouseEvent.BUTTON3_MASK) {
					pMenu.show(thisP, e.getX(), e.getY());
				}
			}
		});

		setBorder(titled);
		setLocation(ticket.getDeppland_place(), ticket.getArrpland_place());
		setDate(ticket.getDeppland_time());
		setTime();
		setPersonnel(Integer.parseInt(ticket.getPersonnel()));
	}
	
	public void setLocation(String dep, String arr) {
		labels[0].setText("<html><center>출발&nbsp;&nbsp;&emsp;도착</center>" + "<center>" + dep + " → " + arr + "</center></html>");
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
		if(e.getSource() instanceof JButton)
			new TicketViewMenu(ticket).setVisible(true);
		else if(e.getSource() instanceof JMenuItem)
		{
			TrainDAO dao = TrainDAO.getInstance();
			dao.deleteReservation(ticket.getTicket_id());
			((MainMenu)parent).<TicketVo>updateTicketList(ticket);
			new NoticeDialog("티켓이 취소되었습니다.", parent);
		}
	}
}















