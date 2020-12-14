package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import database.TrainDAO;
import database.UserVo;
import main.Main;
import util.ScreenUtil;

import javax.swing.JPasswordField;
import javax.swing.JLayeredPane;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;

@SuppressWarnings("serial")
public class StartMenu extends JFrame {

	private ArrayList<JComponent> signUpComponents;

	private JPanel contentPane;
	private JTextField idTextField_in;
	private JTextField idTextField_signUp;
	private JTextField nameTextField_signUp;
	private JTextField contactTextField_signUp;

	private TrainDAO dao;
	private JPasswordField passwordField_in;
	private JPasswordField passwordField_signUp;
	private JPasswordField checkPasswordField_signUp;

	public StartMenu() {
		setTitle("기차 예매 프로그램");
		dao = TrainDAO.getInstance();
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 510);
		contentPane = new JPanel();
		setContentPane(contentPane);
		setResizable(false);
		signUpComponents = new ArrayList<JComponent>();
		contentPane.setBackground(Color.WHITE);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 1, 0 };
		gbl_contentPane.rowHeights = new int[] { 1, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JLayeredPane layeredPane = new JLayeredPane();
		GridBagConstraints gbc_layeredPane = new GridBagConstraints();
		gbc_layeredPane.fill = GridBagConstraints.BOTH;
		gbc_layeredPane.gridx = 0;
		gbc_layeredPane.gridy = 0;
		contentPane.add(layeredPane, gbc_layeredPane);
		GridBagLayout gbl_layeredPane = new GridBagLayout();
		gbl_layeredPane.columnWidths = new int[] { 10, 0 };
		gbl_layeredPane.rowHeights = new int[] { 10, 0 };
		gbl_layeredPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_layeredPane.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		layeredPane.setLayout(gbl_layeredPane);

		JPanel signUpLayer = new JPanel();
		signUpLayer.setBackground(Color.WHITE);
		layeredPane.setLayer(signUpLayer, 0);
		GridBagConstraints gbc_signUpLayer = new GridBagConstraints();
		gbc_signUpLayer.fill = GridBagConstraints.BOTH;
		gbc_signUpLayer.gridx = 0;
		gbc_signUpLayer.gridy = 0;
		layeredPane.add(signUpLayer, gbc_signUpLayer);

		JPanel signInLayer = new JPanel();
		signInLayer.setBackground(Color.WHITE);
		layeredPane.setLayer(signInLayer, 1);
		GridBagConstraints gbc_signInLayer = new GridBagConstraints();
		gbc_signInLayer.fill = GridBagConstraints.VERTICAL;
		gbc_signInLayer.gridx = 0;
		gbc_signInLayer.gridy = 0;
		layeredPane.add(signInLayer, gbc_signInLayer);
		GridBagLayout gbl_signUpLayer = new GridBagLayout();
		gbl_signUpLayer.columnWidths = new int[] { 98, 0 };
		gbl_signUpLayer.rowHeights = new int[] { 36, 0 };
		gbl_signUpLayer.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_signUpLayer.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		signUpLayer.setLayout(gbl_signUpLayer);

		JPanel signUpPanel = new JPanel();
		// GridBagConstraints gbc_signUpPanel = new GridBagConstraints();
		// gbc_signUpPanel.fill = GridBagConstraints.BOTH;
		// gbc_signUpPanel.gridx = 2;
		// gbc_signUpPanel.gridy = 1;
		GridBagConstraints gbc_signUpPanel = new GridBagConstraints();
		gbc_signUpPanel.gridx = 0;
		gbc_signUpPanel.gridy = 0;
		signUpLayer.add(signUpPanel, gbc_signUpPanel);
		signUpPanel.setLayout(new BorderLayout(0, 0));
		signUpPanel.setBackground(new Color(0x99, 0xCC, 0xFF));
		signUpPanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 1)));

		JPanel signUpInfoPanel = new JPanel();
		signUpPanel.add(signUpInfoPanel, BorderLayout.CENTER);
		signUpInfoPanel.setBackground(new Color(0xEB, 0xFB, 0xFF));
		GridBagLayout gbl_signUpInfoPanel = new GridBagLayout();
		gbl_signUpInfoPanel.columnWidths = new int[] { 297, 0 };
		gbl_signUpInfoPanel.rowHeights = new int[] { 360, 0 };
		gbl_signUpInfoPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_signUpInfoPanel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		signUpInfoPanel.setLayout(gbl_signUpInfoPanel);

		JPanel inputPanel_SignUp = new JPanel();
		GridBagConstraints gbc_inputPanel_SignUp = new GridBagConstraints();
		gbc_inputPanel_SignUp.fill = GridBagConstraints.BOTH;
		gbc_inputPanel_SignUp.gridx = 0;
		gbc_inputPanel_SignUp.gridy = 0;
		signUpInfoPanel.add(inputPanel_SignUp, gbc_inputPanel_SignUp);
		GridBagLayout gbl_inputPanel_SignUp = new GridBagLayout();
		gbl_inputPanel_SignUp.columnWidths = new int[] { 30, 90, 147, 30, 0 };
		gbl_inputPanel_SignUp.rowHeights = new int[] { 60, 60, 60, 60, 60, 60, 0 };
		gbl_inputPanel_SignUp.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_inputPanel_SignUp.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		inputPanel_SignUp.setLayout(gbl_inputPanel_SignUp);
		inputPanel_SignUp.setBackground(new Color(0xEB, 0xFB, 0xFF));

		JLabel lblNewLabel = new JLabel("");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		inputPanel_SignUp.add(lblNewLabel, gbc_lblNewLabel);

		JLabel idLabel_signUp = new JLabel("ID");
		idLabel_signUp.setHorizontalAlignment(SwingConstants.CENTER);
		idLabel_signUp.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		GridBagConstraints gbc_idLabel_signUp = new GridBagConstraints();
		gbc_idLabel_signUp.fill = GridBagConstraints.BOTH;
		gbc_idLabel_signUp.insets = new Insets(0, 0, 5, 5);
		gbc_idLabel_signUp.gridx = 1;
		gbc_idLabel_signUp.gridy = 0;
		inputPanel_SignUp.add(idLabel_signUp, gbc_idLabel_signUp);

		idTextField_signUp = new JTextField();
		idTextField_signUp.setColumns(10);
		GridBagConstraints gbc_idTextField_signUp = new GridBagConstraints();
		gbc_idTextField_signUp.fill = GridBagConstraints.HORIZONTAL;
		gbc_idTextField_signUp.insets = new Insets(0, 0, 5, 5);
		gbc_idTextField_signUp.gridx = 2;
		gbc_idTextField_signUp.gridy = 0;
		inputPanel_SignUp.add(idTextField_signUp, gbc_idTextField_signUp);
		signUpComponents.add(idTextField_signUp);

		JLabel pwLabel_signUp = new JLabel("비밀번호");
		pwLabel_signUp.setHorizontalAlignment(SwingConstants.CENTER);
		pwLabel_signUp.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		GridBagConstraints gbc_pwLabel_signUp = new GridBagConstraints();
		gbc_pwLabel_signUp.fill = GridBagConstraints.HORIZONTAL;
		gbc_pwLabel_signUp.insets = new Insets(0, 0, 5, 5);
		gbc_pwLabel_signUp.gridx = 1;
		gbc_pwLabel_signUp.gridy = 1;
		inputPanel_SignUp.add(pwLabel_signUp, gbc_pwLabel_signUp);

		passwordField_signUp = new JPasswordField();
		GridBagConstraints gbc_passwordField_signUp = new GridBagConstraints();
		gbc_passwordField_signUp.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField_signUp.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField_signUp.gridx = 2;
		gbc_passwordField_signUp.gridy = 1;
		inputPanel_SignUp.add(passwordField_signUp, gbc_passwordField_signUp);
		signUpComponents.add(passwordField_signUp);

		JLabel checkPwLabel_signUp = new JLabel("비밀번호 확인");
		checkPwLabel_signUp.setHorizontalAlignment(SwingConstants.CENTER);
		checkPwLabel_signUp.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		GridBagConstraints gbc_checkPwLabel_signUp = new GridBagConstraints();
		gbc_checkPwLabel_signUp.fill = GridBagConstraints.HORIZONTAL;
		gbc_checkPwLabel_signUp.insets = new Insets(0, 0, 5, 5);
		gbc_checkPwLabel_signUp.gridx = 1;
		gbc_checkPwLabel_signUp.gridy = 2;
		inputPanel_SignUp.add(checkPwLabel_signUp, gbc_checkPwLabel_signUp);

		checkPasswordField_signUp = new JPasswordField();
		GridBagConstraints gbc_checkPasswordField_signUp = new GridBagConstraints();
		gbc_checkPasswordField_signUp.insets = new Insets(0, 0, 5, 5);
		gbc_checkPasswordField_signUp.fill = GridBagConstraints.HORIZONTAL;
		gbc_checkPasswordField_signUp.gridx = 2;
		gbc_checkPasswordField_signUp.gridy = 2;
		inputPanel_SignUp.add(checkPasswordField_signUp, gbc_checkPasswordField_signUp);
		signUpComponents.add(checkPasswordField_signUp);

		JLabel nameLabel_signUp = new JLabel("이름");
		nameLabel_signUp.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel_signUp.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		GridBagConstraints gbc_nameLabel_signUp = new GridBagConstraints();
		gbc_nameLabel_signUp.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameLabel_signUp.insets = new Insets(0, 0, 5, 5);
		gbc_nameLabel_signUp.gridx = 1;
		gbc_nameLabel_signUp.gridy = 3;
		inputPanel_SignUp.add(nameLabel_signUp, gbc_nameLabel_signUp);

		nameTextField_signUp = new JTextField();
		nameTextField_signUp.setColumns(10);
		GridBagConstraints gbc_nameTextField_signUp = new GridBagConstraints();
		gbc_nameTextField_signUp.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameTextField_signUp.insets = new Insets(0, 0, 5, 5);
		gbc_nameTextField_signUp.gridx = 2;
		gbc_nameTextField_signUp.gridy = 3;
		inputPanel_SignUp.add(nameTextField_signUp, gbc_nameTextField_signUp);
		signUpComponents.add(nameTextField_signUp);

		JLabel contactLabel_signUp = new JLabel("연락처");
		contactLabel_signUp.setHorizontalAlignment(SwingConstants.CENTER);
		contactLabel_signUp.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		GridBagConstraints gbc_contactLabel_signUp = new GridBagConstraints();
		gbc_contactLabel_signUp.fill = GridBagConstraints.HORIZONTAL;
		gbc_contactLabel_signUp.insets = new Insets(0, 0, 5, 5);
		gbc_contactLabel_signUp.gridx = 1;
		gbc_contactLabel_signUp.gridy = 4;
		inputPanel_SignUp.add(contactLabel_signUp, gbc_contactLabel_signUp);

		contactTextField_signUp = new JTextField();
		contactTextField_signUp.setColumns(10);
		GridBagConstraints gbc_contactTextField_signUp = new GridBagConstraints();
		gbc_contactTextField_signUp.insets = new Insets(0, 0, 5, 5);
		gbc_contactTextField_signUp.fill = GridBagConstraints.HORIZONTAL;
		gbc_contactTextField_signUp.gridx = 2;
		gbc_contactTextField_signUp.gridy = 4;
		inputPanel_SignUp.add(contactTextField_signUp, gbc_contactTextField_signUp);
		signUpComponents.add(contactTextField_signUp);

		JButton confirmButton_signUp = new JButton("확인");
		confirmButton_signUp.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		GridBagConstraints gbc_confirmButton_signUp = new GridBagConstraints();
		gbc_confirmButton_signUp.anchor = GridBagConstraints.WEST;
		gbc_confirmButton_signUp.insets = new Insets(0, 0, 0, 5);
		gbc_confirmButton_signUp.gridx = 1;
		gbc_confirmButton_signUp.gridy = 5;
		inputPanel_SignUp.add(confirmButton_signUp, gbc_confirmButton_signUp);
		confirmButton_signUp.setBackground(Color.white);

		JButton cancelButton_signUp = new JButton("취소");
		cancelButton_signUp.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		GridBagConstraints gbc_cancelButton_signUp = new GridBagConstraints();
		gbc_cancelButton_signUp.insets = new Insets(0, 0, 0, 5);
		gbc_cancelButton_signUp.anchor = GridBagConstraints.EAST;
		gbc_cancelButton_signUp.gridx = 2;
		gbc_cancelButton_signUp.gridy = 5;
		inputPanel_SignUp.add(cancelButton_signUp, gbc_cancelButton_signUp);
		cancelButton_signUp.setBackground(Color.white);

		// 회원가입 등록 버튼
		confirmButton_signUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String f_pw = String.valueOf(passwordField_signUp.getPassword());
				String s_pw = String.valueOf(checkPasswordField_signUp.getPassword());

				if (f_pw.length() == 0 || idTextField_signUp.getText().length() == 0) {
					showDialog("<html>ID 혹은 Password를<br><center>입력하지 않았습니다.</center></html>");
					return;
				}

				if (f_pw.equals(s_pw)) {
					UserVo u = new UserVo(idTextField_signUp.getText(),
							String.valueOf(passwordField_signUp.getPassword()), nameTextField_signUp.getText(),
							contactTextField_signUp.getText(), getTodayDate());

					if (isConfirmSign(u)) {
						layeredPane.setLayer(signUpLayer, 0);
						showDialog("회원가입이 완료되었습니다.");
					} else {
						showDialog("이미 존재하는 ID입니다.");
					}
				} else
					showDialog("비밀 번호가 일치하지 않습니다.");
			}
		});

		// 회원가입 취소 버튼
		cancelButton_signUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.setLayer(signUpLayer, 0);
				showDialog("회원가입을 취소하셨습니다.");
			}
		});

		JLabel signUpLabel = new JLabel("회원가입");
		signUpLabel.setHorizontalAlignment(SwingConstants.CENTER);
		signUpLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 24));
		signUpPanel.add(signUpLabel, BorderLayout.NORTH);
		GridBagLayout gbl_signInLayer = new GridBagLayout();
		gbl_signInLayer.columnWidths = new int[] { 299, 0 };
		gbl_signInLayer.rowHeights = new int[] {45, 240, 0, 0};
		gbl_signInLayer.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_signInLayer.rowWeights = new double[] { 0.0, 1.0, 1.0, Double.MIN_VALUE };
		signInLayer.setLayout(gbl_signInLayer);

		JLabel programNameLabel = new JLabel("기차 예매 프로그램");
		programNameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 22));
		GridBagConstraints gbc_programNameLabel = new GridBagConstraints();
		gbc_programNameLabel.gridx = 0;
		gbc_programNameLabel.gridy = 0;
		signInLayer.add(programNameLabel, gbc_programNameLabel);

		JPanel imagePanel = new JPanel() {
			int width = gbl_signInLayer.rowHeights[1] > gbl_signInLayer.columnWidths[0]
					? gbl_signInLayer.columnWidths[0]
					: gbl_signInLayer.rowHeights[1];
			int x;
			Image background = new ImageIcon(Main.class.getResource("../image/korailGooglePlay.png")).getImage();
			public void paint(Graphics g) {
				if (width == gbl_signInLayer.rowHeights[1])
					x = Math.abs(gbl_signInLayer.columnWidths[0] - width) / 2;
				else
					x = Math.abs(gbl_signInLayer.rowHeights[1] - width) / 2;

				g.drawImage(background, x, 0, width, width, null);
			}
		};
		imagePanel.setBackground(Color.WHITE);
		GridBagConstraints gbc_imagePanel = new GridBagConstraints();
		gbc_imagePanel.insets = new Insets(0, 0, 5, 0);
		gbc_imagePanel.fill = GridBagConstraints.BOTH;
		gbc_imagePanel.gridx = 0;
		gbc_imagePanel.gridy = 1;
		signInLayer.add(imagePanel, gbc_imagePanel);

		JPanel signInPanel = new JPanel();
		GridBagConstraints gbc_signInPanel = new GridBagConstraints();
		gbc_signInPanel.insets = new Insets(0, 0, 5, 0);
		gbc_signInPanel.gridx = 0;
		gbc_signInPanel.gridy = 2;
		signInLayer.add(signInPanel, gbc_signInPanel);
		layeredPane.setLayer(signInLayer, 1);
		signInPanel.setBackground(Color.WHITE);

		GridBagLayout gbl_signInPanel = new GridBagLayout();
		gbl_signInPanel.columnWidths = new int[] { 254, 0 };
		gbl_signInPanel.rowHeights = new int[] { 51, 50, 39, 0 };
		gbl_signInPanel.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_signInPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		signInPanel.setLayout(gbl_signInPanel);

		JPanel gridPanel = new JPanel();
		gridPanel.setBorder(null);
		GridBagConstraints gbc_gridPanel = new GridBagConstraints();
		gbc_gridPanel.fill = GridBagConstraints.BOTH;
		gbc_gridPanel.insets = new Insets(0, 0, 5, 0);
		gbc_gridPanel.gridx = 0;
		gbc_gridPanel.gridy = 0;
		signInPanel.add(gridPanel, gbc_gridPanel);
		gridPanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 1)));
		gridPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		gridPanel.setBackground(Color.WHITE);

		JPanel inputPanel_SignIn = new JPanel();
		inputPanel_SignIn.setBackground(Color.WHITE);
		gridPanel.add(inputPanel_SignIn);
		GridBagLayout gbl_inputPanel_SignIn = new GridBagLayout();
		gbl_inputPanel_SignIn.columnWidths = new int[] { 23, 57, 0 };
		gbl_inputPanel_SignIn.rowHeights = new int[] { 15, 0, 0 };
		gbl_inputPanel_SignIn.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_inputPanel_SignIn.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		inputPanel_SignIn.setLayout(gbl_inputPanel_SignIn);

		JLabel idLabel = new JLabel("ID");
		idLabel.setHorizontalAlignment(SwingConstants.CENTER);
		idLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		GridBagConstraints gbc_idLabel = new GridBagConstraints();
		gbc_idLabel.insets = new Insets(0, 0, 5, 5);
		gbc_idLabel.gridx = 0;
		gbc_idLabel.gridy = 0;
		inputPanel_SignIn.add(idLabel, gbc_idLabel);

		idTextField_in = new JTextField();
		idTextField_in.setText("test");
		idTextField_in.setFont(new Font("굴림", Font.PLAIN, 14));
		idTextField_in.setColumns(10);
		GridBagConstraints gbc_idTextField_in = new GridBagConstraints();
		gbc_idTextField_in.fill = GridBagConstraints.HORIZONTAL;
		gbc_idTextField_in.insets = new Insets(0, 0, 5, 0);
		gbc_idTextField_in.gridx = 1;
		gbc_idTextField_in.gridy = 0;
		inputPanel_SignIn.add(idTextField_in, gbc_idTextField_in);

		JLabel pwLabel = new JLabel("Password");
		pwLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		pwLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		GridBagConstraints gbc_pwLabel = new GridBagConstraints();
		gbc_pwLabel.anchor = GridBagConstraints.EAST;
		gbc_pwLabel.insets = new Insets(0, 0, 0, 5);
		gbc_pwLabel.gridx = 0;
		gbc_pwLabel.gridy = 1;
		inputPanel_SignIn.add(pwLabel, gbc_pwLabel);

		passwordField_in = new JPasswordField();
		passwordField_in.setFont(new Font("굴림", Font.PLAIN, 14));
		GridBagConstraints gbc_passwordField_in = new GridBagConstraints();
		gbc_passwordField_in.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField_in.gridx = 1;
		gbc_passwordField_in.gridy = 1;
		inputPanel_SignIn.add(passwordField_in, gbc_passwordField_in);

		JButton signinButton = new JButton("로그인");
		signinButton.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		gridPanel.add(signinButton);
		signinButton.setBackground(new Color(0x99, 0xCC, 0xFF));

		// 로그인 버튼
		signinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				signIn();
			}
		});

		passwordField_in.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					signIn();
				}
			}
		});

		JButton signUpButton = new JButton("회원 가입");
		signUpButton.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
		GridBagConstraints gbc_signUpButton = new GridBagConstraints();
		gbc_signUpButton.anchor = GridBagConstraints.NORTH;
		gbc_signUpButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_signUpButton.gridx = 0;
		gbc_signUpButton.gridy = 2;
		signInPanel.add(signUpButton, gbc_signUpButton);
		signUpButton.setBackground(new Color(0x99, 0xCC, 0xFF));

		// 회원가입 버튼
		signUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("회원 가입");
				layeredPane.setLayer(signUpLayer, 2);

			}
		});
		setLocation(ScreenUtil.getCenterPosition(this));
	}

	@SuppressWarnings("unused")
	void showDialog(String msg) {
		NoticeDialog nd = new NoticeDialog(msg, this);
		clearTextField();
	}

	private void clearTextField() {
		for (JComponent c : signUpComponents) {
			if (c instanceof JTextField) {
				((JTextField) c).setText("");
			} else {
				((JPasswordField) c).setText("");
			}
		}
	}

	private void signIn() {
		UserVo userVo;
		if ((userVo = dao.checkLoginData(idTextField_in.getText(),
				String.valueOf(passwordField_in.getPassword()))) != null) {
			new MainMenu(userVo, this).setVisible(true);
			// dispose();
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
