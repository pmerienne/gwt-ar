package com.pmerienne.gwt.ar.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.VideoElement;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.pmerienne.gwt.ar.device.DeviceInformationProvider;

public class CameraCapturePanel extends Composite {

	private static CameraCapturePanelUiBinder uiBinder = GWT.create(CameraCapturePanelUiBinder.class);

	interface CameraCapturePanelUiBinder extends UiBinder<Widget, CameraCapturePanel> {
	}

	private final static String ACCESS_DENIED = "Unable to display camera output : access denied";
	private final static String CAMERA_NOT_SUPPORTED = "Unable to display camera output : camera not supported by your browser";

	@UiField
	VideoElement videoElement;

	@UiField
	Label errorLabel;

	public CameraCapturePanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void start(boolean video, boolean audio) {
		if (DeviceInformationProvider.get().isCameraAPISupported()) {
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
			videoElement.src = ($wnd.URL && $wnd.URL.createObjectURL) ? $wnd.URL
					.createObjectURL(stream)
					: stream
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

}
