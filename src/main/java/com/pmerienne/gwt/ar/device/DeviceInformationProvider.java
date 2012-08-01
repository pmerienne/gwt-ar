package com.pmerienne.gwt.ar.device;

import com.pmerienne.gwt.ar.ARSettings;
import com.pmerienne.gwt.ar.math.MathUtil;

public class DeviceInformationProvider {

	private final static DeviceInformationProvider INSTANCE = new DeviceInformationProvider(ARSettings.get().getGeolocationUpdatePeriod());
	
	public final static Integer DEFAULT_UPDATE_PERIOD = 1000;

	private boolean geolocationSupported = detectGeolocationSupport();
	private boolean orientationSupported = detectOrientationSupport();

	private Location location = new Location(0D, 0D, 0D);
	private Double heading = 0.0;
	private Orientation deviceOrientation = new Orientation(0D, 0D, 0D);
	private Orientation cameraOrientation = new Orientation(0D, -90D, 0D);
	private FieldOfView fieldOfView = new FieldOfView(45, 45);

	public DeviceInformationProvider(Integer updatePeriod) {
		this.watchOrientation();
		this.watchGeolocation(updatePeriod);
	}

	public final static DeviceInformationProvider get() {
		return INSTANCE;
	}

	private native void watchOrientation() /*-{
		var instance = this;

		var updateOrientation = function(event) {
			var alpha = event.alpha ? event.alpha : 0;
			var beta = event.beta ? event.beta : 0;
			var gamma = event.gamma ? event.gamma : 0;
			instance.@com.pmerienne.gwt.ar.device.DeviceInformationProvider::setOrientation(DDD)(alpha, beta, gamma);
		};

		$wnd.addEventListener('deviceorientation', updateOrientation);
	}-*/;

	private native int watchGeolocation(Integer _timeout) /*-{
		var instance = this;

		var updateLocation = function(position) {
			var latitude = position.coords.latitude;
			var longitude = position.coords.longitude;
			var altitude = position.coords.altitude ? position.coords.altitude
					: 0;
			var heading = position.coords.heading ? position.coords.heading : 0;

			instance.@com.pmerienne.gwt.ar.device.DeviceInformationProvider::setGeolocation(DDDD)(latitude, longitude, altitude, heading);
		};

		var handleError = function(err) {
			var errMsg = "Unable to get current geolocation : ";
			if (err.code == 1) {
				errMsg += "access is denied!";
			} else if (err.code == 2) {
				errMsg += "position is unavailable!";
			}
			$wnd.console.log(errMsg);
		};

		var options = {
			timeout : _timeout
		};

		var geolocation = $wnd.navigator.geolocation;
		return geolocation.watchPosition(updateLocation, handleError, options);
	}-*/;

	private static native boolean detectGeolocationSupport() /*-{
		return !!$wnd.navigator.geolocation;
	}-*/;

	private static native boolean detectOrientationSupport() /*-{
		return !!$wnd.window.OrientationEvent;
	}-*/;

	private void setOrientation(double alpha, double beta, double gamma) {
		this.deviceOrientation = new Orientation(alpha, beta, gamma);
		this.cameraOrientation = MathUtil.getCameraOrientation(this.deviceOrientation);
	}

	private void setGeolocation(double latitude, double longitude, double altitude, double heading) {
		this.location = new Location(latitude, longitude, altitude);
		this.heading = heading;
	}

	public boolean isGeolocationSupported() {
		return geolocationSupported;
	}

	public boolean isOrientationSupported() {
		return orientationSupported;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Double getHeading() {
		return heading;
	}

	public void setHeading(Double heading) {
		this.heading = heading;
	}

	public Orientation getDeviceOrientation() {
		return deviceOrientation;
	}

	public void setDeviceOrientation(Orientation deviceOrientation) {
		this.deviceOrientation = deviceOrientation;
		this.cameraOrientation = MathUtil.getCameraOrientation(this.deviceOrientation);
	}

	public Orientation getCameraOrientation() {
		return cameraOrientation;
	}

	public FieldOfView getFieldOfView() {
		return fieldOfView;
	}

	public void setFieldOfView(FieldOfView fieldOfView) {
		this.fieldOfView = fieldOfView;
	}

	@Override
	public String toString() {
		return "DeviceInformationProvider [geolocationSupported=" + geolocationSupported + ", orientationSupported=" + orientationSupported + ", location="
				+ location + ", heading=" + heading + ", orientation=" + deviceOrientation + ", fieldOfView=" + fieldOfView + "]";
	}

}
