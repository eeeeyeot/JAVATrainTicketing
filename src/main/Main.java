package main;

import view.StartMenu;

public class Main
{
	public static void main(String[] args)
	{
		new StartMenu().setVisible(true);
		System.out.println("main method closing");
	}
}