package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("unused")
public class TrainDAO
{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521/xe";
	String user = "green";
	String password = "green1234";
	
	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	private ResultSetMetaData rsmd;
	
	public TrainDAO(){
		try {
			connDB();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean inputUserData(UserVo u)
	{
		try {
			String sql = "insert into userdata values ('" + u.getId() + "', '" + u.getPw() + 
					"', '" + u.getName() + "', '" + u.getContact() + 
					"', to_date('" + u.getRegister() + "', '" + "yyyyMMddHH24MIss'))";
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
}
