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
	 * see if Category category is in the timeline's list of categories
	 * 
	 * @param category the category to see if the timeline contains
	 * @return whether the category is in the timeline
	 */
	public boolean contains(Category category);
	
	/**
	 * add a Category to the timeline
	 * 
	 * @param category the category to add to the timeline
	 */
	public void addCategory(Category category);
	
	/**
	 * remove a Category to the timeline
	 * 
	 * @param category the category to remove
	 * @return whether the category was removed correctly
	 */
	public boolean removeCategory(Category category);
	
	/**
	 * Swap an oldCategory out for a newCategory. Useful for updating an category whose data has changed
	 * 
	 * @param oldCategoryName the category to switch out
	 * @param newCategoryName the category to switch in
	 * @return whether the category was swapped successfully (false if oldCategory is not in the timeline)
	 */
	public boolean changeCategory(Category oldCategoryName, Category newCategoryName);
	
	/**
	 * Returns an array with all the timeline's categories, length is exactly the number of categories
	 * 
	 * @return array of all the timeline's categories
	 */
	public Category[] getCategories();
	
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
