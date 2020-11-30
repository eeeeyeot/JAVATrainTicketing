package constants;

import java.util.Calendar;

public class Constants {
	public static final String ONE_WAY = "10";
	public static final String ROUND_TRIP = "20";
	
	public static String getTodayDate() {
		StringBuilder sb = new StringBuilder();
		Calendar cal = Calendar.getInstance();
		
		sb.append(cal.get(Calendar.YEAR)).append(cal.get(Calendar.MONTH) + 1)
		.append(cal.get(Calendar.DAY_OF_MONTH)).append(cal.get(Calendar.HOUR_OF_DAY))
		.append(cal.get(Calendar.MINUTE));
		
		return sb.toString();
	}
}