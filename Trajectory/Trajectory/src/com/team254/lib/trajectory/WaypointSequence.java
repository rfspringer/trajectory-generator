package com.team254.lib.trajectory;

import com.team254.lib.util.ChezyMath;

/**
 * A WaypointSequence is a sequence of Waypoints.  #whatdidyouexpect
 *
 * @author Art Kalb
 * @author Stephen Pinkerton
 * @author Jared341
 */
public class WaypointSequence {

  Waypoint[] waypoints;
  int num_waypoints_;

  public WaypointSequence(int max_size) {
	  //build array of specified size
    waypoints = new Waypoint[max_size];
  }

  public void addWaypoint(Waypoint w) {
	  //If there is space in the array, add the waypoint
    if (num_waypoints_ < waypoints.length) {
      waypoints[num_waypoints_] = w;
      ++num_waypoints_;
    }
  }

  public int getNumWaypoints() {
    return num_waypoints_;
  }

  public Waypoint getWaypoint(int index) {
    if (index >= 0 && index < getNumWaypoints()) {
      return waypoints[index];
    } else {
      return null;
    }
  }
  
  public WaypointSequence invertY() {
    WaypointSequence inverted = new WaypointSequence(waypoints.length);
    inverted.num_waypoints_ = num_waypoints_;
    for (int i = 0; i < num_waypoints_; ++i) {
      inverted.waypoints[i] = waypoints[i];
      inverted.waypoints[i].y *= -1;
      inverted.waypoints[i].theta = ChezyMath.boundAngle0to2PiRadians(
              2*Math.PI - inverted.waypoints[i].theta);
    }
    
    return inverted;
  }
}
