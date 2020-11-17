package util;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;

public class ScreenUtil {
	public static Point getCenterPosition(Window window) {
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension currentWindowSize = window.getSize();
		
		int x = (screenSize.width / 2) - (currentWindowSize.width / 2);
		int y = (screenSize.height / 2) - (currentWindowSize.height / 2);
		
		return new Point(x, y);
	}
}
