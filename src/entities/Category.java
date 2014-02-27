/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.image.Image;

/**
 *
 * @author Daniel
 */
public class Category implements CategoryAPI{
    
    /**
     * ArrayList to keep track of the events in the category
     */
    private ArrayList<TLEvent> events;

    /**
     * Name of the category
     */
    private String name;

    /**
     * enum for keeping track of the potential units to render the category in
     * currently only DAYS, MONTHS, and YEARS work, but implementing the others would be very simple
     */
    public static enum AxisLabel {
            DAYS, WEEKS, MONTHS, YEARS, DECADES, CENTURIES, MILLENNIA;
    }

    /**
     * Array of the AxisLabels, for getting the value based on an index
     */
    private static final Category.AxisLabel[] AXIS_LABELS = { Category.AxisLabel.DAYS, Category.AxisLabel.WEEKS, Category.AxisLabel.MONTHS, Category.AxisLabel.YEARS, Category.AxisLabel.DECADES, Category.AxisLabel.CENTURIES, Category.AxisLabel.MILLENNIA};

    /**
     * The units to render the category in
     */
    private Category.AxisLabel axisLabel;

    /**
     * whether the category has been changed since its last database sync
     */
    private boolean dirty;
    
    /**
     * The icon of the category.
     */
    private Image icon;

    /**
     * Constructor
     * 
     * @param name Category name
     */
    public Category(String name){
            this.name = name;
            events = new ArrayList<TLEvent>();
            axisLabel = Category.AxisLabel.YEARS;
            setDirty(true);
    }

    /**
     * Constructor for name and events
     * Sets axisLabel to YEARS by default
     * 
     * @param name Category name
     * @param events Events in category
     */
    public Category(String name, TLEvent[] events){
            this.name = name;
            this.events = new ArrayList<TLEvent>(Arrays.asList(events));
            axisLabel = Category.AxisLabel.YEARS;
            setDirty(true);
    }

    /**
     * Constructor for name and axisLabel
     * 
     * @param name Category name
     * @param axisLabel Unit to render category in
     */
    public Category(String name, int axisLabel) {
            this.name = name;
            events = new ArrayList<TLEvent>();
            this.axisLabel = AXIS_LABELS[axisLabel];
            this.events = new ArrayList<TLEvent>();
            dirty = true;
    }

    /**
     * Constructor for name, events, and axisLabel
     * 
     * @param name Category name
     * @param events Events in category
     * @param axisLabel Unit to render category in
     */
    public Category(String name, TLEvent[] events, int axisLabel) {
            this.name = name;
            if(events != null)
                    this.events = new ArrayList<TLEvent>(Arrays.asList(events));
            else
                    this.events = new ArrayList<TLEvent>();
            this.axisLabel = AXIS_LABELS[axisLabel];
            dirty = true;
    }

    @Override
    public boolean contains(TLEvent event) {
            for (TLEvent e : events)
                    if (e.equals(event))
                            return true;
            return false;
    }

    @Override
    public void addEvent(TLEvent event) {
            setDirty(true);
            events.add(event);
    }

    @Override
    public boolean removeEvent(TLEvent event) {
            if(events.contains(event)){
                    events.remove(event);
                    setDirty(true);
                    return true;
            }else{
                    return false;
            }
    }

    @Override
    public boolean changeEvent(TLEvent oldEvent, TLEvent newEvent) {
            if(events.contains(oldEvent)){
                    events.remove(oldEvent);
                    events.add(newEvent);
                    setDirty(true);
                    return true;
            }else{
                    return false;
            }
    }

    @Override
    public TLEvent[] getEvents() {
            if(events.isEmpty()) return null;
            return (TLEvent[])events.toArray(new TLEvent[events.size()]);
    }

    @Override
    public boolean isDirty() {
            return dirty;
    }

    @Override
    public void setDirty(boolean dirty) {
            this.dirty = dirty;
    }

    @Override
    public String getName() {
            return name;
    }

    @Override
    public int getAxisLabelIndex() { 
            for (int i = 0; i < AXIS_LABELS.length; i++)
                    if (AXIS_LABELS[i] == axisLabel)
                            return i;
            return -1;
    }

    @Override
    public Category.AxisLabel getAxisLabel() {
            return axisLabel;
    }
    
    @Override
    public void setIcon(Image icon){
        this.icon=icon;
    }
}
