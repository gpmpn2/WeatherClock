package clock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 
 * @author Grant
 * Parses the time data for a city
 */
public class Time {
			
	/**
	 * Given a timezone, it will return it's actual time
	 * @param timezone
	 * @return
	 */
	public static String getTime(String timezone) {
		
		Date date = new Date();
		
		final SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy hh:mm:ss a z");

		// Give it to me in GMT time.
		sdf.setTimeZone(TimeZone.getTimeZone(timezone));
		return sdf.format(date).replace("EET", "SAST");
	}
	
	/**
	 * Gets the military time from a specific String date supplied
	 * @param time
	 * @return
	 */
	public static int getMilitaryTime(String time){
		
		String hours[] = time.split(":");
		String hour = hours[0].substring(hours[0].length() - 2, hours[0].length());
		
		if((time.contains("PM") && !time.contains("12:")) || ((time.contains("12:") && time.contains("AM")))) {
			return 12 + Integer.parseInt(hour);
		} else {
			return Integer.parseInt(hour);
		}
	}
	
}
