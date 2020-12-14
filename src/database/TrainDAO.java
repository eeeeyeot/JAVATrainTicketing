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

import constants.Constants;
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
	
	public int getEmptySeatCount(TrainVo vo) {
		int cnt = 0;
		
		String sql = "SELECT seat FROM TICKET WHERE train_name = '" + vo.getTrainName() + 
				"' and car_number = " + vo.getCarNumber() + " AND DEPPLAND_TIME BETWEEN " +
				"to_date('" + vo.getDepplandTime() + "', 'yyyyMMddHH24MI') AND to_date('" + 
				vo.getArrplandTime() + "', 'yyyyMMddHH24MI')";
		try {
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String[] seats = rs.getString("SEAT").split(" ");
				for(String s : seats) {
					if(!s.equals("null"))
						cnt++;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return 40 - cnt;
	}
	
	public ArrayList<String> getSeatList(TrainVo vo)
	{
		ArrayList<String> list = new ArrayList<String>();
		
		try {
			//출발 시간과
			String sql = "SELECT seat FROM TICKET WHERE train_name = '" + vo.getTrainName() + 
					"' and car_number = " + vo.getCarNumber() + " AND DEPPLAND_TIME BETWEEN " +
					"to_date('" + vo.getDepplandTime() + "', 'yyyyMMddHH24MI') AND to_date('" + 
					vo.getArrplandTime() + "', 'yyyyMMddHH24MI')";
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
					"t.DEPPLAND_TIME, t.ARRPLAND_TIME, t.PRICE, t.TICKETING_DAY " + 
					"FROM TICKET t, RESERVATION r " + 
					"WHERE r.USER_ID = '" + userId + "' AND r.TICKET_ID = t.TICKET_ID";
			
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				if(Constants.getTodayTimeToString().compareTo(getDateToString(rs.getString("ARRPLAND_TIME"))) > 0) {
					deleteReservation(rs.getString("TICKET_ID"));
					continue;
				}
				
				TicketVo vo = new TicketVo()
						.setTicket_id(rs.getString("TICKET_ID"))
						.setDeppland_place(rs.getString("DEPPLAND_PLACE"))
						.setArrpland_place(rs.getString("ARRPLAND_PLACE"))
						.setTrain_name(rs.getString("TRAIN_NAME"))
						.setCar_number(rs.getString("CAR_NUMBER"))
						.setSeat(rs.getString("SEAT"))
						.setPersonnel(rs.getString("PERSONNEL"))
						.setDeppland_time(getDateToString(rs.getString("DEPPLAND_TIME")))
						.setArrpland_time(getDateToString(rs.getString("ARRPLAND_TIME")))
						.setPrice(rs.getString("PRICE"))
						.setTicketing_day(getDateToString(rs.getString("TICKETING_DAY")));
				
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
	
	public ArrayList<SeasonTicketVo> getSeasonList(String user_id) {
		ArrayList<SeasonTicketVo> list = new ArrayList<SeasonTicketVo>();
		String sql = "SELECT s.SEASON_ID, s.DEPPLAND_PLACE, s.ARRPLAND_PLACE, s.TERM, s.EFFECTIVE_DATE, s.EXPIRATION_DATE " +
				"FROM SEASON_TICKET s, RESERVATION_SEASON r WHERE s.SEASON_ID = r.SEASON_ID AND r.USER_ID = '" + user_id + "'";
		System.out.println(sql);
		try {
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				if(Constants.getTodayTimeToString().compareTo(getDateToString(rs.getString("EXPIRATION_DATE"))) > 0) {
					deleteSeason(rs.getString("SEASON_ID"));
					continue;
				}
				
				SeasonTicketVo vo = new SeasonTicketVo()
						.setSeason_id(rs.getString("SEASON_ID"))
						.setDepplandPlace(rs.getString("DEPPLAND_PLACE"))
						.setArrplandPlace(rs.getString("ARRPLAND_PLACE"))
						.setTerm(Integer.parseInt(rs.getString("TERM")))
						.setEffDate(getDateToString(rs.getString("EFFECTIVE_DATE")))
						.setExpDate(getDateToString(rs.getString("EXPIRATION_DATE")));
				
				list.add(vo);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if(list.size() == 0) return null;
		
		return list;
	}
	
	public boolean deleteReservation(String ticket_id) {
		try {
			String sql = "DELETE FROM ticket WHERE ticket_id = " + ticket_id;
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			
			sql = "DELETE FROM reservation WHERE ticket_id = " + ticket_id;
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean deleteSeason(String season_id) {
		try {
			String sql = "DELETE FROM season_ticket WHERE season_id = " + season_id;
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			
			sql = "DELETE FROM reservation_season WHERE season_id = " + season_id;
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean insertSeasonReservation(String userId, String seasonId) {
		String sql = "insert into reservation_season values ('" + userId + "', " + seasonId + ")";
		System.out.println(sql);
		try {
			rs = stmt.executeQuery(sql);
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean insertReservationData(String userId, String ticketId) {
		String sql = "insert into reservation values ('" + userId + "', " + ticketId + ")";
		System.out.println(sql);
		try {
			rs = stmt.executeQuery(sql);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
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
	
	public boolean insertSeasonData(SeasonTicketVo vo) {
		try {
			String sql = "INSERT INTO season_ticket VALUES (" + vo.getSeason_id() + ", '" + vo.getDepplandPlace() + "', '" + vo.getArrplandPlace() + 
					"', " + vo.getTerm() + ", " + vo.getEffDateByFormat() + ", " + vo.getExpDateByFormat() + ")";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean insertTicketData(TicketVo vo) {
		try {
			String sql = "insert into ticket values (" + vo.getTicket_id() + ", '" + vo.getDeppland_place() + "', '" 
					+ vo.getArrpland_place() + "', '" + vo.getTrain_name() + "', " + vo.getCar_number() + ", '" + vo.getSeat() 
					+ "', " + vo.getPersonnel() + ", "+ vo.getDepTimeByFormat() + ", "
					+ vo.getArrTimeByFormat() + ", " + vo.getPrice() + ", " + vo.getTicketingDayByFormat() + ")";
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
	
	public int getLastSeasonId() {
		int result = -1;
		try {
			String sql = "SELECT MAX(season_id) FROM SEASON_TICKET";
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				String maxSeasonId = rs.getString("MAX(SEASON_ID)");
				if(maxSeasonId != null) 
					result = Integer.parseInt(maxSeasonId);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int getLastTicketId() {
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
			.append(String.format("%02d", cal.get(Calendar.DAY_OF_MONTH))).append(String.format("%02d", cal.get(Calendar.HOUR_OF_DAY)))
			.append(String.format("%02d", cal.get(Calendar.MINUTE)));
		
		return sb.toString();
	}
}
