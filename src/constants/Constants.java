package constants;

import java.util.Calendar;

public class Constants {
	public static final String ONE_WAY = "10";
	public static final String ROUND_TRIP = "20";
	public static final String[] DAY_OF_WEEK = { "일" , "월", "화", "수", "목", "금", "토" };
	
	public static String getTodayToString() {
		StringBuilder sb = new StringBuilder();
		Calendar cal = Calendar.getInstance();
		
		sb.append(cal.get(Calendar.YEAR)).append(String.format("%02d", cal.get(Calendar.MONTH) + 1))
		.append(String.format("%02d", cal.get(Calendar.DAY_OF_MONTH))).append(String.format("%02d", cal.get(Calendar.HOUR_OF_DAY)))
		.append(String.format("%02d", cal.get(Calendar.MINUTE)));
		
		return sb.toString();
	}
}