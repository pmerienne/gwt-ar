package com.pmerienne.gwt.ar.marker;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.pmerienne.gwt.ar.device.Location;

public class DefaultMarker extends Composite implements Marker {

	// private final static CssColor MARKER_COLOR =
	// CssColor.make("rgba(116, 173, 214, 0.8)");

	private HTMLPanel mainPanel;
	// private Canvas canvas = Canvas.createIfSupported();

	private Location location;

	private String label;

	public DefaultMarker() {
		this(null);
	}

	public DefaultMarker(Location location) {
		this("", location);
	}

	public DefaultMarker(String label, Location location) {
		this.mainPanel = new HTMLPanel("");
		this.label = label;
		this.location = location;
		this.initWidget(this.mainPanel);
	}

	@Override
	public void update(Double distance) {
		this.clear();

		// Create a circle if canvas is supported
		// if (this.canvas != null) {
		// this.mainPanel.add(this.canvas);
		// Context2d context = this.canvas.getContext2d();
		// context.setFillStyle(MARKER_COLOR);
		// context.beginPath();
		// context.arc(0, 0, 10, 0, Math.PI * 2.0, true);
		// context.closePath();
		// context.fill();
		// }

		// Create a label
		this.mainPanel.add(new Label(label + " : " + distance.intValue() + " m"));
	}

	private void clear() {
		this.mainPanel.clear();
	}

	@Override
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
