package com.pmerienne.gwt.ar.device;

public class Location {

	public Double latitude;
	public Double longitude;
	public Double altitude;

	public Location() {
		super();
	}

	public Location(Double latitude, Double longitude, Double altitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
	}

	@Override
	public String toString() {
		return "Position [latitude=" + latitude + ", longitude=" + longitude + ", altitude=" + altitude + "]";
	}
}
