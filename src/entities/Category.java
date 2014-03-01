/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

/**
 * Category.java
 * 
 * The Category class is an identifier and a means by which to classify and group like events.
 * A category contains an ArrayList of known events in this category, a name,
 * a color for events when selected, a color for events when deselected,
 * and a JavaFX image which shall be the optional image to display for events.
 * 
 * @author Daniel Conroy
 * Wheaton College, CS 335, Spring 2014
 * Project Phase 2
 * Feb 27, 2014
 */
public class Category implements CategoryAPI{
    
    /**
     * ArrayList to keep track of the events in the Category
     */
    private ArrayList<Event> events;

    /**
     * Name of the Category
     */
    private String name;
    
    /**
     * Color for belonging events when selected
     */
    private Color selectColor;
    
    /**
     * Color for belonging events when deselected
     */
    private Color deselectColor;
    
    /**
     * Icon for optional display of a event.
     */
    private Image icon;
    
    /**
     * Constructor for name.
     * 
     * @param name 
     */
    public Category(String name){
        this.name = name;
    }
    
    /**
     * Constructor for name, selectColor, and deselectColor.
     * 
     * @param name
     * @param sColor
     * @param dsColor 
     */
    public Category(String name, Color sColor, Color dsColor){
        this.name = name;
        this.selectColor = sColor;
        this.deselectColor = dsColor;
    }
    
    /**
     * Constructor for name and icon.
     * 
     * @param name
     * @param icon 
     */
    public Category(String name, Image icon){
        this.name = name;
        this.icon = icon;
    }
    
    /**
     * Constructor for name, selectColor, deselectColor, and icon.
     * 
     * @param name
     * @param sColor
     * @param dsColor
     * @param icon 
     */
    public Category(String name, Color sColor, Color dsColor, Image icon){
        this.name = name;
        this.selectColor = sColor;
        this.deselectColor = dsColor;
        this.icon = icon;
    }
    
    /**
     * Constructor for name, selectColor, deselectColor, icon, and events.
     * 
     * @param name
     * @param sColor
     * @param dsColor
     * @param icon
     * @param events 
     */
    public Category(String name, Color sColor, Color dsColor, Image icon, ArrayList<Event> events) {
        this.name = name;
        this.selectColor = sColor;
        this.deselectColor = dsColor;
        this.icon = icon;
        this.events = events;
    }
    
    /**
     * Check whether the category contains an event.
     * 
     * @param event the event for which to search
     * @return true if found, false otherwise
     */
    public boolean contains(Event event){
        return events.contains(event);
    }
    
    /**
     * Add an event to this categories known list of events. Sets the event's category to this.
     * 
     * @param event the event to add
     * @return true if successful, false otherwise
     */
    public boolean addEvent(Event event){
        event.setCategory(this);
        return events.add(event);
    }
    
    /**
     * Removes an event from this categories known list of events. If the event has a reference to this category,
     * its category field is set to null.
     * 
     * @param event the event to remove
     * @return true if successful, false otherwise
     */
    public boolean removeEvent(Event event){
        if(this.equals(event.getCategory()))
            event.setCategory(null);
        return events.remove(event);
    }
    
    /**
     * Access a copy of the list of events known to belong to this category.
     * 
     * @return a copy of the ArrayList of events
     */
    public ArrayList<Event> getEvents(){
        ArrayList<Event> toReturn = new ArrayList<Event>();
        toReturn.addAll(events);
        return toReturn;
    }
    
    /**
     * Get a copy of the name of the category.
     * 
     * @return a copy of the String representing the name of the category
     */
    public String getName(){
        return ""+name;
    }
    
    /**
     * Get a copy of the selectColor of the category.
     * 
     * @return a copy of the selectColor of this category
     */
    public Color getSelectColor(){
        return new Color(selectColor.getRed(),selectColor.getGreen(),selectColor.getBlue(),selectColor.getOpacity());
    }
    
    /**
     * Get a copy of the deselectColor of the category.
     * 
     * @return a copy of the deselectColor of this category
     */
    public Color getDeselectColor(){
        return new Color(deselectColor.getRed(),deselectColor.getGreen(),deselectColor.getBlue(),deselectColor.getOpacity());
    }
    
    /**
     * Get the icon of this category.
     * 
     * @return the icon of this category
     */
    public Image getIcon(){
        return icon;
    }
    
    /**
     * Change the name of the Category.
     * 
     * @param name The new name for the category
     * @return true if successful, false otherwise
     */
    public boolean setName(String name){
        try{
            this.name=name;
        }catch(Exception e){
            return false;
        }
        return true;
    }
    
    /**
     * Change the selectColor of the Category.
     * 
     * @param selectColor The new selectColor for the category
     * @return true if successful, false otherwise
     */
    public boolean setSelectColor(Color selectColor){
        try{
            this.selectColor=selectColor;
        }catch(Exception e){
            return false;
        }
        return true;
    }
    
    /**
     * Change the deselectColor of the Category.
     * 
     * @param deselectColor The new deselectColor for the category
     * @return true if successful, false otherwise
     */
    public boolean setDeselectColor(Color deselectColor){
        try{
            this.deselectColor=deselectColor;
        }catch(Exception e){
            return false;
        }
        return true;
    }
    
    /**
     * Change the icon of the Category.
     * 
     * @param icon The new icon for the category
     * @return true if successful, false otherwise
     */
    public boolean setIcon(Image icon){
        try{
            this.icon=icon;
        }catch(Exception e){
            return false;
        }
        return true;
    }

}