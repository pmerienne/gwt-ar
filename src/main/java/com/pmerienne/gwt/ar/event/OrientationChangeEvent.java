package com.pmerienne.gwt.ar.event;

import com.google.gwt.event.shared.GwtEvent;
import com.pmerienne.gwt.ar.device.Orientation;

public class OrientationChangeEvent extends GwtEvent<OrientationChangeHandler> {

	private static Type<OrientationChangeHandler> TYPE;

	public static Type<OrientationChangeHandler> getType() {
		return TYPE != null ? TYPE : (TYPE = new Type<OrientationChangeHandler>());
	}

	private Double globalOrientation;

	private Orientation deviceOrientation;

	private Orientation cameraOrientation;

	public OrientationChangeEvent(Orientation deviceOrientation, Orientation cameraOrientation, Double globalOrientation) {
		super();
		this.globalOrientation = globalOrientation;
		this.deviceOrientation = deviceOrientation;
		this.cameraOrientation = cameraOrientation;
	}

	public Orientation getDeviceOrientation() {
		return deviceOrientation;
	}

	public Orientation getCameraOrientation() {
		return cameraOrientation;
	}

	public Double getGlobalOrientation() {
		return globalOrientation;
	}

	@Override
	protected void dispatch(OrientationChangeHandler handler) {
		handler.onOrientationChange(this);
	}

	@Override
	public GwtEvent.Type<OrientationChangeHandler> getAssociatedType() {
		return getType();
	}
}
