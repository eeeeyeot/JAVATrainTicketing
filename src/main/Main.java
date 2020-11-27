package main;

import view.StartMenu;
import java.lang.reflect.Field;
import java.nio.charset.Charset;

public class Main
{
	public static void main(String[] args)
	{
		try {
			Field charset = Charset.class.getDeclaredField("defaultCharset");
			charset.setAccessible(true);
			charset.set(null, null);
		} catch (Exception e) {

		}

		System.setProperty("file.encoding","UTF-8");
		new StartMenu().setVisible(true);
	}
}
