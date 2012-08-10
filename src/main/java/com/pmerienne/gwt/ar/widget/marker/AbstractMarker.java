package com.pmerienne.gwt.ar.widget.marker;

import com.pmerienne.gwt.ar.device.Location;

public abstract class AbstractMarker implements Marker {

	protected Location location;
	protected MarkerAlignment markerAlignment;

	public AbstractMarker(Location location) {
		this(location, MarkerAlignment.MIDDLE_CENTER);
	}

	public AbstractMarker(Location location, MarkerAlignment markerAlignment) {
		this.location = location;
		this.markerAlignment = markerAlignment;
	}

	@Override
	public Location getLocation() {
		return this.location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setMarkerAlignment(MarkerAlignment markerAlignment) {
		this.markerAlignment = markerAlignment;
	}

	@Override
	public MarkerAlignment getMarkerAlignment() {
		return this.markerAlignment;
	}
}
