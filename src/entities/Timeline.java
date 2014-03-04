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
         * @param builder the builder required to build the timeline.
         */
        private Timeline(Builder builder){
            this.name = builder.name;
            this.events = builder.events;
            this.axisLabel = builder.axisLabel;
        }
        
        /**
         * The Builder class to build a Timeline.
         */
        public static class Builder {
            // Required Field
            private String name;
            // Optional Fields
            private ArrayList<Event> events = new ArrayList<Event>();
            private static final AxisLabel[] AXIS_LABELS = { AxisLabel.DAYS, AxisLabel.WEEKS, AxisLabel.MONTHS, AxisLabel.YEARS, AxisLabel.DECADES, AxisLabel.CENTURIES, AxisLabel.MILLENNIA};
            private Timeline.AxisLabel axisLabel = AXIS_LABELS[4];
            
            /**
             * Constructor for the Timeline.Builder class.
             * 
             * @param name The name of the Timeline.
             */
            public Builder(String name){
                this.name = name;
            }
            
            /**
             * Add events to the Timeline.
             * 
             * @param events The events to be added.
             * @return The Builder building the Timeline.
             */
            public Builder events(ArrayList<Event> events){
                this.events = events; return this;
            }
            
            /**
             * Add events to the TImeline
             * 
             * @param events The events to be added
             * @return The Builder building the Timeline
             */
            public Builder events(Event[] events){
                if(events != null)
                    this.events = new ArrayList<Event>(Arrays.asList(events));
                return this;
            }
            
            /**
             * Add an axis label to the Timeline
             * 
             * @param axisLabel The axisLabel to be added
             * @return The Builder building the Timeline
             */
            public Builder axisLabel(int axisLabel){
                this.axisLabel = AXIS_LABELS[axisLabel]; return this;
            }
            
            /**
             * Build the completed Timeline
             * 
             * @return The built Timeline
             */
            public Timeline build(){
                return new Timeline(this);
            }
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
