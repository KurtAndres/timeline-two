/**
 * 
 */
package storage;

import entities.Category;
import entities.Event;
import entities.Timeline;
import entities.Timeline.AxisLabel;

/**
 * @author leanne.miller
 *
 */
public interface SaveMeAPI {
	/**
	 * Saves an instance of class Timeline.
	 * 
	 * @param timeline The timeline to save.
	 *
	 */
	public abstract void saveTimeline(Timeline timeline);
	
	/**
	 * Loads an instance of class Timeline.
	 * 
	 * @param timeLineName The name the the timeline to load.
	 * @return The timeline stored under the given name, with all of its events.
	 *
	 */
	public abstract Timeline loadTimeline(String timelineName);
	
	/**
	 * Saves an instance of class Category.
	 * 
	 * @param category The category to save.
	 * @param timeline The timeline which the category is part of.
	 *
	 */
	public abstract void saveCategory(Category category, String timeline);
	
	/**
	 * Loads an instance of class Category.
	 * 
	 * @param filename The name of the category to load.
	 * @param timeline The name of the timeline which the category is a part of.
	 * @return The category stored at the given filePath.
	 *
	 */
	public abstract Category loadCategory(String filename, String timeline);
	
	/**
	 * Saves an event of type Event.
	 * 
	 * @param event The event to save.
	 * @param timeline The timeline which the event is part of. 
	 *
	 */
	public abstract void saveEvent(Event event, String timeline);
	
	/**
	 * Loads an event of type TLEvent.
	 * 
	 * @param filename The name of the event to load.
	 * @param timeline The name of the timeline which the event is a part of.
	 * @return The event stored under the given name.
	 *
	 */
	public abstract Event loadEvent(String filename, String timeline);
	
	/**
	 * Saves a timeline's current AxisLabel.
	 * 
	 * @param axislabel The axis label to save.
	 * @param timeline The name of the timeline which the axis label belongs to.
	 *
	 */
	public abstract void saveAxisLabel(int axislabel, String timeline);
	
	
	/**
	 * Loads a timeline's AxisLabel
	 * 
	 * @param timeline The timeline whose axis label you want to load.
	 * @return The saved axis label for the given timeline.
	 *
	 */
	public abstract int loadAxisLabel(String timeline);

	
	
}
