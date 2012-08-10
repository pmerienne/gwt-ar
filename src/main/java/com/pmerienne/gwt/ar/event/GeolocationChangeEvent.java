package com.pmerienne.gwt.ar.event;

import com.google.gwt.event.shared.GwtEvent;
import com.pmerienne.gwt.ar.device.Location;

public class GeolocationChangeEvent extends GwtEvent<GeolocationChangeHandler> {

	private static Type<GeolocationChangeHandler> TYPE;

	public static Type<GeolocationChangeHandler> getType() {
		return TYPE != null ? TYPE : (TYPE = new Type<GeolocationChangeHandler>());
	}

	private Location location;

	private Double heading;

	public GeolocationChangeEvent(Location location, Double heading) {
		super();
		this.location = location;
		this.heading = heading;
	}

	public Location getLocation() {
		return location;
	}

	public Double getHeading() {
		return heading;
	}

	@Override
	protected void dispatch(GeolocationChangeHandler handler) {
		handler.onGeolocationChange(this);
	}

	@Override
	public GwtEvent.Type<GeolocationChangeHandler> getAssociatedType() {
		return getType();
	}
}
