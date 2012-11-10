package com.pmerienne.gwt.ar.widget.marker;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button
import com.pmerienne.gwt.ar.device.Location;

public class ButtonMarker extends AbstractMarker implements Marker {

	private Button button;

	public ButtonMarker(Location location) {
		this("", location);
	}

	public ButtonMarker(String text, Location location) {
		super(location);
		this.button = new Button(text);
		this.button.setSmall(true);
	}

	@Override
	public void update(MarkerInformation markerInformation) {
		// Nothing to do
	}

	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return this.button.addClickHandler(handler);
	}

	public void setText(String text) {
		this.button.setText(text);
	}

	@Override
	public Widget asWidget() {
		return this.button;
	}

}
