package model;

import graphics.*;
import gui.*;
import entities.*;

import javax.swing.*;

import java.util.*;
import java.util.logging.*;

import storage.DeleteMe;
import storage.SaveMe;

/**
 * TimelineMaker.java
 * 
 * The model of the timeline editor and viewer. Contains all necessary objects to model the application.
 * 
 * @author Andrew Thompson
 * Wheaton College, CS 335, Spring 2014
 * Project Phase 1
 * Feb 15, 2014
 *
 */
public class TimelineMaker {
	/**
	 * A list of all the timelines in this application.
	 */
	private ArrayList<Timeline> timelines;
	/**
	 * The timeline selected in this application.
	 */
	private Timeline selectedTimeline;
	/**
	 * The category selected for display and editing in the application.
	 */
	private Category selectedCategory;
	/**
	 * The event selected in this application.
	 */
	private Event selectedEvent;
	/**
	 * Whether or not to display all categories in the selected timeline.
	 */
	private boolean displayAll;
	/**
	 * The database for storing timelines of this application.
	 */
	// TODO Add storage object.

	/**
	 * The main GUI window for this application.
	 */
	private MainWindow gui;
	/**
	 * The graphics object for displaying timelines in this application.
	 */
	private TimelineGraphics graphics;

	/**
	 * Constructor.
	 * Create a new TimelineMaker application model with database, graphics, and GUI components.
	 */
	public TimelineMaker() {
		displayAll = true;
		graphics = new TimelineGraphics(this);
		timelines = new ArrayList<Timeline>();
		timelines = SaveMe.loadAll();

		initGUI();
	}

	/**
	 * Initialize the GUI components of this application.
	 */
	private void initGUI() {
		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
		} catch (UnsupportedLookAndFeelException ex) {
			Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				gui = new MainWindow(TimelineMaker.this, graphics);
				gui.setVisible(true);
				new Thread(new Runnable() {
					public void run() {
						gui.updateTimelines();
					}
				}).start();
			}
		});

	}

	/**
	 * Retrieve a list of the names of all the timelines.
	 * @return timelines
	 */
	public ArrayList<String> getTimelineTitles() {
		ArrayList<String> toReturn = new ArrayList<String>();
		for (Timeline t: timelines)
			toReturn.add(t.getName());
		return toReturn;
	}

	/**
	 * Retrieve the timeline with the parameterized name.
	 * @param title The name of the timeline to be found
	 * @return The timeline with the correct name; null otherwise.
	 */
	private Timeline getTimeline(String title) { 
		for (Timeline t : timelines)
			if (t.getName().equals(title))
				return t;
		return null;
	}

	/**
	 * Retrieve the currently selected timeline.
	 * @return selectedTimeline
	 */
	public Timeline getSelectedTimeline() {
		return selectedTimeline;
	}

	/**
	 * Set the selected timeline.
	 * Find the timeline of the parameterized title and set selectedTimeline to it.
	 * Update selectedTimeline, selectedEvent, and graphics.
	 * @param title of the timeline
	 */
	public void selectTimeline(String title) {
		selectedTimeline = getTimeline(title);
		selectedEvent = null;
		if (selectedTimeline != null) {
			gui.updateCategories(selectedTimeline);
			updateGraphics();
		}
	}

	/**
	 * Add a timeline to this model.
	 * Update selectedTimeline, selectedEvent, graphics, and database.
	 * @param t the timeline to be added
	 */
	public void addTimeline(Timeline t) {
		selectedTimeline = t;
		selectedEvent = null;
		timelines.add(selectedTimeline);

		// TODO Add selectedTimeline to the storage helper.
		gui.updateTimelines();
		updateGraphics();
	}

	/**
	 * Remove a timeline from this model.
	 * Update selectedTimeline, selectedEvent, graphics, and database.
	 * @param t the timeline to be removed
	 */
	public void deleteTimeline() {
		if (selectedTimeline != null) {
			timelines.remove(selectedTimeline);
			DeleteMe.deleteTimeline(selectedTimeline);
			selectedTimeline = null;
			selectedEvent = null;
			graphics.clearScreen();
			gui.updateTimelines();
		}
	}

	/**
	 * Edit the selected timeline.
	 * Remove the selected timeline and replace it with the parameterized one.
	 * Update selectedTimeline, selectedEvent, graphics, and database.
	 * @param t the new timeline
	 */
	public void editTimeline(Timeline t) {
		timelines.remove(selectedTimeline);
		// TODO Remove selectedTimeline from the storage helper.

		boolean newName;
		try {
			newName = !selectedTimeline.getName().equals(t.getName());
		} catch (NullPointerException e) {
			newName = true;
		}
		selectedTimeline = t;
		timelines.add(selectedTimeline);
		// TODO Add selectedTimeline to the storage helper.
		if (newName)
			gui.updateTimelines();
		updateGraphics();
	}

	/**
	 * Retrieves a category with a particular name. If it can't find the category,
	 * it returns the Category.defaultCategory.
	 * 
	 * @param name The name of the Category to be returned
	 * @return The found Category of the Category.defaultCategory
	 */
	public Category getCategory(String name){
		for(Category cat : selectedTimeline.getCategories()){
			if(cat.getName().equals(name))
				return cat;
		}
		return selectedTimeline.defaultCategory;
	}

	/**
	 * Retrieve the currently-selected category in this application.
	 * @return selectedCategory
	 */
	public Category getSelectedCategory() {
		return selectedCategory;
	}

	/**
	 * Set the selected category to the parameterized category.
	 * @param c The category to select.
	 */
	public void selectCategory(String c) {
		selectedCategory = getCategory(c);
		updateGraphics();
	}

	/**
	 * Add the category to the currently-selected timeline.
	 * @param c The category to add.
	 */
	public void addCategory(Category c) {
		selectedTimeline.addCategory(c);
		selectedCategory = c;
		gui.updateCategories(selectedTimeline);
		updateGraphics();
	}

	/**
	 * Delete the currently-selected category.
	 */
	public void deleteCategory() {
		if (selectedCategory != null && !selectedCategory.equals(selectedTimeline.defaultCategory)) {
			selectedTimeline.removeCategory(selectedCategory);
			DeleteMe.deleteCategory(selectedCategory, selectedTimeline.getName());
			selectedCategory = null;
			gui.updateCategories(selectedTimeline);
			updateGraphics();			
		}
	}

	/**
	 * Replace the currently-selected category with the parameterized category in the currently-selected timeline.
	 * @param b The edited timeline.
	 */
	public void editCategory(Category b) {
		selectedTimeline.replaceCategory(selectedCategory, b);
		gui.updateCategories(selectedTimeline);
		updateGraphics();
	}

	/**
	 * Retrieve the currently selected event.
	 * @return selectedEvent
	 */
	public Event getSelectedEvent() { 
		return selectedEvent; 
	}

	/**
	 * Set the selected event.
	 * @param e The event to be selected
	 */
	public void selectEvent(Event e) {
		if (e != null)
			selectedEvent = e;
	}

	/**
	 * Add an event to the selected timeline.
	 * Update selectedTimeline, selectedEvent, graphics, and database.
	 * @param e the new event
	 */
	public void addEvent(Event e) {
		if (selectedTimeline != null) {
			selectedTimeline.addEvent(e);
			selectedEvent = e;

			updateGraphics();

			// TODO Add selectedTimeline to the storage helper.
		}
	}

	/**
	 * Delete the selected event from the timeline.
	 * Update selectedTimeline, selectedEvent, graphics, and database.
	 */
	public void deleteEvent() {
		if (selectedEvent != null && selectedTimeline != null && selectedTimeline.contains(selectedEvent)) {
			selectedTimeline.removeEvent(selectedEvent);
			DeleteMe.deleteEvent(selectedEvent, selectedTimeline.getName());
			selectedEvent = null;

			updateGraphics();

		}
	}

	/**
	 * Edit the selected event.
	 * Remove the currently selected event from the timeline and replace it with the parameter.
	 * Update selectedTimeline, selectedEvent, graphics, and database.
	 * @param e the new event
	 */
	public void editEvent(Event e) {
		if (selectedEvent != null && selectedTimeline != null && selectedTimeline.contains(selectedEvent)) {
			selectedTimeline.removeEvent(selectedEvent);
			selectedEvent = e;
			selectedTimeline.addEvent(selectedEvent);

			updateGraphics();

			// TODO Add selectedTimeline to the storage helper.
		}
	}
	

	public void toggleDisplayType() {
		displayAll = !displayAll;
		System.out.println("Will all categories be displayed: " + displayAll);
		updateGraphics();
	}


	/**
	 * Update the graphics for the display screen.
	 */
	public void updateGraphics() { 
		graphics.clearScreen();
		if (selectedTimeline != null)
			if (displayAll)
				graphics.renderTimeline(selectedTimeline);
			else
				graphics.renderCategory(selectedTimeline, selectedCategory);
	}

}
