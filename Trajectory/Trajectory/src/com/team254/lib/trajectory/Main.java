package com.team254.lib.trajectory;

/**
 *
 * @author Rachel
 */
public class Main {
  
  public static void main(String[] args) {
    TrajectoryWriter trajectoryWriter = new TrajectoryWriter();
    trajectoryWriter.setDirectory(args);

    RobotConfig defaultConfig = new RobotConfig(0.01, 4.0, 3.25, 30.0,
            1.0);

      {
          // Path name must be a valid Java class name.
          final String path_name = "TestTrajectory";

          // Description of this auto mode path.
          WaypointSequence waypointSequence = new WaypointSequence(10);    //Create WaypointSequence to hold up to 10 waypoints
          //Create Waypoint sequences, add these waypoints to the waypoint sequences 3 times
          waypointSequence.addWaypoint(new Waypoint(0, 0, 0));
          waypointSequence.addWaypoint(new Waypoint(2.675, 0, 0));

          Path currentPath = PathGenerator.makePath(waypointSequence, defaultConfig, path_name);

          trajectoryWriter.writeFile(path_name, currentPath);

      }
    {

      // Path name must be a valid Java class name.
      final String path_name = "TestTrajectory2";

      // Description of this auto mode path.
      WaypointSequence p = new WaypointSequence(10);    //Create WaypointSequence to hold up to 10 waypoints
      //Create Waypoint sequences, add these waypoints to the waypoint sequences 3 times
      p.addWaypoint(new Waypoint(0, 0, 0));
      p.addWaypoint(new Waypoint(2.5, 0, 0));

      Path currentPath = PathGenerator.makePath(p, defaultConfig, path_name);

      trajectoryWriter.writeFile(path_name, currentPath);
    }
  }
}
