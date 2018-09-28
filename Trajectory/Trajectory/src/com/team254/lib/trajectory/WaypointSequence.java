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
  private Waypoint[] waypoints;
  private int numWaypoints;

  public WaypointSequence(int max_size) {
    waypoints = new Waypoint[max_size];
  }

  public void addWaypoint(Waypoint w) {
    if (numWaypoints < waypoints.length) {
      waypoints[numWaypoints] = w;
      ++numWaypoints;
    } else {
      throw new RuntimeException("Cannot add waypoint to file, not enough space in waypoint array");
    }
  }

  public int getNumWaypoints() {
    return numWaypoints;
  }

  public Waypoint getWaypoint(int index) {
    if (index >= 0 && index < getNumWaypoints()) {
      return waypoints[index];
    } else {
      return null;
    }
  }
  
  public WaypointSequence invertPath() {
    WaypointSequence invertedWaypointSequence = new WaypointSequence(waypoints.length);
    invertedWaypointSequence.numWaypoints = numWaypoints;
    for (int i = 0; i < numWaypoints; ++i) {
      invertedWaypointSequence.waypoints[i] = invertWaypoint(waypoints[i]);
    }
    return invertedWaypointSequence;
  }

  private Waypoint invertWaypoint(Waypoint correspondingWaypoint){
      Waypoint currentWaypoint = correspondingWaypoint;
      currentWaypoint.y *= -1;
      currentWaypoint.theta = ChezyMath.boundAngle0to2PiRadians(2 * Math.PI - currentWaypoint.theta);
      return currentWaypoint;
  }
}
