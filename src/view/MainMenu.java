package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import util.ScreenUtil;
import javax.swing.JTabbedPane;

public class MainMenu extends JFrame {

	private JPanel contentPane;
	
	public static void main(String[] args) {
		new MainMenu().setVisible(true);
	}
	
	public MainMenu() {
		setTitle("기차 예매 프로그램");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel northPanel = new JPanel();
		contentPane.add(northPanel, BorderLayout.NORTH);
		GridBagLayout gbl_northPanel = new GridBagLayout();
		gbl_northPanel.columnWidths = new int[]{266, 476, 0};
		gbl_northPanel.rowHeights = new int[]{15, 0};
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
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.RIGHT);
		tabbedPane.setBounds(0, 0, 774, 397);
		centerPanel.add(tabbedPane);
		setLocation(ScreenUtil.getCenterPosition(this));
		
		JPanel p1 = new JPanel();
		JPanel tab1 = new JPanel();
		JLabel l1 = new JLabel("<html>승차권 예매</html>");
		l1.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		tab1.add(l1);
		tabbedPane.add(p1);
		tabbedPane.setTabComponentAt(0, tab1);
		
		JPanel tab2 = new JPanel();
		JPanel p2 = new JPanel();
		JLabel l2 = new JLabel("<html>정기권 예매</html>");
		l2.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		tab2.add(l2);
		tabbedPane.add(p2);
		tabbedPane.setTabComponentAt(1, tab2);
		
		JPanel tab3 = new JPanel();
		JPanel p3 = new JPanel();
		JLabel l3 = new JLabel("<html>승차권 및 <br>정기권 확인</html>");
		l3.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		tab3.add(l3);
		tabbedPane.add(p3);
		tabbedPane.setTabComponentAt(2, tab3);
	}

}
