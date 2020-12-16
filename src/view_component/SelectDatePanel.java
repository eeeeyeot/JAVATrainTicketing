package view_component;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import constants.Constants;

import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class SelectDatePanel extends JPanel {
	private JTextField yearTextField;
	private JTextField monthTextField;
	private JTextField dayTextField;
	
	public SelectDatePanel(String type) {
		setBackground(new Color(240, 255, 255));
		String title;
		if(type.equals("oneway"))
			title = "출 발 일";
		else if(type.equals("daytogo"))
			title = "가 는 날";
		else if(type.equals("comingday"))
			title = "오 는 날";
		else if(type.equals("season"))
			title = "사 용 기 간";
		else
			title = "something wrong";
		
		TitledBorder panelBorder = new TitledBorder(new LineBorder(Color.BLACK, 2));
		TitledBorder titleBorder = new TitledBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		setBorder(panelBorder);
		setLayout(new BorderLayout(0, 0));
		
		JLabel dateLabel = new JLabel(title);
		dateLabel.setBackground(new Color(240, 255, 255));
		dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dateLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		add(dateLabel, BorderLayout.NORTH);
		dateLabel.setBorder(titleBorder);
		
		JPanel detailDatePanel = new JPanel();
		detailDatePanel.setBackground(Color.WHITE);
		add(detailDatePanel);
		detailDatePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		detailDatePanel.setBorder(titleBorder);
		
		JPanel yearSelectPanel = new JPanel();
		yearSelectPanel.setBackground(Color.WHITE);
		detailDatePanel.add(yearSelectPanel);
		GridBagLayout gbl_yearSelectPanel = new GridBagLayout();
		gbl_yearSelectPanel.columnWidths = new int[] {30, 39, 80, 39, 30, 0};
		gbl_yearSelectPanel.rowHeights = new int[] { 40, 0 };
		gbl_yearSelectPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_yearSelectPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		yearSelectPanel.setLayout(gbl_yearSelectPanel);
		
		JButton decreaseYearButton = new JButton("-");
		decreaseYearButton.setFont(new Font("굴림", Font.PLAIN, 14));
		decreaseYearButton.setBackground(Constants.BUTTON_COLOR);
		GridBagConstraints gbc_decreaseYearButton = new GridBagConstraints();
		gbc_decreaseYearButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_decreaseYearButton.insets = new Insets(0, 0, 0, 5);
		gbc_decreaseYearButton.gridx = 1;
		gbc_decreaseYearButton.gridy = 0;
		yearSelectPanel.add(decreaseYearButton, gbc_decreaseYearButton);

		yearTextField = new JTextField();
		yearTextField.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_TextField = new GridBagConstraints();
		gbc_TextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_TextField.insets = new Insets(0, 0, 0, 5);
		gbc_TextField.gridx = 2;
		gbc_TextField.gridy = 0;
		yearSelectPanel.add(yearTextField, gbc_TextField);

		JButton increaseYearButton = new JButton("+");
		increaseYearButton.setFont(new Font("굴림", Font.PLAIN, 14));
		increaseYearButton.setBackground(Constants.BUTTON_COLOR);
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
		monthSelectPanel.setBackground(Color.WHITE);
		detailDatePanel.add(monthSelectPanel);
		GridBagLayout gbl_monthSelectPanel = new GridBagLayout();
		gbl_monthSelectPanel.columnWidths = new int[] {30, 39, 80, 39, 30, 0};
		gbl_monthSelectPanel.rowHeights = new int[] { 40, 0 };
		gbl_monthSelectPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_monthSelectPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		monthSelectPanel.setLayout(gbl_monthSelectPanel);

		JButton decreaseMonthButton = new JButton("-");
		decreaseMonthButton.setFont(new Font("굴림", Font.PLAIN, 14));
		decreaseMonthButton.setBackground(Constants.BUTTON_COLOR);
		GridBagConstraints gbc_decreaseMonthButton = new GridBagConstraints();
		gbc_decreaseMonthButton.anchor = GridBagConstraints.WEST;
		gbc_decreaseMonthButton.insets = new Insets(0, 0, 0, 5);
		gbc_decreaseMonthButton.gridx = 1;
		gbc_decreaseMonthButton.gridy = 0;
		monthSelectPanel.add(decreaseMonthButton, gbc_decreaseMonthButton);

		monthTextField = new JTextField();
		monthTextField.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_TextField_1 = new GridBagConstraints();
		gbc_TextField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_TextField_1.insets = new Insets(0, 0, 0, 5);
		gbc_TextField_1.gridx = 2;
		gbc_TextField_1.gridy = 0;
		monthSelectPanel.add(monthTextField, gbc_TextField_1);

		JButton increaseMonthButton = new JButton("+");
		increaseMonthButton.setFont(new Font("굴림", Font.PLAIN, 14));
		increaseMonthButton.setBackground(Constants.BUTTON_COLOR);
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
		daySelectPanel.setBackground(Color.WHITE);
		detailDatePanel.add(daySelectPanel);
		GridBagLayout gbl_daySelectPanel = new GridBagLayout();
		gbl_daySelectPanel.columnWidths = new int[] {30, 39, 80, 39, 30, 0};
		gbl_daySelectPanel.rowHeights = new int[] { 40, 0 };
		gbl_daySelectPanel.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_daySelectPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		daySelectPanel.setLayout(gbl_daySelectPanel);

		JButton decreaseDayButton = new JButton("-");
		decreaseDayButton.setFont(new Font("굴림", Font.PLAIN, 14));
		decreaseDayButton.setBackground(Constants.BUTTON_COLOR);
		GridBagConstraints gbc_decreaseDayButton = new GridBagConstraints();
		gbc_decreaseDayButton.anchor = GridBagConstraints.WEST;
		gbc_decreaseDayButton.insets = new Insets(0, 0, 0, 5);
		gbc_decreaseDayButton.gridx = 1;
		gbc_decreaseDayButton.gridy = 0;
		daySelectPanel.add(decreaseDayButton, gbc_decreaseDayButton);

		dayTextField = new JTextField();
		dayTextField.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_TextField_2 = new GridBagConstraints();
		gbc_TextField_2.insets = new Insets(0, 0, 0, 5);
		gbc_TextField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_TextField_2.gridx = 2;
		gbc_TextField_2.gridy = 0;
		daySelectPanel.add(dayTextField, gbc_TextField_2);
		
		initializeDate(yearTextField, monthTextField, dayTextField);

		JButton increaseDayButton = new JButton("+");
		increaseDayButton.setFont(new Font("굴림", Font.PLAIN, 14));
		increaseDayButton.setBackground(Constants.BUTTON_COLOR);
		GridBagConstraints gbc_increaseDayButton = new GridBagConstraints();
		gbc_increaseDayButton.insets = new Insets(0, 0, 0, 5);
		gbc_increaseDayButton.anchor = GridBagConstraints.WEST;
		gbc_increaseDayButton.gridx = 3;
		gbc_increaseDayButton.gridy = 0;
		daySelectPanel.add(increaseDayButton, gbc_increaseDayButton);
		
		JLabel dayLabel = new JLabel("일");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.gridx = 4;
		gbc_lblNewLabel_1.gridy = 0;
		daySelectPanel.add(dayLabel, gbc_lblNewLabel_1);

		
		
		yearTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				Constants.numberFormatLimit(e, 4);
			}
		});

		monthTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				Constants.numberFormatLimit(e, 2);
			}

			public void keyReleased(KeyEvent e) {
				setValidValue((JTextField) e.getSource(), yearTextField.getText(), monthTextField.getText(), false);
			}
		});

		dayTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				Constants.numberFormatLimit(e, 2);
			}

			public void keyReleased(KeyEvent e) {
				setValidValue((JTextField) e.getSource(), yearTextField.getText(), monthTextField.getText(), true);
			}
		});
		
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
				System.out.println(decreaseYearButton.getSize());
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
				System.out.println(increaseYearButton.getSize());
			}
		});
	}
	
	public String getTextFieldText(String name) {
		if(name.equals("yearTextField"))
		{
			return yearTextField.getText();
		}
		else if(name.equals("monthTextField")) 
		{
			return monthTextField.getText();
		}
		else 
		{
			return dayTextField.getText();
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
}
