package com.team254.lib.trajectory;

import com.team254.lib.util.ChezyMath;

/**
 * Do cubic spline interpolation between points.
 *
 * @author Art Kalb
 * @author Jared341
 */
public class Spline {

  public static class Type {

    private final String value_;

    private Type(String value) {
      this.value_ = value;
    }

    public String toString() {
      return value_;
    }
  }

  // Cubic spline where positions and first derivatives (angle) constraints will
  // be met but second derivatives may be discontinuous.
  public static final Type CubicHermite = new Type("CubicHermite");

  // Quintic spline where positions and first derivatives (angle) constraints
  // will be met, and all second derivatives at knots = 0.
  public static final Type QuinticHermite = new Type("QuinticHermite");

  Type type_;
  double A_;  // ax^5
  double B_;  // + bx^4
  double C_;  // + cx^3
  double D_;  // + dx^2
  double E_;  // + ex
  // f is always 0 for the spline formulation we support.

  // The offset from the world frame to the spline frame.
  // Add these to the output of the spline to obtain world coordinates.
  double y_offset_;
  double x_offset_;
  double linear_distance_;
  double theta_offset_;
  double arc_length_;

  Spline() {
    // All splines should be made via the static interface
    arc_length_ = -1;	//Initializes spline length
  }

  private static boolean almostEqual(double x, double y) {
    return Math.abs(x - y) < 1E-6;
  }

  public static boolean reticulateSplines(Waypoint start,
          Waypoint goal, Spline result, Type type) {
    return reticulateSplines(start.x, start.y, start.theta, goal.x, goal.y,
            goal.theta, result, type);
  }

  public static boolean reticulateSplines(double x0, double y0, double theta0,
          double x1, double y1, double theta1, Spline result, Type type) {
    System.out.println("Reticulating splines...");
    result.type_ = type;	//set spline type to given type

    // Set spline offsets
    result.x_offset_ = x0;
    result.y_offset_ = y0;
   
    double d = Math.sqrt((x1 - x0) * (x1 - x0) + (y1 - y0) * (y1 - y0));	//distance between two waypoints in a straight line
    if (d == 0) {
      return false;
    }
    result.linear_distance_ = d;
    result.theta_offset_ = Math.atan2(y1 - y0, x1 - x0);	//angle between the two new points
    double theta0_hat = ChezyMath.getDifferenceInAngleRadians(
            result.theta_offset_, theta0);		//difference from the angle between the two waypoints and the starting angle
    double theta1_hat = ChezyMath.getDifferenceInAngleRadians(
            result.theta_offset_, theta1);		//difference from the angle between the two waypoints and the ending angle
    // We cannot handle vertical slopes in our rotated, translated basis.
    // This would mean the user wants to end up 90 degrees off of the straight
    // line between p0 and p1.
    if (almostEqual(Math.abs(theta0_hat), Math.PI / 2)
            || almostEqual(Math.abs(theta1_hat), Math.PI / 2)) {
      return false;
    }
    // We also cannot handle the case that the end angle is facing towards the
    // start angle (total turn > 90 degrees).
    if (Math.abs(ChezyMath.getDifferenceInAngleRadians(theta0_hat,
            theta1_hat))
            >= Math.PI / 2) {
      return false;
    }
    // Turn angles into slopes
    double m0 = Math.tan(theta0_hat);
    double m1 = Math.tan(theta1_hat);

    if (type == CubicHermite) {
      // Calculate the cubic spline coefficients
      result.A_ = 0;
      result.B_ = 0;
      result.C_ = (m1 + m0) / (d * d);
      result.D_ = -(2 * m0 + m1) / d;
      result.E_ = m0;
    } else if (type == QuinticHermite) {
    	//solve for spline coefficients
      result.A_ = -(3 * (m0 + m1)) / (d * d * d * d);
      result.B_ = (8 * m0 + 7 * m1) / (d * d * d);
      result.C_ = -(6 * m0 + 4 * m1) / (d * d);
      result.D_ = 0;
      result.E_ = m0;
    }

    return true;
  }

  public double calculateLength() {
    if (arc_length_ >= 0) {
      return arc_length_;
    }

    final int kNumSamples = 100000;
    double arc_length = 0;	//initialize arc length
    double t, dydt;
    double integrand;
    double last_integrand = Math.sqrt(1 + derivativeAt(0) * derivativeAt(0)) / kNumSamples;		//Initialize to estimated length of first segment
    for (int i = 1; i <= kNumSamples; ++i) {
      t = ((double) i) / kNumSamples;	//fraction of the arc covered
      dydt = derivativeAt(t);
      integrand = Math.sqrt(1 + dydt * dydt) / kNumSamples;		//find estimated length of segment based on derivative
      arc_length += (integrand + last_integrand) / 2;	//add average of integrand and last integrand to arc length
      last_integrand = integrand;
    }
    arc_length_ = linear_distance_ * arc_length;	//scale arc to actual distance (originally based on a linear distance of 1)
    return arc_length_;
  }

  public double getPercentageForDistance(double distance) {
    final int kNumSamples = 100000;
    double arc_length = 0;
    double t = 0;
    double last_arc_length = 0;
    double dydt;
    double integrand, last_integrand
            = Math.sqrt(1 + derivativeAt(0) * derivativeAt(0)) / kNumSamples;
    distance /= linear_distance_;
    for (int i = 1; i <= kNumSamples; ++i) {
      t = ((double) i) / kNumSamples;
      dydt = derivativeAt(t);
      integrand = Math.sqrt(1 + dydt * dydt) / kNumSamples;
      arc_length += (integrand + last_integrand) / 2;
      if (arc_length > distance) {
        break;
      }
      last_integrand = integrand;
      last_arc_length = arc_length;
    }

    // Interpolate between samples.
    double interpolated = t;
    if (arc_length != last_arc_length) {
      interpolated += ((distance - last_arc_length)
              / (arc_length - last_arc_length) - 1) / (double) kNumSamples;
    }
    return interpolated;
  }

  public double[] getXandY(double percentage) {
    double[] result = new double[2];

    percentage = Math.max(Math.min(percentage, 1), 0);
    double x_hat = percentage * linear_distance_;
    double y_hat = (A_ * x_hat + B_) * x_hat * x_hat * x_hat * x_hat
            + C_ * x_hat * x_hat * x_hat + D_ * x_hat * x_hat + E_ * x_hat;

    double cos_theta = Math.cos(theta_offset_);
    double sin_theta = Math.sin(theta_offset_);

    result[0] = x_hat * cos_theta - y_hat * sin_theta + x_offset_;
    result[1] = x_hat * sin_theta + y_hat * cos_theta + y_offset_;
    return result;
  }

  public double valueAt(double percentage) {
    percentage = Math.max(Math.min(percentage, 1), 0);
    double x_hat = percentage * linear_distance_;
    double y_hat = (A_ * x_hat + B_) * x_hat * x_hat * x_hat * x_hat
            + C_ * x_hat * x_hat * x_hat + D_ * x_hat * x_hat + E_ * x_hat;

    double cos_theta = Math.cos(theta_offset_);
    double sin_theta = Math.sin(theta_offset_);

    double value = x_hat * sin_theta + y_hat * cos_theta + y_offset_;
    return value;
  }

  private double derivativeAt(double percentage) {
    percentage = Math.max(Math.min(percentage, 1), 0);

    double d = percentage * linear_distance_;		//how far you are along the linear distance
    double yp_hat = (5 * A_ * d + 4 * B_) * d * d * d + 3 * C_ * d * d
            + 2 * D_ * d + E_;

    return yp_hat;
  }

  private double secondDerivativeAt(double percentage) {
    percentage = Math.max(Math.min(percentage, 1), 0);

    double x_hat = percentage * linear_distance_;
    double ypp_hat = (20 * A_ * x_hat + 12 * B_) * x_hat * x_hat + 6 * C_ * x_hat + 2 * D_;

   
    return ypp_hat;
  }

  public double angleAt(double percentage) {
    double angle = ChezyMath.boundAngle0to2PiRadians(
            Math.atan(derivativeAt(percentage)) + theta_offset_);
    return angle;
  }

  public double angleChangeAt(double percentage) {
    return ChezyMath.boundAngleNegPiToPiRadians(
            Math.atan(secondDerivativeAt(percentage)));
  }

  public String toString() {
    return "a=" + A_ + "; b=" + B_ + "; c=" + C_ + "; d=" + D_ + "; e=" + E_;
  }
}
