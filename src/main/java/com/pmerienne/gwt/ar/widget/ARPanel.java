package com.pmerienne.gwt.ar.widget;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.pmerienne.gwt.ar.ARSettings;
import com.pmerienne.gwt.ar.device.DeviceInformationProvider;
import com.pmerienne.gwt.ar.device.FieldOfView;
import com.pmerienne.gwt.ar.device.Location;
import com.pmerienne.gwt.ar.device.Orientation;
import com.pmerienne.gwt.ar.device.ScreenSize;
import com.pmerienne.gwt.ar.geom.Point2D;
import com.pmerienne.gwt.ar.geom.ProjectionCalculator;
import com.pmerienne.gwt.ar.widget.marker.Marker;
import com.pmerienne.gwt.ar.widget.marker.MarkerInformation;

public class ARPanel extends AbsolutePanel {

	private ARSettings settings = ARSettings.get();

	private Timer updateTimer;

	private List<Marker> markers = new ArrayList<Marker>();

	private CameraCapturePanel cameraCapturePanel;

	private DeviceInformationProvider deviceInformationProvider;

	private ProjectionCalculator calculator = new ProjectionCalculator();

	public ARPanel() {
		this.deviceInformationProvider = DeviceInformationProvider.get();
		this.cameraCapturePanel = new CameraCapturePanel();
		this.add(this.cameraCapturePanel);

		// Create timer
		this.updateTimer = new Timer() {
			@Override
			public void run() {
				updateMarkers();
			}
		};

		this.start();
	}

	/**
	 * Add a geolocalized {@link Marker}.
	 * 
	 * @param marker
	 */
	public void addMarker(Marker marker) {
		this.markers.add(marker);
	}

	/**
	 * Add some {@link Marker}.
	 * 
	 * @param markers
	 */
	public void addMarkers(List<Marker> markers) {
		this.markers.addAll(markers);
	}

	/**
	 * Remove a {@link Marker}
	 * 
	 * @param marker
	 */
	public void removeMarker(Marker marker) {
		this.markers.remove(marker);
		this.remove(marker);
	}

	/**
	 * Remove all markers.
	 */
	public void clearMarkers() {
		// Remove marker from panel
		for (Marker marker : this.markers) {
			this.remove(marker);
		}

		this.markers.clear();
	}

	/**
	 * Start displaying camera output and {@link Marker}s
	 */
	public void start() {
		this.cameraCapturePanel.start(true, false);
		this.updateTimer.scheduleRepeating(this.settings.getMarkerUpdatePeriod());
	}

	/**
	 * Stop displaying camera output and {@link Marker}s
	 */
	public void stop() {
		this.cameraCapturePanel.stop();
		this.updateTimer.cancel();
	}

	/**
	 * Update markers display
	 */
	private void updateMarkers() {
		for (Marker marker : this.markers) {
			this.updateMarker(marker);
		}
	}

	/**
	 * Update marker display
	 * 
	 * @param marker
	 */
	private void updateMarker(Marker marker) {
		// Retrieve info
		Location markerLocation = marker.getLocation();
		Location cameraLocation = this.deviceInformationProvider.getGeolocation();
		Orientation cameraOrientation = this.deviceInformationProvider.getCameraOrientation();
		FieldOfView cameraFOV = this.deviceInformationProvider.getFieldOfView();
		ScreenSize screenSize = new ScreenSize(this.getOffsetWidth(), this.getOffsetHeight());

		// Tricks to avoid dirty rendering when altitude is missing
		boolean cameraAltitudeIsMissing = cameraLocation.altitude == null || cameraLocation.altitude.equals(0D);
		boolean markerAltitudeIsMissing = markerLocation.altitude == null || markerLocation.altitude.equals(0D);
		if (cameraAltitudeIsMissing && markerAltitudeIsMissing) {
			markerLocation.altitude = 0D;
			cameraLocation.altitude = 0D;
		} else if (cameraAltitudeIsMissing) {
			cameraLocation.altitude = markerLocation.altitude;
		} else if (markerAltitudeIsMissing) {
			markerLocation.altitude = cameraLocation.altitude;
		}

		// Calculate marker information
		MarkerInformation mi = this.calculator.calculMakerInformaion(markerLocation, cameraLocation, cameraOrientation, cameraFOV, screenSize);

		if (mi != null && screenSize.contains(mi.getScreenPostion())) {
			// Update marker display
			marker.update(mi);

			// Get screen position
			Point2D screenPosition = mi.getScreenPostion();
			Integer[] offsets = this.getMarkerOffset(marker);

			if (!this.getChildren().contains(marker.asWidget())) {
				// Add marker
				this.add(marker, screenPosition.x.intValue() + offsets[0], screenPosition.y.intValue() + offsets[1]);
			} else {
				// Set widget position
				this.setWidgetPosition(marker.asWidget(), screenPosition.x.intValue() + offsets[0], screenPosition.y.intValue() + offsets[1]);
			}
		} else {
			// Remove the marker if it's not visible
			marker.asWidget().removeFromParent();
		}
	}

	private Integer[] getMarkerOffset(Marker marker) {
		Integer[] offsets = new Integer[2];

		// Get marker height
		Integer markerWidth = marker.asWidget().getElement().getClientWidth();
		Integer markerHeight = marker.asWidget().getElement().getClientHeight();

		// Calcultate offsets according to marker alignment
		switch (marker.getMarkerAlignment()) {
		case BOTTOM_CENTER:
			offsets[0] = - markerWidth / 2;
			offsets[1] = - markerHeight;
			break;
		case BOTTOM_LEFT:
			offsets[0] = 0;
			offsets[1] = - markerHeight;
			break;
		case BOTTOM_RIGHT:
			offsets[0] = - markerWidth;
			offsets[1] = - markerHeight;
			break;
		case MIDDLE_CENTER:
			offsets[0] = - markerWidth / 2;
			offsets[1] = - markerHeight / 2;
			break;
		case MIDDLE_LEFT:
			offsets[0] = 0;
			offsets[1] = - markerHeight / 2;
			break;
		case MIDDLE_RIGHT:
			offsets[0] = - markerWidth;
			offsets[1] = - markerHeight / 2;
			break;
		case TOP_CENTER:
			offsets[0] = - markerWidth / 2;
			offsets[1] = 0;
			break;
		case TOP_LEFT:
			offsets[0] = 0;
			offsets[1] = 0;
			break;
		case TOP_RIGHT:
			offsets[0] = - markerWidth;
			offsets[1] = 0;
			break;
		default:
			offsets[0] = - markerWidth / 2;
			offsets[1] = - markerHeight / 2;
			break;
		}

		return offsets;
	}

	public DeviceInformationProvider getDeviceInformationProvider() {
		return deviceInformationProvider;
	}

}
