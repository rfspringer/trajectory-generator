package com.team254.lib.trajectory;

public class RobotConfig {
    private double timeInterval;
    private double maxVelocity;
    private double maxAcceleration;
    private double maxJerk;
    private double wheelbaseWidth;

    public RobotConfig(double timeInterval, double maxVelocity, double maxAcceleration, double maxJerk,
                       double wheelbaseWidth) {
        this.timeInterval = timeInterval;
        this.maxVelocity = maxVelocity;
        this.maxAcceleration = maxAcceleration;
        this.maxJerk = maxJerk;
        this.wheelbaseWidth = wheelbaseWidth;
    }

    public double getTimeInterval() {
        return timeInterval;
    }

    public double getMaxVelocity() {
        return maxVelocity;
    }

    public double getMaxAcceleration() {
        return maxAcceleration;
    }

    public double getMaxJerk() {
        return maxJerk;
    }

    public double getWheelbaseWidth() {
        return wheelbaseWidth;
    }
}
