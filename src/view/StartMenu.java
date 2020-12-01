package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import database.TrainDAO;
import database.UserVo;
import util.ScreenUtil;

import javax.swing.JPasswordField;

@SuppressWarnings("serial")
public class StartMenu extends JFrame {

	private ArrayList<JComponent> signUpComponents;
	
	private JPanel contentPane;
	private JTextField idTextField_in;
	private JTextField idTextField_up;
	private JTextField nameTextField_up;
	private JTextField contactTextField_up;

	private TrainDAO dao;
	private JPasswordField passwordField_in;
	private JPasswordField passwordField_up;
	private JPasswordField checkPasswordField_up;

	public StartMenu() {
		setTitle("기차 예매 프로그램");
		dao = TrainDAO.getInstance();
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		signUpComponents = new ArrayList<JComponent>();
		

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		JPanel signUpPanel = new JPanel();
		signUpPanel.setBounds(205, 10, 371, 451);
		contentPane.add(signUpPanel);
		signUpPanel.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		signUpPanel.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		signUpPanel.setVisible(false);

		JPanel inputPanel_1 = new JPanel();
		inputPanel_1.setBounds(70, 22, 237, 386);
		panel.add(inputPanel_1);
		GridBagLayout gbl_inputPanel_1 = new GridBagLayout();
		gbl_inputPanel_1.columnWidths = new int[] { 90, 147, 0 };
		gbl_inputPanel_1.rowHeights = new int[] { 60, 60, 60, 60, 60, 60, 0 };
		gbl_inputPanel_1.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_inputPanel_1.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		inputPanel_1.setLayout(gbl_inputPanel_1);

		JLabel idLabel_1 = new JLabel("ID");
		GridBagConstraints gbc_idLabel_1 = new GridBagConstraints();
		gbc_idLabel_1.fill = GridBagConstraints.VERTICAL;
		gbc_idLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_idLabel_1.gridx = 0;
		gbc_idLabel_1.gridy = 0;
		inputPanel_1.add(idLabel_1, gbc_idLabel_1);

		idTextField_up = new JTextField();
		idTextField_up.setColumns(10);
		GridBagConstraints gbc_idTextField_up = new GridBagConstraints();
		gbc_idTextField_up.fill = GridBagConstraints.HORIZONTAL;
		gbc_idTextField_up.insets = new Insets(0, 0, 5, 0);
		gbc_idTextField_up.gridx = 1;
		gbc_idTextField_up.gridy = 0;
		inputPanel_1.add(idTextField_up, gbc_idTextField_up);
		signUpComponents.add(idTextField_up);

		JLabel pwLabel_1 = new JLabel("비밀번호");
		GridBagConstraints gbc_pwLabel_1 = new GridBagConstraints();
		gbc_pwLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_pwLabel_1.gridx = 0;
		gbc_pwLabel_1.gridy = 1;
		inputPanel_1.add(pwLabel_1, gbc_pwLabel_1);

		passwordField_up = new JPasswordField();
		GridBagConstraints gbc_passwordField_up = new GridBagConstraints();
		gbc_passwordField_up.insets = new Insets(0, 0, 5, 0);
		gbc_passwordField_up.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField_up.gridx = 1;
		gbc_passwordField_up.gridy = 1;
		inputPanel_1.add(passwordField_up, gbc_passwordField_up);
		signUpComponents.add(passwordField_up);

		JLabel checkPwLabel = new JLabel("비밀번호 확인");
		GridBagConstraints gbc_checkPwLabel = new GridBagConstraints();
		gbc_checkPwLabel.insets = new Insets(0, 0, 5, 5);
		gbc_checkPwLabel.gridx = 0;
		gbc_checkPwLabel.gridy = 2;
		inputPanel_1.add(checkPwLabel, gbc_checkPwLabel);

		checkPasswordField_up = new JPasswordField();
		GridBagConstraints gbc_checkPasswordField_up = new GridBagConstraints();
		gbc_checkPasswordField_up.insets = new Insets(0, 0, 5, 0);
		gbc_checkPasswordField_up.fill = GridBagConstraints.HORIZONTAL;
		gbc_checkPasswordField_up.gridx = 1;
		gbc_checkPasswordField_up.gridy = 2;
		inputPanel_1.add(checkPasswordField_up, gbc_checkPasswordField_up);
		signUpComponents.add(checkPasswordField_up);
		
		JLabel nameLabel = new JLabel("이름");
		GridBagConstraints gbc_nameLabel = new GridBagConstraints();
		gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nameLabel.gridx = 0;
		gbc_nameLabel.gridy = 3;
		inputPanel_1.add(nameLabel, gbc_nameLabel);

		nameTextField_up = new JTextField();
		nameTextField_up.setColumns(10);
		GridBagConstraints gbc_nameTextField_up = new GridBagConstraints();
		gbc_nameTextField_up.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameTextField_up.insets = new Insets(0, 0, 5, 0);
		gbc_nameTextField_up.gridx = 1;
		gbc_nameTextField_up.gridy = 3;
		inputPanel_1.add(nameTextField_up, gbc_nameTextField_up);
		signUpComponents.add(nameTextField_up);
		
		JLabel contactLabel = new JLabel("연락처");
		GridBagConstraints gbc_contactLabel = new GridBagConstraints();
		gbc_contactLabel.insets = new Insets(0, 0, 5, 5);
		gbc_contactLabel.gridx = 0;
		gbc_contactLabel.gridy = 4;
		inputPanel_1.add(contactLabel, gbc_contactLabel);

		contactTextField_up = new JTextField();
		contactTextField_up.setColumns(10);
		GridBagConstraints gbc_contactTextField_up = new GridBagConstraints();
		gbc_contactTextField_up.insets = new Insets(0, 0, 5, 0);
		gbc_contactTextField_up.fill = GridBagConstraints.HORIZONTAL;
		gbc_contactTextField_up.gridx = 1;
		gbc_contactTextField_up.gridy = 4;
		inputPanel_1.add(contactTextField_up, gbc_contactTextField_up);
		signUpComponents.add(contactTextField_up);

		JButton confirmButton = new JButton("확인");
		confirmButton.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		GridBagConstraints gbc_confirmButton = new GridBagConstraints();
		gbc_confirmButton.anchor = GridBagConstraints.WEST;
		gbc_confirmButton.insets = new Insets(0, 0, 0, 5);
		gbc_confirmButton.gridx = 0;
		gbc_confirmButton.gridy = 5;
		inputPanel_1.add(confirmButton, gbc_confirmButton);

		JButton cancelButton = new JButton("취소");
		cancelButton.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		GridBagConstraints gbc_cancelButton = new GridBagConstraints();
		gbc_cancelButton.anchor = GridBagConstraints.EAST;
		gbc_cancelButton.gridx = 1;
		gbc_cancelButton.gridy = 5;
		inputPanel_1.add(cancelButton, gbc_cancelButton);

		JLabel signUpLabel = new JLabel("회원가입");
		signUpLabel.setHorizontalAlignment(SwingConstants.CENTER);
		signUpLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 24));
		signUpPanel.add(signUpLabel, BorderLayout.NORTH);

		JPanel signInPanel = new JPanel();
		signInPanel.setBounds(242, 265, 293, 156);
		contentPane.add(signInPanel);
		signInPanel.setLayout(new BorderLayout(0, 0));

		JPanel gridPanel = new JPanel();
		gridPanel.setBorder(null);
		signInPanel.add(gridPanel, BorderLayout.NORTH);
		gridPanel.setLayout(new BorderLayout(0, 0));

		JPanel inputPanel = new JPanel();
		gridPanel.add(inputPanel, BorderLayout.CENTER);
		GridBagLayout gbl_inputPanel = new GridBagLayout();
		gbl_inputPanel.columnWidths = new int[] { 23, 57, 0 };
		gbl_inputPanel.rowHeights = new int[] { 15, 0, 0 };
		gbl_inputPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_inputPanel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		inputPanel.setLayout(gbl_inputPanel);

		JLabel idLabel = new JLabel("ID");
		idLabel.setHorizontalAlignment(SwingConstants.CENTER);
		idLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		GridBagConstraints gbc_idLabel = new GridBagConstraints();
		gbc_idLabel.insets = new Insets(0, 0, 5, 5);
		gbc_idLabel.gridx = 0;
		gbc_idLabel.gridy = 0;
		inputPanel.add(idLabel, gbc_idLabel);

		idTextField_in = new JTextField();
		idTextField_in.setText("test");
		idTextField_in.setFont(new Font("굴림", Font.PLAIN, 14));
		idTextField_in.setColumns(10);
		GridBagConstraints gbc_idTextField_in = new GridBagConstraints();
		gbc_idTextField_in.fill = GridBagConstraints.HORIZONTAL;
		gbc_idTextField_in.insets = new Insets(0, 0, 5, 0);
		gbc_idTextField_in.gridx = 1;
		gbc_idTextField_in.gridy = 0;
		inputPanel.add(idTextField_in, gbc_idTextField_in);

		JLabel pwLabel = new JLabel("Password");
		pwLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		pwLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		GridBagConstraints gbc_pwLabel = new GridBagConstraints();
		gbc_pwLabel.anchor = GridBagConstraints.EAST;
		gbc_pwLabel.insets = new Insets(0, 0, 0, 5);
		gbc_pwLabel.gridx = 0;
		gbc_pwLabel.gridy = 1;
		inputPanel.add(pwLabel, gbc_pwLabel);

		passwordField_in = new JPasswordField();
		passwordField_in.setFont(new Font("굴림", Font.PLAIN, 14));
		GridBagConstraints gbc_passwordField_in = new GridBagConstraints();
		gbc_passwordField_in.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField_in.gridx = 1;
		gbc_passwordField_in.gridy = 1;
		inputPanel.add(passwordField_in, gbc_passwordField_in);

		JButton signinButton = new JButton("로그인");
		signinButton.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		gridPanel.add(signinButton, BorderLayout.EAST);

		JButton signUpButton = new JButton("회원 가입");
		signUpButton.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
		signInPanel.add(signUpButton, BorderLayout.SOUTH);

		// 회원가입 등록 버튼
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String f_pw = String.valueOf(passwordField_up.getPassword());
				String s_pw = String.valueOf(checkPasswordField_up.getPassword());

				if (f_pw.length() == 0 || idTextField_up.getText().length() == 0) {
					showDialog("<html>ID 혹은 Password를<br><center>입력하지 않았습니다.</center></html>");
					return;
				}

				if (f_pw.equals(s_pw)) {
					UserVo u = new UserVo(idTextField_up.getText(), String.valueOf(passwordField_up.getPassword()),
							nameTextField_up.getText(), contactTextField_up.getText(), getTodayDate());

					if (isConfirmSign(u)) {
						signUpPanel.setVisible(false);
						signInPanel.setVisible(true);
						showDialog("회원가입이 완료되었습니다.");
					} else {
						showDialog("이미 존재하는 ID입니다.");
					}
				} else
					showDialog("비밀 번호가 일치하지 않습니다.");
			}
		});

		// 회원가입 취소 버튼
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				signUpPanel.setVisible(false);
				signInPanel.setVisible(true);
				showDialog("회원가입을 취소하셨습니다.");
			}
		});

		// 회원가입 버튼
		signUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				signInPanel.setVisible(false);
				System.out.println("회원 가입");
				signUpPanel.setVisible(true);
			}
		});

		// 로그인 버튼
		signinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				signIn();
			}
		});
		setLocation(ScreenUtil.getCenterPosition(this));
		
		passwordField_in.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					signIn();
				}
			}
		});
	}

	@SuppressWarnings("unused")
	void showDialog(String msg) {
		NoticeDialog nd = new NoticeDialog(msg, this);
		clearTextField();
	}
	
	private void clearTextField() {
		for(JComponent c : signUpComponents) {
			if(c instanceof JTextField) {
				((JTextField)c).setText("");
			}
			else {
				((JPasswordField)c).setText("");
			}
		}
	}
	private void signIn() {
		UserVo userVo;
		if ((userVo = dao.checkLoginData(idTextField_in.getText(), String.valueOf(passwordField_in.getPassword()))) != null) {
			new MainMenu(userVo, this).setVisible(true);
			//dispose();
			setVisible(false);
		} else {
			showDialog("ID / PW 가 틀렸습니다");
		}
	}
	
	private Date getTodayDate() {
		
		return new Date(Calendar.getInstance().getTimeInMillis());
	}

	private boolean isConfirmSign(UserVo u) {
		
		return dao.insertUserData(u);
	}

	protected void finalize() throws Throwable {
		System.out.println("StartMenu destroyed");
	}
	
}
