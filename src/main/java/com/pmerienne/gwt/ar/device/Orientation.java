package com.pmerienne.gwt.ar.device;

public class Orientation {
	/**
	 * azimuth
	 */
	public Double alpha;

	/**
	 * pitch
	 */
	public Double beta;

	/**
	 * roll
	 */
	public Double gamma;

	public Orientation() {
		super();
	}

	public Orientation(Double alpha, Double beta, Double gamma) {
		super();
		this.alpha = alpha;
		this.beta = beta;
		this.gamma = gamma;
	}

	@Override
	public String toString() {
		return "Orientation [alpha=" + alpha + ", beta=" + beta + ", gamma=" + gamma + "]";
	}

}
