package com.team254.lib.trajectory;

import com.team254.lib.trajectory.io.JavaSerializer;
import com.team254.lib.trajectory.io.JavaStringSerializer;
import com.team254.lib.trajectory.io.TextFileSerializer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Rachel
 */
public class Main {
  
  public static void main(String[] args) {
    TrajectoryWriter trajectoryWriter = new TrajectoryWriter();
    trajectoryWriter.setDirectory(args);

    Config defaultConfig = new Config(0.01, 4.0, 3.25, 30.0, 1.0);


    {
      // Edits only written for this one
      // Edit to just use a preset config
      // Edit to pass path name, config, and waypoint sequence to path object
      // Edit so that all of the generic test formatting stuff should be in a different class for path formatting, probably with the two functions at the top of this class
      // Edit the braces out because they =are pointless

      // Path name must be a valid Java class name.
      final String path_name = "TestTrajectory";

      // Description of this auto mode path.
      WaypointSequence waypointSequence = new WaypointSequence(10);    //Create WaypointSequence to hold up to 10 waypoints
      //Create Waypoint sequences, add these waypoints to the waypoint sequences 3 times
      waypointSequence.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
      waypointSequence.addWaypoint(new WaypointSequence.Waypoint(2.675, 0, 0));

      Path currentPath = PathGenerator.makePath(waypointSequence, defaultConfig, path_name);

      trajectoryWriter.writeFile(path_name, currentPath);
    }

    {

      // Path name must be a valid Java class name.
      final String path_name = "TestTrajectory2";

      // Description of this auto mode path.
      // Remember that this is for the GO LEFT CASE!
      WaypointSequence p = new WaypointSequence(10);    //Create WaypointSequence to hold up to 10 waypoints
      //Create Waypoint sequences, add these waypoints to the waypoint sequences 3 times
      p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
      p.addWaypoint(new WaypointSequence.Waypoint(2.5, 0, 0));

      Path currentPath = PathGenerator.makePath(p, defaultConfig, path_name);

      trajectoryWriter.writeFile(path_name, currentPath);
    }
  }
}
