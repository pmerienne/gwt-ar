package com.pmerienne.gwt.ar.marker;

import com.google.gwt.user.client.ui.IsWidget;
import com.pmerienne.gwt.ar.device.Location;

public interface Marker extends IsWidget {

	void update(Double distance);

	Location getLocation();

}
