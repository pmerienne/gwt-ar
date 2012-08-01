package com.pmerienne.gwt.ar.math;

import static java.lang.Math.atan;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;

import com.pmerienne.gwt.ar.device.Location;
import com.pmerienne.gwt.ar.device.Orientation;

public class MathUtil {

	/**
	 * Get camera orientation according to device orientation. This orientation
	 * is calculated for a camera situated at the back of an handset.
	 * 
	 * @param deviceOrientation
	 * @return
	 */
	public static Orientation getCameraOrientation(Orientation deviceOrientation) {
		// https://developer.mozilla.org/en/DOM/Orientation_and_motion_data_explained
		// Alpha is still the angle to the north pole
		double alpha = deviceOrientation.alpha;

		// Beta has an 90° angle with the handset
		double beta = deviceOrientation.beta - 90;

		// Gamma is still the same angle
		// TODO use MGWT portrait
		double gamma = deviceOrientation.gamma;
		return new Orientation(alpha, beta, gamma);
	}

	/**
	 * Rotate a 2D point.
	 * 
	 * @param point
	 * @param angle
	 */
	public static void rotate(Point2D point, double angle) {
		double tempX = cos(angle) * point.x - sin(angle) * point.y;
		double tempY = sin(angle) * point.x + cos(angle) * point.y;
		point.x = tempX;
		point.y = tempY;
	}

	public static double getBearingAngle(Location from, Location to) {
		double deltaLong = toRadians(to.longitude - from.longitude);

		double lat1 = toRadians(from.latitude);
		double lat2 = toRadians(to.latitude);

		double y = sin(deltaLong) * cos(lat2);
		double x = cos(lat1) * sin(lat2) - sin(lat1) * cos(lat2) * cos(deltaLong);
		double result = toDegrees(atan2(y, x));
		return (result + 360.0) % 360.0;
	}

	public static double getInclinaisonAngle(Location from, Location to, double vincentyDistance) {
		return toDegrees(atan((to.altitude - from.altitude) / vincentyDistance));
	}

	public static double getVincentyDistance(Location from, Location to) {
		double lat1 = from.latitude;
		double lon1 = from.longitude;
		double lat2 = to.latitude;
		double lon2 = to.longitude;

		double a = 6378137, b = 6356752.314245, f = 1 / 298.257223563; // WGS-84
																		// ellipsoid
																		// params
		double L = Math.toRadians(lon2 - lon1);
		double U1 = Math.atan((1 - f) * Math.tan(Math.toRadians(lat1)));
		double U2 = Math.atan((1 - f) * Math.tan(Math.toRadians(lat2)));
		double sinU1 = Math.sin(U1), cosU1 = Math.cos(U1);
		double sinU2 = Math.sin(U2), cosU2 = Math.cos(U2);

		double sinLambda, cosLambda, sinSigma, cosSigma, sigma, sinAlpha, cosSqAlpha, cos2SigmaM;
		double lambda = L, lambdaP, iterLimit = 100;
		do {
			sinLambda = Math.sin(lambda);
			cosLambda = Math.cos(lambda);
			sinSigma = Math.sqrt((cosU2 * sinLambda) * (cosU2 * sinLambda) + (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda)
					* (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda));
			if (sinSigma == 0)
				return 0; // co-incident points
			cosSigma = sinU1 * sinU2 + cosU1 * cosU2 * cosLambda;
			sigma = Math.atan2(sinSigma, cosSigma);
			sinAlpha = cosU1 * cosU2 * sinLambda / sinSigma;
			cosSqAlpha = 1 - sinAlpha * sinAlpha;
			cos2SigmaM = cosSigma - 2 * sinU1 * sinU2 / cosSqAlpha;
			if (Double.isNaN(cos2SigmaM))
				cos2SigmaM = 0; // equatorial line: cosSqAlpha=0 (§6)
			double C = f / 16 * cosSqAlpha * (4 + f * (4 - 3 * cosSqAlpha));
			lambdaP = lambda;
			lambda = L + (1 - C) * f * sinAlpha * (sigma + C * sinSigma * (cos2SigmaM + C * cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM)));
		} while (Math.abs(lambda - lambdaP) > 1e-12 && --iterLimit > 0);

		if (iterLimit == 0)
			return Double.NaN; // formula failed to converge

		double uSq = cosSqAlpha * (a * a - b * b) / (b * b);
		double A = 1 + uSq / 16384 * (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)));
		double B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)));
		double deltaSigma = B
				* sinSigma
				* (cos2SigmaM + B
						/ 4
						* (cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM) - B / 6 * cos2SigmaM * (-3 + 4 * sinSigma * sinSigma)
								* (-3 + 4 * cos2SigmaM * cos2SigmaM)));
		double dist = b * A * (sigma - deltaSigma);

		return dist;
	}
}
