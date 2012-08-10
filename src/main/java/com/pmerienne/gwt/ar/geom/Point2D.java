package com.pmerienne.gwt.ar.geom;

public class Point2D {

	public Double x;
	public Double y;

	public Point2D() {
		super();
	}

	public Point2D(Double x, Double y) {
		super();
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "[x=" + x + ", y=" + y + "]";
	}
}
