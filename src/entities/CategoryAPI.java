/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.util.ArrayList;

/**
 * API for the Category class. Useful for keeping track of the public methods.
 * 
 * @author Daniel
 */
public interface CategoryAPI {
    
    /**
     * Check whether the category contains an event.
     * 
     * @param event the event for which to search
     * @return true if found, false otherwise
     */
    public boolean contains(Event event);
    
    /**
     * Add an event to this categories known list of events.
     * 
     * @param event the event to add
     * @return true if successful, false otherwise
     */
    public boolean addEvent(Event event);
    
    /**
     * Removes an event from this categories known list of events.
     * 
     * @param event the event to remove
     * @return true if successful, false otherwise
     */
    public boolean removeEvent(Event event);
    
    /**
     * Access a copy of the list of events known to belong to this category.
     * 
     * @return a copy of the ArrayList of events
     */
    public ArrayList<Event> getEvents();
    
    /**
     * Get a copy of the name of the category.
     * 
     * @return a copy of the String representing the name of the category
     */
    public String getName();
    
    /**
     * Get a copy of the selectColor of the category.
     * 
     * @return a copy of the selectColor of this category
     */
    public Color getSelectColor();
    
    /**
     * Get a copy of the deselectColor of the category.
     * 
     * @return a copy of the deselectColor of this category
     */
    public Color getDeselectColor();
    
    /**
     * Get the icon of this category.
     * 
     * @return the icon of this category
     */
    public Image getIcon();
    
    /**
     * Change the name of the Category.
     * 
     * @param name The new name for the category
     * @return true if successful, false otherwise
     */
    public boolean setName(String name);
    
    /**
     * Change the selectColor of the Category.
     * 
     * @param selectColor The new selectColor for the category
     * @return true if successful, false otherwise
     */
    public boolean setSelectColor(Color selectColor);
    
    /**
     * Change the deselectColor of the Category.
     * 
     * @param deselectColor The new deselectColor for the category
     * @return true if successful, false otherwise
     */
    public boolean setDeselectColor(Color deselectColor);
    
    /**
     * Change the icon of the Category.
     * 
     * @param icon The new icon for the category
     * @return true if successful, false otherwise
     */
    public boolean setIcon(Image icon);
    
}
