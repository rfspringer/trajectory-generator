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
  //test comment refactoring
	//TOP OF CODE SHOULD BE MOVED TO SOMEWHERE ELSE
  public static String joinPath(String path1, String path2)
  {
      File file1 = new File(path1);    // Creates a file with a pathname  of path1
      File file2 = new File(file1, path2);    // Creates a file with parent location of file1 and child location of path2
      return file2.getPath();
  }
  
  private static boolean writeFile(String path, String data) {
    try {
      File file = new File(path);
      // if file doesn't exists, then create it
      if (!file.exists()) {
          file.createNewFile();
      }
      
      // writes file based off of inputted path and data
      FileWriter fw = new FileWriter(file.getAbsoluteFile());
      BufferedWriter bw = new BufferedWriter(fw);
      bw.write(data);
      bw.close();
    } catch (IOException e) {
      return false;
    }
    
    return true;
  }
  
  public static void main(String[] args) {
	  
    String directory = ".";		//Set default directory to store files
    if (args.length >= 1) {
      directory = args[0];		//If the user passes a directory, use that instead
    }
    
	//create config objects of for robot constraints
    // Edit to avoid public objects, pass constraints instead and use getters and setters
    TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
    TrajectoryGenerator.Config defaultConfig = new TrajectoryGenerator.Config();
    
    //Edit to move to Config class
    final double kWheelbaseWidth = 12/12;
    
    {
    	// Edits only written for this one
    	// Edit to just use a preset config
    	// Edit to pass path name, config, and waypoint sequence to path object
    	// Edit so that all of the generic test formatting stuff should be in a different class for path formatting, probably with the two functions at the top of this class
    	// Edit the braces out because they =are pointless
    	
    	//set config values for InsideLanePathFar
        config.timeInterval = .01;	//seconds
        config.max_acc = 3.25;	//feet/second^2
        config.max_jerk = 30.0;	//feet/second^3
        config.max_vel = 4.0;	//feet/second
      // Path name must be a valid Java class name.
      final String path_name = "RedOffPlatformClose";
      
      // Description of this auto mode path.
      // Remember that this is for the GO LEFT CASE!
      WaypointSequence p = new WaypointSequence(10);	//Create WaypointSequence to hold up to 10 waypoints
      //Create Waypoint sequences, add these waypoints to the waypoint sequences 3 times
      p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
      p.addWaypoint(new WaypointSequence.Waypoint(2.675, 0, 0));

      Path path = PathGenerator.makePath(p, config,
          kWheelbaseWidth, path_name);

      // Outputs to the directory supplied as the first argument.
      TextFileSerializer js = new TextFileSerializer();
      String serialized = js.serialize(path);
      //System.out.print(serialized);
      String fullpath = joinPath(directory, path_name + ".txt");
      if (!writeFile(fullpath, serialized)) {
        System.err.println(fullpath + " could not be written!!!!1");
        System.exit(1);
      } else {
        System.out.println("Wrote " + fullpath);
      }
    }
    
    {
    	//set config values for InsideLanePathFar
        config.timeInterval = .01;	//seconds
        config.max_acc = 3.25;	//feet/second^2
        config.max_jerk = 30.0;	//feet/second^3
        config.max_vel = 4.0;	//feet/second
      // Path name must be a valid Java class name.
      final String path_name = "BlueOffPlatformClose";
      
      // Description of this auto mode path.
      // Remember that this is for the GO LEFT CASE!
      WaypointSequence p = new WaypointSequence(10);	//Create WaypointSequence to hold up to 10 waypoints
      //Create Waypoint sequences, add these waypoints to the waypoint sequences 3 times
      p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
      p.addWaypoint(new WaypointSequence.Waypoint(2.5, 0, 0));

      Path path = PathGenerator.makePath(p, config,
          kWheelbaseWidth, path_name);

      // Outputs to the directory supplied as the first argument.
      TextFileSerializer js = new TextFileSerializer();
      String serialized = js.serialize(path);
      //System.out.print(serialized);
      String fullpath = joinPath(directory, path_name + ".txt");
      if (!writeFile(fullpath, serialized)) {
        System.err.println(fullpath + " could not be written!!!!1");
        System.exit(1);
      } else {
        System.out.println("Wrote " + fullpath);
      }
    }
    
    
    {
    	//set config values for InsideLanePathFar
        config.timeInterval = .01;	//seconds
        config.max_acc = 3.25;	//feet/second^2
        config.max_jerk = 30.0;	//feet/second^3
        config.max_vel = 4.0;	//feet/second
      // Path name must be a valid Java class name.
      final String path_name = "RedOffPlatformCenter";
      
      // Description of this auto mode path.
      // Remember that this is for the GO LEFT CASE!
      WaypointSequence p = new WaypointSequence(10);	//Create WaypointSequence to hold up to 10 waypoints
      //Create Waypoint sequences, add these waypoints to the waypoint sequences 3 times
      p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
      p.addWaypoint(new WaypointSequence.Waypoint(3.35, 0, 0));

      Path path = PathGenerator.makePath(p, config,
          kWheelbaseWidth, path_name);

      // Outputs to the directory supplied as the first argument.
      TextFileSerializer js = new TextFileSerializer();
      String serialized = js.serialize(path);
      //System.out.print(serialized);
      String fullpath = joinPath(directory, path_name + ".txt");
      if (!writeFile(fullpath, serialized)) {
        System.err.println(fullpath + " could not be written!!!!1");
        System.exit(1);
      } else {
        System.out.println("Wrote " + fullpath);
      }
    }
    
    {
    	//set config values for InsideLanePathFar
        config.timeInterval = .01;	//seconds
        config.max_acc = 3.25;	//feet/second^2
        config.max_jerk = 30.0;	//feet/second^3
        config.max_vel = 4.0;	//feet/second
      // Path name must be a valid Java class name.
      final String path_name = "BlueOffPlatformCenter";
      
      // Description of this auto mode path.
      // Remember that this is for the GO LEFT CASE!
      WaypointSequence p = new WaypointSequence(10);	//Create WaypointSequence to hold up to 10 waypoints
      //Create Waypoint sequences, add these waypoints to the waypoint sequences 3 times
      p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
      p.addWaypoint(new WaypointSequence.Waypoint(3.0, 0, 0));

      Path path = PathGenerator.makePath(p, config,
          kWheelbaseWidth, path_name);

      // Outputs to the directory supplied as the first argument.
      TextFileSerializer js = new TextFileSerializer();
      String serialized = js.serialize(path);
      //System.out.print(serialized);
      String fullpath = joinPath(directory, path_name + ".txt");
      if (!writeFile(fullpath, serialized)) {
        System.err.println(fullpath + " could not be written!!!!1");
        System.exit(1);
      } else {
        System.out.println("Wrote " + fullpath);
      }
    }
    
    {
    	//set config values for InsideLanePathFar
        config.timeInterval = .01;	//seconds
        config.max_acc = 3.25;	//feet/second^2
        config.max_jerk = 30.0;	//feet/second^3
        config.max_vel = 4.0;	//feet/second
      // Path name must be a valid Java class name.
      final String path_name = "RedOffPlatformFar";
      
      // Description of this auto mode path.
      // Remember that this is for the GO LEFT CASE!
      WaypointSequence p = new WaypointSequence(10);	//Create WaypointSequence to hold up to 10 waypoints
      //Create Waypoint sequences, add these waypoints to the waypoint sequences 3 times
      p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
      p.addWaypoint(new WaypointSequence.Waypoint(3.765, 0, 0));

      Path path = PathGenerator.makePath(p, config,
          kWheelbaseWidth, path_name);

      // Outputs to the directory supplied as the first argument.
      TextFileSerializer js = new TextFileSerializer();
      String serialized = js.serialize(path);
      //System.out.print(serialized);
      String fullpath = joinPath(directory, path_name + ".txt");
      if (!writeFile(fullpath, serialized)) {
        System.err.println(fullpath + " could not be written!!!!1");
        System.exit(1);
      } else {
        System.out.println("Wrote " + fullpath);
      }
    }
    
    {
    	//set config values for InsideLanePathFar
        config.timeInterval = .01;	//seconds
        config.max_acc = 3.25;	//feet/second^2
        config.max_jerk = 30.0;	//feet/second^3
        config.max_vel = 4.0;	//feet/second
      // Path name must be a valid Java class name.
      final String path_name = "BlueOffPlatformFar";
      
      // Description of this auto mode path.
      // Remember that this is for the GO LEFT CASE!
      WaypointSequence p = new WaypointSequence(10);	//Create WaypointSequence to hold up to 10 waypoints
      //Create Waypoint sequences, add these waypoints to the waypoint sequences 3 times
      p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
      p.addWaypoint(new WaypointSequence.Waypoint(3.635, 0, 0));

      Path path = PathGenerator.makePath(p, config,
          kWheelbaseWidth, path_name);

      // Outputs to the directory supplied as the first argument.
      TextFileSerializer js = new TextFileSerializer();
      String serialized = js.serialize(path);
      //System.out.print(serialized);
      String fullpath = joinPath(directory, path_name + ".txt");
      if (!writeFile(fullpath, serialized)) {
        System.err.println(fullpath + " could not be written!!!!1");
        System.exit(1);
      } else {
        System.out.println("Wrote " + fullpath);
      }
    }
    
    
    {
    	//set config values for InsideLanePathFar
        config.timeInterval = .01;	//seconds
        config.max_acc = 3.25;	//feet/second^2
        config.max_jerk = 30.0;	//feet/second^3
        config.max_vel = 4.0;	//feet/second
      // Path name must be a valid Java class name.
      final String path_name = "ToCryptobox";
      
      // Description of this auto mode path.
      // Remember that this is for the GO LEFT CASE!
      WaypointSequence p = new WaypointSequence(10);	//Create WaypointSequence to hold up to 10 waypoints
      //Create Waypoint sequences, add these waypoints to the waypoint sequences 3 times
      p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
      p.addWaypoint(new WaypointSequence.Waypoint(1.15, 0, 0));

      Path path = PathGenerator.makePath(p, config,
          kWheelbaseWidth, path_name);

      // Outputs to the directory supplied as the first argument.
      TextFileSerializer js = new TextFileSerializer();
      String serialized = js.serialize(path);
      //System.out.print(serialized);
      String fullpath = joinPath(directory, path_name + ".txt");
      if (!writeFile(fullpath, serialized)) {
        System.err.println(fullpath + " could not be written!!!");
        System.exit(1);
      } else {
        System.out.println("Wrote " + fullpath);
      }
    }
    
    {
        config.timeInterval = .01;	//seconds
        config.max_acc = 3.25;	//feet/second^2
        config.max_jerk = 30.0;	//feet/second^3
        config.max_vel = 4.0;	//feet/second
      // Path name must be a valid Java class name.
      final String path_name = "BackUp";
      
      // Description of this auto mode path.
      WaypointSequence p = new WaypointSequence(10);
      p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
      p.addWaypoint(new WaypointSequence.Waypoint(0.8, 0, 0));

      Path path = PathGenerator.makePath(p, config,
          kWheelbaseWidth, path_name);

      // Outputs to the directory supplied as the first argument.
      TextFileSerializer js = new TextFileSerializer();
      String serialized = js.serialize(path);
      //System.out.print(serialized);
      String fullpath = joinPath(directory, path_name + ".txt");
      if (!writeFile(fullpath, serialized)) {
        System.err.println(fullpath + " could not be written!!!!1");
        System.exit(1);
      } else {
        System.out.println("Wrote " + fullpath);
      }
    }
    
    
    {
        config.timeInterval = .01;	//seconds
        config.max_acc = 3.25;	//feet/second^2
        config.max_jerk = 30.0;	//feet/second^3
        config.max_vel = 4.0;	//feet/second
      // Path name must be a valid Java class name.
      final String path_name = "AngledToCryptobox";
      
      // Description of this auto mode path.
      WaypointSequence p = new WaypointSequence(10);
      p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
      p.addWaypoint(new WaypointSequence.Waypoint(3.3, 0, 0));

      Path path = PathGenerator.makePath(p, config,
          kWheelbaseWidth, path_name);

      // Outputs to the directory supplied as the first argument.
      TextFileSerializer js = new TextFileSerializer();
      String serialized = js.serialize(path);
      //System.out.print(serialized);
      String fullpath = joinPath(directory, path_name + ".txt");
      if (!writeFile(fullpath, serialized)) {
        System.err.println(fullpath + " could not be written!!!!1");
        System.exit(1);
      } else {
        System.out.println("Wrote " + fullpath);
      }
    }
    
    
    
    {
        config.timeInterval = .01;	//seconds
        config.max_acc = 3.25;	//feet/second^2
        config.max_jerk = 30.0;	//feet/second^3
        config.max_vel = 4.0;	//feet/second
      // Path name must be a valid Java class name.
      final String path_name = "RedFarClose";
      
      // Description of this auto mode path.
      WaypointSequence p = new WaypointSequence(10);
      p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
      p.addWaypoint(new WaypointSequence.Waypoint(2.088, 0, 0));

      Path path = PathGenerator.makePath(p, config,
          kWheelbaseWidth, path_name);

      // Outputs to the directory supplied as the first argument.
      TextFileSerializer js = new TextFileSerializer();
      String serialized = js.serialize(path);
      //System.out.print(serialized);
      String fullpath = joinPath(directory, path_name + ".txt");
      if (!writeFile(fullpath, serialized)) {
        System.err.println(fullpath + " could not be written!!!!1");
        System.exit(1);
      } else {
        System.out.println("Wrote " + fullpath);
      }
    }
    
    
    {
        config.timeInterval = .01;	//seconds
        config.max_acc = 3.25;	//feet/second^2
        config.max_jerk = 30.0;	//feet/second^3
        config.max_vel = 4.0;	//feet/second
      // Path name must be a valid Java class name.
      final String path_name = "BlueFarClose";
      
      // Description of this auto mode path.
      WaypointSequence p = new WaypointSequence(10);
      p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
      p.addWaypoint(new WaypointSequence.Waypoint(2.088, 0, 0));


      Path path = PathGenerator.makePath(p, config,
          kWheelbaseWidth, path_name);

      // Outputs to the directory supplied as the first argument.
      TextFileSerializer js = new TextFileSerializer();
      String serialized = js.serialize(path);
      //System.out.print(serialized);
      String fullpath = joinPath(directory, path_name + ".txt");
      if (!writeFile(fullpath, serialized)) {
        System.err.println(fullpath + " could not be written!!!");
        System.exit(1);
      } else {
        System.out.println("Wrote " + fullpath);
      }
    }
    
    
    
    {
        config.timeInterval = .01;	//seconds
        config.max_acc = 3.25;	//feet/second^2
        config.max_jerk = 30.0;	//feet/second^3
        config.max_vel = 4.0;	//feet/second
      // Path name must be a valid Java class name.
      final String path_name = "RedFarCenter";
      
      // Description of this auto mode path.
      WaypointSequence p = new WaypointSequence(10);
      p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
      p.addWaypoint(new WaypointSequence.Waypoint(2.236, 0, 0));

      Path path = PathGenerator.makePath(p, config,
          kWheelbaseWidth, path_name);

      // Outputs to the directory supplied as the first argument.
      TextFileSerializer js = new TextFileSerializer();
      String serialized = js.serialize(path);
      //System.out.print(serialized);
      String fullpath = joinPath(directory, path_name + ".txt");
      if (!writeFile(fullpath, serialized)) {
        System.err.println(fullpath + " could not be written!!!!1");
        System.exit(1);
      } else {
        System.out.println("Wrote " + fullpath);
      }
    }
    
    
    {
        config.timeInterval = .01;	//seconds
        config.max_acc = 3.25;	//feet/second^2
        config.max_jerk = 30.0;	//feet/second^3
        config.max_vel = 4.0;	//feet/second
      // Path name must be a valid Java class name.
      final String path_name = "BlueFarCenter";
      
      // Description of this auto mode path.
      WaypointSequence p = new WaypointSequence(10);
      p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
      p.addWaypoint(new WaypointSequence.Waypoint(2.4, 0, 0));
      
      
      Path path = PathGenerator.makePath(p, config,
          kWheelbaseWidth, path_name);

      // Outputs to the directory supplied as the first argument.
      TextFileSerializer js = new TextFileSerializer();
      String serialized = js.serialize(path);
      //System.out.print(serialized);
      String fullpath = joinPath(directory, path_name + ".txt");
      if (!writeFile(fullpath, serialized)) {
        System.err.println(fullpath + " could not be written!!!!1");
        System.exit(1);
      } else {
        System.out.println("Wrote " + fullpath);
      }
    }
    
    
    
    
    {
        config.timeInterval = .01;	//seconds
        config.max_acc = 3.25;	//feet/second^2
        config.max_jerk = 30.0;	//feet/second^3
        config.max_vel = 4.0;	//feet/second
      // Path name must be a valid Java class name.
      final String path_name = "RedFarFar";
      
      // Description of this auto mode path.
      WaypointSequence p = new WaypointSequence(10);
      p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
      p.addWaypoint(new WaypointSequence.Waypoint(2.6, 0, 0));

      Path path = PathGenerator.makePath(p, config,
          kWheelbaseWidth, path_name);

      // Outputs to the directory supplied as the first argument.
      TextFileSerializer js = new TextFileSerializer();
      String serialized = js.serialize(path);
      //System.out.print(serialized);
      String fullpath = joinPath(directory, path_name + ".txt");
      if (!writeFile(fullpath, serialized)) {
        System.err.println(fullpath + " could not be written!!!!1");
        System.exit(1);
      } else {
        System.out.println("Wrote " + fullpath);
      }
    }
    
    
    {
        config.timeInterval = .01;	//seconds
        config.max_acc = 3.25;	//feet/second^2
        config.max_jerk = 30.0;	//feet/second^3
        config.max_vel = 4.0;	//feet/second
      // Path name must be a valid Java class name.
      final String path_name = "BlueFarFar";
      
      // Description of this auto mode path.
      WaypointSequence p = new WaypointSequence(10);
      p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
      p.addWaypoint(new WaypointSequence.Waypoint(2.6, 0, 0));

      Path path = PathGenerator.makePath(p, config,
          kWheelbaseWidth, path_name);

      // Outputs to the directory supplied as the first argument.
      TextFileSerializer js = new TextFileSerializer();
      String serialized = js.serialize(path);
      //System.out.print(serialized);
      String fullpath = joinPath(directory, path_name + ".txt");
      if (!writeFile(fullpath, serialized)) {
        System.err.println(fullpath + " could not be written!!!!1");
        System.exit(1);
      } else {
        System.out.println("Wrote " + fullpath);
      }
    }
    
    
    
    
    {
        config.timeInterval = .01;	//seconds
        config.max_acc = 3.25;	//feet/second^2
        config.max_jerk = 30.0;	//feet/second^3
        config.max_vel = 4.0;	//feet/second
      // Path name must be a valid Java class name.
      final String path_name = "GlyphPickUp";
      
      // Description of this auto mode path.
      WaypointSequence p = new WaypointSequence(10);
      p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
      p.addWaypoint(new WaypointSequence.Waypoint(0.16, 0, 0));

      Path path = PathGenerator.makePath(p, config,
          kWheelbaseWidth, path_name);

      // Outputs to the directory supplied as the first argument.
      TextFileSerializer js = new TextFileSerializer();
      String serialized = js.serialize(path);
      //System.out.print(serialized);
      String fullpath = joinPath(directory, path_name + ".txt");
      if (!writeFile(fullpath, serialized)) {
        System.err.println(fullpath + " could not be written!!!");
        System.exit(1);
      } else {
        System.out.println("Wrote " + fullpath);
      }
    }
   
  }
}
