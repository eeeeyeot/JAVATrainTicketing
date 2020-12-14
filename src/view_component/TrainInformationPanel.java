package view_component;

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

import constants.Constants;
import database.TrainDAO;
import openAPI.*;
import view.TrainInquiryMenu;

@SuppressWarnings("serial")
public class TrainInformationPanel extends JPanel implements ActionListener{
	public final int[] overKTX = {4000, 6000, 5000};
	public final int[] underKTX = {2300, 3600, 2800};
	
	private JLabel trainIdentityLabel;
	private JLabel depplandTimeLabel;
	private JLabel arrplandTimeLabel;
	private JLabel personnelLabel;
	private JButton priceButton;
	
	private TrainInquiryMenu parent;
	private TrainDAO dao;
	private TrainVo vo;
	
	private int emptySeat;
	
	public TrainInformationPanel() {
		setBackground(Color.WHITE);
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
		priceButton.setBackground(new Color(135, 206, 250));
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

	public boolean hasEmptySeat(int personnel) {
		if(personnel <= emptySeat)
			return true;
		else
			return false;
	}
	
	public void setEnabled(boolean enabled) {
		this.setBackground(Color.LIGHT_GRAY);
	}
	
	public void actionPerformed(ActionEvent e) {
		//선택된 TrainVo를 가져와야함
		parent.setLayer(TrainInquiryMenu.HIDE);
		parent.setCurrentTrainVo(vo);
		parent.updateSeatButton(vo);
		if(vo.getTrainName().contains("KTX"))
			parent.setCharge(overKTX);
		else
			parent.setCharge(underKTX);
	}
	
	public void setChargeButtonText(int[] personnel) {
		StringBuilder charge = new StringBuilder();
		if(personnel == null) {
			charge.append("정기권 (무료)");
			vo.setFee(0);
		}
		else {
			int total = 0;
			if (vo.getTrainName().contains("KTX")) {
				for (int i = 0; i < personnel.length; i++) {
					total += overKTX[i] * personnel[i];
				}
			} else {
				for (int i = 0; i < personnel.length; i++) {
					total += underKTX[i] * personnel[i];
				}
			}
			vo.setFee(total);
			charge.append(Constants.dFormatter.format(total)).append("원");
		}
		priceButton.setText(charge.toString());
	}
}
