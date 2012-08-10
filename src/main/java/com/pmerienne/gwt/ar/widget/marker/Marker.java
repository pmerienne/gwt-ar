package com.pmerienne.gwt.ar.widget.marker;

import com.google.gwt.user.client.ui.IsWidget;
import com.pmerienne.gwt.ar.device.Location;

public interface Marker extends IsWidget {

	/**
	 * Update marker display
	 * 
	 * @param distance
	 */
	void update(MarkerInformation markerInformation);

	Location getLocation();

	MarkerAlignment getMarkerAlignment();

}
