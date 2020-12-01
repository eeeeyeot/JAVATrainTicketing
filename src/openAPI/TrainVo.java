package openAPI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TrainVo
{
	private String trainName;
	private String carNumber;
	private String depplandTime;
	private String arrplandTime;
	private String depPlace;
	private String arrPlace;
	
	public TrainVo() {}
	public TrainVo(String trainName, String trainNo, String depplandTime, String arrplandTime, String depPlace, String arrPlace) {
		this.trainName = trainName;
		this.carNumber = trainNo;
		this.depplandTime = depplandTime;
		this.arrplandTime = arrplandTime;
		this.depPlace = depPlace;
		this.arrPlace = arrPlace;
	}
	
	public String getTrainName() { return trainName; }
	public void setName(String name) { this.trainName = name; }
	
	public String getCarNumber() { return carNumber; }
	public void setCarNumber(String trainNo) { this.carNumber = trainNo; }
	
	public String getDepplandTime() { return depplandTime; }
	public void setDepplandTime(String depplandTime) { this.depplandTime = depplandTime; }
	
	public String getArrplandTime() { return arrplandTime; }
	public void setArrplandTime(String arrplandTime) { this.arrplandTime = arrplandTime; }
	
	public String toString() {
		return trainName + " "+ carNumber + " " + dateFormatToString(depplandTime) + " " + dateFormatToString(arrplandTime);
	}
	
	public String toFileString() {
		return trainName + " "+ carNumber + " " + depplandTime + " " + arrplandTime;
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
	public String getDepPlace() { return depPlace; }
	public void setDepPlace(String depPlace) { this.depPlace = depPlace; }
	
	public String getArrPlace() { return arrPlace; }
	public void setArrPlace(String arrPlace) { this.arrPlace = arrPlace; }
}
