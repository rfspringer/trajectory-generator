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

//  public static class Type {
//
//    private final String value_;
//
//    private Type(String value) {
//      this.value_ = value;
//    }
//
//    public String toString() {
//      return value_;
//    }
//  }
//
//  // Cubic spline where positions and first derivatives (angle) constraints will
//  // be met but second derivatives may be discontinuous.
//  public static final Type CubicHermite = new Type("CubicHermite");
//
//  // Quintic spline where positions and first derivatives (angle) constraints
//  // will be met, and all second derivatives at knots = 0.
//  public static final Type QuinticHermite = new Type("QuinticHermite");
//
//  Type type;
//  double a;  // ax^5
//  double b;  // + bx^4
//  double c;  // + cx^3
//  double d;  // + dx^2
//  double e;  // + ex
//
//    // f is always 0 for the spline formulation this supports.
//
//  // The offset from the world frame to the spline frame.
//  // Add these to the output of the spline to obtain world coordinates.
//  double yOffset;
//  double xOffset;
//  double linearDistance;
//  double thetaOffset;
//  double arcLength;
//
//  SplineGenerator() {
//    // All splines should be made via the static interface
//    arcLength = -1;	//Initializes spline length
//  }

  public static Spline reticulateSplines(Waypoint start,
                                          Waypoint goal, Spline.SplineType type) {
    return reticulateSplines(start.x, start.y, start.theta, goal.x, goal.y,
            goal.theta, type);
  }

  public static Spline reticulateSplines(double x0, double y0, double theta0,
                                          double x1, double y1, double theta1, Spline.SplineType type) {
    System.out.println("Reticulating splines...");
//    result.type = type;	//set spline type to given type
//
//    // Set spline offsets
//    result.xOffset = x0;
//    result.yOffset = y0;
   
    double linearDistance = TrajectoryMath.getDistanceBetweenCoordinates(x0, y0, x1, y1);    //distance between two waypoints in a straight line
    if (linearDistance == 0) {
      return null;
    }


    double thetaOffset = TrajectoryMath.getAngleOffsetBetweenCoordinates(x0, y0, x1, y1);
    double theta0_hat = ChezyMath.getDifferenceInAngleRadians(thetaOffset, theta0);		//difference from the angle between the two waypoints and the initial angle
    double theta1_hat = ChezyMath.getDifferenceInAngleRadians(thetaOffset, theta1);		//difference from the angle between the two waypoints and the ending angle

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
      // Calculate the cubic spline coefficients
      a = 0;
      b = 0;
      c = (m1 + m0) / (linearDistance * linearDistance);
      d = -(2 * m0 + m1) / linearDistance;
      e = m0;

    } else if (type == Spline.SplineType.QUINTIC_HERMITE) {
    	//solve for spline coefficients
      a = -(3 * (m0 + m1)) / (linearDistance * linearDistance * linearDistance * linearDistance);
      b = (8 * m0 + 7 * m1) / (linearDistance * linearDistance * linearDistance);
      c = -(6 * m0 + 4 * m1) / (linearDistance * linearDistance);
      d = 0;
      e = m0;
    } else {
        return null;
    }

      System.out.println(a);
      System.out.println(b);
      System.out.println(c);
    return new Spline(a, b, c, d, e, x0, y0, linearDistance, thetaOffset);

  }

//  public double calculateLength() {
//    if (arcLength >= 0) {
//      return arcLength;
//    }
//
//    final int kNumSamples = 100000;
//    double arc_length = 0;	//initialize arc length
//    double t, dydt;
//    double integrand;
//    double last_integrand = Math.sqrt(1 + derivativeAt(0) * derivativeAt(0)) / kNumSamples;		//Initialize to estimated length of first segment
//    for (int i = 1; i <= kNumSamples; ++i) {
//      t = ((double) i) / kNumSamples;	//fraction of the arc covered
//      dydt = derivativeAt(t);
//      integrand = Math.sqrt(1 + dydt * dydt) / kNumSamples;		//find estimated length of segment based on derivative
//      arc_length += (integrand + last_integrand) / 2;	//add average of integrand and last integrand to arc length
//      last_integrand = integrand;
//    }
//    arcLength = linearDistance * arc_length;	//scale arc to actual distance (originally based on a linear distance of 1)
//    return arcLength;
//  }

//  public double getPercentageForDistance(double distance) {
//    final int kNumSamples = 100000;
//    double arc_length = 0;
//    double t = 0;
//    double last_arc_length = 0;
//    double dydt;
//    double integrand, last_integrand
//            = Math.sqrt(1 + derivativeAt(0) * derivativeAt(0)) / kNumSamples;
//    distance /= linearDistance;
//    for (int i = 1; i <= kNumSamples; ++i) {
//      t = ((double) i) / kNumSamples;
//      dydt = derivativeAt(t);
//      integrand = Math.sqrt(1 + dydt * dydt) / kNumSamples;
//      arc_length += (integrand + last_integrand) / 2;
//      if (arc_length > distance) {
//        break;
//      }
//      last_integrand = integrand;
//      last_arc_length = arc_length;
//    }
//
//    // Interpolate between samples.
//    double interpolated = t;
//    if (arc_length != last_arc_length) {
//      interpolated += ((distance - last_arc_length)
//              / (arc_length - last_arc_length) - 1) / (double) kNumSamples;
//    }
//    return interpolated;
//  }
//
//  public double[] getXandY(double percentage) {
//    double[] result = new double[2];
//
//    percentage = Math.max(Math.min(percentage, 1), 0);
//    double x_hat = percentage * linearDistance;
//    double y_hat = (a * x_hat + b) * x_hat * x_hat * x_hat * x_hat
//            + c * x_hat * x_hat * x_hat + d * x_hat * x_hat + e * x_hat;
//
//    double cos_theta = Math.cos(thetaOffset);
//    double sin_theta = Math.sin(thetaOffset);
//
//    result[0] = x_hat * cos_theta - y_hat * sin_theta + xOffset;
//    result[1] = x_hat * sin_theta + y_hat * cos_theta + yOffset;
//    return result;
//  }

//  public double valueAt(double percentage) {
//    percentage = Math.max(Math.min(percentage, 1), 0);
//    double x_hat = percentage * linearDistance;
//    double y_hat = (a * x_hat + b) * x_hat * x_hat * x_hat * x_hat
//            + c * x_hat * x_hat * x_hat + d * x_hat * x_hat + e * x_hat;
//
//    double cos_theta = Math.cos(thetaOffset);
//    double sin_theta = Math.sin(thetaOffset);
//
//    double value = x_hat * sin_theta + y_hat * cos_theta + yOffset;
//    return value;
//  }
//
//  private double derivativeAt(double percentage) {
//    percentage = Math.max(Math.min(percentage, 1), 0);
//
//    double d = percentage * linearDistance;		//how far you are along the linear distance
//    double yp_hat = (5 * a * d + 4 * b) * d * d * d + 3 * c * d * d
//            + 2 * this.d * d + e;
//
//    return yp_hat;
//  }
//
//  private double secondDerivativeAt(double percentage) {
//    percentage = Math.max(Math.min(percentage, 1), 0);
//
//    double x_hat = percentage * linearDistance;
//    double ypp_hat = (20 * a * x_hat + 12 * b) * x_hat * x_hat + 6 * c * x_hat + 2 * d;
//
//
//    return ypp_hat;
//  }
//
//  public double angleAt(double percentage) {
//    double angle = ChezyMath.boundAngle0to2PiRadians(
//            Math.atan(derivativeAt(percentage)) + thetaOffset);
//    return angle;
//  }
//
//  public double angleChangeAt(double percentage) {
//    return ChezyMath.boundAngleNegPiToPiRadians(
//            Math.atan(secondDerivativeAt(percentage)));
//  }
//
//  public String toString() {
//    return "a=" + a + "; b=" + b + "; c=" + c + "; d=" + d + "; e=" + e;
//  }
}
