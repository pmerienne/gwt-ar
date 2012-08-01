package com.pmerienne.gwt.ar.math;

import static com.pmerienne.gwt.ar.math.MathUtil.getBearingAngle;
import static com.pmerienne.gwt.ar.math.MathUtil.getInclinaisonAngle;
import static com.pmerienne.gwt.ar.math.MathUtil.getVincentyDistance;
import static com.pmerienne.gwt.ar.math.MathUtil.rotate;
import static java.lang.Math.toRadians;

import com.pmerienne.gwt.ar.device.FieldOfView;
import com.pmerienne.gwt.ar.device.Location;
import com.pmerienne.gwt.ar.device.Orientation;
import com.pmerienne.gwt.ar.device.ScreenSize;
import com.pmerienne.gwt.ar.marker.MarkerInformation;

/**
 * <pre>
 * 		See http://eprints.cs.univie.ac.at/3453/1/submission_46_cameraready_20120618.pdf
 * </pre>
 * 
 * @author pmerienne
 * 
 */
public class ProjectionCalculator {

	public MarkerInformation calculMakerInformaion(Location point, Location cameraLocation, Orientation cameraOrientation, FieldOfView cameraFOV,
			ScreenSize screenSize) {
		Point2D screenCoordinate = new Point2D();

		double z = getVincentyDistance(cameraLocation, point);
		// System.out.println("z : " + z);
		double bearingAngle = getBearingAngle(cameraLocation, point);
		// System.out.println("bearingAngle : " + bearingAngle);
		double inclinaisonAngle = getInclinaisonAngle(cameraLocation, point, z);
		// System.out.println("inclinaisonAngle : " + inclinaisonAngle);
		double lborder = (cameraOrientation.alpha - cameraFOV.horizontal / 2) % 360;
		// System.out.println("lborder : " + lborder);
		double tborder = (cameraOrientation.beta + cameraFOV.vertical / 2) % 360;
		// System.out.println("tborder : " + tborder);
		double xdeg = bearingAngle - lborder;
		// System.out.println("xdeg : " + xdeg);
		double ydeg = tborder - inclinaisonAngle;
		// System.out.println("ydeg : " + ydeg);

		screenCoordinate.x = screenSize.width * xdeg / cameraFOV.horizontal;
		screenCoordinate.y = screenSize.height * ydeg / cameraFOV.vertical;

		// Rotate according to roll
		rotate(screenCoordinate, toRadians(cameraOrientation.gamma));

		MarkerInformation mi = new MarkerInformation(screenCoordinate, z);
		return mi;
	}
}
