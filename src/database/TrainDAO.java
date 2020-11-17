package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

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
	
	public boolean checkLoginData(String id, String pw) {
		try {
			String sql = "select * from userdata "
					+ "where user_id = '" + id + "' and user_pw = '" + pw + "'";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			
			if(!rs.next())
				return false;
		}
		catch(Exception e) 
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
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
