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
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
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
import view_component.TrainInformationPanel;

import javax.swing.JLayeredPane;
import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class TrainInquiryMenu extends JFrame implements ActionListener{
	private static final Color SKY_BLUE = new Color(51,153,255);
	public static final int SHOW = 2;
	public static final int HIDE = 0;
	
	private TrainDAO dao;
	private TrainVo currentTrainVo;
	
	private ArrayList<Integer> seatList;
	private Queue<ArrayList<TrainVo>> commandQue;
	private int personnel;
	
	@SuppressWarnings("unused")
	private int childCharge;
	@SuppressWarnings("unused")
	private int adultCharge;
	@SuppressWarnings("unused")
	private int seniorCharge;
	
	private JFrame parent;
	
	private JPanel contentPane;
	
	private ArrayList<TrainVo> trainList;
	private JPanel trainListPanel;
	private JLayeredPane refLayeredPane;
	private JPanel refTrainList;
	private JLabel refDepStation;
	private JLabel refArrStation;
	private JLabel refPresentlyDate;
	
	private JButton[] seatButtons;
	
//	public static void main(String[] args) {
//		new TrainInquiry().setVisible(true);
//	}
	
	public TrainInquiryMenu() {
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
		
		JPanel selectTrainPanel = new JPanel();
		layeredPane.setLayer(selectTrainPanel, 2);
		GridBagConstraints gbc_selectTrainPanel = new GridBagConstraints();
		gbc_selectTrainPanel.fill = GridBagConstraints.BOTH;
		gbc_selectTrainPanel.gridx = 0;
		gbc_selectTrainPanel.gridy = 0;
		layeredPane.add(selectTrainPanel, gbc_selectTrainPanel);
		GridBagLayout gbl_selectTrainPanel = new GridBagLayout();
		gbl_selectTrainPanel.columnWidths = new int[]{977, 0};
		gbl_selectTrainPanel.rowHeights = new int[]{35, 20, 26, 414, 0};
		gbl_selectTrainPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_selectTrainPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		selectTrainPanel.setLayout(gbl_selectTrainPanel);
		selectTrainPanel.setBorder(titled);
		
		JPanel datePanel = new JPanel();
		GridBagConstraints gbc_datePanel = new GridBagConstraints();
		gbc_datePanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_datePanel.insets = new Insets(0, 0, 5, 0);
		gbc_datePanel.gridx = 0;
		gbc_datePanel.gridy = 0;
		selectTrainPanel.add(datePanel, gbc_datePanel);
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
		selectTrainPanel.add(trainCategoryBox, gbc_trainCategoryBox);
		
		JPanel subTitlePanel = new JPanel();
		GridBagConstraints gbc_subTitlePanel = new GridBagConstraints();
		gbc_subTitlePanel.fill = GridBagConstraints.BOTH;
		gbc_subTitlePanel.insets = new Insets(0, 0, 5, 0);
		gbc_subTitlePanel.gridx = 0;
		gbc_subTitlePanel.gridy = 2;
		selectTrainPanel.add(subTitlePanel, gbc_subTitlePanel);
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
		
		trainListPanel = new JPanel();
		JScrollPane trainListScrollPane = new JScrollPane(trainListPanel);
		GridBagLayout gbl_trainInfomationsPanel = new GridBagLayout();
		gbl_trainInfomationsPanel.columnWidths = new int[]{0};
		gbl_trainInfomationsPanel.rowHeights = new int[]{0};
		gbl_trainInfomationsPanel.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_trainInfomationsPanel.rowWeights = new double[]{Double.MIN_VALUE};
		trainListPanel.setLayout(gbl_trainInfomationsPanel);
		GridBagConstraints gbc_trainListScrollPane = new GridBagConstraints();
		gbc_trainListScrollPane.fill = GridBagConstraints.BOTH;
		gbc_trainListScrollPane.gridx = 0;
		gbc_trainListScrollPane.gridy = 3;
		selectTrainPanel.add(trainListScrollPane, gbc_trainListScrollPane);
		trainListScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		
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
		seatButtons = new JButton[40];
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
		String seatType = "요금";
		String[] byAge = { "어린이", "어른", "경로" };
		String[] prices = { "1,300", "2,600", "1,300" };
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
		refTrainList = selectTrainPanel;
		setLocation(ScreenUtil.getCenterPosition(this));
	}
	
	public TrainInquiryMenu(Queue<ArrayList<TrainVo>> commands, JFrame parent, int personnel) {
		this();
		this.parent = parent;
		this.personnel = personnel;
		
		System.out.println("first command size : " + commands.size());
		
		trainList = commands.poll();
		dao = TrainDAO.getInstance();
		
		initLabel();
		
		updateTrainList(trainList, "모두");
		
		System.out.println("인원 : " + personnel);
		seatList = new ArrayList<Integer>(personnel);
		
		commandQue = new LinkedList<ArrayList<TrainVo>>();
		for(int i = 0; i < commands.size(); i++) {
			addCommand(commands.poll());
		}
		
		System.out.println("remain command length : " + commandQue.size());
	}
	
	public TrainInquiryMenu setPersonnel(int personnel) {
		this.personnel = personnel;
		return this;
	}
	public TrainInquiryMenu setChildCount(int childCount) {
		this.childCharge = childCount * currentTrainVo.getFee();
		return this;
	}
	public TrainInquiryMenu setAdultCount(int personnel) {
		this.personnel = personnel;
		return this;
	}
	public TrainInquiryMenu setSeniorCount(int personnel) {
		this.personnel = personnel;
		return this;
	}
	
	public void addCommand(ArrayList<TrainVo> command) {
		commandQue.add(command);
	}
	
	public void setLayer(int layer) {
		refLayeredPane.setLayer(refTrainList, layer);
	}

	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
			if(e.getActionCommand().equals("예 매")) 
			{
				if(seatList.size() < personnel) {
					showDialog("<html><center>인원 수 보다 좌석의 수가 적습니다.</center><center>나머지 좌석은 무작위 혹은 입석으로 선택됩니다.</center></html>", 0);
				}
				else {
					String seat = "";
					Collections.sort(seatList);
					
					for(Integer i : seatList) {
						seat += i + " ";
					}
					showDialog("<html>현재 선택된 좌석으로 진행 하시겠습니까?<br><center>" + seat + "</center></html>", 1);
				}
			}
			//좌석 선택 화면일 때
			else if(e.getActionCommand().contains("뒤로") && (refLayeredPane.getComponentCountInLayer(0) > 0))
			{
				refLayeredPane.setLayer(refTrainList, SHOW);
				
				clearSeats();
			}
			//열차 선택 화면일 때
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
				
				if (color.equals(SKY_BLUE) && seatList.size() < personnel) {
					System.out.println(e.getActionCommand() + " SKY BLUE");
					btn.setBackground(Color.RED);
					seatList.add(Integer.parseInt(seat));
				}
				else if(color.equals(Color.RED)) {
					System.out.println(e.getActionCommand() + " RED");
					btn.setBackground(SKY_BLUE);
					seatList.remove(new Integer(Integer.parseInt(seat)));
				}
				System.out.println("seats size " + seatList.size());
			}	
		}
		else if(e.getSource() instanceof JComboBox)
		{
			String type = ((JComboBox<String>)e.getSource()).getSelectedItem().toString();
			updateTrainList(new ArrayList<TrainVo>(trainList), type);
		}
		else 
		{
			
		}
	}
	
	public void updateSeatButton(TrainVo vo) {
		ArrayList<String> seats = dao.getSeatList(vo);
		if(seats == null) return;
		
		for(String s : seats) 
		{
			for(JButton b : seatButtons)
			{
				if(b.getActionCommand().equals(s)) {
					b.setBackground(Color.GRAY);
					b.setEnabled(false);
				}
			}
		}
	}
	
	private void updateTrainList(ArrayList<TrainVo> list, String type) {
		trainListPanel.removeAll();
		System.out.println("모든 열차 정보 수 : " + trainList.size());
		int cnt = 0;
		for(TrainVo vo : list) {
			if(type.equals(vo.getTrainName()) || type.equals("모두")) {
				TrainInformationPanel ti = new TrainInformationPanel(vo, this);
				if(!ti.hasEmptySeat(personnel)) {
					ti.setEnabled(false);
				}
				
				GridBagConstraints gbc_panel = new GridBagConstraints();
				gbc_panel.insets = new Insets(0, 0, 3, 0);
				
				if(cnt >= list.size() - 1) {
					gbc_panel.anchor = GridBagConstraints.NORTH;
					gbc_panel.fill = GridBagConstraints.HORIZONTAL;
				}
				else {
					gbc_panel.fill = GridBagConstraints.BOTH;
				}
				
				gbc_panel.gridx = 0;
				gbc_panel.gridy = cnt;
				
				trainListPanel.add(ti, gbc_panel);
				cnt++;
			}
		}
		
		double[] newRowWeights = new double[trainListPanel.getComponentCount()];
		for(int j = 0; j < newRowWeights.length; j++) {
			if(j < newRowWeights.length - 1)
				newRowWeights[j] = 0.0;
			else
				newRowWeights[j] = 1.0;
		}
		((GridBagLayout)trainListPanel.getLayout()).rowWeights = newRowWeights;
		
		trainListPanel.updateUI();

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
		jd.setSize(310, 120);
		jd.setLocation(ScreenUtil.getCurrentCenter(this, jd));
		jd.setResizable(false);
		jd.getContentPane().setLayout(new FlowLayout());
		
		JLabel message = new JLabel(msg);
		JButton confirm = new JButton("확인");
		JButton cancel = new JButton("취소");
		message.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		jd.getContentPane().add(message);
		jd.getContentPane().add(confirm);
		jd.getContentPane().add(cancel);
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jd.dispose();
			}
		});
		
		JFrame thisFrame = this;
		
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(hasOverlapSeat(dao.getSeatList(currentTrainVo), seatList)) {
					new NoticeDialog("<html><center>이미 예매 완료된 좌석이 있습니다.</center><center>다시 선택해주세요.</center></html>", thisFrame);
					clearSeats();
					updateSeatButton(currentTrainVo);
					jd.dispose();
					return;
				}
				
				if(type == 0) {
					int emptyCount = dao.getEmptySeatCount(currentTrainVo) - seatList.size();
					int remainPersonnel = personnel - seatList.size();
					
					if(emptyCount < remainPersonnel) 
					{
						setRandomSeat(emptyCount);
					}
					else 
					{
						setRandomSeat(remainPersonnel);
					}
				}
				reservationTrain();
				jd.dispose();
				subDialog("기차 예약이 완료되었습니다.");
			}
		});
		
		jd.setVisible(true);
	}
	
	private boolean hasOverlapSeat(ArrayList<String> dbSeats, ArrayList<Integer> seat) {
		for(Integer i : seat) {
			for(String s : dbSeats) {
				if(i == Integer.parseInt(s))
					return true;
			}
		}
		
		return false;
	}
	
	private void subDialog(String msg) {
		@SuppressWarnings("unused")
		NoticeDialog nd = new NoticeDialog(msg, this);
	}
	
	private void setRandomSeat(int count) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		for(JButton b : seatButtons) {
			if(b.getBackground().equals(SKY_BLUE)) {
				list.add(Integer.parseInt(b.getActionCommand()));
			}
		}
		
		for(int i = 0; i < count; i++) {
			int rand = (int)(Math.random() * list.size());
			Integer num = list.get(rand);
			seatList.add(num);
			list.remove(num);
		}
	}
	
	private void reservationTrain() {
		TicketVo ticket = new TicketVo(dao);
		((MainMenu)parent).addReservation(ticket.getTicket_id());
		if(currentTrainVo == null)
			System.out.println("currentVo is null");
		String tmpPrice = "1000";
		
		ticket.setDeppland_place(currentTrainVo.getDepPlace())
		.setArrpland_place(currentTrainVo.getArrPlace())
		.setTrain_name(currentTrainVo.getTrainName())
		.setCar_number(currentTrainVo.getCarNumber())
		.setPersonnel(String.format("%d", personnel))
		.setSeat(getSeatString())
		.setDeppland_time(currentTrainVo.getDepplandTime())
		.setArrpland_time(currentTrainVo.getArrplandTime())
		.setPrice(tmpPrice)
		.setTicketing_day(Constants.getTodayTimeToString());
		
		dao.insertTicketData(ticket);
		
		ArrayList<TrainVo> newList = commandQue.poll();
		if(newList == null) {
			System.out.println("dispose");
			parent.setVisible(true);
			dispose();
		}
		else {
			trainList = newList;
			updateTrainList(trainList, "모두");
			setLayer(SHOW);
			initLabel();
			clearSeats();
		}
	}
	
	private void clearSeats() {
		for(JButton b : seatButtons) {
			b.setBackground(SKY_BLUE);
			b.setEnabled(true);
		}
		
		seatList.clear();
	}
	
	private void initLabel() {
		refPresentlyDate.setText(getDateString(trainList.get(0).getDepplandTime()));
		refDepStation.setText(trainList.get(0).getDepPlace());
		refArrStation.setText(trainList.get(0).getArrPlace());
	}
	
	private String getSeatString() {
		StringBuilder sb = new StringBuilder();
		
		seatList.sort(new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				if(o1 > o2) return 1;
				else if(o1 == o2) return 0;
				else return -1;
			}
		});
		
		for(Integer i : seatList) {
			sb.append(i).append(" ");
		}
		
		return sb.toString().trim();
	}
	
	public void setCurrentTrainVo(TrainVo currentTrainVo) {
		this.currentTrainVo = currentTrainVo;
	}
}










