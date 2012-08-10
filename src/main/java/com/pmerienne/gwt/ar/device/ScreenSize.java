package com.pmerienne.gwt.ar.device;

import com.pmerienne.gwt.ar.geom.Point2D;

public class ScreenSize {

	public double width;
	public double height;

	public ScreenSize(double width, double height) {
		super();
		this.width = width;
		this.height = height;
	}

	public boolean contains(Point2D point2d) {
		return point2d.x > 0 && point2d.x < this.width && point2d.y > 0 && point2d.y < this.height;
	}

	@Override
	public String toString() {
		return "ScreenSize [width=" + width + ", height=" + height + "]";
	}
}
