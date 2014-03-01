/**
 * 
 */
package entities;

import entities.Timeline.AxisLabel;

/**
 * API for the Timeline class. Useful for keeping track of the public methods.
 * 
 * @author Josh Wright
 * Created: Jan 29, 2014
 * Package: backend
 *
 */
public interface TimelineAPI {
        
        /**
         * remove a Category from the timeline. Assigns the category of all associated events to null.
         * 
         * @param category The category to remove
         * @return 
         */
        public boolean removeCategory(Category category);
        
        /**
         * add a Category to the timeline
         * 
         * @param category 
         */
        public void addCategory(Category category);
        
        /**
         * see if a Category is in the timeline's list of categories
         * 
         * @param category the category for which to search
         * @return true if found, else false
         */
        public boolean contains(Category category);
    
	/**
	 * see if Event event is in the timeline's list of events
	 * 
	 * @param event the event to see if the timeline contains
	 * @return whether the event is in the timeline
	 */
	public boolean contains(Event event);
	
	/**
	 * add a Event to the timeline
	 * 
	 * @param event the event to add to the timeline
	 */
	public void addEvent(Event event);
	
	/**
	 * remove a Event to the timeline
	 * 
	 * @param event the event to remove
	 * @return whether the event was removed correctly
	 */
	public boolean removeEvent(Event event);
	
	/**
	 * Swap an oldTLEvent out for a newTLEvent. Useful for updating an event whose data has changed
	 * 
	 * @param oldTLEventName the event to switch out
	 * @param newTLEventName the event to switch in
	 * @return whether the event was swapped successfully (false if oldTLEvent is not in the timeline)
	 */
	public boolean changeEvent(Event oldTLEventName, Event newTLEventName);
	
	/**
	 * Returns an array with all the timeline's events, length is exactly the number of events
	 * 
	 * @return array of all the timeline's events
	 */
	public Event[] getEvents();
	
	/**
	 * Returns true if the timeline has been altered since last database sync. Currently not in use
	 * 
	 * @return if the timeline is dirty
	 */
	public boolean isDirty();
	
	/**
	 * Sets the dirty value
	 * 
	 * @param dirty value to set
	 */
	public void setDirty(boolean dirty);
	
	/**
	 * getter method for the timeline's name
	 * 
	 * @return timeline name
	 */
	public String getName();
	
	/**
	 * Returns an int representing the index of the axisLabel
	 * 
	 * @return axisLabel index
	 */
	public int getAxisLabelIndex();
	
	/**
	 * Returns the actual axisLabel
	 * 
	 * @return axisLabel
	 */
	public AxisLabel getAxisLabel();
}
