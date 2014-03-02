/**
 * 
 */
package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Timeline.java
 * 
 * Timeline object to keep track of the different timelines in the project. Contains a name, ArrayList of TLEvents, AxisLabel (for rendering), 
 * and boolean, dirty, which is updated whenever the Timeline is changed. This can be used for deciding when to sync to the database, but is
 * currently not in use (we sync whenever certain buttons in the GUI are pressed). 
 * 
 * @author Josh Wright and Andrew Thompson
 * Wheaton College, CS 335, Spring 2014
 * Project Phase 1
 * Feb 15, 2014
 */
public class Timeline implements TimelineAPI{
        
        /**
         * ArrayList to keep track of the categories in the timeline
         */
        private HashSet<Category> categories = new HashSet<Category>();
	
	/**
	 * ArrayList to keep track of the events in the timeline
	 */
	private ArrayList<Event> events;
	
	/**
	 * Name of the timeline
	 */
	private String name;
	
	/**
	 * enum for keeping track of the potential units to render the timeline in
	 * currently only DAYS, MONTHS, and YEARS work, but implementing the others would be very simple
	 */
	public static enum AxisLabel {
		DAYS, WEEKS, MONTHS, YEARS, DECADES, CENTURIES, MILLENNIA;
	}
	
	/**
	 * Array of the AxisLabels, for getting the value based on an index
	 */
	private static final AxisLabel[] AXIS_LABELS = { AxisLabel.DAYS, AxisLabel.WEEKS, AxisLabel.MONTHS, AxisLabel.YEARS, AxisLabel.DECADES, AxisLabel.CENTURIES, AxisLabel.MILLENNIA};
	
	/**
	 * The units to render the timeline in
	 */
	private AxisLabel axisLabel;
	
	/**
	 * whether the timeline has been changed since its last database sync
	 */
	private boolean dirty;
	
	/**
	 * Constructor
	 * 
	 * @param name Timeline name
	 */
	public Timeline(String name){
		this.name = name;
		events = new ArrayList<Event>();
		axisLabel = AxisLabel.YEARS;
		setDirty(true);
                categories.add(Category.defaultCategory);
	}
	
	/**
	 * Constructor for name and events
	 * Sets axisLabel to YEARS by default
	 * 
	 * @param name Timeline name
	 * @param events Categories in timeline
	 */
	public Timeline(String name, Event[] events){
		this.name = name;
		this.events = new ArrayList<Event>(Arrays.asList(events));
		axisLabel = AxisLabel.YEARS;
		setDirty(true);
                categories.add(Category.defaultCategory);
	}
	
	/**
	 * Constructor for name and axisLabel
	 * 
	 * @param name Timeline name
	 * @param axisLabel Unit to render timeline in
	 */
	public Timeline(String name, int axisLabel) {
		this.name = name;
		events = new ArrayList<Event>();
		this.axisLabel = AXIS_LABELS[axisLabel];
		this.events = new ArrayList<Event>();
		dirty = true;
                categories.add(Category.defaultCategory);
	}
	
	/**
	 * Constructor for name, events, and axisLabel
	 * 
	 * @param name Timeline name
	 * @param events Categories in timeline
	 * @param axisLabel Unit to render timeline in
	 */
	public Timeline(String name, Event[] events, int axisLabel) {
		this.name = name;
		if(events != null)
			this.events = new ArrayList<Event>(Arrays.asList(events));
		else
			this.events = new ArrayList<Event>();
		this.axisLabel = AXIS_LABELS[axisLabel];
		dirty = true;
                categories.add(Category.defaultCategory);
	}
        
        /**
         * add a Category to the timeline
         * 
         * @param category 
         */
        @Override
        public void addCategory(Category category){
            categories.add(category);
        }
        
        /**
         * see if a Category is in the timeline's list of categories
         * 
         * @param category the category for which to search
         * @return true if found, else false
         */
        @Override
        public boolean contains(Category category){
            return categories.contains(category);
        }
        
        /**
         * remove a Category from the timeline. Assigns the category of all associated events to null.
         * 
         * @param category The category to remove
         * @return 
         */
        @Override
        public boolean removeCategory(Category category){
            for(Event event : events){
                if(category.equals(event.getCategory())){
                    event.setCategory(null);
                }
            }
            return categories.remove(category);
        }
        
        /**
         * return the HashSet of categories belonging to this timeline
         * 
         * @return The HashSet of categories
         */
        @Override
        public HashSet<Category> getCategories(){
            return categories;
        }
        
        /**
         * return an ArrayList of the category names.
         * 
         * @return The arraylist of names
         */
        @Override
        public ArrayList<String> getCategoryNames(){
            ArrayList<String> toReturn = new ArrayList<String>();
            for(Category c : categories)
                toReturn.add(c.getName());
            return toReturn;
        }
	
	@Override
	public boolean contains(Event event) {
		for (Event e : events)
			if (e.equals(event))
				return true;
		return false;
	}

	@Override
	public void addEvent(Event event) {
		setDirty(true);
		events.add(event);
	}

	@Override
	public boolean removeEvent(Event event) {
		if(events.contains(event)){
			events.remove(event);
			setDirty(true);
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean changeEvent(Event oldTLEvent, Event newTLEvent) {
		if(events.contains(oldTLEvent)){
			events.remove(oldTLEvent);
			events.add(newTLEvent);
			setDirty(true);
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Event[] getEvents() {
		if(events.isEmpty()) return null;
		return (Event[])events.toArray(new Event[events.size()]);
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
	public AxisLabel getAxisLabel() {
		return axisLabel;
	}
}
