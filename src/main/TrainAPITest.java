package main;

import java.util.ArrayList;
import java.util.Scanner;

import openAPI.TrainAPI;
import openAPI.TrainVo;

public class TrainAPITest {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		TrainAPI api = TrainAPI.getInstance();
		
		ArrayList<TrainVo> list = new ArrayList<TrainVo>(); 
		list.addAll(api.getTrainList(sc.next(), sc.next(), "20201116"));
		
		for(TrainVo t : list)
			System.out.println(t);
		
		sc.close();
	}
}

