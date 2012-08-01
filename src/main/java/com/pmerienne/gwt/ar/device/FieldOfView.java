package com.pmerienne.gwt.ar.device;

public class FieldOfView {

	public double horizontal;
	public double vertical;

	public FieldOfView(double horizontal, double vertical) {
		super();
		this.horizontal = horizontal;
		this.vertical = vertical;
	}

	@Override
	public String toString() {
		return "FieldOfView [horizontal=" + horizontal + ", vertical=" + vertical + "]";
	}
}
