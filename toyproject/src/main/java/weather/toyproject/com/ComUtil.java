package weather.toyproject.com;

public class ComUtil {
	
	public static boolean IsNumber(String s) {
		if(s == null || s.equals("")) {
			return false;
		}
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return false;
	}

}
