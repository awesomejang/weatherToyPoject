package weather.toyproject;

public class GpsTransfer {
	private double lat; // gps로 반환하는 위도 
	private double lon; // gps로 반환받은 위도
	
	private double xLat; // x좌표로 변한된 위도 
	private double xLon; // y좌표로 변환된 경도 
	
	public GpsTransfer() {
		
	}
	
	public GpsTransfer(double lat, double lon) {
		this.lat = lat;
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getxLat() {
		return xLat;
	}

	public void setxLat(double xLat) {
		this.xLat = xLat;
	}

	public double getxLon() {
		return xLon;
	}

	public void setxLon(double xLon) {
		this.xLon = xLon;
	}

	// x좌표로 변환해주는것
	public void transfer(GpsTransfer gpt, int mode) {
		double RE = 6371.00877; // 지구 반경(Km)
		double GRID = 5.0; // 격자 간격(Km)
		double SLAT1 = 30.0; // 투영 위도1(degree)
		double SLAT2 = 60.0; // 투영 위도2(degree)
		double OLON = 126.0; // 기준접 경도(degree)
	}
	
}
