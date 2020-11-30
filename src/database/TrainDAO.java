package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import openAPI.TrainVo;

@SuppressWarnings("unused")
public class TrainDAO
{
	private static TrainDAO instance = null;
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521/xe";
	String user = "green";
	String password = "green1234";
	
	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	private ResultSetMetaData rsmd;
	
	private TrainDAO(){
		try {
			connDB();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static TrainDAO getInstance() {
		if(instance == null)
			instance = new TrainDAO();
		
		return instance;
	}
	
	public ArrayList<String> getSeatList(TrainVo vo)
	{
		ArrayList<String> list = new ArrayList<String>();
		
		try {
			String sql = "SELECT seat FROM TICKET WHERE train_name = '" + vo.getTrainName() + 
					"' and car_number = '" + vo.getTrainNo() + "'";
			System.out.println(sql);
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String[] seats = rs.getString("SEAT").split(" ");
				
				for(String s : seats)
					list.add(s);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("no elements");
			return null;
		}
		
		return list;
	}
	
	public ArrayList<TicketVo> getTicketList(String userId) {
		ArrayList<TicketVo> list = new ArrayList<TicketVo>();
		
		try {
			String sql = "SELECT t.TICKET_ID, t.DEPPLAND_PLACE, t.ARRPLAND_PLACE, t.TRAIN_NAME, t.CAR_NUMBER, t.SEAT, t.PERSONNEL, " + 
					"t.DEPPLAND_TIME, t.ARRPLAND_TIME, t.PRICE, t.TICKETING_DAY, t.TICKET_TYPE, t.TERM, t.EFFECTIVE_DATE, t.EXPIRATION_DATE " + 
					"FROM TICKET t, RESERVATION r " + 
					"WHERE r.USER_ID = '" + userId + "' AND r.TICKET_ID = t.TICKET_ID";
			
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			rsmd = rs.getMetaData();
			
			while(rs.next()) {
				TicketVo vo = new TicketVo();
				
				vo.setTicket_id(rs.getString("TICKET_ID"));
				vo.setDeppland_place(rs.getString("DEPPLAND_PLACE"));
				vo.setArrpland_place(rs.getString("ARRPLAND_PLACE"));
				vo.setTrain_name(rs.getString("TRAIN_NAME"));
				vo.setCar_number(rs.getString("CAR_NUMBER"));
				vo.setSeat(rs.getString("SEAT"));
				vo.setPersonnel(rs.getString("PERSONNEL"));
				vo.setDeppland_time(getDateToString(rs.getString("DEPPLAND_TIME")));
				vo.setArrpland_time(getDateToString(rs.getString("ARRPLAND_TIME")));
				vo.setPrice(rs.getString("PRICE"));
				vo.setTicketing_day(getDateToString(rs.getString("TICKETING_DAY")));
				vo.setTicket_type(rs.getString("TICKET_TYPE"));
				vo.setTerm(rs.getString("TERM"));
				vo.setEffective_date(getDateToString(rs.getString("EFFECTIVE_DATE")));
				vo.setExpiration_date(getDateToString(rs.getString("EXPIRATION_DATE")));
				
				list.add(vo);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if(list.size() == 0) return null;
		
		return list;
	}
	
	public boolean insertUserData(UserVo vo)
	{
		String id = vo.getId();
		String pw = vo.getPw();
		String name = vo.getName().length() == 0 ? "null" : "'" + vo.getName() + "'";
		String contact = vo.getContact().length() == 0 ? "null" : "'" + vo.getContact() + "'";;
		String register = vo.getRegister();
		
		try {
			String sql = "insert into userdata values ('" + id + "', '" + pw + 
					"', " + name + ", " + contact +	", to_date('" + register + "', 'yyyyMMddHH24MIss'))";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean insertReservationData(String userId, String ticketId) {
		try {
			String sql = "insert into reservation values ('" + userId + "', " + ticketId + ")";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean insertTicketData(TicketVo vo) {
		try {
			String sql = "insert into ticket values (" + vo.getTicket_id() + ", '" + vo.getDeppland_place() + "', '" 
					+ vo.getArrpland_place() + "', '" + vo.getTrain_name() + "', " + vo.getCar_number() + ", '" + vo.getSeat() 
					+ "', " + vo.getPersonnel() + ", "+ vo.getDepTimeDB() + ", "
					+ vo.getArrTimeDB() + ", " + vo.getPrice() + ", " + vo.getTicketingDayDB() + ", " + vo.getTicket_type() 
					+ ", " + vo.getTerm() + ", " + vo.getEffective_date() + ", " + vo.getExpiration_date() + ")";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public UserVo checkLoginData(String id, String pw) {
		UserVo userVo = null;
		try {
			String sql = "select * from userdata "
					+ "where user_id = '" + id + "' and user_pw = '" + pw + "'";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			
			if(!rs.next())
				return null;
			
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date register = sf.parse(rs.getString("register_date"));
			
			userVo = new UserVo(rs.getString("user_id"), rs.getString("user_pw"), 
					rs.getString("uname"), rs.getString("contact"), register);
		}
		catch(Exception e) 
		{
			e.printStackTrace();
			return null;
		}
		
		return userVo;
	}
	
	public int getLastId() {
		int result = -1;
		try {
			String sql = "select MAX(ticket_id) from ticket";
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				String maxTicketId = rs.getString("MAX(TICKET_ID)");
				if(maxTicketId != null)
					result = Integer.parseInt(maxTicketId);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			result = -1;
		}
		return result;
	}
	
	public void connDB() {
		try {
			Class.forName(driver);
			System.out.println("jdbc driver loading success.");
			con = DriverManager.getConnection(url, user, password);
			System.out.println("oracle connection success.");
			stmt = con.createStatement();
			System.out.println("statement create success.");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void finalize() throws Throwable {
		System.out.println("TrainDAO destroyed");
	}
	
	private String getDateToString(String sDate) {
		if(sDate == null) return null;
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sf.parse(sDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		sb.append(cal.get(Calendar.YEAR)).append(String.format("%02d", cal.get(Calendar.MONTH) + 1))
			.append(String.format("%02d", cal.get(Calendar.DAY_OF_MONTH))).append(String.format("%02d", cal.get(Calendar.HOUR)))
			.append(String.format("%02d", cal.get(Calendar.MINUTE)));
		
		return sb.toString();
	}
}
