package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import constants.Constants;
import database.TicketVo;
import database.TrainDAO;
import openAPI.TrainAPI;
import openAPI.TrainVo;
import util.ScreenUtil;

import javax.swing.JLayeredPane;
import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class TrainInquiry extends JFrame implements ActionListener{

	private TrainDAO dao;
	private TrainVo currentTrainVo;
	
	private static final Color SKY_BLUE = new Color(51,153,255);
	private ArrayList<String> seats;
	private int personnel;
	private JFrame parent;
	
	private JPanel contentPane;
	
	private ArrayList<TrainVo> trainList;
	private JPanel trainInfomationsPanel;
	private JLayeredPane refLayeredPane;
	private JPanel refTrainList;
	private JLabel refDepStation;
	private JLabel refArrStation;
	private JLabel refPresentlyDate;
	
//	public static void main(String[] args) {
//		new TrainInquiry().setVisible(true);
//	}
	
	public TrainInquiry() {
		setTitle("기차 조회");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] {980, 0};
		gbl_contentPane.rowHeights = new int[] {40, 0, 507, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		TitledBorder titled = new TitledBorder(new LineBorder(Color.black, 1));
		
		JPanel titlePanel = new JPanel();
		GridBagConstraints gbc_titlePanel = new GridBagConstraints();
		gbc_titlePanel.insets = new Insets(0, 0, 5, 0);
		gbc_titlePanel.fill = GridBagConstraints.BOTH;
		gbc_titlePanel.gridx = 0;
		gbc_titlePanel.gridy = 0;
		contentPane.add(titlePanel, gbc_titlePanel);
		GridBagLayout gbl_titlePanel = new GridBagLayout();
		gbl_titlePanel.columnWidths = new int[]{100, ((GridBagLayout)contentPane.getLayout()).columnWidths[0] - 200, 0};
		gbl_titlePanel.rowHeights = new int[] {34, 0};
		gbl_titlePanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_titlePanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		titlePanel.setLayout(gbl_titlePanel);
		
		JButton backButton = new JButton("◀ 뒤로");
		GridBagConstraints gbc_backButton = new GridBagConstraints();
		gbc_backButton.insets = new Insets(0, 0, 0, 5);
		gbc_backButton.gridx = 0;
		gbc_backButton.gridy = 0;
		titlePanel.add(backButton, gbc_backButton);
		backButton.addActionListener(this);
		
		JLabel inquiryTitleLabel = new JLabel("열차 조회");
		inquiryTitleLabel.setFont(new Font("굴림", Font.PLAIN, 20));
		GridBagConstraints gbc_inquiryTitleLabel = new GridBagConstraints();
		gbc_inquiryTitleLabel.gridx = 1;
		gbc_inquiryTitleLabel.gridy = 0;
		titlePanel.add(inquiryTitleLabel, gbc_inquiryTitleLabel);
		
		JPanel stationPanel = new JPanel();
		GridBagConstraints gbc_stationPanel = new GridBagConstraints();
		gbc_stationPanel.insets = new Insets(0, 0, 5, 0);
		gbc_stationPanel.fill = GridBagConstraints.BOTH;
		gbc_stationPanel.gridx = 0;
		gbc_stationPanel.gridy = 1;
		contentPane.add(stationPanel, gbc_stationPanel);
		
		JLabel depStationLabel = new JLabel("출발역");
		stationPanel.add(depStationLabel);
		refDepStation = depStationLabel;
		
		JLabel arrowLabel = new JLabel(" → ");
		stationPanel.add(arrowLabel);
		
		JLabel arrStationLabel = new JLabel("도착역");
		stationPanel.add(arrStationLabel);
		refArrStation = arrStationLabel;
		
		JLayeredPane layeredPane = new JLayeredPane();
		GridBagConstraints gbc_layeredPane = new GridBagConstraints();
		gbc_layeredPane.fill = GridBagConstraints.BOTH;
		gbc_layeredPane.gridx = 0;
		gbc_layeredPane.gridy = 2;
		contentPane.add(layeredPane, gbc_layeredPane);
		GridBagLayout gbl_layeredPane = new GridBagLayout();
		gbl_layeredPane.columnWidths = new int[]{974, 0};
		gbl_layeredPane.rowHeights = new int[]{499, 0};
		gbl_layeredPane.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_layeredPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		layeredPane.setLayout(gbl_layeredPane);
		
		JPanel trainListPanel = new JPanel();
		layeredPane.setLayer(trainListPanel, 2);
		GridBagConstraints gbc_trainListPanel = new GridBagConstraints();
		gbc_trainListPanel.fill = GridBagConstraints.BOTH;
		gbc_trainListPanel.gridx = 0;
		gbc_trainListPanel.gridy = 0;
		layeredPane.add(trainListPanel, gbc_trainListPanel);
		GridBagLayout gbl_trainListPanel = new GridBagLayout();
		gbl_trainListPanel.columnWidths = new int[]{977, 0};
		gbl_trainListPanel.rowHeights = new int[]{35, 20, 26, 414, 0};
		gbl_trainListPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_trainListPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		trainListPanel.setLayout(gbl_trainListPanel);
		trainListPanel.setBorder(titled);
		
		JPanel datePanel = new JPanel();
		GridBagConstraints gbc_datePanel = new GridBagConstraints();
		gbc_datePanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_datePanel.insets = new Insets(0, 0, 5, 0);
		gbc_datePanel.gridx = 0;
		gbc_datePanel.gridy = 0;
		trainListPanel.add(datePanel, gbc_datePanel);
		GridBagLayout gbl_datePanel = new GridBagLayout();
		gbl_datePanel.columnWidths = new int[]{364, 73, 92, 73, 0};
		gbl_datePanel.rowHeights = new int[]{23, 0};
		gbl_datePanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_datePanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		datePanel.setLayout(gbl_datePanel);
		
		JButton previousDayButton = new JButton("이전 날");
		GridBagConstraints gbc_previousDayButton = new GridBagConstraints();
		gbc_previousDayButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_previousDayButton.insets = new Insets(0, 0, 0, 5);
		gbc_previousDayButton.gridx = 1;
		gbc_previousDayButton.gridy = 0;
		datePanel.add(previousDayButton, gbc_previousDayButton);
		
		JLabel presentlyDateLabel = new JLabel("2020년 00월 00일");
		GridBagConstraints gbc_presentlyDateLabel = new GridBagConstraints();
		gbc_presentlyDateLabel.anchor = GridBagConstraints.WEST;
		gbc_presentlyDateLabel.insets = new Insets(0, 0, 0, 5);
		gbc_presentlyDateLabel.gridx = 2;
		gbc_presentlyDateLabel.gridy = 0;
		datePanel.add(presentlyDateLabel, gbc_presentlyDateLabel);
		refPresentlyDate = presentlyDateLabel;
		
		JButton nextDayButton = new JButton("다음 날");
		GridBagConstraints gbc_nextDayButton = new GridBagConstraints();
		gbc_nextDayButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_nextDayButton.gridx = 3;
		gbc_nextDayButton.gridy = 0;
		datePanel.add(nextDayButton, gbc_nextDayButton);
		
		JComboBox<String> trainCategoryBox = new JComboBox<String>();
		GridBagConstraints gbc_trainCategoryBox = new GridBagConstraints();
		gbc_trainCategoryBox.anchor = GridBagConstraints.WEST;
		gbc_trainCategoryBox.insets = new Insets(0, 0, 5, 0);
		gbc_trainCategoryBox.gridx = 0;
		gbc_trainCategoryBox.gridy = 1;
		trainListPanel.add(trainCategoryBox, gbc_trainCategoryBox);
		
		JPanel subTitlePanel = new JPanel();
		GridBagConstraints gbc_subTitlePanel = new GridBagConstraints();
		gbc_subTitlePanel.fill = GridBagConstraints.BOTH;
		gbc_subTitlePanel.insets = new Insets(0, 0, 5, 0);
		gbc_subTitlePanel.gridx = 0;
		gbc_subTitlePanel.gridy = 2;
		trainListPanel.add(subTitlePanel, gbc_subTitlePanel);
		GridBagLayout gbl_subTitlePanel = new GridBagLayout();
		gbl_subTitlePanel.columnWidths = new int[]{200, 50, 200, 50, 200, 50, 200, 0};
		gbl_subTitlePanel.rowHeights = new int[]{15, 0};
		gbl_subTitlePanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_subTitlePanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		subTitlePanel.setLayout(gbl_subTitlePanel);
		
		JLabel subTitleTrain = new JLabel("열차");
		subTitleTrain.setHorizontalAlignment(SwingConstants.CENTER);
		subTitleTrain.setFont(new Font("굴림", Font.PLAIN, 18));
		GridBagConstraints gbc_subTitleTrain = new GridBagConstraints();
		gbc_subTitleTrain.anchor = GridBagConstraints.NORTH;
		gbc_subTitleTrain.insets = new Insets(0, 0, 0, 5);
		gbc_subTitleTrain.gridx = 0;
		gbc_subTitleTrain.gridy = 0;
		subTitlePanel.add(subTitleTrain, gbc_subTitleTrain);
		
		JLabel subTitleDepplandTime = new JLabel("출발");
		subTitleDepplandTime.setHorizontalAlignment(SwingConstants.CENTER);
		subTitleDepplandTime.setFont(new Font("굴림", Font.PLAIN, 18));
		GridBagConstraints gbc_subTitleDepplandTime = new GridBagConstraints();
		gbc_subTitleDepplandTime.anchor = GridBagConstraints.NORTH;
		gbc_subTitleDepplandTime.insets = new Insets(0, 0, 0, 5);
		gbc_subTitleDepplandTime.gridx = 2;
		gbc_subTitleDepplandTime.gridy = 0;
		subTitlePanel.add(subTitleDepplandTime, gbc_subTitleDepplandTime);
		
		JLabel subTitleArrplandTime = new JLabel("도착");
		subTitleArrplandTime.setHorizontalAlignment(SwingConstants.CENTER);
		subTitleArrplandTime.setFont(new Font("굴림", Font.PLAIN, 18));
		GridBagConstraints gbc_subTitleArrplandTime = new GridBagConstraints();
		gbc_subTitleArrplandTime.anchor = GridBagConstraints.NORTH;
		gbc_subTitleArrplandTime.insets = new Insets(0, 0, 0, 5);
		gbc_subTitleArrplandTime.gridx = 4;
		gbc_subTitleArrplandTime.gridy = 0;
		subTitlePanel.add(subTitleArrplandTime, gbc_subTitleArrplandTime);
		
		JLabel subTitlePrice = new JLabel("가격");
		subTitlePrice.setHorizontalAlignment(SwingConstants.CENTER);
		subTitlePrice.setFont(new Font("굴림", Font.PLAIN, 18));
		GridBagConstraints gbc_subTitlePrice = new GridBagConstraints();
		gbc_subTitlePrice.anchor = GridBagConstraints.NORTH;
		gbc_subTitlePrice.gridx = 6;
		gbc_subTitlePrice.gridy = 0;
		subTitlePanel.add(subTitlePrice, gbc_subTitlePrice);
		
		trainInfomationsPanel = new JPanel();
		JScrollPane scrollPane = new JScrollPane(trainInfomationsPanel);
		trainInfomationsPanel.setLayout(new GridLayout(0, 1, 0, 0));
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 3;
		trainListPanel.add(scrollPane, gbc_scrollPane);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		
		JPanel selectSeatPanel = new JPanel();
		layeredPane.setLayer(selectSeatPanel, 1);
		GridBagConstraints gbc_selectSeatPanel = new GridBagConstraints();
		gbc_selectSeatPanel.fill = GridBagConstraints.BOTH;
		gbc_selectSeatPanel.gridx = 0;
		gbc_selectSeatPanel.gridy = 0;
		layeredPane.add(selectSeatPanel, gbc_selectSeatPanel);
		GridBagLayout gbl_selectSeatPanel = new GridBagLayout();
		gbl_selectSeatPanel.columnWidths = new int[]{973, 0};
		gbl_selectSeatPanel.rowHeights = new int[]{113, 409, 0, 0};
		gbl_selectSeatPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_selectSeatPanel.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		selectSeatPanel.setLayout(gbl_selectSeatPanel);
		
		JPanel chargeInfoPanel = new JPanel();
		GridBagConstraints gbc_chargeInfoPanel = new GridBagConstraints();
		gbc_chargeInfoPanel.fill = GridBagConstraints.BOTH;
		gbc_chargeInfoPanel.insets = new Insets(0, 0, 5, 0);
		gbc_chargeInfoPanel.gridx = 0;
		gbc_chargeInfoPanel.gridy = 0;
		selectSeatPanel.add(chargeInfoPanel, gbc_chargeInfoPanel);
		chargeInfoPanel.setLayout(new GridLayout(3, 0, 0, 0));
		
		JPanel seatInfoPanel = new JPanel();
		GridBagConstraints gbc_seatInfoPanel = new GridBagConstraints();
		gbc_seatInfoPanel.insets = new Insets(0, 0, 5, 0);
		gbc_seatInfoPanel.fill = GridBagConstraints.BOTH;
		gbc_seatInfoPanel.gridx = 0;
		gbc_seatInfoPanel.gridy = 1;
		selectSeatPanel.add(seatInfoPanel, gbc_seatInfoPanel);
		seatInfoPanel.setLayout(new BorderLayout(0, 0));
		seatInfoPanel.setBorder(titled);
		
		JLabel lblNewLabel = new JLabel("기차 앞");
		lblNewLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		seatInfoPanel.add(lblNewLabel, BorderLayout.WEST);
		
		JPanel seatSelectPanel = new JPanel();
		seatInfoPanel.add(seatSelectPanel, BorderLayout.CENTER);
		GridBagLayout gbl_seatSelectPanel = new GridBagLayout();
		gbl_seatSelectPanel.columnWidths = new int[]{872, 0, 0};
		gbl_seatSelectPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_seatSelectPanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_seatSelectPanel.rowWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		seatSelectPanel.setLayout(gbl_seatSelectPanel);
		
		GridLayout[] btn_gl = { new GridLayout(2, 0, 5, 5), new GridLayout(2, 0, 5, 5)};
		
		JPanel buttonPanel1 = new JPanel();
		GridBagConstraints gbc_buttonPanel1 = new GridBagConstraints();
		gbc_buttonPanel1.insets = new Insets(0, 0, 5, 5);
		gbc_buttonPanel1.fill = GridBagConstraints.HORIZONTAL;
		gbc_buttonPanel1.gridx = 0;
		gbc_buttonPanel1.gridy = 0;
		seatSelectPanel.add(buttonPanel1, gbc_buttonPanel1);
		buttonPanel1.setLayout(btn_gl[0]);
		
		
		JPanel buttonPanel2 = new JPanel();
		GridBagConstraints gbc_buttonPanel2 = new GridBagConstraints();
		gbc_buttonPanel2.insets = new Insets(0, 0, 0, 5);
		gbc_buttonPanel2.fill = GridBagConstraints.HORIZONTAL;
		gbc_buttonPanel2.gridx = 0;
		gbc_buttonPanel2.gridy = 2;
		seatSelectPanel.add(buttonPanel2, gbc_buttonPanel2);
		buttonPanel2.setLayout(btn_gl[1]);
		
		JButton reservationButton = new JButton("예 매");
		reservationButton.setFont(new Font("맑은 고딕", Font.PLAIN, 30));
		GridBagConstraints gbc_reservationButton = new GridBagConstraints();
		gbc_reservationButton.fill = GridBagConstraints.BOTH;
		gbc_reservationButton.gridx = 0;
		gbc_reservationButton.gridy = 2;
		selectSeatPanel.add(reservationButton, gbc_reservationButton);
		reservationButton.addActionListener(this);
		
		//좌석 버튼
		JButton[] seatButtons = new JButton[40];
		int row = btn_gl[0].getRows() * 2;
		int cnt = 0;
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < seatButtons.length / row; j++) {
				seatButtons[cnt] = new JButton(String.format("%d", (j * row) + i + 1));
				seatButtons[cnt].setFont(new Font("맑은 고딕", Font.BOLD, 30));
				seatButtons[cnt].addActionListener(this);
				seatButtons[cnt].setBackground(SKY_BLUE);
				seatButtons[cnt].setForeground(Color.WHITE);
				if(i < btn_gl[0].getRows())
				{
					buttonPanel1.add(seatButtons[cnt]);
				}
				else 
				{
					buttonPanel2.add(seatButtons[cnt]);	
				}
				cnt++;
			}
		}
		
		
		JLabel[] labels = new JLabel[9];
		String seatType = "<html>일반&emsp;&emsp;입석</html>";
		String[] byAge = { "어린이", "어른", "경로" };
		String[] prices = { "<html>1,300&emsp;&emsp;1,100</html>", "<html>2,600&emsp;&emsp;2,200</html>", "<html>1,800&emsp;&emsp;1,300</html>" };
		JPanel[] chargePanels = new JPanel[3];
		
		for(int i = 0; i < labels.length; i++) 
		{
			switch(i % 3) 
			{
			case 0:
				labels[i] = new JLabel(byAge[i/3]);
				chargePanels[i / 3] = new JPanel(new GridLayout(0, 3));
				break;
			case 1:
				labels[i] = new JLabel(seatType);
				break;
			case 2:
				labels[i] = new JLabel(prices[i/3]);
				break;
			}
			labels[i].setFont(new Font("맑은 고딕", Font.BOLD, 16));
			labels[i].setHorizontalAlignment(SwingConstants.CENTER);
			chargePanels[i / 3].add(labels[i]);
		}
		
		for(int i = 0; i < chargePanels.length; i++) {
			chargeInfoPanel.add(chargePanels[i]);
			chargePanels[i].setBorder(titled);
		}
		
		Vector<String> trainKindList = TrainAPI.getInstance().getTrainKind();
		trainCategoryBox.addItem("모두");
		for(String s : trainKindList)
			trainCategoryBox.addItem(s);
		
		trainCategoryBox.addActionListener(this);
		
		refLayeredPane = layeredPane;
		refTrainList = trainListPanel;
		setLocation(ScreenUtil.getCenterPosition(this));
	}
	
	public TrainInquiry(ArrayList<TrainVo> list, JFrame parent, int personnel) {
		this();
		trainList = list;
		dao = TrainDAO.getInstance();
		refPresentlyDate.setText(getDateString(trainList.get(0).getDepplandTime()));
		refDepStation.setText(trainList.get(0).getDepPlace());
		refArrStation.setText(trainList.get(0).getArrPlace());
		this.parent = parent;
		initScrollPane(list, "모두");
		this.personnel = personnel;
		seats = new ArrayList<String>(personnel);
	}
	
	public void setLayer(int layer) {
		refLayeredPane.setLayer(refTrainList, layer);
	}

	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
			//System.out.println("Button " + e.getActionCommand());
			if(e.getActionCommand().equals("예 매")) 
			{
				if(seats.size() < personnel) {
					System.out.println("인원 좌석 안맞음");
					showDialog("<html>인원 수 보다 좌석의 수가 적습니다.<br><center>나머지 좌석은 무작위로 선택됩니다.</center></html>", 0);
				}
				else {
					System.out.println("인원 좌석 맞음");
					String seat = "";
					for(String s : seats) {
						seat += s + " ";
					}
					showDialog("<html>현재 선택된 좌석으로 진행 하시겠습니까?<br><center>" + seat + "</center></html>", 1);
				}
			}
			else if(e.getActionCommand().contains("뒤로") && (refLayeredPane.getComponentCountInLayer(0) > 0))
				refLayeredPane.setLayer(refTrainList, 2);
			else if(e.getActionCommand().contains("뒤로") && (refLayeredPane.getComponentCountInLayer(2) > 0)) 
			{
				parent.setVisible(true);
				this.dispose();
			}
			else
			{
				JButton btn = (JButton)e.getSource();
				Color color = btn.getBackground();
				String seat = btn.getActionCommand();
				
				if (color.equals(SKY_BLUE) && seats.size() < personnel) {
					System.out.println(e.getActionCommand() + " SKY BLUE");
					btn.setBackground(Color.RED);
					seats.add(seat);
				}
				else if(color.equals(Color.RED)) {
					System.out.println(e.getActionCommand() + " RED");
					btn.setBackground(SKY_BLUE);
					seats.remove(seat);
				}
				System.out.println("seats size " + seats.size());
			}	
		}
		else if(e.getSource() instanceof JComboBox)
		{
			String type = ((JComboBox<String>)e.getSource()).getSelectedItem().toString();
			initScrollPane(new ArrayList<TrainVo>(trainList), type);
		}
		else 
		{
			
		}
	}
	
	private void initScrollPane(ArrayList<TrainVo> list, String type) {
		trainInfomationsPanel.removeAll();
		System.out.println("모든 열차 정보 수 : " + trainList.size());
		int cnt = 0;
		for(TrainVo vo : list) {
			if(type.equals(vo.getTrainName()) || type.equals("모두")) {
				TrainInformation ti = new TrainInformation(vo, this);
				trainInfomationsPanel.add(ti);
				cnt++;
			}
		}
		trainInfomationsPanel.updateUI();

		System.out.println(type + " 열차 정보 수 : " + cnt);
	}
	
	private String getDateString(String str_date) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmm");
		Calendar cal = Calendar.getInstance();
		StringBuilder sb = new StringBuilder();
		try {
			cal.setTime(sf.parse(str_date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		sb.append(cal.get(Calendar.YEAR)).append("년 ").append(cal.get(Calendar.MONTH) + 1).append("월 ")
			.append(cal.get(Calendar.DAY_OF_MONTH)).append("일");
		
		return sb.toString(); 
	}
	
	private void showDialog(String msg, int type) {
		JDialog jd = new JDialog(this, "알림", true);
		jd.setSize(200, 120);
		jd.setLocation(ScreenUtil.getCurrentCenter(this, jd));
		jd.setResizable(false);
		jd.getContentPane().setLayout(new FlowLayout());
		
		JLabel message = new JLabel(msg);
		JButton confirm = new JButton("확인");
		JButton cancel = new JButton("취소");
		jd.getContentPane().add(message);
		jd.getContentPane().add(confirm);
		jd.getContentPane().add(cancel);
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jd.dispose();
			}
		});
		
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(type == 0)
					setRandomSeat(personnel - seats.size());
				reservationTrain();
				jd.dispose();
				subDialog("기차 예약이 완료되었습니다.");
			}
		});
		
		jd.setVisible(true);
	}
	
	@SuppressWarnings("unused")
	private void subDialog(String msg) {
		NoticeDialog nd = new NoticeDialog(msg, this);
	}
	
	private void setRandomSeat(int count) {
		for(int i = 0; i < count; i++) {
			String tmp = null;
			while(true) {
				boolean isOverlap = false;
				tmp = String.format("%d", (int)(Math.random() * 40 + 1));
				
				for(int j = 0; j < seats.size(); j++) {
					if(seats.get(j).equals(tmp)) {
						isOverlap = true;
						break;
					}
				}
				
				if(!isOverlap) break;
			}
			seats.add(tmp);
		}
	}
	
	private void reservationTrain() {
		TicketVo ticket = new TicketVo();
		((MainMenu)parent).addReservation(ticket.getTicket_id());
		ticket.setDeppland_place(currentTrainVo.getDepPlace());
		ticket.setArrpland_place(currentTrainVo.getArrPlace());
		ticket.setTrain_name(currentTrainVo.getTrainName());
		ticket.setCar_number(currentTrainVo.getTrainNo());
		ticket.setSeat(getSeatString());
		ticket.setDeppland_time(currentTrainVo.getDepplandTime());
		ticket.setArrpland_time(currentTrainVo.getArrplandTime());
		String tmpPrice = "1000";
		ticket.setPrice(tmpPrice);
		ticket.setTicketing_day(Constants.getTodayDate());
		ticket.setTicket_type(Constants.ONE_WAY);
		
		dao.insertTicketData(ticket);
		
		parent.setVisible(true);
		dispose();
	}

	private String getSeatString() {
		StringBuilder sb = new StringBuilder();
		
		for(String s : seats) {
			sb.append(s).append(" ");
		}
		
		return sb.toString().trim();
	}
	
	
	public void setCurrentTrainVo(TrainVo currentTrainVo) {
		this.currentTrainVo = currentTrainVo;
	}
}










