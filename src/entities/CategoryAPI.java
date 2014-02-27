/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import javafx.scene.image.Image;

/**
 * API for the Category class. Useful for keeping track of the public methods.
 * 
 * @author Daniel
 */
public interface CategoryAPI {
    /**
     * see if TLEvent event is in the category's list of events
     * 
     * @param event the event to see if the category contains
     * @return whether the event is in the category
     */
    public boolean contains(TLEvent event);
    
    /**
     * add a TLEvent to the category
     * 
     * @param event the event to add to the category
     */
    public void addEvent(TLEvent event);
    
    /**
     * remove a TLEvent to the category
     * 
     * @param event the event to remove
     * @return whether the event was removed correctly
     */
    public boolean removeEvent(TLEvent event);
    
    /**
     * Swap an oldEvent out for a newEvent. Useful for updating an event whose data has changed
     * 
     * @param oldEventName the event to switch out
     * @param newEvent the event to switch in
     * @return whether the event was swapped successfully (false if oldEvent is not in the category)
     */
    public boolean changeEvent(TLEvent oldEventName, TLEvent newEvent);

    /**
     * Returns an array with all the category's events, length is exactly the number of events
     * 
     * @return array of all the category's events
     */
    public TLEvent[] getEvents();

    /**
     * Returns true if the category has been altered since last database sync. Currently not in use
     * 
     * @return if the category is dirty
     */
    public boolean isDirty();

    /**
     * Sets the dirty value
     * 
     * @param dirty value to set
     */
    public void setDirty(boolean dirty);

    /**
     * getter method for the category's name
     * 
     * @return category name
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
    public Category.AxisLabel getAxisLabel();
    
    /**
     * Sets the image of for the Category
     * 
     * @param icon image to set
     */
    public void setIcon(Image icon);
}
