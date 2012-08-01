package com.pmerienne.gwt.ar;

public class ARSettings {

	private final static ARSettings INSTANCE = new ARSettings();

	private Integer geolocationUpdatePeriod = 500;

	private Integer markerUpdatePeriod = 200;

	protected ARSettings() {
		super();
	}

	public final static ARSettings get() {
		return INSTANCE;
	}

	public Integer getGeolocationUpdatePeriod() {
		return geolocationUpdatePeriod;
	}

	public void setGeolocationUpdatePeriod(Integer geolocationUpdatePeriod) {
		this.geolocationUpdatePeriod = geolocationUpdatePeriod;
	}

	public Integer getMarkerUpdatePeriod() {
		return markerUpdatePeriod;
	}

	public void setMarkerUpdatePeriod(Integer markerUpdatePeriod) {
		this.markerUpdatePeriod = markerUpdatePeriod;
	}

}
