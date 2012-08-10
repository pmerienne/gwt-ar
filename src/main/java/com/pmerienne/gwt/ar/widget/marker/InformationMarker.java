package com.pmerienne.gwt.ar.widget.marker;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.pmerienne.gwt.ar.device.Location;

public class InformationMarker extends AbstractMarker implements Marker {

	private static InformationMarkerUiBinder uiBinder = GWT.create(InformationMarkerUiBinder.class);

	interface InformationMarkerUiBinder extends UiBinder<Widget, InformationMarker> {
	}

	private Widget container;

	@UiField
	Label markerTitle;

	@UiField
	HTML markerInformation;

	public InformationMarker(Location location) {
		this(location, "", "");
	}

	public InformationMarker(Location location, String title) {
		this(location, title, "");
	}

	public InformationMarker(Location location, String title, String information) {
		super(location);
		this.container = uiBinder.createAndBindUi(this);
		this.markerTitle.setText(title);
		this.markerInformation.setText(information);
	}

	@Override
	public void update(MarkerInformation markerInformation) {
		// Do nothing
	}

	public void setTitle(String title) {
		this.markerTitle.setText(title);
	}

	public void setInformation(String information) {
		this.markerInformation.setText(information);
	}

	@Override
	public Widget asWidget() {
		return this.container;
	}

}
