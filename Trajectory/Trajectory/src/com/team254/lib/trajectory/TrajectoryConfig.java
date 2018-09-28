package com.team254.lib.trajectory;

public class TrajectoryConfig {
    public enum Strategy {
        STEP,
        TRAPEZOIDAL,
        S_CURVE,
        AUTOMATIC
    }

    public enum IntegrationMethod {
        RECTANGULAR,
        TRAPEZOIDAL
    }

    public static Strategy chooseStrategy(double start_vel, double goal_vel, double max_vel) {
        Strategy strategy;
        if (start_vel == goal_vel && start_vel == max_vel) {
            strategy = Strategy.STEP;
        } else if (start_vel == goal_vel && start_vel == 0) {
            strategy = Strategy.S_CURVE;
        } else {
            strategy = Strategy.TRAPEZOIDAL;
        }
        return strategy;
    }
}
