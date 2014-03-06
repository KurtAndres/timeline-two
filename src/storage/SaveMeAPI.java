/**
 * 
 */
package storage;

import entities.Category;
import entities.Event;
import entities.Timeline;

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
	 * @param filePath The path from the current dir to the category to load. Normally takes the form of timelineName + "\\categories\\" + categoryName 
	 * @return The category stored at the given filePath.
	 *
	 */
	public abstract Category loadCategory(String filePath);
	
	/**
	 * Saves an event of type TLEvent.
	 * 
	 * @param event The event to save.
	 * @param timeline The timeline which the event is part of. 
	 *
	 */
	public abstract void saveEvent(Event event, String timeline);
	
	/**
	 * Loads an event of type TLEvent.
	 * 
	 * @param filePath The path from the current dir to the event to load. Normally takes the form of timelineName + "\\events\\" + eventName 
	 * @return The event stored at the given filePath.
	 *
	 */
	public abstract Event loadEvent(String filePath);

}
