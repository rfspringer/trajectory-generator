package com.team254.lib.util;

import com.team254.lib.trajectory.Spline;

public class TrajectoryMath {
    public static double getDistanceBetweenCoordinates(double x0, double y0, double x1, double y1){
        return Math.sqrt((x1 - x0) * (x1 - x0) + (y1 - y0) * (y1 - y0));
    }

    public static double getAngleOffsetBetweenCoordinates(double x0, double y0, double x1, double y1){
        return Math.atan2(y1 - y0, x1 - x0);
    }

    public static boolean almostEqual(double x, double y) {
        return Math.abs(x - y) < 1E-6;
    }


    public static double angleAt(Spline spline, double percentage) {
        double angle = ChezyMath.boundAngle0to2PiRadians(
                Math.atan(derivativeAt(spline, percentage)) + spline.getThetaOffset());
        return angle;
    }

    public static double angleChangeAt(Spline spline, double percentage) {
        return ChezyMath.boundAngleNegPiToPiRadians(
                Math.atan(secondDerivativeAt(spline, percentage)));
    }

    public static double getYHat(Spline spline, double x_hat) {
        return spline.a() * Math.pow(x_hat, 5) + spline.b() * Math.pow(x_hat, 4)
                + spline.c() * Math.pow(x_hat, 3) + spline.d() * Math.pow(x_hat, 2) + spline.e() * x_hat;
    }

    private static double derivativeAt(Spline spline, double percentage) {
        percentage = Math.max(Math.min(percentage, 1), 0);
        double distanceSoFar = percentage * spline.getLinearDistance();
        double yp_hat = 5.0 * spline.a() * Math.pow(distanceSoFar, 4.0) + 4.0 * spline.b() * Math.pow(distanceSoFar, 3.0) + 3.0 * spline.c() * Math.pow(distanceSoFar, 2.0)
                + 2.0 * spline.d() * distanceSoFar + spline.e();
        return yp_hat;
    }

    private static double secondDerivativeAt(Spline spline, double percentage) {
        percentage = Math.max(Math.min(percentage, 1), 0);

        double x_hat = percentage * spline.getLinearDistance();
        double ypp_hat = (20 * spline.a() * x_hat + 12 * spline.b()) * x_hat * x_hat + 6 * spline.c() * x_hat + 2 * spline.d();


        return ypp_hat;
    }
//
//    public double calculateArcLength(Spline spline) {
//        if (spline.getArcLength() >= 0) {
//            return spline.getArcLength();
//        }
//
//        final int kNumSamples = 100000;
//        double arc_length = 0;	//initialize arc length
//        double t, dydt;
//        double integrand;
//        double last_integrand = Math.sqrt(1 + derivativeAt(0) * derivativeAt(0)) / kNumSamples;		//Initialize to estimated length of first segment
//        for (int i = 1; i <= kNumSamples; ++i) {
//            t = ((double) i) / kNumSamples;	//fraction of the arc covered
//            dydt = derivativeAt(t);
//            integrand = Math.sqrt(1 + dydt * dydt) / kNumSamples;		//find estimated length of segment based on derivative
//            arc_length += (integrand + last_integrand) / 2;	//add average of integrand and last integrand to arc length
//            last_integrand = integrand;
//        }
//        arc_length_ = linear_distance_ * arc_length;	//scale arc to actual distance (originally based on a linear distance of 1)
//        System.out.println("arc length: " + arc_length_);
//        return arc_length_;
//    }

    public static double calculateArcLength(Spline spline) {
        if (spline.getArcLength() >= 0) {
            return spline.getArcLength();
        }

        final int kNumSamples = 100000;
        double arcLength = 0;	//initialize arc length
        double t;
        double dydt;
        double integrand;
        double last_integrand = Math.sqrt(1 + derivativeAt(spline, 0) * derivativeAt(spline, 0)) / kNumSamples;		//Initialize to estimated length of first segment
        for (int i = 1; i <= kNumSamples; ++i) {
            t = ((double) i) / kNumSamples;	//fraction of the arc covered
            dydt = derivativeAt(spline, t);
//            System.out.println(dydt);
            integrand = Math.sqrt(1 + dydt * dydt) / kNumSamples;		//find estimated length of segment based on derivative
            arcLength += (integrand + last_integrand) / 2;	//add average of integrand and last integrand to arc length
            last_integrand = integrand;
        }
        System.out.println("out");
        return spline.getLinearDistance() * arcLength;	//scale arc to actual distance (originally based on a linear distance of 1)
    }


    public static double getPercentageForDistance(Spline spline, double distance) {
        // EDIT: Needs a ton of refactoring
        final int kNumSamples = 100000;
        double arc_length = 0;
        double t = 0;
        double last_arc_length = 0;
        double dydt;
        double integrand, last_integrand
                = Math.sqrt(1 + derivativeAt(spline, 0) * derivativeAt(spline, 0)) / kNumSamples;
        distance /= spline.getLinearDistance();
        for (int i = 1; i <= kNumSamples; ++i) {
            t = ((double) i) / kNumSamples;
            dydt = derivativeAt(spline, t);
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


    public static double[] getXandYOnSpline(Spline spline, double percentage) {
        double[] result = new double[2];

        percentage = Math.max(Math.min(percentage, 1), 0);
        double x_hat = percentage * spline.getLinearDistance();
        double y_hat = getYHat(spline, x_hat);

        double cos_theta = Math.cos(spline.getThetaOffset());
        double sin_theta = Math.sin(spline.getThetaOffset());

        result[0] = x_hat * cos_theta - y_hat * sin_theta + spline.getxOffset();
        result[1] = x_hat * sin_theta + y_hat * cos_theta + spline.getyOffset();
        return result;
    }

    public static double valueAt(Spline spline, double percentage) {
        percentage = Math.max(Math.min(percentage, 1), 0);
        double x_hat = percentage * spline.getLinearDistance();
        double y_hat = getYHat(spline, x_hat);

        double cos_theta = Math.cos(spline.getThetaOffset());
        double sin_theta = Math.sin(spline.getThetaOffset());

        return x_hat * sin_theta + y_hat * cos_theta + spline.getyOffset();
    }

}
