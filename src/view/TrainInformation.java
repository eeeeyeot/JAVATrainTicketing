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

import openAPI.*;

@SuppressWarnings("serial")
public class TrainInformation extends JPanel implements ActionListener{
	private JLabel trainIdentityLabel;
	private JLabel depplandTimeLabel;
	private JLabel arrplandTimeLabel;
	private JButton priceButton;
	
	private TrainInquiry parent;
	private TrainVo vo;

	
	public TrainInformation() {
		setForeground(Color.BLACK);
		setBorder(null);
		setLayout(new GridLayout(0, 4, 0, 0));
		
		trainIdentityLabel = new JLabel();
		trainIdentityLabel.setFont(new Font("굴림", Font.PLAIN, 20));
		trainIdentityLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(trainIdentityLabel);
		
		depplandTimeLabel = new JLabel();
		depplandTimeLabel.setFont(new Font("굴림", Font.PLAIN, 20));
		depplandTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(depplandTimeLabel);
		
		arrplandTimeLabel = new JLabel();
		arrplandTimeLabel.setFont(new Font("굴림", Font.PLAIN, 20));
		arrplandTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(arrplandTimeLabel);
		
		priceButton = new JButton("0,000원");
		priceButton.setFont(new Font("굴림", Font.PLAIN, 20));
		add(priceButton);
		
		TitledBorder titled = new TitledBorder(new LineBorder(Color.black, 2));
		trainIdentityLabel.setBorder(titled);
		depplandTimeLabel.setBorder(titled);
		arrplandTimeLabel.setBorder(titled);
		priceButton.setBorder(titled);
		
		priceButton.addActionListener(this);
	}
	
	public TrainInformation(TrainVo vo, TrainInquiry parent) {
		this();
		this.parent = parent;
		this.vo = vo;
		trainIdentityLabel.setText("<html>" + vo.getTrainName() + "<br><center>" + vo.getTrainNo() + "</center></html>");
		depplandTimeLabel.setText(toDate(vo.getDepplandTime()));
		arrplandTimeLabel.setText(toDate(vo.getArrplandTime()));
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
	}
}

























