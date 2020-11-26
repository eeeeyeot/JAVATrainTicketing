package main;

import java.util.ArrayList;
import openAPI.TrainAPI;
import openAPI.TrainVo;

public class TrainAPITest {
	public static void main(String[] args) {
		TrainAPI api = TrainAPI.getInstance();
		System.out.println("wait for loading");
		
		/*
		 * ArrayList<TrainVo> list = api.getTrainList("서울", "대전", "20201126");
		 * 
		 * for(TrainVo vo : list) { System.out.println(vo); }
		 */
	}
}

