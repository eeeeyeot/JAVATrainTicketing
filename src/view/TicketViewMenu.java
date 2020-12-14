package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import constants.Constants;
import database.TicketVo;
import util.ScreenUtil;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.awt.GridLayout;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;

@SuppressWarnings("serial")
public class TicketViewMenu extends JFrame {

	JLabel reservationDateLabel;
	JLabel depPlandPlace;
	JLabel arrPlandPlace;
	JLabel depPlandTime;
	JLabel arrPlandTime;
	JLabel trainInfoLabel;
	
	JLabel seatLabel;
	JLabel personnelLabel;
	JLabel priceLabel;
	
	TitledBorder titled;
	
	private JPanel contentPane;

	public TicketViewMenu() {
		setTitle("승차권");
		setBounds(100, 100, 600, 400);
		setLocation(ScreenUtil.getCenterPosition(this));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{32, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		contentPane.setBackground(new Color(176, 196, 222));
		
		titled = new TitledBorder(new LineBorder(Color.black, 2));
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		
		JPanel titlePanel = new JPanel();
		//titlePanel.setBackground(new Color(192, 192, 192));
		GridBagConstraints gbc_titlePanel = new GridBagConstraints();
		gbc_titlePanel.insets = new Insets(0, 0, 5, 0);
		gbc_titlePanel.fill = GridBagConstraints.BOTH;
		gbc_titlePanel.gridx = 0;
		gbc_titlePanel.gridy = 0;
		contentPane.add(titlePanel, gbc_titlePanel);
		titlePanel.setLayout(new GridLayout(0, 1, 0, 0));
		//titlePanel.setBorder(new TitledBorder(new LineBorder(new Color(153, 153, 255), 2)));
		
		JLabel titleLabel = new JLabel("승차권");
		titleLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titlePanel.add(titleLabel);
		
		
		JPanel datePanel = new JPanel();
		datePanel.setBackground(Color.WHITE);
		GridBagConstraints gbc_datePanel = new GridBagConstraints();
		gbc_datePanel.insets = new Insets(0, 0, 5, 0);
		gbc_datePanel.fill = GridBagConstraints.BOTH;
		gbc_datePanel.gridx = 0;
		gbc_datePanel.gridy = 1;
		contentPane.add(datePanel, gbc_datePanel);
		datePanel.setLayout(new GridLayout(0, 1, 0, 0));
		datePanel.setBorder(titled);
		
		reservationDateLabel = new JLabel("2020년 00월 00일 (월)");
		reservationDateLabel.setBackground(Color.WHITE);
		reservationDateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		reservationDateLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		datePanel.add(reservationDateLabel);
		
		JPanel depArrPanel = new JPanel();
		depArrPanel.setBackground(new Color(176, 196, 222));
		GridBagConstraints gbc_depArrPanel = new GridBagConstraints();
		gbc_depArrPanel.insets = new Insets(0, 0, 5, 0);
		gbc_depArrPanel.fill = GridBagConstraints.BOTH;
		gbc_depArrPanel.gridx = 0;
		gbc_depArrPanel.gridy = 2;
		contentPane.add(depArrPanel, gbc_depArrPanel);
		GridBagLayout gbl_depArrPanel = new GridBagLayout();
		gbl_depArrPanel.columnWidths = new int[]{92, 0};
		gbl_depArrPanel.rowHeights = new int[]{15, 0, 0};
		gbl_depArrPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_depArrPanel.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		depArrPanel.setLayout(gbl_depArrPanel);
		
		JPanel depPlacePanel = new JPanel();
		depPlacePanel.setBackground(Color.WHITE);
		GridBagConstraints gbc_depPlacePanel = new GridBagConstraints();
		gbc_depPlacePanel.insets = new Insets(0, 0, 5, 0);
		gbc_depPlacePanel.fill = GridBagConstraints.BOTH;
		gbc_depPlacePanel.gridx = 0;
		gbc_depPlacePanel.gridy = 0;
		depArrPanel.add(depPlacePanel, gbc_depPlacePanel);
		GridBagLayout gbl_depPlacePanel = new GridBagLayout();
		gbl_depPlacePanel.columnWidths = new int[] {70, 57, 30, 57, 30, 57, 70, 0};
		gbl_depPlacePanel.rowHeights = new int[] {0};
		gbl_depPlacePanel.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_depPlacePanel.rowWeights = new double[]{1.0};
		depPlacePanel.setLayout(gbl_depPlacePanel);
		depPlacePanel.setBorder(titled);
		
		depPlandPlace = new JLabel("서울");
		depPlandPlace.setHorizontalAlignment(SwingConstants.CENTER);
		depPlandPlace.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		GridBagConstraints gbc_depPlandPlace = new GridBagConstraints();
		gbc_depPlandPlace.fill = GridBagConstraints.VERTICAL;
		gbc_depPlandPlace.insets = new Insets(0, 0, 0, 5);
		gbc_depPlandPlace.gridx = 1;
		gbc_depPlandPlace.gridy = 0;
		depPlacePanel.add(depPlandPlace, gbc_depPlandPlace);
		
		JLabel arrowLabel = new JLabel("→");
		arrowLabel.setHorizontalAlignment(SwingConstants.CENTER);
		arrowLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		GridBagConstraints gbc_arrowLabel = new GridBagConstraints();
		gbc_arrowLabel.fill = GridBagConstraints.VERTICAL;
		gbc_arrowLabel.insets = new Insets(0, 0, 0, 5);
		gbc_arrowLabel.gridx = 3;
		gbc_arrowLabel.gridy = 0;
		depPlacePanel.add(arrowLabel, gbc_arrowLabel);
		
		arrPlandPlace = new JLabel("대전");
		arrPlandPlace.setHorizontalAlignment(SwingConstants.CENTER);
		arrPlandPlace.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		GridBagConstraints gbc_arrPlandPlace = new GridBagConstraints();
		gbc_arrPlandPlace.insets = new Insets(0, 0, 0, 5);
		gbc_arrPlandPlace.fill = GridBagConstraints.VERTICAL;
		gbc_arrPlandPlace.gridx = 5;
		gbc_arrPlandPlace.gridy = 0;
		depPlacePanel.add(arrPlandPlace, gbc_arrPlandPlace);
		
		JPanel arrPlacePanel = new JPanel();
		arrPlacePanel.setBackground(Color.WHITE);
		GridBagConstraints gbc_arrPlacePanel = new GridBagConstraints();
		gbc_arrPlacePanel.fill = GridBagConstraints.BOTH;
		gbc_arrPlacePanel.gridx = 0;
		gbc_arrPlacePanel.gridy = 1;
		depArrPanel.add(arrPlacePanel, gbc_arrPlacePanel);
		GridBagLayout gbl_arrPlacePanel = new GridBagLayout();
		gbl_arrPlacePanel.columnWidths = new int[] {30, 287, 40, 287, 30, 0};
		gbl_arrPlacePanel.rowHeights = new int[] {0};
		gbl_arrPlacePanel.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_arrPlacePanel.rowWeights = new double[]{1.0};
		arrPlacePanel.setLayout(gbl_arrPlacePanel);
		arrPlacePanel.setBorder(titled);
		
		depPlandTime = new JLabel("05:05");
		depPlandTime.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		depPlandTime.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_depPlandTime = new GridBagConstraints();
		gbc_depPlandTime.fill = GridBagConstraints.VERTICAL;
		gbc_depPlandTime.insets = new Insets(0, 0, 0, 5);
		gbc_depPlandTime.gridx = 1;
		gbc_depPlandTime.gridy = 0;
		arrPlacePanel.add(depPlandTime, gbc_depPlandTime);
		
		arrPlandTime = new JLabel("06:35");
		arrPlandTime.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		arrPlandTime.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_arrPlandTime = new GridBagConstraints();
		gbc_arrPlandTime.insets = new Insets(0, 0, 0, 5);
		gbc_arrPlandTime.fill = GridBagConstraints.VERTICAL;
		gbc_arrPlandTime.gridx = 3;
		gbc_arrPlandTime.gridy = 0;
		arrPlacePanel.add(arrPlandTime, gbc_arrPlandTime);
		
		JPanel trainInfoPanel = new JPanel();
		trainInfoPanel.setBackground(Color.WHITE);
		GridBagConstraints gbc_trainInfoPanel = new GridBagConstraints();
		gbc_trainInfoPanel.insets = new Insets(0, 0, 5, 0);
		gbc_trainInfoPanel.fill = GridBagConstraints.BOTH;
		gbc_trainInfoPanel.gridx = 0;
		gbc_trainInfoPanel.gridy = 3;
		contentPane.add(trainInfoPanel, gbc_trainInfoPanel);
		trainInfoPanel.setLayout(new GridLayout(0, 1, 0, 0));
		trainInfoPanel.setBorder(titled);
		
		trainInfoLabel = new JLabel("무궁화호  1551");
		trainInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		trainInfoLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		trainInfoPanel.add(trainInfoLabel);
		
		JPanel reservationInfoPanel = new JPanel();
		reservationInfoPanel.setBackground(Color.WHITE);
		GridBagConstraints gbc_reservationInfoPanel = new GridBagConstraints();
		gbc_reservationInfoPanel.fill = GridBagConstraints.BOTH;
		gbc_reservationInfoPanel.gridx = 0;
		gbc_reservationInfoPanel.gridy = 4;
		contentPane.add(reservationInfoPanel, gbc_reservationInfoPanel);
		reservationInfoPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel reservationGridPanel = new JPanel();
		reservationGridPanel.setBackground(new Color(176, 196, 222));
		reservationInfoPanel.add(reservationGridPanel);
		GridBagLayout gbl_reservationGridPanel = new GridBagLayout();
		gbl_reservationGridPanel.columnWidths = new int[] {191, 80, 191, 0};
		gbl_reservationGridPanel.rowHeights = new int[]{71, 0};
		gbl_reservationGridPanel.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_reservationGridPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		reservationGridPanel.setLayout(gbl_reservationGridPanel);
		
		JPanel seatPnel = new JPanel();
		seatPnel.setBackground(Color.WHITE);
		seatPnel.setBorder(titled);
		GridBagConstraints gbc_seatPnel = new GridBagConstraints();
		gbc_seatPnel.fill = GridBagConstraints.BOTH;
		gbc_seatPnel.insets = new Insets(0, 0, 0, 5);
		gbc_seatPnel.gridx = 0;
		gbc_seatPnel.gridy = 0;
		reservationGridPanel.add(seatPnel, gbc_seatPnel);
		seatPnel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel seatTitleLabel = new JLabel("좌석 번호");
		seatTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		seatTitleLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
		seatPnel.add(seatTitleLabel);
		
		seatLabel = new JLabel("00, 00, 00");
		seatLabel.setHorizontalAlignment(SwingConstants.CENTER);
		seatLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
		seatPnel.add(seatLabel);
		seatLabel.validate();
		
		JPanel personnelPanel = new JPanel();
		personnelPanel.setBackground(Color.WHITE);
		personnelPanel.setBorder(titled);
		GridBagConstraints gbc_personnelPanel = new GridBagConstraints();
		gbc_personnelPanel.fill = GridBagConstraints.BOTH;
		gbc_personnelPanel.insets = new Insets(0, 0, 0, 5);
		gbc_personnelPanel.gridx = 1;
		gbc_personnelPanel.gridy = 0;
		reservationGridPanel.add(personnelPanel, gbc_personnelPanel);
		personnelPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel personnelTitleLabel = new JLabel("인 원");
		personnelTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		personnelTitleLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
		personnelPanel.add(personnelTitleLabel);
		
		personnelLabel = new JLabel("3");
		personnelLabel.setHorizontalAlignment(SwingConstants.CENTER);
		personnelLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
		personnelPanel.add(personnelLabel);
		
		JPanel pricePanel = new JPanel();
		pricePanel.setBackground(Color.WHITE);
		GridBagConstraints gbc_pricePanel = new GridBagConstraints();
		gbc_pricePanel.fill = GridBagConstraints.BOTH;
		gbc_pricePanel.gridx = 2;
		gbc_pricePanel.gridy = 0;
		reservationGridPanel.add(pricePanel, gbc_pricePanel);
		pricePanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel priceTitleLabel = new JLabel("가 격");
		priceTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		priceTitleLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
		pricePanel.add(priceTitleLabel);
		
		priceLabel = new JLabel("3,000");
		priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		priceLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
		pricePanel.add(priceLabel);
		pricePanel.setBorder(titled);
	}

	public TicketViewMenu(TicketVo ticket) {
		this();
		reservationDateLabel.setText(getDate(ticket.getDeppland_time()));
		depPlandPlace.setText(ticket.getDeppland_place());
		arrPlandPlace.setText(ticket.getArrpland_place());
		depPlandTime.setText(getTime(ticket.getDeppland_time()));
		arrPlandTime.setText(getTime(ticket.getArrpland_time()));
		trainInfoLabel.setText(ticket.getTrain_name() + "   " + ticket.getCar_number());
		
		int seatLength;
		if(ticket.getSeat().equals("null")) {
			seatLabel.setText("");
			seatLength = 0;
		}
		else {
			seatLabel.setText(ticket.getSeat());
			seatLength = ticket.getSeat().split(" ").length;
		}
		
		int personnel = Integer.parseInt(ticket.getPersonnel());
		if(seatLength < personnel) {
			String mult = "";
			int remains = personnel - seatLength;
			if(remains > 1)
				mult = "X" + remains;
			seatLabel.setText(seatLabel.getText() + " " + "입석" + mult);	
		}
		seatLabel.validate();
		
		personnelLabel.setText(ticket.getPersonnel());
		
		int price = Integer.parseInt(ticket.getPrice());
		priceLabel.setText(Constants.dFormatter.format(price));
	}
	
	private String getDate(String time) {
		Calendar cal = getCalendar(time);
		StringBuilder sb = new StringBuilder();
		
		sb.append(cal.get(Calendar.YEAR)).append("년 ").append(cal.get(Calendar.MONTH) + 1)
		.append("월 ").append(cal.get(Calendar.DAY_OF_MONTH)).append("일 (")
		.append(Constants.DAY_OF_WEEK[cal.get(Calendar.DAY_OF_WEEK)]).append(")");
		
		return sb.toString();
	}
	
	private String getTime(String time) {
		Calendar cal = getCalendar(time);
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("%02d", cal.get(Calendar.HOUR_OF_DAY))).append(":").append(String.format("%02d", cal.get(Calendar.MINUTE)));
		
		return sb.toString();
	}
	
	private Calendar getCalendar(String time) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmm");
		
		try {
			cal.setTime(sf.parse(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return cal;
	}
}
