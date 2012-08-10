package com.pmerienne.gwt.ar;

public class ARSettings {

	private final static ARSettings INSTANCE = new ARSettings();

	private Integer geolocationUpdatePeriod = 5000;

	private Integer markerUpdatePeriod = 100;

	private Double filterThreshold = 2.0;

	private Double filterStep = 1.0;

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

	public Double getFilterThreshold() {
		return filterThreshold;
	}

	public void setFilterThreshold(Double filterThreshold) {
		this.filterThreshold = filterThreshold;
	}

	public Double getFilterStep() {
		return filterStep;
	}

	public void setFilterStep(Double filterStep) {
		this.filterStep = filterStep;
	}

}
