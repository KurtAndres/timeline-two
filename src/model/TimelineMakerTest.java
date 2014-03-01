package model;

import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.Test;

import storage.DBHelper;

import entities.*;

/**
 * TimelineMakerTest.java
 * 
 * This class tests the functionality of the TimelineMaker class.
 * The tests are a little contrived because the TimelineMaker is interacting
 * with the database, GUI, and graphics objects, so these tests had to be
 * simplified slightly.
 * 
 * @author Andrew Thompson
 * Wheaton College, CS 335, Spring 2014
 * Project Phase 1
 * Feb 15, 2014
 */
public class TimelineMakerTest {
	
	/**
	 * Ensure that the TimelineMaker constructor instantiates correctly.
	 */
	@Test
	public void testTimelineMakerInit() {
		TimelineMaker app = null;
		assertNull(app);
		app = new TimelineMaker(new DBHelper("databases/timelinemakertest.db"));
		assertNotNull(app);
	}
	
	/**
	 * Ensure that a new Timeline is correctly added to a TimelineMaker.
	 */
	@Test
	public void testTimelineAddition() {
		TimelineMaker app = new TimelineMaker(new DBHelper("databases/timelinemakertest.db"));
		Timeline test = new Timeline("Test");
		assertFalse(app.getTimelineTitles().contains(test.getName()));
		assertNull(app.getSelectedTimeline());
		app.addTimeline(test);
		assertTrue(app.getTimelineTitles().contains(test.getName()));
		assertTrue(app.getSelectedTimeline().equals(test));
	}
	
	/**
	 * Ensure the selected timeline of the TimelineMaker is correctly deleted.
	 */
	@Test
	public void testTimelineDeletion() {
		TimelineMaker app = new TimelineMaker(new DBHelper("databases/timelinemakertest.db"));
		Timeline test = new Timeline("Test");
		app.addTimeline(test);
		assertTrue(app.getTimelineTitles().contains(test.getName()));
		assertTrue(app.getSelectedTimeline().equals(test));
		app.deleteTimeline();
		assertFalse(app.getTimelineTitles().contains(test.getName()));
		assertNull(app.getSelectedTimeline());
	}
	
	/**
	 * Ensure TimelineMaker handles timeline selection correctly.
	 */
	@Test
	public void testTimelineSelection() {
		TimelineMaker app = new TimelineMaker(new DBHelper("databases/timelinemakertest.db"));
		Timeline test1 = new Timeline("Test1");
		app.addTimeline(test1);
		app.selectTimeline(test1.getName());
		assertTrue(app.getSelectedTimeline().equals(test1));
	}
	
	/**
	 * Ensure the selected timeline of the TimelineMaker is correctly edited.
	 */
	@Test
	public void testTimelineEditing() {
		TimelineMaker app = new TimelineMaker(new DBHelper("databases/timelinemakertest.db"));
		Timeline test1 = new Timeline("Test1");
		Timeline test2 = new Timeline("Test2", test1.getEvents(), 0);
		app.addTimeline(test1);
		assertTrue(app.getTimelineTitles().contains(test1.getName()));
		app.selectTimeline(test1.getName());
		assertTrue(app.getSelectedTimeline().equals(test1));
		app.editTimeline(test2);
		assertFalse(app.getTimelineTitles().contains(test1.getName()));
		assertTrue(app.getTimelineTitles().contains(test2.getName()));
		app.selectTimeline(test2.getName());
		assertTrue(app.getSelectedTimeline().equals(test2));
	}
	
	/**
	 * Ensure a new event is added to the selected timeline correctly.
	 */
	@Test
	public void testEventAddition() {
		TimelineMaker app = new TimelineMaker(new DBHelper("databases/timelinemakertest.db"));
		Timeline test = new Timeline("Test");
                Category cat1 = new Category("category1");
                Category cat2 = new Category("category2");
		Atomic event1 = new Atomic("event1", cat1, new Date(0));
		Duration event2 = new Duration("event2", cat2, new Date(0), new Date(100000));
		app.addTimeline(test);
		assertFalse(app.getSelectedTimeline().contains(event1));
		assertFalse(app.getSelectedTimeline().contains(event2));
		assertNull(app.getSelectedEvent());
		app.addEvent(event1);
		assertTrue(app.getSelectedTimeline().contains(event1));
		assertTrue(app.getSelectedEvent().equals(event1));
		app.addEvent(event2);
		assertTrue(app.getSelectedTimeline().contains(event2));
		assertTrue(app.getSelectedEvent().equals(event2));
	}
	
	/**
	 * Ensure the selected event is correctly deleted.
	 */
	@Test
	public void testEventDeletion() {
		TimelineMaker app = new TimelineMaker(new DBHelper("databases/timelinemakertest.db"));
		Timeline test = new Timeline("Test");
                Category cat1 = new Category("category1");
		Atomic event = new Atomic("event1", cat1, new Date(0));
		app.addTimeline(test);
		app.addEvent(event);
		assertTrue(app.getSelectedTimeline().contains(event));
		assertTrue(app.getSelectedEvent().equals(event));
		app.deleteEvent();
		assertFalse(app.getSelectedTimeline().contains(event));
		assertNull(app.getSelectedEvent());
	}
	
	/**
	 * Ensure events are selected correctly.
	 */
	@Test
	public void testEventSelection() {
		TimelineMaker app = new TimelineMaker(new DBHelper("databases/timelinemakertest.db"));
		Timeline test = new Timeline("Test");
                Category cat1 = new Category("category1");
		Atomic event = new Atomic("event1", cat1, new Date(0));
		app.addTimeline(test);
		app.addEvent(event);
		app.selectEvent(event);
		assertTrue(app.getSelectedEvent().equals(event));
	}
	
	/**
	 * Ensure events are edited correctly.
	 */
	@Test
	public void testEventEditing() {
		TimelineMaker app = new TimelineMaker(new DBHelper("databases/timelinemakertest.db"));
		Timeline test = new Timeline("Test");
                Category cat1 = new Category("category1");
                Category cat2 = new Category("category2");
		Atomic event1 = new Atomic("event1", cat1, new Date(0));
		app.addTimeline(test);
		app.addEvent(event1);
		app.selectEvent(event1);
		Duration event2 = new Duration("event2", cat2, new Date(0), new Date(100));
		app.editEvent(event2);
		assertTrue(app.getSelectedTimeline().contains(event2));
		assertTrue(app.getSelectedEvent().equals(event2));
		
	}
}
