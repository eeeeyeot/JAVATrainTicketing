package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
//import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
//import java.awt.Image;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

//import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import database.TrainDAO;
import database.UserVo;
import openAPI.TrainAPI;
import openAPI.TrainVo;
import util.ScreenUtil;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

@SuppressWarnings("serial")
public class MainMenu extends JFrame {

	private UserVo userVo;
	private JPanel contentPane;
	
	public MainMenu() {
		setTitle("기차 예매 프로그램");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		setResizable(false);

		this.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e)
			{
				UpdateTicket();
			}
		});
		
		JPanel northPanel = new JPanel();
		contentPane.add(northPanel, BorderLayout.NORTH);
		GridBagLayout gbl_northPanel = new GridBagLayout();
		gbl_northPanel.columnWidths = new int[] { 266, 476, 0 };
		gbl_northPanel.rowHeights = new int[] { 121, 0 };
		gbl_northPanel.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_northPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
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
		//northPanel.add(imagePanel, gbc_imagePanel);

		JLabel korailLabel = new JLabel("Korail");
		korailLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 40));
		korailLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_korailLabel = new GridBagConstraints();
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

		// #####################################################
		JPanel reservTicketPanel = new JPanel();
		JLabel reservTicketLabel = new JLabel("<html>승차권 예매</html>");
		reservTicketLabel.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		ticketingTabbedPane.add(reservTicketPanel);
		ticketingTabbedPane.setTitleAt(0, "승차권                예매");
		ticketingTabbedPane.setTabComponentAt(0, reservTicketLabel);
		reservTicketPanel.setLayout(new BorderLayout(0, 0));

		JTabbedPane reservTicketTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		reservTicketPanel.add(reservTicketTabbedPane);

		JPanel onewayPanel = new JPanel();
		JLabel onewayLabel = new JLabel("편도");
		onewayLabel.setPreferredSize(new Dimension(380, 30));
		reservTicketTabbedPane.add(onewayPanel);
		// reservTicketTabbedPane.setTitleAt(0, "편도");
		reservTicketTabbedPane.setTabComponentAt(0, onewayLabel);
		GridBagLayout gbl_onewayPanel = new GridBagLayout();
		gbl_onewayPanel.columnWidths = new int[] { 823, 0 };
		gbl_onewayPanel.rowHeights = new int[] { 90, 90, 90, 90, 0, 0 };
		gbl_onewayPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_onewayPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		onewayPanel.setLayout(gbl_onewayPanel);

		JPanel selectStationPanel = new JPanel();
		GridBagConstraints gbc_selectStationPanel = new GridBagConstraints();
		gbc_selectStationPanel.fill = GridBagConstraints.BOTH;
		gbc_selectStationPanel.insets = new Insets(0, 0, 5, 0);
		gbc_selectStationPanel.gridx = 0;
		gbc_selectStationPanel.gridy = 0;
		onewayPanel.add(selectStationPanel, gbc_selectStationPanel);
		selectStationPanel.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel departurePanel = new JPanel();
		selectStationPanel.add(departurePanel);
		departurePanel.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel depPlaceLabel = new JLabel("출 발 역");
		depPlaceLabel.setFont(new Font("굴림", Font.PLAIN, 20));
		depPlaceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		departurePanel.add(depPlaceLabel);

		AutoSuggest depComboBox = new AutoSuggest();
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

		JLabel arrPlaceLabel = new JLabel("도 착 역");
		arrPlaceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		arrPlaceLabel.setFont(new Font("굴림", Font.PLAIN, 20));
		arrivePanel.add(arrPlaceLabel);
		AutoSuggest arrComboBox = new AutoSuggest();
		arrivePanel.add(arrComboBox);
		GridBagLayout gbl_arrComboBox = new GridBagLayout();
		gbl_arrComboBox.columnWidths = new int[] { 0 };
		gbl_arrComboBox.rowHeights = new int[] { 0 };
		gbl_arrComboBox.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_arrComboBox.rowWeights = new double[] { Double.MIN_VALUE };
		arrComboBox.setLayout(gbl_arrComboBox);
		
		depComboBox.setDefault("서울");
		arrComboBox.setDefault("대전");

		JPanel selectDepDatePanel = new JPanel();
		GridBagConstraints gbc_selectDepDatePanel = new GridBagConstraints();
		gbc_selectDepDatePanel.fill = GridBagConstraints.VERTICAL;
		gbc_selectDepDatePanel.insets = new Insets(0, 0, 5, 0);
		gbc_selectDepDatePanel.gridx = 0;
		gbc_selectDepDatePanel.gridy = 1;
		onewayPanel.add(selectDepDatePanel, gbc_selectDepDatePanel);
		selectDepDatePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel depDateLabel = new JLabel("출 발 일");
		depDateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		depDateLabel.setFont(new Font("굴림", Font.PLAIN, 21));
		selectDepDatePanel.add(depDateLabel);

		JPanel detailDatePanel = new JPanel();
		selectDepDatePanel.add(detailDatePanel);
		detailDatePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel yearSelectPanel = new JPanel();
		detailDatePanel.add(yearSelectPanel);
		GridBagLayout gbl_yearSelectPanel = new GridBagLayout();
		gbl_yearSelectPanel.columnWidths = new int[] { 30, 39, 105, 39, 30, 0 };
		gbl_yearSelectPanel.rowHeights = new int[] { 40, 0 };
		gbl_yearSelectPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_yearSelectPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		yearSelectPanel.setLayout(gbl_yearSelectPanel);

		JButton decreaseYearButton = new JButton("-");

		GridBagConstraints gbc_decreaseYearButton = new GridBagConstraints();
		gbc_decreaseYearButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_decreaseYearButton.insets = new Insets(0, 0, 0, 5);
		gbc_decreaseYearButton.gridx = 1;
		gbc_decreaseYearButton.gridy = 0;
		yearSelectPanel.add(decreaseYearButton, gbc_decreaseYearButton);

		JTextField yearTextField = new JTextField();
		yearTextField.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_TextField = new GridBagConstraints();
		gbc_TextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_TextField.insets = new Insets(0, 0, 0, 5);
		gbc_TextField.gridx = 2;
		gbc_TextField.gridy = 0;
		yearSelectPanel.add(yearTextField, gbc_TextField);
		yearTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				numberFormatLimit(e, 4);
			}
		});

		JButton increaseYearButton = new JButton("+");
		GridBagConstraints gbc_increaseYearButton = new GridBagConstraints();
		gbc_increaseYearButton.insets = new Insets(0, 0, 0, 5);
		gbc_increaseYearButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_increaseYearButton.gridx = 3;
		gbc_increaseYearButton.gridy = 0;
		yearSelectPanel.add(increaseYearButton, gbc_increaseYearButton);

		JLabel yearLabel = new JLabel("년");
		GridBagConstraints gbc_yearLabel = new GridBagConstraints();
		gbc_yearLabel.gridx = 4;
		gbc_yearLabel.gridy = 0;
		yearSelectPanel.add(yearLabel, gbc_yearLabel);

		JPanel monthSelectPanel = new JPanel();
		detailDatePanel.add(monthSelectPanel);
		GridBagLayout gbl_monthSelectPanel = new GridBagLayout();
		gbl_monthSelectPanel.columnWidths = new int[] { 30, 39, 105, 39, 30, 0 };
		gbl_monthSelectPanel.rowHeights = new int[] { 40, 0 };
		gbl_monthSelectPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_monthSelectPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		monthSelectPanel.setLayout(gbl_monthSelectPanel);

		JButton decreaseMonthButton = new JButton("-");
		GridBagConstraints gbc_decreaseMonthButton = new GridBagConstraints();
		gbc_decreaseMonthButton.anchor = GridBagConstraints.WEST;
		gbc_decreaseMonthButton.insets = new Insets(0, 0, 0, 5);
		gbc_decreaseMonthButton.gridx = 1;
		gbc_decreaseMonthButton.gridy = 0;
		monthSelectPanel.add(decreaseMonthButton, gbc_decreaseMonthButton);

		JTextField monthTextField = new JTextField();
		monthTextField.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_TextField_1 = new GridBagConstraints();
		gbc_TextField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_TextField_1.insets = new Insets(0, 0, 0, 5);
		gbc_TextField_1.gridx = 2;
		gbc_TextField_1.gridy = 0;
		monthSelectPanel.add(monthTextField, gbc_TextField_1);

		JButton increaseMonthButton = new JButton("+");
		GridBagConstraints gbc_increaseMonthButton = new GridBagConstraints();
		gbc_increaseMonthButton.insets = new Insets(0, 0, 0, 5);
		gbc_increaseMonthButton.anchor = GridBagConstraints.WEST;
		gbc_increaseMonthButton.gridx = 3;
		gbc_increaseMonthButton.gridy = 0;
		monthSelectPanel.add(increaseMonthButton, gbc_increaseMonthButton);

		JLabel monthLabel = new JLabel("월");
		GridBagConstraints gbc_monthLabel = new GridBagConstraints();
		gbc_monthLabel.gridx = 4;
		gbc_monthLabel.gridy = 0;
		monthSelectPanel.add(monthLabel, gbc_monthLabel);

		JPanel daySelectPanel = new JPanel();
		detailDatePanel.add(daySelectPanel);
		GridBagLayout gbl_daySelectPanel = new GridBagLayout();
		gbl_daySelectPanel.columnWidths = new int[] { 30, 39, 105, 39, 30, 0 };
		gbl_daySelectPanel.rowHeights = new int[] { 40, 0 };
		gbl_daySelectPanel.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_daySelectPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		daySelectPanel.setLayout(gbl_daySelectPanel);

		JButton decreaseDayButton = new JButton("-");
		GridBagConstraints gbc_decreaseDayButton = new GridBagConstraints();
		gbc_decreaseDayButton.anchor = GridBagConstraints.WEST;
		gbc_decreaseDayButton.insets = new Insets(0, 0, 0, 5);
		gbc_decreaseDayButton.gridx = 1;
		gbc_decreaseDayButton.gridy = 0;
		daySelectPanel.add(decreaseDayButton, gbc_decreaseDayButton);

		JTextField dayTextField = new JTextField();
		dayTextField.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_TextField_2 = new GridBagConstraints();
		gbc_TextField_2.insets = new Insets(0, 0, 0, 5);
		gbc_TextField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_TextField_2.gridx = 2;
		gbc_TextField_2.gridy = 0;
		daySelectPanel.add(dayTextField, gbc_TextField_2);

		monthTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				numberFormatLimit(e, 2);
			}

			public void keyReleased(KeyEvent e) {
				setValidValue((JTextField) e.getSource(), yearTextField.getText(), monthTextField.getText(), false);
			}
		});

		dayTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				numberFormatLimit(e, 2);
			}

			public void keyReleased(KeyEvent e) {
				setValidValue((JTextField) e.getSource(), yearTextField.getText(), monthTextField.getText(), true);
			}
		});

		initializeDate(yearTextField, monthTextField, dayTextField);

		JButton increaseDayButton = new JButton("+");
		GridBagConstraints gbc_increaseDayButton = new GridBagConstraints();
		gbc_increaseDayButton.insets = new Insets(0, 0, 0, 5);
		gbc_increaseDayButton.anchor = GridBagConstraints.WEST;
		gbc_increaseDayButton.gridx = 3;
		gbc_increaseDayButton.gridy = 0;
		daySelectPanel.add(increaseDayButton, gbc_increaseDayButton);

		decreaseDayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				decreaseDate(dayTextField);
			}
		});
		decreaseMonthButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				decreaseDate(monthTextField);
			}
		});
		decreaseYearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				decreaseDate(yearTextField);
			}
		});

		increaseDayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				increaseDate(dayTextField, yearTextField.getText(), monthTextField.getText(), true);
			}
		});
		increaseMonthButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				increaseDate(monthTextField, yearTextField.getText(), monthTextField.getText(), false);
			}
		});
		increaseYearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				increaseDate(yearTextField, yearTextField.getText(), null, false);
			}
		});

		JLabel lblNewLabel_1 = new JLabel("일");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.gridx = 4;
		gbc_lblNewLabel_1.gridy = 0;
		daySelectPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);

		JPanel showHeadCntPanel = new JPanel();
		GridBagConstraints gbc_showHeadCntPanel = new GridBagConstraints();
		gbc_showHeadCntPanel.fill = GridBagConstraints.BOTH;
		gbc_showHeadCntPanel.insets = new Insets(0, 0, 5, 0);
		gbc_showHeadCntPanel.gridx = 0;
		gbc_showHeadCntPanel.gridy = 2;
		onewayPanel.add(showHeadCntPanel, gbc_showHeadCntPanel);

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

		JPanel selectHeadCntPaenl = new JPanel();
		GridBagConstraints gbc_selectHeadCntPaenl = new GridBagConstraints();
		gbc_selectHeadCntPaenl.insets = new Insets(0, 0, 5, 0);
		gbc_selectHeadCntPaenl.fill = GridBagConstraints.BOTH;
		gbc_selectHeadCntPaenl.gridx = 0;
		gbc_selectHeadCntPaenl.gridy = 3;
		onewayPanel.add(selectHeadCntPaenl, gbc_selectHeadCntPaenl);
		selectHeadCntPaenl.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel selectDetailCountPanel = new JPanel();
		selectHeadCntPaenl.add(selectDetailCountPanel);
		selectDetailCountPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel selectChildCountPanel = new JPanel();
		selectDetailCountPanel.add(selectChildCountPanel);
		selectChildCountPanel.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel selectChildLabel = new JLabel("어린이");
		selectChildLabel.setHorizontalAlignment(SwingConstants.CENTER);
		selectChildLabel.setFont(new Font("굴림", Font.PLAIN, 20));
		selectChildCountPanel.add(selectChildLabel);

		JPanel panel = new JPanel();
		selectChildCountPanel.add(panel);

		JButton decreaseChildCntButton = new JButton("-");
		panel.add(decreaseChildCntButton);

		JTextField childCntTextField = new JTextField("0");
		childCntTextField.setHorizontalAlignment(SwingConstants.CENTER);
		childCntTextField.setColumns(10);
		panel.add(childCntTextField);

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
				numberFormatLimit(e, 1);

			}

			public void keyReleased(KeyEvent e) {
				setNumberLimit((JTextField) e.getSource());
			}
		});

		JButton increaseChildCntButton = new JButton("+");
		panel.add(increaseChildCntButton);
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

		JPanel panel_1 = new JPanel();
		selectAdultCountPanel.add(panel_1);

		JButton decreaseAdultCntButton = new JButton("-");
		panel_1.add(decreaseAdultCntButton);

		JTextField adultCntTextField = new JTextField("0");
		adultCntTextField.setHorizontalAlignment(SwingConstants.CENTER);
		adultCntTextField.setColumns(10);
		panel_1.add(adultCntTextField);

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
				numberFormatLimit(e, 1);
			}
		});

		decreaseAdultCntButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				decreaseCount(adultCntTextField);
			}
		});

		JButton increaseAdultCntButton = new JButton("+");
		panel_1.add(increaseAdultCntButton);
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

		JPanel panel_2 = new JPanel();
		selectSeniorCountPanel.add(panel_2);

		JButton decreaseSeniorCntButton = new JButton("-");
		panel_2.add(decreaseSeniorCntButton);

		JTextField seniorCntTextField = new JTextField("0");
		seniorCntTextField.setHorizontalAlignment(SwingConstants.CENTER);
		seniorCntTextField.setColumns(10);
		panel_2.add(seniorCntTextField);

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
				numberFormatLimit(e, 1);
			}
		});

		JButton increaseSeniorCntButton = new JButton("+");
		panel_2.add(increaseSeniorCntButton);

		increaseSeniorCntButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				increaseCount(seniorCntTextField);
			}
		});
		
		JPanel roundTripPanel = new JPanel();
		JLabel roundTripLabel = new JLabel("왕복");
		roundTripLabel.setPreferredSize(new Dimension(380, 30));
		reservTicketTabbedPane.add(roundTripPanel);
		// reservTicketTabbedPane.setTitleAt(1, "왕복");

		JFrame thisObj = this;
		
		JButton reservationButton = new JButton("열차 조회");
		reservationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int personnel = Integer.parseInt(childCntTextField.getText()) + Integer.parseInt(adultCntTextField.getText()) + Integer.parseInt(seniorCntTextField.getText());
				if(personnel > 0) { 
					String depText = depComboBox.getComboBoxText();
					String arrText = arrComboBox.getComboBoxText();
					String date = yearTextField.getText() + monthTextField.getText() + dayTextField.getText();
					ArrayList<TrainVo> list = TrainAPI.getInstance().getTrainList(depText, arrText, date);
					new TrainInquiry(list, thisObj, personnel).setVisible(true);
					setVisible(false);
				}else
				{
					String msg = "인원의 수가 0명입니다.";
					showDialog(msg);
				}
			}
		});
		reservationButton.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		reservTicketPanel.add(reservationButton, BorderLayout.SOUTH);
		reservTicketTabbedPane.setTabComponentAt(1, roundTripLabel);
		// #####################################################

		// #####################################################
		JPanel seasonTicketPanel = new JPanel();
		JLabel seasonTicketLabel = new JLabel("<html>정기권 예매</html>");
		seasonTicketLabel.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		ticketingTabbedPane.add(seasonTicketPanel);
		ticketingTabbedPane.setTabComponentAt(1, seasonTicketLabel);
		// #####################################################

		// #####################################################
		JPanel ticketConfirmPanel = new JPanel();
		JLabel ticketConfirmLabel = new JLabel("<html>승차권  확인</html>");
		ticketConfirmLabel.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		ticketingTabbedPane.add(ticketConfirmPanel);
		ticketingTabbedPane.setTabComponentAt(2, ticketConfirmLabel);
		// #####################################################
		setLocation(ScreenUtil.getCenterPosition(this));
	}

	public MainMenu(UserVo userVo) {
		this();
		this.userVo = userVo;
	}
	
	public void addReservation(String ticketId) {
		//add reservation information into DB
		TrainDAO dao = TrainDAO.getInstance();
		dao.insertReservationData(userVo.getId(), ticketId);
	}
	
	private void numberFormatLimit(KeyEvent e, int limit) {
		char c = e.getKeyChar();
		if (!Character.isDigit(c)) {
			e.consume();
			return;
		}

		if (((JTextField) e.getSource()).getText().length() > limit - 1) {
			e.consume();
			return;
		}
	}

	private void initializeDate(JTextField year, JTextField month, JTextField day) {
		Calendar cal = Calendar.getInstance();

		year.setText(String.format("%d", cal.get(Calendar.YEAR)));
		month.setText(String.format("%d", cal.get(Calendar.MONTH) + 1));
		day.setText(String.format("%d", cal.get(Calendar.DAY_OF_MONTH)));
	}

	private void decreaseDate(JTextField tf) {
		int value = Integer.parseInt(tf.getText());
		value--;
		if (value < 1) {
			value = 1;
		}
		tf.setText(String.format("%d", value));
	}

	private void increaseDate(JTextField tf, String year, String month, boolean isDay) {
		int value = Integer.parseInt(tf.getText());
		value++;

		if (month != null) {
			int maxDay = getMaximumDay(year, month);
			if (value > maxDay) {
				value = maxDay;
			}
			tf.setText(String.format("%d", value));
			setValidValue(tf, year, month, isDay);
		} else
			tf.setText(String.format("%d", value));
	}

	private void setValidValue(JTextField tf, String year, String month, boolean isDay) {
		if (tf.getText().equals(""))
			return;

		int textInt = Integer.parseInt(tf.getText());
		// dayTextField일 경우
		if (isDay) {
			System.out.println(month);
			int maxDay = getMaximumDay(year, month);
			if (textInt > maxDay)
				tf.setText(String.format("%d", maxDay));
			else if (textInt < 1)
				tf.setText("1");
		}
		// monthTextField일 경우
		else {
			if (textInt > 12)
				tf.setText("12");
			else if (textInt < 1)
				tf.setText("1");
		}

	}

	private int getMaximumDay(String year, String month) {
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMM");
		Date date = null;
		try {
			date = fm.parse(year + String.format("%02d", Integer.parseInt(month)));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int corDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		return corDay;
	}

	private void increaseCount(JTextField tf) {
		setNumberLimit(tf);
		int tmp = Integer.parseInt(tf.getText());
		tmp++;
		if (tmp > 9)
			tmp = 9;
		tf.setText(String.format("%d", tmp));
	}

	private void decreaseCount(JTextField tf) {
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
	
	private void UpdateTicket() 
	{
		@SuppressWarnings("unused")
		TrainDAO dao = TrainDAO.getInstance();
		
		//TicketInformation 승차권확인 탭에 추가
	}
}
