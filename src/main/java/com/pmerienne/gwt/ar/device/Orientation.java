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

	/**
	 * Implementations that are unable to provide absolute values for the three
	 * angles may instead provide values relative to some arbitrary orientation,
	 * as this may still be of utility. In this case, the absolute property must
	 * be set to false. Otherwise, the absolute property must be set to true.
	 */
	public boolean absolute = true;

	public Orientation() {
		super();
	}

	public Orientation(Double alpha, Double beta, Double gamma) {
		super();
		this.alpha = alpha;
		this.beta = beta;
		this.gamma = gamma;
	}

	public Orientation(Double alpha, Double beta, Double gamma, boolean absolute) {
		super();
		this.alpha = alpha;
		this.beta = beta;
		this.gamma = gamma;
		this.absolute = absolute;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (absolute ? 1231 : 1237);
		result = prime * result + ((alpha == null) ? 0 : alpha.hashCode());
		result = prime * result + ((beta == null) ? 0 : beta.hashCode());
		result = prime * result + ((gamma == null) ? 0 : gamma.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Orientation other = (Orientation) obj;
		if (absolute != other.absolute)
			return false;
		if (alpha == null) {
			if (other.alpha != null)
				return false;
		} else if (!alpha.equals(other.alpha))
			return false;
		if (beta == null) {
			if (other.beta != null)
				return false;
		} else if (!beta.equals(other.beta))
			return false;
		if (gamma == null) {
			if (other.gamma != null)
				return false;
		} else if (!gamma.equals(other.gamma))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Orientation [alpha=" + alpha + ", beta=" + beta + ", gamma=" + gamma + ", absolute=" + absolute + "]";
	}

}
