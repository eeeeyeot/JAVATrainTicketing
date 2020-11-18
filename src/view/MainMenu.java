package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.NumberFormatter;

import util.ScreenUtil;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class MainMenu extends JFrame {

	private JPanel contentPane;
	
	public static void main(String[] args) {
		new MainMenu().setVisible(true);
	}
	
	public MainMenu() {
		setLocation(ScreenUtil.getCenterPosition(this));
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
		gbl_northPanel.columnWidths = new int[]{266, 476, 0};
		gbl_northPanel.rowHeights = new int[]{121, 0};
		gbl_northPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_northPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		northPanel.setLayout(gbl_northPanel);
		
		JPanel imagePanel = new JPanel() {
			Image icon = new ImageIcon(MainMenu.class.getResource("../image/test.png")).getImage();
			public void paint(Graphics g) {
				g.drawImage(icon, 0, 0, null);
			}
		};
		GridBagConstraints gbc_imagePanel = new GridBagConstraints();
		gbc_imagePanel.fill = GridBagConstraints.BOTH;
		gbc_imagePanel.insets = new Insets(0, 0, 0, 5);
		gbc_imagePanel.gridx = 0;
		gbc_imagePanel.gridy = 0;
		northPanel.add(imagePanel, gbc_imagePanel);
		
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
		
		//#####################################################
		JPanel reservTicketPanel = new JPanel();
		JLabel reservTicketLabel = new JLabel("<html>승차권 예매</html>");
		reservTicketLabel.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		ticketingTabbedPane.add(reservTicketPanel);
		ticketingTabbedPane.setTabComponentAt(0, reservTicketLabel);
		reservTicketPanel.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane reservTicketTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		reservTicketPanel.add(reservTicketTabbedPane);
		
		JPanel onewayPanel = new JPanel();
		JLabel onewayLabel = new JLabel("편도");
		onewayLabel.setPreferredSize(new Dimension(reservTicketTabbedPane.getWidth() / 2 - 93, 30));
		reservTicketTabbedPane.add(onewayPanel);
		reservTicketTabbedPane.setTitleAt(0, "편도");
		reservTicketTabbedPane.setTabComponentAt(0, onewayLabel);
		GridBagLayout gbl_onewayPanel = new GridBagLayout();
		gbl_onewayPanel.columnWidths = new int[]{948, 0};
		gbl_onewayPanel.rowHeights = new int[]{90, 90, 90, 90, 0};
		gbl_onewayPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_onewayPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		gbl_depComboBox.columnWidths = new int[]{0};
		gbl_depComboBox.rowHeights = new int[]{0};
		gbl_depComboBox.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_depComboBox.rowWeights = new double[]{Double.MIN_VALUE};
		depComboBox.setLayout(gbl_depComboBox);
		
		
		JPanel arrivePanel = new JPanel();
		selectStationPanel.add(arrivePanel);
		arrivePanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		
		JLabel arrPlaceLabel = new JLabel("도 착 역");
		arrPlaceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		arrPlaceLabel.setFont(new Font("굴림", Font.PLAIN, 20));
		arrivePanel.add(arrPlaceLabel);
		AutoSuggest arrComboBox = new AutoSuggest();
		arrivePanel.add(arrComboBox);
		GridBagLayout gbl_arrComboBox = new GridBagLayout();
		gbl_arrComboBox.columnWidths = new int[]{0};
		gbl_arrComboBox.rowHeights = new int[]{0};
		gbl_arrComboBox.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_arrComboBox.rowWeights = new double[]{Double.MIN_VALUE};
		arrComboBox.setLayout(gbl_arrComboBox);
		
		JPanel selectDepDatePanel = new JPanel();
		GridBagConstraints gbc_selectDepDatePanel = new GridBagConstraints();
		gbc_selectDepDatePanel.fill = GridBagConstraints.BOTH;
		gbc_selectDepDatePanel.insets = new Insets(0, 0, 5, 0);
		gbc_selectDepDatePanel.gridx = 0;
		gbc_selectDepDatePanel.gridy = 1;
		onewayPanel.add(selectDepDatePanel, gbc_selectDepDatePanel);
		selectDepDatePanel.setLayout(new GridLayout(0, 1));
		
		JLabel depDateLabel = new JLabel("출 발 일");
		depDateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		depDateLabel.setFont(new Font("굴림", Font.PLAIN, 21));
		selectDepDatePanel.add(depDateLabel);
		
		JPanel detailDatePanel = new JPanel();
		selectDepDatePanel.add(detailDatePanel);
		detailDatePanel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel yearSelectPanel = new JPanel();
		detailDatePanel.add(yearSelectPanel);
		GridBagLayout gbl_yearSelectPanel = new GridBagLayout();
		gbl_yearSelectPanel.columnWidths = new int[] {63, 39, 105, 39, 63, 0};
		gbl_yearSelectPanel.rowHeights = new int[] {40, 0};
		gbl_yearSelectPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_yearSelectPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		yearSelectPanel.setLayout(gbl_yearSelectPanel);
		
		JButton decreaseYearButton = new JButton("-");
		decreaseYearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				e.getClass().getName();
			}
		});
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
			public void keyReleased(KeyEvent e) {
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
		gbl_monthSelectPanel.columnWidths = new int[] {63, 39, 105, 39, 63, 0};
		gbl_monthSelectPanel.rowHeights = new int[] {40, 0};
		gbl_monthSelectPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_monthSelectPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
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
		monthTextField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				numberFormatLimit(e, 2);
			}
		});
		
		JButton increaseMonthButton = new JButton("+");
		GridBagConstraints gbc_increaseMonthButton = new GridBagConstraints();
		gbc_increaseMonthButton.insets = new Insets(0, 0, 0, 5);
		gbc_increaseMonthButton.anchor = GridBagConstraints.WEST;
		gbc_increaseMonthButton.gridx = 3;
		gbc_increaseMonthButton.gridy = 0;
		monthSelectPanel.add(increaseMonthButton, gbc_increaseMonthButton);
		
		JLabel lblNewLabel = new JLabel("월");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridx = 4;
		gbc_lblNewLabel.gridy = 0;
		monthSelectPanel.add(lblNewLabel, gbc_lblNewLabel);
		
		JPanel daySelectPanel = new JPanel();
		detailDatePanel.add(daySelectPanel);
		GridBagLayout gbl_daySelectPanel = new GridBagLayout();
		gbl_daySelectPanel.columnWidths = new int[] {63, 39, 105, 39, 63, 0};
		gbl_daySelectPanel.rowHeights = new int[]{40, 0};
		gbl_daySelectPanel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_daySelectPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
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
		dayTextField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				numberFormatLimit(e, 2);
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
		
		JPanel selectHeadCntPaenl = new JPanel();
		GridBagConstraints gbc_selectHeadCntPaenl = new GridBagConstraints();
		gbc_selectHeadCntPaenl.fill = GridBagConstraints.BOTH;
		gbc_selectHeadCntPaenl.gridx = 0;
		gbc_selectHeadCntPaenl.gridy = 3;
		onewayPanel.add(selectHeadCntPaenl, gbc_selectHeadCntPaenl);
		
		
		JPanel roundTripPanel = new JPanel();
		JLabel roundTripLabel = new JLabel("왕복");
		roundTripLabel.setPreferredSize(new Dimension(reservTicketTabbedPane.getWidth() / 2 - 93, 30));
		reservTicketTabbedPane.add(roundTripPanel);
		reservTicketTabbedPane.setTitleAt(1, "왕복");
		
		JButton reservationButton = new JButton("예 매");
		reservationButton.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		reservTicketPanel.add(reservationButton, BorderLayout.SOUTH);
		reservTicketTabbedPane.setTabComponentAt(1, roundTripLabel);
		//#####################################################
		
		//#####################################################
		JPanel seasonTicketPanel = new JPanel();
		JLabel seasonTicketLabel = new JLabel("<html>정기권 예매</html>");
		seasonTicketLabel.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		ticketingTabbedPane.add(seasonTicketPanel);
		ticketingTabbedPane.setTabComponentAt(1, seasonTicketLabel);
		//#####################################################
		
		//#####################################################
		JPanel ticketConfirmPanel = new JPanel();
		JLabel ticketConfirmLabel = new JLabel("<html>승차권  확인</html>");
		ticketConfirmLabel.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		ticketingTabbedPane.add(ticketConfirmPanel);
		ticketingTabbedPane.setTabComponentAt(2, ticketConfirmLabel);
		//#####################################################
	}
	
	void numberFormatLimit(KeyEvent e, int limit) {
		if(((JTextField)e.getSource()).getText().length() > limit) {
			e.consume();
			return;
		}
		
		char c = e.getKeyChar();
		if(!Character.isDigit(c))
		{
			e.consume();
			return;
		}
	}
	
	void initializeDate(JTextField year, JTextField month, JTextField day) {
		Calendar cal = Calendar.getInstance();
		
		year.setText(String.format("%d", cal.get(Calendar.YEAR)));
		month.setText(String.format("%d", cal.get(Calendar.MONTH) + 1));
		day.setText(String.format("%d", cal.get(Calendar.DAY_OF_MONTH)));
	}
	
	void decreaseValue(ActionEvent e, String name) {
		int value = Integer.parseInt(((JTextField)e.getSource()).getText());
		
		//월 별로 일수가 다른 처리
	}
	
	void increaseValue(ActionEvent e, String name) {

		//월 별로 일수가 다른 처리
	}
}
