package view_component;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import constants.Constants;
import database.SeasonTicketVo;
import openAPI.TrainAPI;
import openAPI.TrainVo;
import view.TrainInquiryMenu;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

@SuppressWarnings("serial")
public class SeasonInformationPanel extends JPanel implements ActionListener{
	static final int SEASON_PERSONNEL = 1;
	
	private SeasonTicketVo seasonVo;
	private JFrame parent;
	
	private JLabel depArrPlaceLabel;
	private JLabel effDateLabel;
	private JLabel expDateLabel;
	private JLabel termLabel;
	private JButton reservTrainBtn;
	private JPanel gridPanel;
	private JLabel ticketingDateLabel;
	
	private JPanel reservationDatePanel;
	private JPanel descriptionPanel;
	private JLabel lblNewLabel;
	private JPanel centerPanel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	
	public SeasonInformationPanel(SeasonTicketVo seasonVo, JFrame parent) {
		this.seasonVo = seasonVo;
		this.parent = parent;
		TitledBorder titled = new TitledBorder(new LineBorder(new Color(47, 48, 61), 2));
		Font font = new Font("맑은 고딕", Font.PLAIN, 18);
		setLayout(new BorderLayout(0, 0));
		
		reservationDatePanel = new JPanel();
		reservationDatePanel.setLayout(new GridLayout(1, 0));
		reservationDatePanel.setBackground(new Color(0xE1, 0xFF, 0xFF));
		add(reservationDatePanel, BorderLayout.NORTH);
		reservationDatePanel.setBorder(titled);
		
		ticketingDateLabel = new JLabel("예매 날짜 : " + Constants.getDateKorean(Constants.getTodayTimeToString()));
		ticketingDateLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		ticketingDateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		reservationDatePanel.add(ticketingDateLabel);
		
		centerPanel = new JPanel();
		add(centerPanel, BorderLayout.CENTER);
		GridBagLayout gbl_centerPanel = new GridBagLayout();
		gbl_centerPanel.columnWidths = new int[]{782, 0};
		gbl_centerPanel.rowHeights = new int[] {40, 62, 0};
		gbl_centerPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_centerPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		centerPanel.setLayout(gbl_centerPanel);
		
		descriptionPanel = new JPanel();
		GridBagConstraints gbc_descriptionPanel = new GridBagConstraints();
		gbc_descriptionPanel.fill = GridBagConstraints.BOTH;
		gbc_descriptionPanel.insets = new Insets(0, 0, 0, 0);
		gbc_descriptionPanel.gridx = 0;
		gbc_descriptionPanel.gridy = 0;
		centerPanel.add(descriptionPanel, gbc_descriptionPanel);
		descriptionPanel.setLayout(new GridLayout(1, 0, 0, 0));
		descriptionPanel.setBackground(Color.LIGHT_GRAY);
		descriptionPanel.setBorder(titled);
		
		lblNewLabel = new JLabel("출발 → 도착");
		lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		descriptionPanel.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("유효 날짜");
		lblNewLabel_1.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		descriptionPanel.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("만료 날짜");
		lblNewLabel_2.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		descriptionPanel.add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel("기간");
		lblNewLabel_3.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		descriptionPanel.add(lblNewLabel_3);
		
		lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		descriptionPanel.add(lblNewLabel_4);
		
		gridPanel = new JPanel();
		GridBagConstraints gbc_gridPanel = new GridBagConstraints();
		gbc_gridPanel.fill = GridBagConstraints.BOTH;
		gbc_gridPanel.gridx = 0;
		gbc_gridPanel.gridy = 1;
		centerPanel.add(gridPanel, gbc_gridPanel);
		gridPanel.setLayout(new GridLayout(1, 0, 0, 0));
		gridPanel.setBackground(new Color(0xF0, 0xFF, 0xFF));
		
		depArrPlaceLabel = new JLabel("출발지 → 목적지");
		depArrPlaceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		depArrPlaceLabel.setFont(font);
		gridPanel.add(depArrPlaceLabel);
		depArrPlaceLabel.setBorder(titled);
		
		effDateLabel = new JLabel("유효날짜");
		effDateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		effDateLabel.setFont(font);
		gridPanel.add(effDateLabel);
		effDateLabel.setBorder(titled);
		
		expDateLabel = new JLabel("만료날짜");
		expDateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		expDateLabel.setFont(font);
		gridPanel.add(expDateLabel);
		expDateLabel.setBorder(titled);
		
		termLabel = new JLabel("기간");
		termLabel.setHorizontalAlignment(SwingConstants.CENTER);
		termLabel.setFont(font);
		gridPanel.add(termLabel);
		termLabel.setBorder(titled);
		
		reservTrainBtn = new JButton("예매하기");
		reservTrainBtn.setFont(font);
		gridPanel.add(reservTrainBtn);
		reservTrainBtn.setBorder(titled);
		reservTrainBtn.setBackground(new Color(153, 204, 255));
		reservTrainBtn.addActionListener(this);
		
		setBorder(titled);
		
		setDepArrPlace(seasonVo.getDepplandPlace(), seasonVo.getArrplandPlace());
		setEffectiveDate(seasonVo.getEffDate());
		setExpirationDate(seasonVo.getExpDate());
		setTerm(seasonVo.getTerm());
	}
	
	public void setDepArrPlace(String dep, String arr) {
		depArrPlaceLabel.setText(dep + " → " + arr);
	}
	
	public void setEffectiveDate(String date) {
		if(date == null) return;
		effDateLabel.setText("" + Constants.getDateKoreanWithLineBreak(date));
	}
	
	public void setExpirationDate(String date) {
		if(date == null) return;
		expDateLabel.setText(Constants.getDateKoreanWithLineBreak(date));
	}
	
	public void setTerm(int term) {
		termLabel.setText(String.format("%d 일", term));
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
			Queue<ArrayList<TrainVo>> command = new LinkedList<ArrayList<TrainVo>>();
			command.add(TrainAPI.getInstance().getTrainList(seasonVo.getDepplandPlace(), seasonVo.getArrplandPlace(), Constants.getTodayDateToString()));
			new TrainInquiryMenu(command, parent, null).setVisible(true);
			parent.setVisible(false);
		}
	}
}
