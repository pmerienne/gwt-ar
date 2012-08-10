package com.pmerienne.gwt.ar.widget.marker;

import com.pmerienne.gwt.ar.geom.Point2D;

public class MarkerInformation {

	private Point2D screenPostion;

	private Double distance;

	public MarkerInformation() {
		super();
	}

	public MarkerInformation(Point2D screenPostion, Double distance) {
		super();
		this.screenPostion = screenPostion;
		this.distance = distance;
	}

	public Point2D getScreenPostion() {
		return screenPostion;
	}

	public void setScreenPostion(Point2D screenPostion) {
		this.screenPostion = screenPostion;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "MarkerInformation [screenPostion=" + screenPostion + ", distance=" + distance + "]";
	}

}
