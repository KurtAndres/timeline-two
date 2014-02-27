/**
 * 
 */
package entities;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Timeline.java
 * 
 * Timeline object to keep track of the different timelines in the project. Contains a name, ArrayList of Categorys, AxisLabel (for rendering), 
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
	private ArrayList<Category> categories;
	
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
		categories = new ArrayList<Category>();
		axisLabel = AxisLabel.YEARS;
		setDirty(true);
	}
	
	/**
	 * Constructor for name and categories
	 * Sets axisLabel to YEARS by default
	 * 
	 * @param name Timeline name
	 * @param categories Categories in timeline
	 */
	public Timeline(String name, Category[] categories){
		this.name = name;
		this.categories = new ArrayList<Category>(Arrays.asList(categories));
		axisLabel = AxisLabel.YEARS;
		setDirty(true);
	}
	
	/**
	 * Constructor for name and axisLabel
	 * 
	 * @param name Timeline name
	 * @param axisLabel Unit to render timeline in
	 */
	public Timeline(String name, int axisLabel) {
		this.name = name;
		categories = new ArrayList<Category>();
		this.axisLabel = AXIS_LABELS[axisLabel];
		this.categories = new ArrayList<Category>();
		dirty = true;
	}
	
	/**
	 * Constructor for name, categories, and axisLabel
	 * 
	 * @param name Timeline name
	 * @param categories Categories in timeline
	 * @param axisLabel Unit to render timeline in
	 */
	public Timeline(String name, Category[] categories, int axisLabel) {
		this.name = name;
		if(categories != null)
			this.categories = new ArrayList<Category>(Arrays.asList(categories));
		else
			this.categories = new ArrayList<Category>();
		this.axisLabel = AXIS_LABELS[axisLabel];
		dirty = true;
	}
	
	@Override
	public boolean contains(Category category) {
		for (Category e : categories)
			if (e.equals(category))
				return true;
		return false;
	}

	@Override
	public void addCategory(Category category) {
		setDirty(true);
		categories.add(category);
	}

	@Override
	public boolean removeCategory(Category category) {
		if(categories.contains(category)){
			categories.remove(category);
			setDirty(true);
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean changeCategory(Category oldCategory, Category newCategory) {
		if(categories.contains(oldCategory)){
			categories.remove(oldCategory);
			categories.add(newCategory);
			setDirty(true);
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Category[] getCategories() {
		if(categories.isEmpty()) return null;
		return (Category[])categories.toArray(new Category[categories.size()]);
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
