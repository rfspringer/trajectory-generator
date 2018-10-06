package com.team254.lib.trajectory;

import com.team254.lib.util.TrajectoryMath;

/**
 * Underlying storage mechanism for spline
 */
public class Spline {
    // Coefficients of ax^5 + bx^4 + cx^3 + dx^2 + ex + f
    private double a;
    private double b;
    private double c;
    private double d;
    private double e;
    private double f;

    // Other spline characteristics
    private double xOffset;
    private double yOffset;
    private double linearDistance;
    private double thetaOffset;
    private double arcLength = -0.02;

    public Spline(double a, double b, double c, double d, double e,
                  double xOffset, double yOffset, double linearDistance, double thetaOffset){
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = 0;

        System.out.println(a + " " +  b + " " + c + " " + d + " " + e);

        this.yOffset = yOffset;
        this.xOffset = xOffset;
        this.linearDistance = linearDistance;
        this.thetaOffset = thetaOffset;

        this.arcLength = TrajectoryMath.calculateArcLength(this);
        System.out.println("Arc length: " + arcLength);
    }

    public enum SplineType {
        QUINTIC_HERMITE,
        CUBIC_HERMITE
    }

    public double a() {
        return a;
    }

    public double b() {
        return b;
    }

    public double c() {
        return c;
    }

    public double d() {
        return d;
    }

    public double e() {
        return e;
    }

    public double f() {
        return f;
    }

    public double getLinearDistance() {
        return linearDistance;
    }

    public double getArcLength() {
        return arcLength;
    }

    public double getThetaOffset() {
        return thetaOffset;
    }

    public double getxOffset() {
        return xOffset;
    }

    public double getyOffset() {
        return yOffset;
    }


    public String toString() {
        return "a=" + a + "; b=" + b + "; c=" + c + "; d=" + d + "; e=" + e;
    }
}
