package main;

import java.util.ArrayList;
import java.util.Scanner;

import openAPI.TrainAPI;
import trainInfo.TrainInfo;

public class TrainAPITest {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		TrainAPI api = TrainAPI.getInstance();
		
		ArrayList<TrainInfo> list = new ArrayList<TrainInfo>(); 
		list.addAll(api.getTrainList(sc.next(), sc.next(), "20201116"));
		
		for(TrainInfo t : list)
			System.out.println(t);
		
		sc.close();
	}
}

