package view;

import java.util.ArrayList;
import java.util.Comparator;

import TrainInfo.TrainInfo;
import openAPI.TrainAPI;

public class Test
{
	public static void main(String[] args)
	{
		TrainAPI ta = TrainAPI.getInstance();
		ArrayList<TrainInfo> list = ta.getTrainList("서울", "대전", "20201117");
		list.sort(new Comparator<TrainInfo>() {
			public int compare(TrainInfo o1, TrainInfo o2)
			{
				return o1.getDepplandTime().compareTo(o2.getDepplandTime());
			}
		});
		
		for(TrainInfo ti : list)
			System.out.println(ti);
	}
}
