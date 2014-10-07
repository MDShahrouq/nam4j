package it.unipr.ce.dsg.gamidroid.android.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Window;

public class Utils {
	
	public enum Orientation {
		LANDSCAPE,
		PORTRAIT
	};

	/**
	 * Method to check if the app is running on a smartphone or on a tablet.
	 * 
	 * @param mContext
	 *            The context of the application.
	 *            
	 * @return true if the app is running on a tablet, false otherwise
	 */
	public static boolean isTablet(Context mContext) {
		return (mContext.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}
	
	/**
	 * Method to get the size of the screen.
	 */
	public static int[] getScreenSize(Context mContext, Window win) {
		final DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();

		Rect rect = new Rect();
		win.getDecorView().getWindowVisibleDisplayFrame(rect);
		int statusHeight = rect.top;
		int contentViewTop = win.findViewById(Window.ID_ANDROID_CONTENT)
				.getTop();
		int titleHeight = contentViewTop - statusHeight;

		/* Getting screen size */
		int screenWidth = metrics.widthPixels;
		int screenHeight = metrics.heightPixels - titleHeight - statusHeight;
		
		return new int[]{screenWidth, screenHeight};

	}

}
