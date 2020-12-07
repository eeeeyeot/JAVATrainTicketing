package constants;

import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JTextField;

public class Constants {
	public static final String ONE_WAY = "10";
	public static final String ROUND_TRIP = "20";
	public static final String[] DAY_OF_WEEK = { "일" , "월", "화", "수", "목", "금", "토" };
	
	private static Calendar cal = Calendar.getInstance();
	
	public static String getTodayDateToString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(cal.get(Calendar.YEAR)).append(String.format("%02d", cal.get(Calendar.MONTH) + 1))
		.append(String.format("%02d", cal.get(Calendar.DAY_OF_MONTH)));
		
		return sb.toString();
	}
	
	public static String getTodayTimeToString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(cal.get(Calendar.YEAR)).append(String.format("%02d", cal.get(Calendar.MONTH) + 1))
		.append(String.format("%02d", cal.get(Calendar.DAY_OF_MONTH))).append(String.format("%02d", cal.get(Calendar.HOUR_OF_DAY)))
		.append(String.format("%02d", cal.get(Calendar.MINUTE)));
		
		return sb.toString();
	}
	
	public static String getAddedDate(int field, int amount) {
		StringBuilder sb = new StringBuilder();
		cal.add(field, amount);
		
		sb.append(getTodayTimeToString());
		
		cal = Calendar.getInstance();
		return sb.toString();
	}
	
	public static String getDateKorean(String time) {
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmm");
		
		try {
			cal.setTime(sf.parse(time));
		}
		catch(Exception e) {}
		
		sb.append(cal.get(Calendar.YEAR)).append("년 ").append(cal.get(Calendar.MONTH) + 1).append("월 ").append(cal.get(Calendar.DAY_OF_MONTH)).append("일 ")
		.append(cal.get(Calendar.HOUR_OF_DAY)).append("시 ").append(cal.get(Calendar.MINUTE)).append("분");
		
		cal = Calendar.getInstance();
		
		return sb.toString();
	}
	
	public static String getDateKoreanWithLineBreak(String time) {
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmm");
		
		try {
			cal.setTime(sf.parse(time));
		}
		catch(Exception e) {}
		
		sb.append("<html><center>").append(cal.get(Calendar.YEAR)).append("년 ").append(cal.get(Calendar.MONTH) + 1).append("월 ")
		.append(cal.get(Calendar.DAY_OF_MONTH)).append("일 ").append("</center><center>")
		.append(cal.get(Calendar.HOUR_OF_DAY)).append("시 ").append(cal.get(Calendar.MINUTE)).append("분").append("</center></html>");
		
		cal = Calendar.getInstance();
		
		return sb.toString();
	}
	
	public static void numberFormatLimit(KeyEvent e, int limit) {
		char c = e.getKeyChar();
		if (!Character.isDigit(c)) {
			e.consume();
			return;
		}

		if (((JTextField) e.getSource()).getText().length() > limit - 1) {
			e.consume();
			return;
		}
	}
}