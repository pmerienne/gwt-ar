package com.pmerienne.gwt.ar.event;

import com.google.gwt.event.shared.EventHandler;

public interface GeolocationChangeHandler extends EventHandler {

	void onGeolocationChange(GeolocationChangeEvent event);
}
