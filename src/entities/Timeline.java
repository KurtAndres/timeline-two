/**
 * 
 */
package entities;

import graphics.Renderable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import storage.SaveMe;

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
public class Timeline implements TimelineAPI, Renderable {

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
	 * Enum for keeping track of the potential units to render the timeline in.
	 * Currently only DAYS, MONTHS, and YEARS work, but implementing the others would be very simple.
	 */
	public static enum AxisLabel {
		DAYS, WEEKS, MONTHS, YEARS, DECADES, CENTURIES, MILLENNIA;
	}

	/**
	 * Array of the AxisLabels, for getting the value based on an index.
	 */
	private static final AxisLabel[] AXIS_LABELS = { AxisLabel.MONTHS, AxisLabel.YEARS };
//	private static final AxisLabel[] AXIS_LABELS = { AxisLabel.DAYS, AxisLabel.WEEKS, AxisLabel.MONTHS, AxisLabel.YEARS, AxisLabel.DECADES, AxisLabel.CENTURIES, AxisLabel.MILLENNIA};

	/**
	 * The units to render the timeline in.
	 */
	private AxisLabel axisLabel;

	/**
	 * The default category. A timeline will always have a default category.
	 */
	public Category defaultCategory; 

	/**
	 * Constructor for a timeline.
	 * 
	 * @param builder The builder required to build the timeline.
	 * @param loading Whether or not the timeline is currently in the process of being loaded.
	 */
	private Timeline(Builder builder, boolean loading){
		this.name = builder.name;
		this.categories = builder.categories;

		for(Category c : categories){
			if(c.getName().equals("Default")) defaultCategory = c;
		}
		if(defaultCategory == null){
			defaultCategory = new Category.Builder("Default").build();
		}

		this.events = builder.events;
		this.axisLabel = builder.axisLabel;
		if (!this.categories.contains(defaultCategory))
			this.categories.add(defaultCategory);
		if(!loading) 
			save();
	}

	/**
	 * The Builder class to build a Timeline.
	 */
	public static class Builder {
		// Required Field
		private String name;
		// Optional Fields
		private HashSet<Category> categories = new HashSet<Category>(); 
		private ArrayList<Event> events = new ArrayList<Event>();
		private static final AxisLabel[] AXIS_LABELS = { AxisLabel.MONTHS, AxisLabel.YEARS };
		private Timeline.AxisLabel axisLabel = AXIS_LABELS[1];

		/**
		 * Constructor for the Timeline.Builder class.
		 * 
		 * @param name The name of the Timeline.
		 */
		public Builder(String name){
			this.name = name;
		}

		/**
		 * Adds categories to the Timeline.
		 * 
		 * @param categories The categories to be added.
		 * @return The Builder building the Timeline.
		 */
		public Builder categories(HashSet<Category> categories) {
			this.categories = categories;
			if(this.categories.contains(null))
				this.categories.remove(null);
			return this;
		}

		/**
		 * Adds events to the Timeline.
		 * 
		 * @param events The events to be added.
		 * @return The Builder building the Timeline.
		 */
		public Builder events(ArrayList<Event> events){
			this.events = events; return this;
		}

		/**
		 * Adds events to the Timeline.
		 * 
		 * @param events The events to be added.
		 * @return The Builder building the Timeline.
		 */
		public Builder events(Event[] events){
			if(events != null)
				this.events = new ArrayList<Event>(Arrays.asList(events));
			return this;
		}

		/**
		 * Adds an axis label to the Timeline.
		 * 
		 * @param axisLabel The axisLabel to be added.
		 * @return The Builder building the Timeline.
		 */
		public Builder axisLabel(int axisLabel){
			this.axisLabel = AXIS_LABELS[axisLabel]; return this;
		}

		/**
		 * Builds the completed Timeline.
		 * 
		 * @return The built Timeline.
		 */
		public Timeline build(boolean loading){
			return new Timeline(this, loading);
		}
	}

	/**
	 * Saves the timeline.
	 */
	private void save(){
		SaveMe.saveTimeline(this);
	}


	/**
	 * Adds a Category to the timeline.
	 * 
	 * @param category The category to add.
	 */
	@Override
	public void addCategory(Category category){
		categories.add(category);
		save();
	}

	/**
	 * Checks if a Category is in the timeline's list of categories.
	 * 
	 * @param category The category for which to search.
	 * @return true if found, else false.
	 */
	@Override
	public boolean contains(Category category){
		return categories.contains(category);
	}

	/**
	 * Removes a Category from the timeline. Assigns the category of all associated events to the default category.
	 * 
	 * @param category The category to remove.
	 */
	@Override
	public void removeCategory(Category category){
		for(Event event : events){
			if(category.equals(event.getCategory())){
				event.setCategory(defaultCategory);
			}
		}
		categories.remove(category);
		save();
	}

	/**
	 * Replaces the initial category with a replacement category. 
	 * All of the initial category's events are reassigned to the replacement category.
	 * 
	 * @param initial The initial category.
	 * @param replacement The category to replace it with.
	 */
	public void replaceCategory(Category initial, Category replacement) {
		categories.add(replacement);
		for(Event event : events){
			if(initial.equals(event.getCategory())){
				event.setCategory(replacement);
			}
		}
		categories.remove(initial);
		save();
	}

	/**
	 * Returns the HashSet of categories belonging to this timeline.
	 * 
	 * @return The HashSet of categories
	 */
	@Override
	public HashSet<Category> getCategories(){
		return categories;
	}

	/**
	 * Returns an ArrayList of the category names.
	 * 
	 * @return The arraylist of category names.
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
		events.add(event);
		save();
	}

	@Override
	public boolean removeEvent(Event event) {
		if(events.contains(event)){
			events.remove(event);
			event.getCategory().removeEvent(event);
			save();
			return true;
		}else{
			save();
			return false;
		}
	}


	@Override
	public ArrayList<Event> getEvents() {
		return events;
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
