package openAPI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TrainVo
{
	private String trainName;
	private String trainNo;
	private String depplandTime;
	private String arrplandTime;
	
	public String getName() { return trainName; }
	public void setName(String name) { this.trainName = name; }
	
	public String getTrainNo() { return trainNo; }
	public void setTrainNo(String trainNo) { this.trainNo = trainNo; }
	
	public String getDepplandTime() { return depplandTime; }
	public void setDepplandTime(String depplandTime) { this.depplandTime = depplandTime; }
	
	public String getArrplandTime() { return arrplandTime; }
	public void setArrplandTime(String arrplandTime) { this.arrplandTime = arrplandTime; }
	
	public String toString() {
		return "[기차 이름 : " + trainName + "][기차 번호 : " + trainNo + "][출발 시간 : " + dateFormatToString(depplandTime) + "][도착 시간 : " + dateFormatToString(arrplandTime) + "]";
	}
	
	public String dateFormatToString(String date) {
		StringBuffer	sb	= new StringBuffer();
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMMddHHmm");
		Calendar		cal	= Calendar.getInstance();
		try {
			cal.setTime(fm.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		sb.append(cal.get(Calendar.YEAR)).append("년 ").append(cal.get(Calendar.MONTH) + 1).append("월 ").append(cal.get(Calendar.DAY_OF_MONTH))
				.append("일 ").append(cal.get(Calendar.HOUR_OF_DAY)).append("시 ").append(cal.get(Calendar.MINUTE)).append("분 ");
		
		return sb.toString();
	}
}
