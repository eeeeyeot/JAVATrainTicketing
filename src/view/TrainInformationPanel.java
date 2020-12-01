package view;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.Color;

import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import database.TrainDAO;
import openAPI.*;

@SuppressWarnings("serial")
public class TrainInformationPanel extends JPanel implements ActionListener{
	private JLabel trainIdentityLabel;
	private JLabel depplandTimeLabel;
	private JLabel arrplandTimeLabel;
	private JLabel personnelLabel;
	private JButton priceButton;
	
	private TrainInquiryMenu parent;
	private TrainDAO dao;
	private TrainVo vo;
	
	int emptySeat;
	
	public TrainInformationPanel() {
		setForeground(Color.BLACK);
		setBorder(null);
		setLayout(new GridLayout(1, 4, 0, 0));
		
		Font font = new Font("맑은 고딕", Font.PLAIN, 20);
		
		trainIdentityLabel = new JLabel();
		trainIdentityLabel.setFont(font);
		trainIdentityLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(trainIdentityLabel);
		
		depplandTimeLabel = new JLabel();
		depplandTimeLabel.setFont(font);
		depplandTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(depplandTimeLabel);
		
		arrplandTimeLabel = new JLabel();
		arrplandTimeLabel.setFont(font);
		arrplandTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(arrplandTimeLabel);
		
		personnelLabel = new JLabel();
		personnelLabel.setFont(font);
		personnelLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(personnelLabel);
		
		priceButton = new JButton("1,000원");
		priceButton.setFont(font);
		add(priceButton);
		
		TitledBorder titled = new TitledBorder(new LineBorder(Color.black, 2));
		trainIdentityLabel.setBorder(titled);
		depplandTimeLabel.setBorder(titled);
		arrplandTimeLabel.setBorder(titled);
		personnelLabel.setBorder(titled);
		priceButton.setBorder(titled);
		
		priceButton.addActionListener(this);
	}
	
	public TrainInformationPanel(TrainVo vo, TrainInquiryMenu parent) {
		this();
		this.parent = parent;
		this.vo = vo;
		dao = TrainDAO.getInstance();
		trainIdentityLabel.setText("<html>" + vo.getTrainName() + "<br><center>" + vo.getCarNumber() + "</center></html>");
		depplandTimeLabel.setText(toDate(vo.getDepplandTime()));
		arrplandTimeLabel.setText(toDate(vo.getArrplandTime()));
		
		emptySeat = dao.getEmptySeatCount(vo);
		if(emptySeat == 0) {
			personnelLabel.setText("좌석 없음");
		}
		else {
			personnelLabel.setText(dao.getEmptySeatCount(vo) + " 좌석 여유");
		}
	}
	
	public String toDate(String time) {
		StringBuilder sb = new StringBuilder();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMMddHHmm");
		try { cal.setTime(fm.parse(time)); } catch (ParseException e) { e.printStackTrace(); }
		
		sb.append(String.format("%02d", cal.get(Calendar.HOUR_OF_DAY))).append(" : ").append(String.format("%02d", cal.get(Calendar.MINUTE)));
		
		return sb.toString();
	}
	
	public void actionPerformed(ActionEvent e) {
		//선택된 TrainVo를 가져와야함
		parent.setLayer(0);
		parent.setCurrentTrainVo(vo);
		parent.initSeatButton(vo);
	}
	
	public boolean hasEmptySeat(int personnel) {
		if(personnel <= emptySeat)
			return true;
		else
			return false;
	}
	
	public void setEnabled(boolean enabled) {
		this.setBackground(Color.LIGHT_GRAY);
	}
}

























