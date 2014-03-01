package model;

import graphics.*;
import gui.*;
import entities.*;

import javax.swing.*;

import storage.*;

import java.util.*;
import java.util.logging.*;

/**
 * TimelineMaker.java
 * 
 * The model of the timeline editor and viewer. Contains all necessary objects to model the application.
 * 
 * @author Josh Wright and Andrew Thompson
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
	 * The categories selected for display in this application.
	 */
	private ArrayList<Category> selectedCategories;
	/**
	 * The event selected in this application.
	 */
	private Event selectedEvent;
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
		// TODO Instantiate storage helper object.
		graphics = new TimelineGraphics(this);
		timelines = new ArrayList<Timeline>();

		// TODO Load timelines from storage helper object. Add them to the timelines ArrayList.

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
						gui.updateTimelines(getTimelineTitles(), null);
						gui.updateCategories(null, null);
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
		if (selectedTimeline != null)
			updateGraphics();
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
		gui.updateTimelines(getTimelineTitles(), selectedTimeline.getName());
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
			// TODO Remove selectedTimeline from the storage helper.
			selectedTimeline = null;
			selectedEvent = null;
			graphics.clearScreen();
			gui.updateTimelines(getTimelineTitles(), null);
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
			gui.updateTimelines(getTimelineTitles(), selectedTimeline.getName());
		updateGraphics();
	}
	
	public ArrayList<String> getCategoryTitles() {
		ArrayList<String> toReturn = new ArrayList<String>();
		// TODO Populate toReturn with categories from selectedTimeline. Use an Iterator maybe?
		return toReturn;
	}
	
	public ArrayList<Category> getSelectedCategories() {
		return selectedCategories;
	}
	
	public void selectCategories(ArrayList<Category> c) {
		selectedCategories = c;
		// TODO Update graphics.
	}
	
	public void addCategory(Category c) {
		// TODO Implement.
	}
	
	public void deleteCategory() {
		// TODO Implement.
		// Loop through the selected categories and remove them from the timeline.
	}
	
	public void editCategory(Category c) {
		// TODO Implement.
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

			// TODO Remove selectedTimeline from the storage helper.
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
			selectedEvent = null;

			updateGraphics();

			// TODO Remove selectedTimeline from the storage helper.
			// TODO Add selectedTimeline to the storage helper.
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

			// TODO Remove selectedTimeline from the storage helper.
			// TODO Add selectedTimeline to the storage helper.
		}
	}
	
	
	/**
	 * Update the graphics for the display screen.
	 */
	public void updateGraphics() { 
		graphics.clearScreen();
		graphics.renderTimeline(selectedTimeline);
	}

}
