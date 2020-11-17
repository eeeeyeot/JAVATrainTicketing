package database;

import java.util.Calendar;
import java.util.Date;

public class UserVo {
	private String id;
	private String pw;
	private String name;
	private String contact;
	private Date register;
	
	public UserVo(String id, String pw, String name, String contact, Date register) {
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.contact = contact;
		this.register = register;
	}
	
	public String getId() {
		return id;
	}
	public String getPw() {
		return pw;
	}
	public String getName() {
		return name;
	}
	public String getContact() {
		return contact;
	}
	public String getRegister() {
		return dateToString(register);
	}
	
	private String dateToString(Date date)
	{
		StringBuffer	sb	= new StringBuffer();
		Calendar		cal	= Calendar.getInstance();
		cal.setTime(date);
		sb.append(cal.get(Calendar.YEAR)).append(cal.get(Calendar.MONTH) + 1)
			.append(cal.get(Calendar.DAY_OF_MONTH)).append(cal.get(Calendar.HOUR_OF_DAY))
			.append(cal.get(Calendar.MINUTE)).append(cal.get(Calendar.SECOND));

		return sb.toString();
	}
}
