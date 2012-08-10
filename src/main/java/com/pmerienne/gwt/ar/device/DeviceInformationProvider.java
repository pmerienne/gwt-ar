package com.pmerienne.gwt.ar.device;

import com.pmerienne.gwt.ar.ARSettings;
import com.pmerienne.gwt.ar.event.EventDispatcher;
import com.pmerienne.gwt.ar.event.GeolocationChangeEvent;
import com.pmerienne.gwt.ar.event.OrientationChangeEvent;
import com.pmerienne.gwt.ar.geom.MathUtil;

public class DeviceInformationProvider extends EventDispatcher {

	private final static DeviceInformationProvider INSTANCE = new DeviceInformationProvider(ARSettings.get().getGeolocationUpdatePeriod());

	static {
		ensureCrossBrowser();
	}

	private boolean geolocationSupported = detectGeolocationSupport();
	private boolean orientationSupported = detectOrientationSupport();
	private boolean orientationAbsolute = true;
	private boolean cameraAPISupported = detectCameraSupport();

	private Location geolocation = new Location(0D, 0D, 0D);
	private Double heading = 0.0;

	private Orientation deviceOrientation = new Orientation(0D, 90D, 0D);
	private Orientation cameraOrientation = new Orientation(0D, 0D, 0D);
	private Double globalOrientation = 0.0;

	private FieldOfView fieldOfView = new FieldOfView(45, 45);

	private OrientationNoiseFilter deviceOrientationFilter = new OrientationNoiseFilter();
	private OrientationNoiseFilter cameraOrientationFilter = new OrientationNoiseFilter();

	private DeviceInformationProvider(Integer updatePeriod) {
		this.watchOrientation();
		this.watchGeolocation(updatePeriod);
		this.watchOrientationChange();
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
			var absolute = event.absolute ? event.absolute : true;
			instance.@com.pmerienne.gwt.ar.device.DeviceInformationProvider::setDeviceOrientation(DDDZ)(alpha, beta, gamma, absolute);
		};

		$wnd.addEventListener('deviceorientation', updateOrientation);
	}-*/;

	private native void watchOrientationChange() /*-{
		var instance = this;

		var updateGlobalOrientation = function(event) {
			var orientation = $wnd.orientation ? $wnd.orientation : 0;
			instance.@com.pmerienne.gwt.ar.device.DeviceInformationProvider::setGlobalOrientation(D)(orientation);
		};

		$wnd.addEventListener('onorientationchange', updateGlobalOrientation);
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
	
	private void setGlobalOrientation(double orientation) {
		this.globalOrientation = orientation;
	}

	public void setDeviceOrientation(double alpha, double beta, double gamma, boolean absolute) {
		this.orientationAbsolute = absolute;

		this.deviceOrientation = this.deviceOrientationFilter.filter(new Orientation(alpha, beta, gamma));
		this.cameraOrientation = this.cameraOrientationFilter.filter(MathUtil.getCameraOrientation(this.deviceOrientation, this.globalOrientation));
		this.fireEvent(new OrientationChangeEvent(this.deviceOrientation, this.cameraOrientation, this.globalOrientation));
	}

	public void setGeolocation(double latitude, double longitude, double altitude, double heading) {
		this.geolocation = new Location(latitude, longitude, altitude);
		this.heading = heading;
		this.fireEvent(new GeolocationChangeEvent(this.geolocation, this.heading));
	}

	/**
	 * Ensure that the window.URL and window.navigator.getUserMedia are
	 * crossbrowser compatible.
	 * 
	 * This is ugly : GWT is designed to do this better with permutation!
	 * 
	 * @return
	 */
	private static native void ensureCrossBrowser() /*-{
		$wnd.URL = $wnd.URL || $wnd.webkitURL || $wnd.msURL || $wnd.mozURL
				|| $wnd.oURL || null;
		$wnd.navigator.getUserMedia = $wnd.navigator.getUserMedia
				|| $wnd.navigator.webkitGetUserMedia
				|| $wnd.navigator.mozGetUserMedia
				|| $wnd.navigator.msGetUserMedia
				|| $wnd.navigator.oGetUserMedia || null;
		$wnd.DeviceOrientationEvent = $wnd.DeviceOrientationEvent
				|| $wnd.OrientationEvent || null;
	}-*/;

	private static native boolean detectGeolocationSupport() /*-{
		return !!$wnd.navigator.geolocation;
	}-*/;

	private static native boolean detectOrientationSupport() /*-{
		return !!$wnd.DeviceOrientationEvent;
	}-*/;

	private static native boolean detectCameraSupport() /*-{
		return !!$wnd.navigator.getUserMedia;
	}-*/;

	public boolean isCameraAPISupported() {
		return cameraAPISupported;
	}

	public boolean isGeolocationSupported() {
		return geolocationSupported;
	}

	public boolean isOrientationSupported() {
		return orientationSupported;
	}

	public boolean isOrientationAbsolute() {
		return orientationAbsolute;
	}

	public Location getGeolocation() {
		return geolocation;
	}

	public Double getHeading() {
		return heading;
	}

	public Orientation getDeviceOrientation() {
		return deviceOrientation;
	}

	public Orientation getCameraOrientation() {
		return cameraOrientation;
	}

	public Double getGlobalOrientation() {
		return globalOrientation;
	}

	public FieldOfView getFieldOfView() {
		return fieldOfView;
	}

	public void setFieldOfView(FieldOfView fieldOfView) {
		this.fieldOfView = fieldOfView;
	}

	@Override
	public String toString() {
		return "DeviceInformationProvider [geolocationSupported=" + geolocationSupported + ", orientationSupported=" + orientationSupported
				+ ", cameraAPISupported=" + cameraAPISupported + ", geolocation=" + geolocation + ", heading=" + heading + ", deviceOrientation="
				+ deviceOrientation + ", cameraOrientation=" + cameraOrientation + ", fieldOfView=" + fieldOfView + "]";
	}

}
