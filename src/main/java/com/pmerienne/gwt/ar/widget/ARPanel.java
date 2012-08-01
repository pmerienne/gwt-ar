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
import com.pmerienne.gwt.ar.marker.Marker;
import com.pmerienne.gwt.ar.marker.MarkerInformation;
import com.pmerienne.gwt.ar.math.Point2D;
import com.pmerienne.gwt.ar.math.ProjectionCalculator;

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
		this.add(marker);
		this.markers.add(marker);
		this.updateMarker(marker);
	}

	/**
	 * Add some {@link Marker}.
	 * 
	 * @param markers
	 */
	public void addMarkers(List<Marker> markers) {
		for (Marker marker : this.markers) {
			this.add(marker);
		}
		this.markers.addAll(markers);
	}

	/**
	 * Remove a {@link Marker}
	 * 
	 * @param marker
	 */
	public void removeMarker(Marker marker) {
		this.remove(marker);
		this.markers.remove(marker);
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
		Location cameraLocation = this.deviceInformationProvider.getLocation();
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

		// Remove marker
		this.remove(marker);

		if (mi != null) {
			Point2D screenPosition = mi.getScreenPostion();
			marker.update(mi.getDistance());
			if (screenSize.contains(screenPosition)) {
				this.add(marker, screenPosition.x.intValue(), screenPosition.y.intValue());
			}
		}
	}

	public DeviceInformationProvider getDeviceInformationProvider() {
		return deviceInformationProvider;
	}

}
