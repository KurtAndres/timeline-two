package graphics;

import java.util.ArrayList;

import entities.Event;

/**
 * Renderable.java
 * 
 * This class encapsulates the commonality between Category and Timeline for rendering functionality.
 * 
 * @author Andrew Thompson
 * Wheaton College, CS 335, Spring 2014
 * Project Phase 2
 * Mar 7, 2014
 */
public interface Renderable {
	
	/**
	 * Get the name of this renderable item.
	 * @return the name
	 */
	public String getName();
	
	/**
	 * Get the events of this item that will be rendered.
	 * @return an array-list of events
	 */
	public ArrayList<Event> getEvents();

}
