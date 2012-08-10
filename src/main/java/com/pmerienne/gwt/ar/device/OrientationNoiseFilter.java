package com.pmerienne.gwt.ar.device;

import com.pmerienne.gwt.ar.ARSettings;

public class OrientationNoiseFilter {

	private final static ARSettings SETTINGS = ARSettings.get();

	private Orientation filteredOrientation = new Orientation(0.0, 0.0, 0.0);

	public Orientation filter(Orientation rawOrientation) {
		this.filteredOrientation.alpha = this.filter(rawOrientation.alpha, this.filteredOrientation.alpha);
		this.filteredOrientation.beta = this.filter(rawOrientation.beta, this.filteredOrientation.beta);
		this.filteredOrientation.gamma = this.filter(rawOrientation.gamma, this.filteredOrientation.gamma);

		return this.filteredOrientation;
	}

	private Double filter(Double rawValue, Double lastValue) {
		Double filteredValue = null;

		if (Math.abs(lastValue - rawValue) > SETTINGS.getFilterThreshold()) {
			filteredValue = rawValue;
		} else {
			filteredValue = lastValue + SETTINGS.getFilterStep() * (rawValue - lastValue);
		}

		return filteredValue;
	}
}
