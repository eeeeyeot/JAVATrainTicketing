package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
//import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
//import java.awt.Image;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import javax.swing.ImageIcon;
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
import database.SeasonTicketVo;
import database.TicketVo;
import database.TrainDAO;
import database.UserVo;
import main.Main;
import openAPI.TrainAPI;
import openAPI.TrainVo;
import util.ScreenUtil;
import view_component.AutoSuggestPanel;
import view_component.SeasonInformationPanel;
import view_component.SelectDatePanel;
import view_component.TicketInformationPanel;

import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JLayeredPane;
import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class MainMenu extends JFrame {
	@SuppressWarnings("unused")
	private static final int SEASON_PERSONNEL = 1;

	private TrainDAO dao;
	private UserVo userVo;

	private Window parent;
	private JTabbedPane ticketingTabbedPane;
	private JPanel ticketListPanel;
	private JPanel seasonListPanel;
	private JPanel reservTicketPanel;
	private JPanel seasonReservPanel;

	private JPanel contentPane;
	private JPanel roundTripPanel;
	private JPanel onewayPanel;
	private JLabel welcomeLabel;

	private AutoSuggestPanel[] depComboBox = new AutoSuggestPanel[2];
	private AutoSuggestPanel[] arrComboBox = new AutoSuggestPanel[2];
	private SelectDatePanel selectComingDayPanel = null;
	private TitledBorder panelBorder;
	private TitledBorder titleBorder; 

	private Font tabFont;

	int[] personnelArray = new int[3];

	private int oneway_personnel = 0;
	private int round_personnel = 0;

	private JLayeredPane reservTicketLayeredPane;

	public MainMenu() {
		setTitle("기차 예매 프로그램");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		setResizable(true);

		panelBorder = new TitledBorder(new LineBorder(Color.BLACK, 2));
		titleBorder = new TitledBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		
		JPanel northPanel = new JPanel();
		northPanel.setBackground(Color.WHITE);
		contentPane.add(northPanel, BorderLayout.NORTH);
		GridBagLayout gbl_northPanel = new GridBagLayout();
		gbl_northPanel.columnWidths = new int[] { 174, 295, 0 };
		gbl_northPanel.rowHeights = new int[] { 121, 0 };
		gbl_northPanel.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_northPanel.rowWeights = new double[] { 0.0, 1.0 };
		northPanel.setLayout(gbl_northPanel);

		GridBagConstraints gbc_imagePanel = new GridBagConstraints();
		gbc_imagePanel.fill = GridBagConstraints.BOTH;
		gbc_imagePanel.insets = new Insets(0, 0, 0, 5);
		gbc_imagePanel.gridx = 0;
		gbc_imagePanel.gridy = 0;

		JPanel userInfoPanel = new JPanel();
		userInfoPanel.setBackground(Color.WHITE);
		GridBagConstraints gbc_userInfoPanel = new GridBagConstraints();
		gbc_userInfoPanel.insets = new Insets(0, 0, 5, 5);
		gbc_userInfoPanel.gridx = 0;
		gbc_userInfoPanel.gridy = 0;
		northPanel.add(userInfoPanel, gbc_userInfoPanel);
		userInfoPanel.setLayout(new GridLayout(0, 1, 0, 0));

		welcomeLabel = new JLabel();
		welcomeLabel.setText("안녕하세요.OOO님");
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		userInfoPanel.add(welcomeLabel);

		JPanel logoutPanel = new JPanel();
		logoutPanel.setBackground(Color.WHITE);
		userInfoPanel.add(logoutPanel);

		JButton logoutButton = new JButton("로그아웃");
		logoutButton.setBackground(new Color(220, 220, 220));
		logoutButton.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		logoutPanel.add(logoutButton);
		// northPanel.add(imagePanel, gbc_imagePanel);

		JPanel titleImage = new JPanel() {
			Image background = new ImageIcon(Main.class.getResource("../image/korailImage.jpg")).getImage();

			public void paint(Graphics g) {

				g.drawImage(background, 0, 0, gbl_northPanel.columnWidths[1], gbl_northPanel.rowHeights[0], null);
			}

		};
		titleImage.setBackground(Color.WHITE);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 0;
		northPanel.add(titleImage, gbc_panel);

		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(Color.WHITE);
		GridBagConstraints gbc_titlePanel = new GridBagConstraints();
		gbc_titlePanel.insets = new Insets(0, 0, 5, 0);
		gbc_titlePanel.fill = GridBagConstraints.BOTH;
		gbc_titlePanel.gridx = 2;
		gbc_titlePanel.gridy = 0;
		northPanel.add(titlePanel, gbc_titlePanel);
		GridBagLayout gbl_titlePanel = new GridBagLayout();
		gbl_titlePanel.columnWidths = new int[] { 265, 0 };
		gbl_titlePanel.rowHeights = new int[] { 20, 0, 0 };
		gbl_titlePanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_titlePanel.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		titlePanel.setLayout(gbl_titlePanel);

		JLabel licenseLabel = new JLabel("OpenAPI 국토교통부_열차조회  ");
		GridBagConstraints gbc_licenseLabel = new GridBagConstraints();
		gbc_licenseLabel.anchor = GridBagConstraints.EAST;
		gbc_licenseLabel.gridx = 0;
		gbc_licenseLabel.gridy = 0;
		titlePanel.add(licenseLabel, gbc_licenseLabel);

		JLabel korailLabel = new JLabel("Korail");
		korailLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 40));
		korailLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_korailLabel = new GridBagConstraints();
		gbc_korailLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_korailLabel.insets = new Insets(0, 0, 5, 0);
		gbc_korailLabel.fill = GridBagConstraints.BOTH;
		gbc_korailLabel.gridx = 0;
		gbc_korailLabel.gridy = 1;
		titlePanel.add(korailLabel, gbc_korailLabel);

		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.WHITE);
		contentPane.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new GridLayout(0, 1, 0, 0));

		ticketingTabbedPane = new JTabbedPane(JTabbedPane.RIGHT);
		ticketingTabbedPane.setBackground(Color.WHITE);
		centerPanel.add(ticketingTabbedPane);

		reservTicketPanel = new JPanel();
		tabFont = new Font("맑은 고딕", Font.BOLD, 24);
		JLabel reservTicketLabel = new JLabel("<html>승차권 예매</html>");
		reservTicketLabel.setFont(tabFont);
		ticketingTabbedPane.add(reservTicketPanel);
		ticketingTabbedPane.setTitleAt(0, "승차권                   예매");
		ticketingTabbedPane.setTabComponentAt(0, reservTicketLabel);
		reservTicketPanel.setLayout(new BorderLayout(0, 0));

		JPanel reservationTypePanel = new JPanel();
		reservTicketPanel.add(reservationTypePanel, BorderLayout.NORTH);
		reservationTypePanel.setLayout(new GridLayout(1, 0, 0, 0));

		JButton selectOnewayBtn = new JButton("편도");
		selectOnewayBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		selectOnewayBtn.setBackground(Color.WHITE);
		reservationTypePanel.add(selectOnewayBtn);

		JButton selectRoundtripBtn = new JButton("왕복");
		selectRoundtripBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		selectRoundtripBtn.setBackground(Color.LIGHT_GRAY);
		reservationTypePanel.add(selectRoundtripBtn);

		JPanel reservationInfoPanel = new JPanel();
		reservTicketPanel.add(reservationInfoPanel, BorderLayout.CENTER);
		GridBagLayout gbl_reservationInfoPanel = new GridBagLayout();
		gbl_reservationInfoPanel.columnWidths = new int[] { 800, 0 };
		gbl_reservationInfoPanel.rowHeights = new int[] { 40, 0 };
		gbl_reservationInfoPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_reservationInfoPanel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		reservationInfoPanel.setLayout(gbl_reservationInfoPanel);

		reservTicketLayeredPane = new JLayeredPane();
		GridBagConstraints gbc_reservTicketLayeredPane = new GridBagConstraints();
		gbc_reservTicketLayeredPane.fill = GridBagConstraints.BOTH;
		gbc_reservTicketLayeredPane.gridx = 0;
		gbc_reservTicketLayeredPane.gridy = 0;
		reservationInfoPanel.add(reservTicketLayeredPane, gbc_reservTicketLayeredPane);
		GridBagLayout gbl_reservTicketLayeredPane = new GridBagLayout();
		gbl_reservTicketLayeredPane.columnWidths = new int[] { 813, 0 };
		gbl_reservTicketLayeredPane.rowHeights = new int[] { 499, 0 };
		gbl_reservTicketLayeredPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_reservTicketLayeredPane.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		reservTicketLayeredPane.setLayout(gbl_reservTicketLayeredPane);

		onewayPanel = new JPanel();
		GridBagConstraints gbc_onewayPanel = new GridBagConstraints();
		gbc_onewayPanel.fill = GridBagConstraints.BOTH;
		gbc_onewayPanel.gridx = 0;
		gbc_onewayPanel.gridy = 0;
		reservTicketLayeredPane.add(onewayPanel, gbc_onewayPanel);
		reservTicketLayeredPane.setLayer(onewayPanel, 2);
		GridBagLayout gbl_onewayPanel = new GridBagLayout();
		gbl_onewayPanel.columnWidths = new int[] { 824, 0 };
		gbl_onewayPanel.rowHeights = new int[] { 90, 78, 90, 0, 0 };
		gbl_onewayPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_onewayPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		onewayPanel.setLayout(gbl_onewayPanel);
		onewayPanel.setBackground(new Color(0x77, 0x88, 0x99));

		roundTripPanel = new JPanel();
		GridBagConstraints gbc_roundTripPanel = new GridBagConstraints();
		gbc_roundTripPanel.fill = GridBagConstraints.BOTH;
		gbc_roundTripPanel.gridx = 0;
		gbc_roundTripPanel.gridy = 0;
		reservTicketLayeredPane.add(roundTripPanel, gbc_roundTripPanel);
		reservTicketLayeredPane.setLayer(roundTripPanel, 1);
		GridBagLayout gbl_roundTripPanel = new GridBagLayout();
		gbl_roundTripPanel.columnWidths = new int[] { 824, 0 };
		gbl_roundTripPanel.rowHeights = new int[] { 90, 90, 90, 80, 30, 0 };
		gbl_roundTripPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_roundTripPanel.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		roundTripPanel.setLayout(gbl_roundTripPanel);
		roundTripPanel.setBackground(new Color(0x77, 0x88, 0x99));

		initSeasonPanel();

		JPanel ticketConfrimPanel = new JPanel();
		JLabel ticketConfirmLabel = new JLabel("<html>승차권 확인</html>");
		ticketConfirmLabel.setFont(tabFont);
		ticketingTabbedPane.add(ticketConfrimPanel);
		ticketingTabbedPane.setTitleAt(2, "승차권  확인");
		ticketingTabbedPane.setTabComponentAt(2, ticketConfirmLabel);
		ticketConfrimPanel.setLayout(new BorderLayout(0, 0));

		JPanel ticketTypePanel = new JPanel();
		ticketConfrimPanel.add(ticketTypePanel, BorderLayout.NORTH);
		ticketTypePanel.setLayout(new GridLayout(0, 2, 0, 0));

		JButton selectTicketBtn = new JButton("승차권");
		selectTicketBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		selectTicketBtn.setBackground(Color.WHITE);
		ticketTypePanel.add(selectTicketBtn);

		JButton selectSeasonBtn = new JButton("정기권");
		selectSeasonBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		selectSeasonBtn.setBackground(Color.LIGHT_GRAY);
		ticketTypePanel.add(selectSeasonBtn);

		JLayeredPane ticketLayeredPane = new JLayeredPane();
		JScrollPane ticketConfirmScrollPane = new JScrollPane(ticketLayeredPane);
		GridBagLayout gbl_ticketLayeredPane = new GridBagLayout();
		gbl_ticketLayeredPane.columnWidths = new int[] { 1, 0 };
		gbl_ticketLayeredPane.rowHeights = new int[] { 1, 0 };
		gbl_ticketLayeredPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_ticketLayeredPane.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		ticketLayeredPane.setLayout(gbl_ticketLayeredPane);

		ticketListPanel = new JPanel();
		GridBagConstraints gbc_ticketListPanel = new GridBagConstraints();
		gbc_ticketListPanel.fill = GridBagConstraints.BOTH;
		gbc_ticketListPanel.gridx = 0;
		gbc_ticketListPanel.gridy = 0;
		ticketLayeredPane.add(ticketListPanel, gbc_ticketListPanel);
		ticketLayeredPane.setLayer(ticketListPanel, 2);
		ticketListPanel.setBackground(new Color(0x77, 0x88, 0x99));
		GridBagLayout gbl_ticketListPanel = new GridBagLayout();

		gbl_ticketListPanel.columnWidths = new int[] { 0, 0 };
		gbl_ticketListPanel.rowHeights = new int[] { 0, 0 };
		gbl_ticketListPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_ticketListPanel.rowWeights = new double[] { 0.0, 1.0 };
		ticketListPanel.setLayout(gbl_ticketListPanel);

		seasonListPanel = new JPanel();
		GridBagConstraints gbc_seasonListPanel = new GridBagConstraints();
		gbc_seasonListPanel.fill = GridBagConstraints.BOTH;
		gbc_seasonListPanel.gridx = 0;
		gbc_seasonListPanel.gridy = 0;
		ticketLayeredPane.add(seasonListPanel, gbc_seasonListPanel);
		ticketLayeredPane.setLayer(seasonListPanel, 1);
		seasonListPanel.setBackground(new Color(0x77, 0x88, 0x99));

		GridBagLayout gbl_seasonListPanel = new GridBagLayout();
		gbl_seasonListPanel.columnWidths = new int[] { 0, 0 };
		gbl_seasonListPanel.rowHeights = new int[] { 0, 0 };
		gbl_seasonListPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_seasonListPanel.rowWeights = new double[] { 0.0, 1.0 };
		seasonListPanel.setLayout(gbl_seasonListPanel);

		ticketConfirmScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		ticketConfirmScrollPane.getHorizontalScrollBar().setUnitIncrement(16);
		ticketConfirmScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		GridBagConstraints gbc_ticketConfirmScrollPane = new GridBagConstraints();
		gbc_ticketConfirmScrollPane.fill = GridBagConstraints.BOTH;
		gbc_ticketConfirmScrollPane.gridx = 0;
		gbc_ticketConfirmScrollPane.gridy = 0;
		ticketConfrimPanel.add(ticketConfirmScrollPane);

		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.setVisible(true);
				dispose();
			}
		});

		selectOnewayBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectRoundtripBtn.setBackground(Color.LIGHT_GRAY);
				((JButton) e.getSource()).setBackground(Color.WHITE);
				reservTicketLayeredPane.setLayer(onewayPanel, 2);
				reservTicketLayeredPane.updateUI();
			}
		});

		selectRoundtripBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectOnewayBtn.setBackground(Color.LIGHT_GRAY);
				((JButton) e.getSource()).setBackground(Color.WHITE);
				reservTicketLayeredPane.setLayer(onewayPanel, 0);
				reservTicketLayeredPane.updateUI();
			}
		});

		selectTicketBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectTicketBtn.setBackground(Color.WHITE);
				selectSeasonBtn.setBackground(Color.LIGHT_GRAY);
				ticketLayeredPane.setLayer(ticketListPanel, 2);
			}
		});

		selectSeasonBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectSeasonBtn.setBackground(Color.WHITE);
				selectTicketBtn.setBackground(Color.LIGHT_GRAY);
				ticketLayeredPane.setLayer(ticketListPanel, 0);
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
			public void componentShown(ComponentEvent e) {
				updateTickets();
			}
		});
	}

	public void addReservation(String ticketId) {
		// add reservation information into DB
		dao.insertReservationData(userVo.getId(), ticketId);
	}

	public void addSeasonReservation(String seasonId) {
		dao.insertSeasonReservation(userVo.getId(), seasonId);

	}

	@SuppressWarnings("unchecked")
	public <T> void updateTicketList(T t) {
		ArrayList<T> tickets = null;
		if (t instanceof TicketVo) {
			ticketListPanel.removeAll();
			tickets = (ArrayList<T>) dao.getTicketList(userVo.getId());
		} else if (t instanceof SeasonTicketVo) {
			seasonListPanel.removeAll();
			tickets = (ArrayList<T>) dao.getSeasonList(userVo.getId());
		}

		if (tickets != null) {
			if (t instanceof TicketVo)
				tickets.sort((Comparator<? super T>) new Comparator<TicketVo>() {
					public int compare(TicketVo o1, TicketVo o2) {
						return o1.getTicketing_day().compareTo(o2.getTicketing_day());
					}
				});
			else
				tickets.sort((Comparator<? super T>) new Comparator<SeasonTicketVo>() {
					public int compare(SeasonTicketVo o1, SeasonTicketVo o2) {
						return o1.getExpDate().compareTo(o2.getExpDate());
					}
				});

			int cnt = 0;
			for (int i = 0; i < tickets.size(); i++) {
				GridBagConstraints gbc_panel = new GridBagConstraints();
				gbc_panel.insets = new Insets(0, 0, 3, 0);

				if (i >= tickets.size() - 1) {
					gbc_panel.anchor = GridBagConstraints.NORTH;
					gbc_panel.fill = GridBagConstraints.HORIZONTAL;
				} else {
					gbc_panel.fill = GridBagConstraints.BOTH;
				}

				gbc_panel.gridx = 0;
				gbc_panel.gridy = cnt;

				if (t instanceof TicketVo)
					ticketListPanel.add(new TicketInformationPanel((TicketVo) tickets.get(i), this), gbc_panel);
				else
					seasonListPanel.add(new SeasonInformationPanel((SeasonTicketVo) tickets.get(i), this), gbc_panel);
				cnt++;
			}

			if (t instanceof TicketVo) {
				double[] newRowWeights = new double[ticketListPanel.getComponentCount()];
				for (int j = 0; j < newRowWeights.length; j++) {
					if (j < newRowWeights.length - 1)
						newRowWeights[j] = 0.0;
					else
						newRowWeights[j] = 1.0;
				}
				((GridBagLayout) ticketListPanel.getLayout()).rowWeights = newRowWeights;
			} else {
				double[] newRowWeights = new double[seasonListPanel.getComponentCount()];
				for (int j = 0; j < newRowWeights.length; j++) {
					if (j < newRowWeights.length - 1)
						newRowWeights[j] = 0.0;
					else
						newRowWeights[j] = 1.0;
				}
				((GridBagLayout) seasonListPanel.getLayout()).rowWeights = newRowWeights;
			}

		}
		if (t instanceof TicketVo)
			ticketListPanel.updateUI();
		else
			seasonListPanel.updateUI();
	}

	private void updateTickets() {
		updateTicketList(TicketVo.getInstance());
		updateTicketList(SeasonTicketVo.getInstance());
	}

	private void initReservationPanel(JPanel target) {
		initReservationPanel(target, "oneway");
	}

	private void initReservationPanel(JPanel target, String type) {
		int gridy = 0;

		JPanel selectStationPanel = new JPanel();
		GridBagConstraints gbc_selectStationPanel = new GridBagConstraints();
		gbc_selectStationPanel.fill = GridBagConstraints.BOTH;
		gbc_selectStationPanel.insets = new Insets(0, 0, 5, 0);
		gbc_selectStationPanel.gridx = 0;
		gbc_selectStationPanel.gridy = gridy;
		target.add(selectStationPanel, gbc_selectStationPanel);
		selectStationPanel.setLayout(new GridLayout(0, 2, 0, 0));
		selectStationPanel.setBorder(panelBorder);
		gridy++;

		JPanel departurePanel = new JPanel();
		departurePanel.setBackground(new Color(240, 255, 255));
		selectStationPanel.add(departurePanel);
		departurePanel.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel depPlaceLabel = new JLabel("출 발 역");
		depPlaceLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		depPlaceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		departurePanel.add(depPlaceLabel);
		depPlaceLabel.setBorder(titleBorder);

		int i = 0;
		if(type.equals("oneway"))
			i = 0;
		else 
			i = 1;
		
		depComboBox[i] = new AutoSuggestPanel();
		depComboBox[i].setBackground(Color.WHITE);
		departurePanel.add(depComboBox[i]);
		GridBagLayout gbl_depComboBox = new GridBagLayout();
		gbl_depComboBox.columnWidths = new int[] { 0 };
		gbl_depComboBox.rowHeights = new int[] { 0 };
		gbl_depComboBox.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_depComboBox.rowWeights = new double[] { Double.MIN_VALUE };
		depComboBox[i].setLayout(gbl_depComboBox);
		depComboBox[i].setBorder(titleBorder);

		JPanel arrivePanel = new JPanel();
		arrivePanel.setBackground(new Color(240, 255, 255));
		selectStationPanel.add(arrivePanel);
		arrivePanel.setLayout(new GridLayout(2, 1, 0, 0));

		JLabel arrPlaceLabel = new JLabel("도 착 역");
		arrPlaceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		arrPlaceLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		arrPlaceLabel.setBorder(titleBorder);
		arrivePanel.add(arrPlaceLabel);
		
		arrComboBox[i] = new AutoSuggestPanel();
		arrComboBox[i].setBackground(Color.WHITE);
		arrivePanel.add(arrComboBox[i]);
		GridBagLayout gbl_arrComboBox = new GridBagLayout();
		gbl_arrComboBox.columnWidths = new int[] { 0 };
		gbl_arrComboBox.rowHeights = new int[] { 0 };
		gbl_arrComboBox.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_arrComboBox.rowWeights = new double[] { Double.MIN_VALUE };
		arrComboBox[i].setLayout(gbl_arrComboBox);
		arrComboBox[i].setBorder(titleBorder);

		depComboBox[i].setDefault("서울");
		arrComboBox[i].setDefault("대전");

		SelectDatePanel selectDepDatePanel;
		if (type.equals("roundtrip"))
			selectDepDatePanel = new SelectDatePanel("daytogo");
		else
			selectDepDatePanel = new SelectDatePanel("oneway");

		GridBagConstraints gbc_selectDepDatePanel = new GridBagConstraints();
		gbc_selectDepDatePanel.fill = GridBagConstraints.BOTH;
		gbc_selectDepDatePanel.insets = new Insets(0, 0, 5, 0);
		gbc_selectDepDatePanel.gridx = 0;
		gbc_selectDepDatePanel.gridy = 1;
		target.add(selectDepDatePanel, gbc_selectDepDatePanel);
		gridy++;

		if (type.equals("roundtrip")) {
			// 도착일 Panel 추가해야함
			selectComingDayPanel = new SelectDatePanel("comingday");
			GridBagConstraints gbc_selectComingDayPanel = new GridBagConstraints();
			gbc_selectComingDayPanel.fill = GridBagConstraints.BOTH;
			gbc_selectComingDayPanel.insets = new Insets(0, 0, 5, 0);
			gbc_selectComingDayPanel.gridx = 0;
			gbc_selectComingDayPanel.gridy = gridy++;
			target.add(selectComingDayPanel, gbc_selectComingDayPanel);
			selectComingDayPanel.setBorder(panelBorder);
		}

		JPanel showHeadCntPanel = new JPanel();
		showHeadCntPanel.setBackground(Color.WHITE);
		GridBagConstraints gbc_showHeadCntPanel = new GridBagConstraints();
		gbc_showHeadCntPanel.fill = GridBagConstraints.BOTH;
		gbc_showHeadCntPanel.insets = new Insets(0, 0, 5, 0);
		gbc_showHeadCntPanel.gridx = 0;
		gbc_showHeadCntPanel.gridy = gridy;
		target.add(showHeadCntPanel, gbc_showHeadCntPanel);
		showHeadCntPanel.setBorder(panelBorder);
		gridy++;
		showHeadCntPanel.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel showCountDetailPanel = new JPanel();
		showCountDetailPanel.setBackground(new Color(240, 255, 255));
		showHeadCntPanel.add(showCountDetailPanel);
		GridBagLayout gbl_showCountDetailPanel = new GridBagLayout();
		gbl_showCountDetailPanel.columnWidths = new int[] { 813, 0 };
		gbl_showCountDetailPanel.rowHeights = new int[] { 40, 40, 0 };
		gbl_showCountDetailPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_showCountDetailPanel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		showCountDetailPanel.setLayout(gbl_showCountDetailPanel);

		JLabel headCountPanel = new JLabel("승객 인원");
		headCountPanel.setHorizontalAlignment(SwingConstants.CENTER);
		headCountPanel.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		headCountPanel.setBorder(titleBorder);
		GridBagConstraints gbc_headCountPanel = new GridBagConstraints();
		gbc_headCountPanel.fill = GridBagConstraints.BOTH;
		gbc_headCountPanel.insets = new Insets(0, 0, 0, 0);
		gbc_headCountPanel.gridx = 0;
		gbc_headCountPanel.gridy = 0;
		showCountDetailPanel.add(headCountPanel, gbc_headCountPanel);

		JPanel countDetailFractionPanel = new JPanel();
		countDetailFractionPanel.setBackground(Color.WHITE);
		countDetailFractionPanel.setBorder(titleBorder);
		GridBagConstraints gbc_countDetailFractionPanel = new GridBagConstraints();
		gbc_countDetailFractionPanel.fill = GridBagConstraints.BOTH;
		gbc_countDetailFractionPanel.gridx = 0;
		gbc_countDetailFractionPanel.gridy = 1;
		showCountDetailPanel.add(countDetailFractionPanel, gbc_countDetailFractionPanel);
		GridBagLayout gbl_countDetailFractionPanel = new GridBagLayout();
		gbl_countDetailFractionPanel.columnWidths = new int[] { 150, 160, 30, 160, 30, 160, 150, 0 };
		gbl_countDetailFractionPanel.rowHeights = new int[] { 24, 0 };
		gbl_countDetailFractionPanel.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gbl_countDetailFractionPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		countDetailFractionPanel.setLayout(gbl_countDetailFractionPanel);

		JLabel childCountLabel = new JLabel("어린이 0명");
		childCountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		childCountLabel.setFont(new Font("맑은 고딕", Font.BOLD, 28));
		GridBagConstraints gbc_childCountLabel = new GridBagConstraints();
		gbc_childCountLabel.anchor = GridBagConstraints.EAST;
		gbc_childCountLabel.fill = GridBagConstraints.VERTICAL;
		gbc_childCountLabel.insets = new Insets(0, 0, 0, 5);
		gbc_childCountLabel.gridx = 1;
		gbc_childCountLabel.gridy = 0;
		countDetailFractionPanel.add(childCountLabel, gbc_childCountLabel);

		JLabel adultCountLabel = new JLabel("어른 0명");
		adultCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
		adultCountLabel.setFont(new Font("맑은 고딕", Font.BOLD, 28));
		GridBagConstraints gbc_adultCountLabel = new GridBagConstraints();
		gbc_adultCountLabel.fill = GridBagConstraints.BOTH;
		gbc_adultCountLabel.insets = new Insets(0, 0, 0, 5);
		gbc_adultCountLabel.gridx = 3;
		gbc_adultCountLabel.gridy = 0;
		countDetailFractionPanel.add(adultCountLabel, gbc_adultCountLabel);

		JLabel seniorCountLabel = new JLabel("경로 0명");
		seniorCountLabel.setFont(new Font("맑은 고딕", Font.BOLD, 28));
		GridBagConstraints gbc_seniorCountLabel = new GridBagConstraints();
		gbc_seniorCountLabel.fill = GridBagConstraints.BOTH;
		gbc_seniorCountLabel.insets = new Insets(0, 0, 0, 5);
		gbc_seniorCountLabel.gridx = 5;
		gbc_seniorCountLabel.gridy = 0;
		countDetailFractionPanel.add(seniorCountLabel, gbc_seniorCountLabel);

		JPanel selectHeadCntPanel = new JPanel();
		selectHeadCntPanel.setBackground(Color.WHITE);
		GridBagConstraints gbc_selectHeadCntPaenl = new GridBagConstraints();
		gbc_selectHeadCntPaenl.fill = GridBagConstraints.BOTH;
		gbc_selectHeadCntPaenl.gridx = 0;
		gbc_selectHeadCntPaenl.gridy = gridy;
		target.add(selectHeadCntPanel, gbc_selectHeadCntPaenl);
		selectHeadCntPanel.setBorder(panelBorder);
		selectHeadCntPanel.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel selectDetailCountPanel = new JPanel();
		selectDetailCountPanel.setBackground(Color.WHITE);
		selectHeadCntPanel.add(selectDetailCountPanel);
		selectDetailCountPanel.setLayout(new GridLayout(0, 3, 0, 0));

		JPanel selectChildCountPanel = new JPanel();
		selectChildCountPanel.setBackground(new Color(240, 255, 255));
		selectDetailCountPanel.add(selectChildCountPanel);
		selectChildCountPanel.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel selectChildLabel = new JLabel("어린이");
		selectChildLabel.setHorizontalAlignment(SwingConstants.CENTER);
		selectChildLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		selectChildCountPanel.add(selectChildLabel);
		selectChildLabel.setBorder(titleBorder);

		JPanel selectChildCount = new JPanel();
		selectChildCount.setBackground(Color.WHITE);
		selectChildCountPanel.add(selectChildCount);

		JButton decreaseChildCntButton = new JButton("-");
		decreaseChildCntButton.setFont(new Font("굴림", Font.PLAIN, 14));
		decreaseChildCntButton.setBackground(Constants.BUTTON_COLOR);
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
				personnelArray[0] = Integer.parseInt(childCntTextField.getText());
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
		increaseChildCntButton.setFont(new Font("굴림", Font.PLAIN, 14));
		increaseChildCntButton.setBackground(Constants.BUTTON_COLOR);
		selectChildCount.add(increaseChildCntButton);
		increaseChildCntButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				increaseCount(childCntTextField);
			}
		});

		JPanel selectAdultCountPanel = new JPanel();
		selectAdultCountPanel.setBackground(new Color(240, 255, 255));
		selectDetailCountPanel.add(selectAdultCountPanel);
		selectAdultCountPanel.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel selectAdultLabel = new JLabel("어른");
		selectAdultLabel.setHorizontalAlignment(SwingConstants.CENTER);
		selectAdultLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		selectAdultLabel.setBorder(titleBorder);
		selectAdultCountPanel.add(selectAdultLabel);

		JPanel selectAdultCount = new JPanel();
		selectAdultCount.setBackground(Color.WHITE);
		selectAdultCountPanel.add(selectAdultCount);

		JButton decreaseAdultCntButton = new JButton("-");
		decreaseAdultCntButton.setFont(new Font("굴림", Font.PLAIN, 14));
		decreaseAdultCntButton.setBackground(Constants.BUTTON_COLOR);
		selectAdultCount.add(decreaseAdultCntButton);

		JTextField adultCntTextField = new JTextField("0");
		adultCntTextField.setHorizontalAlignment(SwingConstants.CENTER);
		adultCntTextField.setColumns(10);
		selectAdultCount.add(adultCntTextField);

		adultCntTextField.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {
				adultCountLabel.setText("어른 " + adultCntTextField.getText() + "명");
				personnelArray[1] = Integer.parseInt(adultCntTextField.getText());
				System.out.println("어른 : " + personnelArray[1]);
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
		increaseAdultCntButton.setFont(new Font("굴림", Font.PLAIN, 14));
		increaseAdultCntButton.setBackground(Constants.BUTTON_COLOR);
		selectAdultCount.add(increaseAdultCntButton);

		increaseAdultCntButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				increaseCount(adultCntTextField);
			}
		});

		JPanel selectSeniorCountPanel = new JPanel();
		selectSeniorCountPanel.setBackground(new Color(240, 255, 255));
		selectDetailCountPanel.add(selectSeniorCountPanel);
		selectSeniorCountPanel.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel selectSeniorLabel = new JLabel("경로");
		selectSeniorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		selectSeniorLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		selectSeniorLabel.setBorder(titleBorder);
		selectSeniorCountPanel.add(selectSeniorLabel);

		JPanel selectSeniorCount = new JPanel();
		selectSeniorCount.setBackground(Color.WHITE);
		selectSeniorCountPanel.add(selectSeniorCount);

		JButton decreaseSeniorCntButton = new JButton("-");
		decreaseSeniorCntButton.setFont(new Font("굴림", Font.PLAIN, 14));
		decreaseSeniorCntButton.setBackground(Constants.BUTTON_COLOR);
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
				personnelArray[2] = Integer.parseInt(seniorCntTextField.getText());
				System.out.println("경로 : " + personnelArray[2]);
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
		increaseSeniorCntButton.setFont(new Font("굴림", Font.PLAIN, 14));
		increaseSeniorCntButton.setBackground(Constants.BUTTON_COLOR);
		selectSeniorCount.add(increaseSeniorCntButton);

		increaseSeniorCntButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				increaseCount(seniorCntTextField);
			}
		});

	}

	private void initInquiryButton() {
		JFrame thisObj = this;
		JButton trainInquiryButton = new JButton("열차 조회");
		trainInquiryButton.setForeground(new Color(0, 0, 0));
		trainInquiryButton.setBackground(new Color(176, 224, 230));
		trainInquiryButton.setBorder(titleBorder);
		trainInquiryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String depText;
				String arrText;
				
				int selectedIndex = 0;
				if (reservTicketLayeredPane.getComponentCountInLayer(2) > 0) {
					System.out.println("편도");
					selectedIndex = 0;
					depText = depComboBox[0].getComboBoxText();
					arrText = arrComboBox[0].getComboBoxText();
				} else {
					System.out.println("왕복");
					selectedIndex = 1;
					depText = depComboBox[1].getComboBoxText();
					arrText = arrComboBox[1].getComboBoxText();
				}

				JPanel selectedTab = (JPanel) reservTicketLayeredPane
						.getComponentsInLayer(selectedIndex == 0 ? 2 : 1)[0];

				System.out.println("선택된 레이어 : " + selectedIndex);

				SelectDatePanel datePanel = (SelectDatePanel) selectedTab.getComponent(1);
				String dayToGo = datePanel.getTextFieldText("yearTextField")
						+ String.format("%02d", Integer.parseInt(datePanel.getTextFieldText("monthTextField")))
						+ String.format("%02d", Integer.parseInt(datePanel.getTextFieldText("dayTextField")));
				String dayComing = null;

				int personnel = (selectedIndex == 0) ? oneway_personnel : round_personnel;

				System.out.println("자식 수 : " + selectedTab.getComponentCount());

				if (selectedIndex == 1) {
					datePanel = (SelectDatePanel) selectedTab.getComponent(2);
					dayComing = datePanel.getTextFieldText("yearTextField")
							+ String.format("%02d", Integer.parseInt(datePanel.getTextFieldText("monthTextField")))
							+ String.format("%02d", Integer.parseInt(datePanel.getTextFieldText("dayTextField")));
				}

				// 도착지가 유효한가
				if (depText.equals(arrText)) 
				{
					showDialog("출발지와 도착지가 같습니다.");
				} else if (!TrainAPI.getInstance().getStationNames().contains(depText)
						|| !TrainAPI.getInstance().getStationNames().contains(arrText)) 
				{
					showDialog("출발지 혹은 도착지의 정보가 잘못됐습니다.");
				}
				// 날짜가 유효한가
				else if (dayToGo.compareTo(Constants.getTodayDateToString()) < 0
						|| (dayComing != null && dayComing.compareTo(dayToGo) < 0)) 
				{
					showDialog("<html><center>선택한 날짜가</center><center>유효하지 않습니다.</center></html>");
				}
				// 인원이 유효한가
				else if (personnel <= 0) 
				{
					showDialog("인원의 수가 0명입니다.");
				}
				// 정상 동작
				else 
				{
					Queue<ArrayList<TrainVo>> command = new LinkedList<ArrayList<TrainVo>>();
					command.add(TrainAPI.getInstance().getTrainList(depText, arrText, dayToGo));

					if (selectedIndex == 1) {
						command.add(TrainAPI.getInstance().getTrainList(arrText, depText, dayComing));
					}

					new TrainInquiryMenu(command, thisObj, personnelArray).setVisible(true);
					setVisible(false);
				}

			}
		});

		trainInquiryButton.setFont(new Font("맑은 고딕", Font.PLAIN, 36));
		reservTicketPanel.add(trainInquiryButton, BorderLayout.SOUTH);

	}

	private void initSeasonPanel() {
		seasonReservPanel = new JPanel();
		seasonReservPanel.setBackground(Color.WHITE);
		JLabel seasonReservLabel = new JLabel("<html>정기권 예매</html>");
		seasonReservLabel.setFont(tabFont);
		ticketingTabbedPane.add(seasonReservPanel);
		ticketingTabbedPane.setTabComponentAt(1, seasonReservLabel);
		seasonReservPanel.setLayout(new BorderLayout(0, 0));

		JPanel inputSeasonReservPanel = new JPanel();
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 122, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		inputSeasonReservPanel.setLayout(gbl_panel);
		inputSeasonReservPanel.setBackground(new Color(0x77, 0x88, 0x99));
		seasonReservPanel.add(inputSeasonReservPanel);

		JPanel selectStationSeasonPanel = new JPanel();
		selectStationSeasonPanel.setBackground(new Color(240, 255, 255));
		GridBagConstraints gbc_selectStationSeasonPanel = new GridBagConstraints();
		gbc_selectStationSeasonPanel.insets = new Insets(0, 0, 5, 0);
		gbc_selectStationSeasonPanel.fill = GridBagConstraints.BOTH;
		gbc_selectStationSeasonPanel.gridx = 0;
		gbc_selectStationSeasonPanel.gridy = 0;
		inputSeasonReservPanel.add(selectStationSeasonPanel, gbc_selectStationSeasonPanel);
		selectStationSeasonPanel.setLayout(new GridLayout(0, 2, 0, 0));
		selectStationSeasonPanel.setBorder(panelBorder);

		JLabel seasonDepPlace = new JLabel("출발역");
		seasonDepPlace.setHorizontalAlignment(SwingConstants.CENTER);
		seasonDepPlace.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		seasonDepPlace.setBorder(titleBorder);
		selectStationSeasonPanel.add(seasonDepPlace);

		JLabel seasonArrPlace = new JLabel("도착역");
		seasonArrPlace.setHorizontalAlignment(SwingConstants.CENTER);
		seasonArrPlace.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		seasonArrPlace.setBorder(titleBorder);
		selectStationSeasonPanel.add(seasonArrPlace);
		ticketingTabbedPane.setTitleAt(1, "정기권  예매");

		AutoSuggestPanel seasonDepCombo = new AutoSuggestPanel();
		seasonDepCombo.setBackground(Color.WHITE);
		AutoSuggestPanel seasonArrCombo = new AutoSuggestPanel();
		seasonArrCombo.setBackground(Color.WHITE);
		seasonDepCombo.setDefault("서울");
		seasonArrCombo.setDefault("대전");

		selectStationSeasonPanel.add(seasonDepCombo);
		selectStationSeasonPanel.add(seasonArrCombo);

		JPanel selectDateSeasonPanel = new JPanel();
		GridBagConstraints gbc_selectDateSeasonPanel = new GridBagConstraints();
		gbc_selectDateSeasonPanel.insets = new Insets(0, 0, 5, 0);
		gbc_selectDateSeasonPanel.fill = GridBagConstraints.BOTH;
		gbc_selectDateSeasonPanel.gridx = 0;
		gbc_selectDateSeasonPanel.gridy = 1;
		inputSeasonReservPanel.add(selectDateSeasonPanel, gbc_selectDateSeasonPanel);
		GridBagLayout gbl_selectDateSeasonPanel = new GridBagLayout();
		gbl_selectDateSeasonPanel.columnWidths = new int[] { 829, 0 };
		gbl_selectDateSeasonPanel.rowHeights = new int[] { 89, 30, 0 };
		gbl_selectDateSeasonPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_selectDateSeasonPanel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		selectDateSeasonPanel.setLayout(gbl_selectDateSeasonPanel);
		selectDateSeasonPanel.setBorder(panelBorder);

		JPanel selectDetailDateSeasonPanel = new SelectDatePanel("season");
		GridBagConstraints gbc_selectDetailDateSeasonPanel = new GridBagConstraints();
		gbc_selectDetailDateSeasonPanel.fill = GridBagConstraints.BOTH;
		gbc_selectDetailDateSeasonPanel.insets = new Insets(0, 0, 0, 0);
		gbc_selectDetailDateSeasonPanel.gridx = 0;
		gbc_selectDetailDateSeasonPanel.gridy = 0;
		selectDateSeasonPanel.add(selectDetailDateSeasonPanel, gbc_selectDetailDateSeasonPanel);
		selectDetailDateSeasonPanel.setBorder(titleBorder);

		JPanel selectTermSeasonPanel = new JPanel();
		selectTermSeasonPanel.setBackground(Color.WHITE);
		GridBagConstraints gbc_selectTermSeasonPanel = new GridBagConstraints();
		gbc_selectTermSeasonPanel.fill = GridBagConstraints.BOTH;
		gbc_selectTermSeasonPanel.gridx = 0;
		gbc_selectTermSeasonPanel.gridy = 1;
		selectDateSeasonPanel.add(selectTermSeasonPanel, gbc_selectTermSeasonPanel);
		selectTermSeasonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		selectTermSeasonPanel.setBorder(titleBorder);

		Vector<Integer> termArray = new Vector<Integer>();
		termArray.add(new Integer(10));
		termArray.add(new Integer(30));
		JComboBox<Integer> termComboBox = new JComboBox<Integer>(termArray);
		selectTermSeasonPanel.add(termComboBox);

		JLabel termLabel = new JLabel("일 사용");
		termLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		selectTermSeasonPanel.add(termLabel);

		JPanel passengerSeasonPanel = new JPanel();
		passengerSeasonPanel.setBackground(Color.WHITE);
		GridBagConstraints gbc_passengerSeasonPanel = new GridBagConstraints();
		gbc_passengerSeasonPanel.fill = GridBagConstraints.BOTH;
		gbc_passengerSeasonPanel.gridx = 0;
		gbc_passengerSeasonPanel.gridy = 2;
		inputSeasonReservPanel.add(passengerSeasonPanel, gbc_passengerSeasonPanel);
		GridBagLayout gbl_passengerSeasonPanel = new GridBagLayout();
		gbl_passengerSeasonPanel.columnWidths = new int[] { 0, 0 };
		gbl_passengerSeasonPanel.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_passengerSeasonPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_passengerSeasonPanel.rowWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		passengerSeasonPanel.setLayout(gbl_passengerSeasonPanel);
		passengerSeasonPanel.setBorder(panelBorder);

		JPanel passengerInfoPanel = new JPanel();
		GridBagConstraints gbc_passengerInfoPanel = new GridBagConstraints();
		gbc_passengerInfoPanel.fill = GridBagConstraints.BOTH;
		gbc_passengerInfoPanel.gridx = 0;
		gbc_passengerInfoPanel.gridy = 0;
		passengerSeasonPanel.add(passengerInfoPanel, gbc_passengerInfoPanel);
		passengerInfoPanel.setBackground(new Color(240, 255, 255));
		passengerInfoPanel.setBorder(titleBorder);

		JLabel passengerInfoLabel = new JLabel("승객 인원");
		passengerInfoLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		passengerInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		passengerInfoPanel.add(passengerInfoLabel);

		JLabel passengerTypeLabel = new JLabel("성인 1명");
		passengerTypeLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 24));
		GridBagConstraints gbc_passengerTypeLabel = new GridBagConstraints();
		gbc_passengerTypeLabel.insets = new Insets(0, 0, 5, 0);
		gbc_passengerTypeLabel.gridx = 0;
		gbc_passengerTypeLabel.gridy = 1;
		passengerSeasonPanel.add(passengerTypeLabel, gbc_passengerTypeLabel);

		JButton reservSeasonButton = new JButton("정기권 예매");
		reservSeasonButton.setFont(new Font("맑은 고딕", Font.PLAIN, 28));
		seasonReservPanel.add(reservSeasonButton, BorderLayout.SOUTH);

		reservSeasonButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (seasonDepCombo.getComboBoxText().equals(seasonArrCombo.getComboBoxText())) {
					showDialog("출발지와 도착지가 같습니다.");
				} else if (!TrainAPI.getInstance().getStationNames().contains(seasonDepCombo.getComboBoxText())
						|| !TrainAPI.getInstance().getStationNames().contains(seasonArrCombo.getComboBoxText())) {
					showDialog("출발지 혹은 도착지의 정보가 잘못됐습니다.");
				} else {
					String expiration = Constants.getAddedDate(Calendar.DAY_OF_MONTH,
							(Integer) termComboBox.getSelectedItem());

					SeasonTicketVo seasonVo = new SeasonTicketVo(dao).setDepplandPlace(seasonDepCombo.getComboBoxText())
							.setArrplandPlace(seasonArrCombo.getComboBoxText())
							.setTerm((Integer) termComboBox.getSelectedItem())
							.setEffDate(Constants.getTodayTimeToString()).setExpDate(expiration);

					reservationSeasonTicket(seasonVo);
				}

				updateTicketList(SeasonTicketVo.getInstance());
			}
		});
	}

	public void reservationSeasonTicket(SeasonTicketVo seasonVo) {
		addSeasonReservation(seasonVo.getSeason_id());
		if (dao.insertSeasonData(seasonVo))
			showDialog("정기권이 예매되었습니다.");
		else
			showDialog("정기권 예매에 실패하였습니다.");
	}

	private void setWelcoming() {
		welcomeLabel.setText("<html>안녕하세요.<br><center>" + userVo.getName() + "님</center></html>");
	}

	private void increaseCount(JTextField tf) {
		int selectedIndex = getSelectedPanel();

		if (selectedIndex == 0) {
			if (oneway_personnel >= 9)
				return;
			oneway_personnel++;
		} else {
			if (round_personnel >= 9)
				return;
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
		int selectedIndex = getSelectedPanel();

		if (selectedIndex == 0) {
			if (oneway_personnel <= 0)
				return;
			oneway_personnel--;
		} else {
			if (round_personnel <= 0)
				return;
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

	private int getSelectedPanel() {
		int selectedIndex;
		if (reservTicketLayeredPane.getComponentCountInLayer(2) > 0)
			selectedIndex = 0;
		else
			selectedIndex = 1;

		return selectedIndex;
	}

	private void showDialog(String msg) {
		@SuppressWarnings("unused")
		NoticeDialog nd = new NoticeDialog(msg, this);
	}
}
