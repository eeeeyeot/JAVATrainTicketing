package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
//import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
//import java.awt.Image;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

//import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import constants.Constants;
import database.TicketVo;
import database.TrainDAO;
import database.UserVo;
import openAPI.TrainAPI;
import openAPI.TrainVo;
import util.ScreenUtil;
import view_component.AutoSuggestPanel;
import view_component.SelectDatePanel;
import view_component.TicketInformationPanel;

import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class MainMenu extends JFrame {
	@SuppressWarnings("unused")
	private static final int SEASON_PERSONNEL = 1;
	
	private TrainDAO dao;
	private UserVo userVo;
	
	private Window parent;
	private JPanel ticketListPanel;
	private JPanel reservTicketPanel;
	private JLabel welcomeLabel;
	JTabbedPane reservTicketTabbedPane;
	
	private AutoSuggestPanel depComboBox;
	private AutoSuggestPanel arrComboBox;
	private SelectDatePanel selectComingDayPanel = null;
	
	private int oneway_personnel = 0;
	private int round_personnel = 0;
	
	private JPanel contentPane;
	private JPanel roundTripPanel;
	
	public MainMenu() {
		setTitle("기차 예매 프로그램");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		setResizable(false);
		
		JPanel northPanel = new JPanel();
		contentPane.add(northPanel, BorderLayout.NORTH);
		GridBagLayout gbl_northPanel = new GridBagLayout();
		gbl_northPanel.columnWidths = new int[] { 174, 476, 0 };
		gbl_northPanel.rowHeights = new int[] { 121, 0, 0 };
		gbl_northPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_northPanel.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		northPanel.setLayout(gbl_northPanel);

		/*
		 * JPanel imagePanel = new JPanel() { Image icon = new
		 * ImageIcon(MainMenu.class.getResource("../image/korailImage.jpg")).getImage();
		 * 
		 * public void paint(Graphics g) { Dimension size = getSize(); g.drawImage(icon,
		 * 0, 0, size.width, size.height, null); } };
		 */
		
		GridBagConstraints gbc_imagePanel = new GridBagConstraints();
		gbc_imagePanel.fill = GridBagConstraints.BOTH;
		gbc_imagePanel.insets = new Insets(0, 0, 0, 5);
		gbc_imagePanel.gridx = 0;
		gbc_imagePanel.gridy = 0;
		
		JPanel logoutPanel = new JPanel();
		GridBagConstraints gbc_logoutPanel = new GridBagConstraints();
		gbc_logoutPanel.insets = new Insets(0, 0, 5, 5);
		gbc_logoutPanel.gridx = 0;
		gbc_logoutPanel.gridy = 0;
		northPanel.add(logoutPanel, gbc_logoutPanel);
		logoutPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		welcomeLabel = new JLabel();
		welcomeLabel.setText("안녕하세요.OOO님");
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLabel.setFont(new Font("나눔스퀘어", Font.PLAIN, 16));
		logoutPanel.add(welcomeLabel);
		
		JPanel panel = new JPanel();
		logoutPanel.add(panel);
		
		JButton logoutButton = new JButton("로그아웃");
		logoutButton.setFont(new Font("나눔스퀘어", Font.PLAIN, 12));
		panel.add(logoutButton);
		//northPanel.add(imagePanel, gbc_imagePanel);

		JLabel korailLabel = new JLabel("Korail");
		korailLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 40));
		korailLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_korailLabel = new GridBagConstraints();
		gbc_korailLabel.insets = new Insets(0, 0, 5, 0);
		gbc_korailLabel.fill = GridBagConstraints.BOTH;
		gbc_korailLabel.gridx = 1;
		gbc_korailLabel.gridy = 0;
		northPanel.add(korailLabel, gbc_korailLabel);

		JPanel centerPanel = new JPanel();
		contentPane.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(null);

		JTabbedPane ticketingTabbedPane = new JTabbedPane(JTabbedPane.RIGHT);
		ticketingTabbedPane.setBounds(0, 0, 974, 530);
		centerPanel.add(ticketingTabbedPane);

		reservTicketPanel = new JPanel();
		JLabel reservTicketLabel = new JLabel("<html>승차권 예매</html>");
		reservTicketLabel.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		ticketingTabbedPane.add(reservTicketPanel);
		ticketingTabbedPane.setTitleAt(0, "승차권                  예매");
		ticketingTabbedPane.setTabComponentAt(0, reservTicketLabel);
		reservTicketPanel.setLayout(new BorderLayout(0, 0));

		reservTicketTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		reservTicketPanel.add(reservTicketTabbedPane);

		JPanel onewayPanel = new JPanel();
		JLabel onewayLabel = new JLabel("편도");
		onewayLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		onewayLabel.setPreferredSize(new Dimension(380, 30));
		reservTicketTabbedPane.add(onewayPanel);
		reservTicketTabbedPane.setTabComponentAt(0, onewayLabel);
		GridBagLayout gbl_onewayPanel = new GridBagLayout();
		gbl_onewayPanel.columnWidths = new int[] { 823, 0 };
		gbl_onewayPanel.rowHeights = new int[] { 90, 90, 90, 0, 0 };
		gbl_onewayPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_onewayPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		onewayPanel.setLayout(gbl_onewayPanel);
		onewayPanel.setBackground(new Color(0x77, 0x88, 0x99));
		
		roundTripPanel = new JPanel();
		JLabel roundTripLabel = new JLabel("왕복");
		roundTripLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		roundTripLabel.setPreferredSize(new Dimension(380, 30));
		reservTicketTabbedPane.add(roundTripPanel);
		reservTicketTabbedPane.setTabComponentAt(1, roundTripLabel);
		GridBagLayout gbl_roundTripPanel = new GridBagLayout();
		gbl_roundTripPanel.columnWidths = new int[] { 823, 0 };
		gbl_roundTripPanel.rowHeights = new int[] {90, 90, 90, 80, 30, 0};
		gbl_roundTripPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_roundTripPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		roundTripPanel.setLayout(gbl_roundTripPanel);
		roundTripPanel.setBackground(new Color(0x77, 0x88, 0x99));
		
		JPanel seasonTicketPanel = new JPanel();
		JLabel seasonTicketLabel = new JLabel("<html>정기권 예매</html>");
		seasonTicketLabel.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		ticketingTabbedPane.add(seasonTicketPanel);
		ticketingTabbedPane.setTabComponentAt(1, seasonTicketLabel);
		
		JPanel ticketConfrimPanel = new JPanel();
		JLabel ticketConfirmLabel = new JLabel("<html>승차권  확인</html>");
		ticketConfirmLabel.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		ticketingTabbedPane.add(ticketConfrimPanel);
		ticketingTabbedPane.setTabComponentAt(2, ticketConfirmLabel);
		
		ticketListPanel = new JPanel();
		JScrollPane ticketConfirmScrollPane = new JScrollPane(ticketListPanel);
		ticketListPanel.setBackground(new Color(0x77, 0x88, 0x99));
		GridBagLayout gbl_ticketListPanel = new GridBagLayout();
		
		gbl_ticketListPanel.columnWidths = new int[]{0, 0};
		gbl_ticketListPanel.rowHeights = new int[]{0, 0};
		gbl_ticketListPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_ticketListPanel.rowWeights = new double[]{0.0, 1.0};
		ticketListPanel.setLayout(gbl_ticketListPanel);
		
		ticketConfirmScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		ticketConfirmScrollPane.getHorizontalScrollBar().setUnitIncrement(16);
		ticketConfirmScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		GridBagConstraints gbc_ticketConfirmScrollPane = new GridBagConstraints();
		gbc_ticketConfirmScrollPane.fill = GridBagConstraints.BOTH;
		gbc_ticketConfirmScrollPane.gridx = 0;
		gbc_ticketConfirmScrollPane.gridy = 0;
		ticketConfrimPanel.setLayout(new GridLayout(0, 1, 0, 0));
		ticketConfrimPanel.add(ticketConfirmScrollPane);
		
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.setVisible(true);
				dispose();
			}
		});
		
		initReservationPanel(onewayPanel);
		initReservationPanel(roundTripPanel, "roundtrip");
		initInquiryButton();
		
		
		
		setLocation(ScreenUtil.getCenterPosition(this));
	}

	public MainMenu(UserVo userVo, Window parent) {
		this();
		this.userVo = userVo;
		dao = TrainDAO.getInstance();
		setWelcoming();
		this.parent = parent;
		
		addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e)
			{
				UpdateTicketList();
			}
		});
	}
	
	public void addReservation(String ticketId) {
		//add reservation information into DB
		dao.insertReservationData(userVo.getId(), ticketId);
	}

	public void UpdateTicketList() {
		ticketListPanel.removeAll();
		ArrayList<TicketVo> tickets = dao.getTicketList(userVo.getId());
		if(tickets != null) {
			tickets.sort(new Comparator<TicketVo>() {
				public int compare(TicketVo o1, TicketVo o2) {
					return o1.getTicketing_day().compareTo(o2.getTicketing_day());
				}
			});
			
			int cnt = 0;
			for(int i = 0; i < tickets.size(); i++) {
				GridBagConstraints gbc_panel = new GridBagConstraints();
				gbc_panel.insets = new Insets(0, 0, 3, 0);
				
				if(i >= tickets.size() - 1) {
					gbc_panel.anchor = GridBagConstraints.NORTH;
					gbc_panel.fill = GridBagConstraints.HORIZONTAL;
				}
				else {
					gbc_panel.fill = GridBagConstraints.BOTH;
				}
				
				gbc_panel.gridx = 0;
				gbc_panel.gridy = cnt;
				
				ticketListPanel.add(new TicketInformationPanel(tickets.get(i), this), gbc_panel);
				
				cnt++;
			}
			
			double[] newRowWeights = new double[ticketListPanel.getComponentCount()];
			for(int j = 0; j < newRowWeights.length; j++) {
				if(j < newRowWeights.length - 1)
					newRowWeights[j] = 0.0;
				else
					newRowWeights[j] = 1.0;
			}
			((GridBagLayout)ticketListPanel.getLayout()).rowWeights = newRowWeights;
		}
		ticketListPanel.updateUI();
	}
	
	private void initReservationPanel(JPanel target) {
		initReservationPanel(target, "oneway");
	}
	
	private void initReservationPanel(JPanel target, String type) {
		int gridy = 0;
		TitledBorder titled = new TitledBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		
		JPanel selectStationPanel = new JPanel();
		GridBagConstraints gbc_selectStationPanel = new GridBagConstraints();
		gbc_selectStationPanel.fill = GridBagConstraints.BOTH;
		gbc_selectStationPanel.insets = new Insets(0, 0, 5, 0);
		gbc_selectStationPanel.gridx = 0;
		gbc_selectStationPanel.gridy = gridy;
		target.add(selectStationPanel, gbc_selectStationPanel);
		selectStationPanel.setLayout(new GridLayout(0, 2, 0, 0));
		gridy++;
		
		JPanel departurePanel = new JPanel();
		selectStationPanel.add(departurePanel);
		departurePanel.setLayout(new GridLayout(0, 1, 0, 0));
		departurePanel.setBorder(titled);

		JLabel depPlaceLabel = new JLabel("출 발 역");
		depPlaceLabel.setFont(new Font("굴림", Font.PLAIN, 20));
		depPlaceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		departurePanel.add(depPlaceLabel);

		depComboBox = new AutoSuggestPanel();
		departurePanel.add(depComboBox);
		GridBagLayout gbl_depComboBox = new GridBagLayout();
		gbl_depComboBox.columnWidths = new int[] { 0 };
		gbl_depComboBox.rowHeights = new int[] { 0 };
		gbl_depComboBox.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_depComboBox.rowWeights = new double[] { Double.MIN_VALUE };
		depComboBox.setLayout(gbl_depComboBox);

		JPanel arrivePanel = new JPanel();
		selectStationPanel.add(arrivePanel);
		arrivePanel.setLayout(new GridLayout(2, 1, 0, 0));
		arrivePanel.setBorder(titled);
		
		JLabel arrPlaceLabel = new JLabel("도 착 역");
		arrPlaceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		arrPlaceLabel.setFont(new Font("굴림", Font.PLAIN, 20));
		arrivePanel.add(arrPlaceLabel);
		arrComboBox = new AutoSuggestPanel();
		arrivePanel.add(arrComboBox);
		GridBagLayout gbl_arrComboBox = new GridBagLayout();
		gbl_arrComboBox.columnWidths = new int[] { 0 };
		gbl_arrComboBox.rowHeights = new int[] { 0 };
		gbl_arrComboBox.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_arrComboBox.rowWeights = new double[] { Double.MIN_VALUE };
		arrComboBox.setLayout(gbl_arrComboBox);
		
		depComboBox.setDefault("서울");
		arrComboBox.setDefault("대전");
		
		SelectDatePanel selectDepDatePanel = new SelectDatePanel("oneway");
		GridBagConstraints gbc_selectDepDatePanel = new GridBagConstraints();
		gbc_selectDepDatePanel.fill = GridBagConstraints.VERTICAL;
		gbc_selectDepDatePanel.insets = new Insets(0, 0, 5, 0);
		gbc_selectDepDatePanel.gridx = 0;
		gbc_selectDepDatePanel.gridy = gridy;
		target.add(selectDepDatePanel, gbc_selectDepDatePanel);
		selectDepDatePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		gridy++;
		
		if(type.equals("roundtrip")) {
			//도착일 Panel 추가해야함
			selectComingDayPanel = new SelectDatePanel("comingday");
			GridBagConstraints gbc_selectComingDayPanel = new GridBagConstraints();
			gbc_selectComingDayPanel.fill = GridBagConstraints.VERTICAL;
			gbc_selectComingDayPanel.insets = new Insets(0, 0, 5, 0);
			gbc_selectComingDayPanel.gridx = 0;
			gbc_selectComingDayPanel.gridy = gridy++;
			target.add(selectComingDayPanel, gbc_selectComingDayPanel);
			selectComingDayPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			selectComingDayPanel.setBorder(titled);
		}

		{
			JPanel showHeadCntPanel = new JPanel();
			GridBagConstraints gbc_showHeadCntPanel = new GridBagConstraints();
			gbc_showHeadCntPanel.fill = GridBagConstraints.BOTH;
			gbc_showHeadCntPanel.insets = new Insets(0, 0, 5, 0);
			gbc_showHeadCntPanel.gridx = 0;
			gbc_showHeadCntPanel.gridy = gridy;
			target.add(showHeadCntPanel, gbc_showHeadCntPanel);
			showHeadCntPanel.setBorder(titled);
			gridy++;

			JPanel showCountDetailPanel = new JPanel();
			showHeadCntPanel.add(showCountDetailPanel);
			showCountDetailPanel.setLayout(new GridLayout(0, 1, 0, 0));

			JLabel headCountPanel = new JLabel("승객 인원");
			headCountPanel.setHorizontalAlignment(SwingConstants.CENTER);
			headCountPanel.setFont(new Font("굴림", Font.PLAIN, 20));
			showCountDetailPanel.add(headCountPanel);

			JPanel countDetailFractionPanel = new JPanel();

			showCountDetailPanel.add(countDetailFractionPanel);
			GridBagLayout gbl_countDetailFractionPanel = new GridBagLayout();
			gbl_countDetailFractionPanel.columnWidths = new int[] { 30, 160, 30, 160, 30, 160, 30, 0 };
			gbl_countDetailFractionPanel.rowHeights = new int[] { 24, 0 };
			gbl_countDetailFractionPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
					Double.MIN_VALUE };
			gbl_countDetailFractionPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
			countDetailFractionPanel.setLayout(gbl_countDetailFractionPanel);

			JLabel childCountLabel = new JLabel("어린이 0명");
			childCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
			childCountLabel.setFont(new Font("굴림", Font.BOLD, 28));
			GridBagConstraints gbc_childCountLabel = new GridBagConstraints();
			gbc_childCountLabel.anchor = GridBagConstraints.NORTH;
			gbc_childCountLabel.insets = new Insets(0, 0, 0, 5);
			gbc_childCountLabel.gridx = 1;
			gbc_childCountLabel.gridy = 0;
			countDetailFractionPanel.add(childCountLabel, gbc_childCountLabel);

			JLabel adultCountLabel = new JLabel("어른 0명");
			adultCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
			adultCountLabel.setFont(new Font("굴림", Font.BOLD, 28));
			GridBagConstraints gbc_adultCountLabel = new GridBagConstraints();
			gbc_adultCountLabel.anchor = GridBagConstraints.NORTH;
			gbc_adultCountLabel.insets = new Insets(0, 0, 0, 5);
			gbc_adultCountLabel.gridx = 3;
			gbc_adultCountLabel.gridy = 0;
			countDetailFractionPanel.add(adultCountLabel, gbc_adultCountLabel);

			JLabel seniorCountLabel = new JLabel("경로 0명");
			seniorCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
			seniorCountLabel.setFont(new Font("굴림", Font.BOLD, 28));
			GridBagConstraints gbc_seniorCountLabel = new GridBagConstraints();
			gbc_seniorCountLabel.insets = new Insets(0, 0, 0, 5);
			gbc_seniorCountLabel.anchor = GridBagConstraints.NORTH;
			gbc_seniorCountLabel.gridx = 5;
			gbc_seniorCountLabel.gridy = 0;
			countDetailFractionPanel.add(seniorCountLabel, gbc_seniorCountLabel);

			JPanel selectHeadCntPanel = new JPanel();
			GridBagConstraints gbc_selectHeadCntPaenl = new GridBagConstraints();
			gbc_selectHeadCntPaenl.fill = GridBagConstraints.BOTH;
			gbc_selectHeadCntPaenl.gridx = 0;
			gbc_selectHeadCntPaenl.gridy = gridy;
			target.add(selectHeadCntPanel, gbc_selectHeadCntPaenl);
			selectHeadCntPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			selectHeadCntPanel.setBorder(titled);

			JPanel selectDetailCountPanel = new JPanel();
			selectHeadCntPanel.add(selectDetailCountPanel);
			selectDetailCountPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

			JPanel selectChildCountPanel = new JPanel();
			selectDetailCountPanel.add(selectChildCountPanel);
			selectChildCountPanel.setLayout(new GridLayout(0, 1, 0, 0));

			JLabel selectChildLabel = new JLabel("어린이");
			selectChildLabel.setHorizontalAlignment(SwingConstants.CENTER);
			selectChildLabel.setFont(new Font("굴림", Font.PLAIN, 20));
			selectChildCountPanel.add(selectChildLabel);

			JPanel selectChildCount = new JPanel();
			selectChildCountPanel.add(selectChildCount);

			JButton decreaseChildCntButton = new JButton("-");
			selectChildCount.add(decreaseChildCntButton);

			JTextField childCntTextField = new JTextField("0");
			childCntTextField.setHorizontalAlignment(SwingConstants.CENTER);
			childCntTextField.setColumns(10);
			selectChildCount.add(childCntTextField);

			decreaseChildCntButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					decreaseCount(childCntTextField);
				}
			});

			childCntTextField.getDocument().addDocumentListener(new DocumentListener() {
				public void insertUpdate(DocumentEvent e) {
					childCountLabel.setText("어린이 " + childCntTextField.getText() + "명");
				}

				public void removeUpdate(DocumentEvent e) {
				}

				public void changedUpdate(DocumentEvent e) {
				}
			});
			childCntTextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent e) {
					Constants.numberFormatLimit(e, 1);
				}

				public void keyReleased(KeyEvent e) {
					setNumberLimit((JTextField) e.getSource());
				}
			});

			JButton increaseChildCntButton = new JButton("+");
			selectChildCount.add(increaseChildCntButton);
			increaseChildCntButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					increaseCount(childCntTextField);
				}
			});

			JPanel selectAdultCountPanel = new JPanel();
			selectDetailCountPanel.add(selectAdultCountPanel);
			selectAdultCountPanel.setLayout(new GridLayout(0, 1, 0, 0));

			JLabel selectAdultLabel = new JLabel("어른");
			selectAdultLabel.setHorizontalAlignment(SwingConstants.CENTER);
			selectAdultLabel.setFont(new Font("굴림", Font.PLAIN, 20));
			selectAdultCountPanel.add(selectAdultLabel);

			JPanel selectAdultCount = new JPanel();
			selectAdultCountPanel.add(selectAdultCount);

			JButton decreaseAdultCntButton = new JButton("-");
			selectAdultCount.add(decreaseAdultCntButton);

			JTextField adultCntTextField = new JTextField("0");
			adultCntTextField.setHorizontalAlignment(SwingConstants.CENTER);
			adultCntTextField.setColumns(10);
			selectAdultCount.add(adultCntTextField);

			adultCntTextField.getDocument().addDocumentListener(new DocumentListener() {
				public void insertUpdate(DocumentEvent e) {
					adultCountLabel.setText("어른 " + adultCntTextField.getText() + "명");
				}

				public void removeUpdate(DocumentEvent e) {
				}

				public void changedUpdate(DocumentEvent e) {
				}
			});

			adultCntTextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent e) {
					Constants.numberFormatLimit(e, 1);
				}
			});

			decreaseAdultCntButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					decreaseCount(adultCntTextField);
				}
			});

			JButton increaseAdultCntButton = new JButton("+");
			selectAdultCount.add(increaseAdultCntButton);
			
			increaseAdultCntButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					increaseCount(adultCntTextField);
				}
			});

			JPanel selectSeniorCountPanel = new JPanel();
			selectDetailCountPanel.add(selectSeniorCountPanel);
			selectSeniorCountPanel.setLayout(new GridLayout(0, 1, 0, 0));

			JLabel selectSeniorLabel = new JLabel("경로");
			selectSeniorLabel.setHorizontalAlignment(SwingConstants.CENTER);
			selectSeniorLabel.setFont(new Font("굴림", Font.PLAIN, 20));
			selectSeniorCountPanel.add(selectSeniorLabel);

			JPanel selectSeniorCount = new JPanel();
			selectSeniorCountPanel.add(selectSeniorCount);

			JButton decreaseSeniorCntButton = new JButton("-");
			selectSeniorCount.add(decreaseSeniorCntButton);

			JTextField seniorCntTextField = new JTextField("0");
			seniorCntTextField.setHorizontalAlignment(SwingConstants.CENTER);
			seniorCntTextField.setColumns(10);
			selectSeniorCount.add(seniorCntTextField);

			decreaseSeniorCntButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					decreaseCount(seniorCntTextField);
				}
			});
			
			seniorCntTextField.getDocument().addDocumentListener(new DocumentListener() {
				public void insertUpdate(DocumentEvent e) {
					seniorCountLabel.setText("경로 " + seniorCntTextField.getText() + "명");
				}

				public void removeUpdate(DocumentEvent e) {
				}

				public void changedUpdate(DocumentEvent e) {
				}
			});

			seniorCntTextField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent e) {
					Constants.numberFormatLimit(e, 1);
				}
			});

			JButton increaseSeniorCntButton = new JButton("+");
			selectSeniorCount.add(increaseSeniorCntButton);

			increaseSeniorCntButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					increaseCount(seniorCntTextField);
				}
			});

		}
	}
	
	private void initSeasonPanel(JPanel target) {
		
	}
	
	private void initInquiryButton() {
		JFrame thisObj = this;
		JButton trainInquiryButton = new JButton("열차 조회");
		trainInquiryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = reservTicketTabbedPane.getSelectedIndex();
				JPanel selectedTab = (JPanel) reservTicketTabbedPane.getSelectedComponent();
				SelectDatePanel datePanel = (SelectDatePanel)selectedTab.getComponent(1);
				
				String dayToGo = datePanel.getTextFieldText("yearTextField")
						+ String.format("%02d", Integer.parseInt(datePanel.getTextFieldText("monthTextField")))
						+ String.format("%02d", Integer.parseInt(datePanel.getTextFieldText("dayTextField")));
				String dayComing = null;
				
				int personnel = selectedIndex == 0 ? oneway_personnel : round_personnel;
				
				if(selectedIndex == 1) {
					datePanel = (SelectDatePanel)selectedTab.getComponent(2);
					dayComing = datePanel.getTextFieldText("yearTextField")
							+ String.format("%02d", Integer.parseInt(datePanel.getTextFieldText("monthTextField")))
							+ String.format("%02d", Integer.parseInt(datePanel.getTextFieldText("dayTextField")));
				}

				//날짜가 유효한가
				if (dayToGo.compareTo(Constants.getTodayDateToString()) < 0 || (dayComing != null && dayComing.compareTo(dayToGo) < 0)) 
				{
					showDialog("<html><center>선택한 날짜가</center><center>유효하지 않습니다.</center></html>");
				}
				//인원이 유효한가
				else if(personnel <= 0) {
					showDialog("인원의 수가 0명입니다.");
				}
				// 정상 동작
				else {
					String depText = depComboBox.getComboBoxText();
					String arrText = arrComboBox.getComboBoxText();

					Queue<ArrayList<TrainVo>> command = new LinkedList<ArrayList<TrainVo>>();
					command.add(TrainAPI.getInstance().getTrainList(depText, arrText, dayToGo));

					if (reservTicketTabbedPane.getSelectedIndex() == 1) {
						command.add(TrainAPI.getInstance().getTrainList(arrText, depText, dayComing));
						personnel = round_personnel;
					}

					new TrainInquiryMenu(command, thisObj, personnel).setVisible(true);
					setVisible(false);
				}

			}
		});
		
		trainInquiryButton.setFont(new Font("맑은 고딕", Font.PLAIN, 24));
		reservTicketPanel.add(trainInquiryButton, BorderLayout.SOUTH);
	}
	
	private void setWelcoming() {
		welcomeLabel.setText("<html>안녕하세요.<br><center>" + userVo.getName() + "님</center></html>");
	}

	private void increaseCount(JTextField tf) {
		if(reservTicketTabbedPane.getSelectedIndex() == 0) {
			if(oneway_personnel >= 9) return;
			oneway_personnel++;
		}
		else {
			if(round_personnel >= 9) return;
			round_personnel++;
		}
		
		setNumberLimit(tf);
		int tmp = Integer.parseInt(tf.getText());
		tmp++;
		if (tmp > 9)
			tmp = 9;
		tf.setText(String.format("%d", tmp));
	}

	private void decreaseCount(JTextField tf) {
		if(reservTicketTabbedPane.getSelectedIndex() == 0) {
			if(oneway_personnel <= 0) return;
			oneway_personnel--;
		}
		else {
			if(round_personnel <= 0) return;
			round_personnel--;
		}
		
		setNumberLimit(tf);
		int tmp = Integer.parseInt(tf.getText());
		tmp--;
		if (tmp < 0)
			tmp = 0;
		tf.setText(String.format("%d", tmp));
	}

	private void setNumberLimit(JTextField tf) {
		if (tf.getText().length() == 0) {
			tf.setText("0");
			return;
		}

		int tmp = Integer.parseInt(tf.getText());
		if (tmp < 0)
			tf.setText("0");
		else if (tmp > 9)
			tf.setText("9");
	}
	
	private void showDialog(String msg) {
		@SuppressWarnings("unused")
		NoticeDialog nd = new NoticeDialog(msg, this);
	}
}
