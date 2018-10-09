package com.team254.lib.trajectory;

import com.team254.lib.util.ChezyMath;
import com.team254.lib.util.TrajectoryMath;

/**
 * Do cubic or quintic spline interpolation between points.
 *
 * @author Art Kalb
 * @author Jared341
 */
public class SplineGenerator {

  public static Spline reticulateSplines(Waypoint start,
                                          Waypoint goal, Spline.SplineType type) {
    return reticulateSplines(start.x, start.y, start.theta, goal.x, goal.y,
            goal.theta, type);
  }

  public static Spline reticulateSplines(double x0, double y0, double theta0,
                                          double x1, double y1, double theta1, Spline.SplineType type) {
    System.out.println("Reticulating splines...");
   
    double linearDistance = TrajectoryMath.getDistanceBetweenCoordinates(x0, y0, x1, y1);
    if (linearDistance == 0) {
      return null;
    }


    double thetaOffset = TrajectoryMath.getAngleOffsetBetweenCoordinates(x0, y0, x1, y1);
    double theta0_hat = ChezyMath.getDifferenceInAngleRadians(thetaOffset, theta0);
    double theta1_hat = ChezyMath.getDifferenceInAngleRadians(thetaOffset, theta1);

      /* We cannot handle vertical slopes in our rotated, translated basis.
      // This would mean the user wants to end up with a heading 90 degrees off of the straight
      // line between p0 and p1.
      */
    if (TrajectoryMath.almostEqual(Math.abs(theta0_hat), Math.PI / 2)
            || TrajectoryMath.almostEqual(Math.abs(theta1_hat), Math.PI / 2)) {
      return null;
    }

    // We also cannot handle the case that the end angle is facing towards the
    // start angle (total turn > 90 degrees).
    if (Math.abs(ChezyMath.getDifferenceInAngleRadians(theta0_hat,
            theta1_hat))
            >= Math.PI / 2) {
      return null;
    }

    // Turn angles into slopes
    double m0 = Math.tan(theta0_hat);
    double m1 = Math.tan(theta1_hat);

    double a;
    double b;
    double c;
    double d;
    double e;

    if (type == Spline.SplineType.CUBIC_HERMITE) {
      a = 0;
      b = 0;
      c = (m1 + m0) / (linearDistance * linearDistance);
      d = -(2 * m0 + m1) / linearDistance;
      e = m0;

    } else if (type == Spline.SplineType.QUINTIC_HERMITE) {
      a = -(3 * (m0 + m1)) / (linearDistance * linearDistance * linearDistance * linearDistance);
      b = (8 * m0 + 7 * m1) / (linearDistance * linearDistance * linearDistance);
      c = -(6 * m0 + 4 * m1) / (linearDistance * linearDistance);
      d = 0;
      e = m0;
    } else {
        return null;
    }
    return new Spline(a, b, c, d, e, x0, y0, linearDistance, thetaOffset);

  }
}
