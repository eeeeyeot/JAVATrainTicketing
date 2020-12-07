package main;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;

import openAPI.TrainAPI;

@SuppressWarnings("unused")
public class TrainAPITest {
	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmm");
		
		String date = "202012311614";
		try {
			cal.setTime(sf.parse(date));
		}
		catch(Exception e) {
			
		}
		cal.add(Calendar.DAY_OF_MONTH, 10);
		String str = cal.get(Calendar.YEAR) + " " + (cal.get(Calendar.MONTH) + 1) + 
				" " + cal.get(Calendar.DAY_OF_MONTH) + " " + cal.get(Calendar.HOUR_OF_DAY) + 
				" " + cal.get(Calendar.MINUTE);
				
		
		System.out.println(str);
	}
}
