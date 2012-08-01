package com.pmerienne.gwt.ar.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.VideoElement;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class CameraCapturePanel extends Composite {

	private static CameraCapturePanelUiBinder uiBinder = GWT.create(CameraCapturePanelUiBinder.class);

	interface CameraCapturePanelUiBinder extends UiBinder<Widget, CameraCapturePanel> {
	}

	static {
		ensureCrossBrowser();
	}

	private final static String ACCESS_DENIED = "Unable to display camera output : access denied";
	private final static String CAMERA_NOT_SUPPORTED = "Unable to display camera output : camera not supported by your browser";

	private boolean supported = detectSupport();

	@UiField
	VideoElement videoElement;

	@UiField
	Label errorLabel;

	public CameraCapturePanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void start(boolean video, boolean audio) {
		if (this.supported) {
			this.clearError();
			this._startVideo(video, audio);
		} else {
			this.showCameraNotSupportedError();
		}
	}

	private native void _startVideo(boolean _video, boolean _audio) /*-{
		var instance = this;
		var videoElement = this.@com.pmerienne.gwt.ar.widget.CameraCapturePanel::videoElement;

		var options = {
			audio : _audio,
			video : _video
		};

		var onStreamReady = function(stream) {
			videoElement.src = ($wnd.URL && $wnd.URL.createObjectURL) ? $wnd.URL.createObjectURL(stream) : stream
			instance
					.@com.pmerienne.gwt.ar.widget.CameraCapturePanel::clearError();
		};

		var onCameraError = function(err) {
			if (e.code == 1) {
				instance
						.@com.pmerienne.gwt.ar.widget.CameraCapturePanel::showAccessDeniedError();
			} else {
				instance
						.@com.pmerienne.gwt.ar.widget.CameraCapturePanel::showCameraNotSupportedError();
			}
		};

		$wnd.navigator.getUserMedia(options, onStreamReady, onCameraError);
	}-*/;

	public void stop() {
		this.videoElement.pause();
	}

	private void clearError() {
		this.errorLabel.setText("");
		this.errorLabel.setVisible(false);
		this.videoElement.getStyle().setDisplay(Display.INLINE);
	}

	private void showAccessDeniedError() {
		this.errorLabel.setVisible(true);
		this.videoElement.getStyle().setDisplay(Display.NONE);
		this.errorLabel.setText(ACCESS_DENIED);
	}

	private void showCameraNotSupportedError() {
		this.errorLabel.setVisible(true);
		this.videoElement.getStyle().setDisplay(Display.NONE);
		this.errorLabel.setText(CAMERA_NOT_SUPPORTED);
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
	}-*/;

	private static native boolean detectSupport() /*-{
		return !!$wnd.navigator.getUserMedia;
	}-*/;

	public boolean isSupported() {
		return supported;
	}

}
