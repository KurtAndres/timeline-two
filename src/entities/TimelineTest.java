package entities;

import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.Test;

/**
 * TimelineTest.java
 * 
 * This class tests the basic functionality of the Timeline class.
 * 
 * @author Andrew Thompson
 * Wheaton College, CS 335, Spring 2014
 * Project Phase 1
 * Feb 15, 2014
 */
public class TimelineTest {

	@Test
	public void testTimelineCreation() {
		String name = "Test";
		TLEvent[] events = new TLEvent[2];
		events[0] = new Atomic("event1", "category1", new Date(0));
		events[1] = new Duration("event2", "category2", new Date(0), new Date(10000));
		Timeline.AxisLabel axisLabel = Timeline.AxisLabel.YEARS;
		Timeline test = new Timeline(name, events, 3);
		assertNotNull("The test timeline should not be null: ", test);
		assertTrue("Test the name: ", test.getName().equals(name));
		assertTrue("Test the events array: ", test.getEvents().length != 0);
		assertTrue("Test the axis label: ", test.getAxisLabel() == axisLabel && test.getAxisLabelIndex() == 3);
	}
	
	@Test
	public void testEventAddition() {
		String name = "Test";
		TLEvent[] events = new TLEvent[2];
		events[0] = new Atomic("event1", "category1", new Date(0));
		events[1] = new Duration("event2", "category2", new Date(0), new Date(10000));
		Timeline test = new Timeline(name, events, 3);
		Atomic testEvent = new Atomic("test event", "category1", new Date(100));
		assertFalse("The event is not in the timeline: ", test.contains(testEvent));
		test.addEvent(testEvent);
		assertTrue("The event is now in the timeline: ", test.contains(testEvent));
	}
	
	@Test
	public void testEventRemoval() {
		String name = "Test";
		TLEvent[] events = new TLEvent[2];
		events[0] = new Atomic("event1", "category1", new Date(0));
		events[1] = new Duration("event2", "category2", new Date(0), new Date(10000));
		Timeline test = new Timeline(name, events, 3);
		assertTrue("The event is in the timeline: ", test.contains(events[0]));
		test.removeEvent(events[0]);
		assertFalse("The event is no longer in the timeline: ", test.contains(events[0]));
	}

}
