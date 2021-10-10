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
		double OLAT = 38.0; // 기준점 위도(degree)
		double XO = 43; // 기준점 x좌표(GRID)
		double YO = 136; // 기준점 y좌표(GRID)
		
		// LCC DFS 좌표변환 (code : "TO_GRID"(위경도 -> 좌표, lat_X:위도, lng_Y:경도) -> 0, "TO_GPS"(좌표 -> 위경도, lat_X:x, lng_Y) -> 1)
		
		double DEGRAD = Math.PI / 180.0;
		double RADDEG = 180.0 / Math.PI;
		
		double re = RE / GRID;
		double slat1 = SLAT1 * DEGRAD;
		double slat2 = SLAT2 * DEGRAD;
		double olon = OLON * DEGRAD;
		double olat = OLAT * DEGRAD;
		
		double sn = Math.tan(Math.PI * 0.25 * slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
		sn = Math.log(Math.cos(slat1) / Math.cos(slat2) / Math.log(sn));
		double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
		sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
		double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
		ro = re * sf / Math.pow(ro, sn);
		
		if(mode == 0) {
//			rs.lat = lat_X; // gps 좌표 위도
//			rs.lng = lng_Y; // gps 좌표 경도 
			double ra = Math.tan(Math.PI * 0.25 + (gpt.getLat()) * DEGRAD * 0.5);
			ra = re * sf / Math.pow(ra, sn);
			double theta = gpt.getLon() * DEGRAD - olon;
			if (theta > Math.PI) theta -= 2.0 * Math.PI;
			if (theta < -Math.PI) theta += 2.0 * Math.PI;
			theta *= sn;
			double x = Math.floor(ra * Math.sin(theta) + XO + 0.5);
			double y = Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);
			gpt.setxLat(x);
			gpt.setLon(y);
//			rs.x = Math.floor(ra * Math.sin(theta) + XO + 0.5);
//			rs.y = Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);
		}
		else {
			System.out.println("in");
//			rs.x = lat_X; // 기존의 x좌표
//			rs.y = lng_Y; // 기존의 경도
			double xlat = gpt.getLat();
			double ylon = gpt.getLon();
			System.out.println("xlat= " + xlat);
			System.out.println("ylon= " + ylon);
			double xn = xlat - XO;
			double yn = ro - ylon + YO;
			double ra = Math.sqrt(xn * xn + yn * yn);
			if (sn < 0.0) {
				ra = -ra;
			}
			double alat = Math.pow((re * sf / ra), (1.0 / sn));					
			alat = 2.0 * Math.atan(alat) - Math.PI * 0.5;
			System.out.println("alat = " + alat);
			
			double theta = 0.0;
			if (Math.abs(xn) <= 0.0) {
				theta = 0.0;
			} else {
				if (Math.abs(yn) <= 0.0) {
					theta = Math.PI * 0.5;
					if (xn < 0.0) {
						theta = -theta;
					}
				}
				else theta = Math.atan2(xn, yn);
			}
			double alon = theta / sn + olon;
			// rs.lat = alat * RADDEG gps 좌표 위도
			// rs.lng = alon * RADDEG gps 좌표 경도
			System.out.println("alat = " + alat);
			System.out.println("alon = " + alon);
			gpt.setLat(alat * RADDEG);
			gpt.setLon(alon * RADDEG);
		}
		
	}

	@Override
	public String toString() {
		return "GpsTransfer [lat=" + lat + ", lon=" + lon + ", xLat=" + xLat + ", xLon=" + xLon + "]";
	}
	
	public static void main(String[] args) {
		GpsTransfer gp = new GpsTransfer(55, 127);
		gp.transfer(gp, 0);
		System.out.println("위도= " + gp.getLat());
		System.out.println("경도= " + gp.getLon());
		
	}
	
}
