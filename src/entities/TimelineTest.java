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
                Category cat1 = new Category.Builder("category1").build();
                Category cat2 = new Category.Builder("category2").build();
		Event[] events = new Event[2];
		events[0] = new Atomic("event1", cat1, "Testing event 1.", new Date(0), new Timeline.Builder(name).events(events).axisLabel(3).build(false));
		events[1] = new Duration("event2", cat2, "Testing event 2.", new Date(0), new Date(10000), new Timeline.Builder(name).events(events).axisLabel(3).build(false));
		Timeline.AxisLabel axisLabel = Timeline.AxisLabel.YEARS;
		Timeline test = new Timeline.Builder(name).events(events).axisLabel(3).build(false);
		assertNotNull("The test timeline should not be null: ", test);
		assertTrue("Test the name: ", test.getName().equals(name));
		assertTrue("Test the events array: ", test.getEvents().size() != 0);
		assertTrue("Test the axis label: ", test.getAxisLabel() == axisLabel && test.getAxisLabelIndex() == 3);
	}
	
	@Test
	public void testEventAddition() {
		String name = "Test";
                Category cat1 = new Category.Builder("category1").build();
                Category cat2 = new Category.Builder("category2").build();
		Event[] events = new Event[2];
		events[0] = new Atomic("event1", cat1, "Testing event 1.", new Date(0), new Timeline.Builder(name).events(events).axisLabel(3).build(false));
		events[1] = new Duration("event2", cat2, "Testing event 2.", new Date(0), new Date(10000), new Timeline.Builder(name).events(events).axisLabel(3).build(false));
		Timeline test = new Timeline.Builder(name).events(events).axisLabel(3).build(false);
		Atomic testEvent = new Atomic("test event", cat1, "Testing event 1.", new Date(100), new Timeline.Builder(name).events(events).axisLabel(3).build(false));
		assertFalse("The event is not in the timeline: ", test.contains(testEvent));
		test.addEvent(testEvent);
		assertTrue("The event is now in the timeline: ", test.contains(testEvent));
	}
	
	@Test
	public void testEventRemoval() {
		String name = "Test";
                Category cat1 = new Category.Builder("category1").build();
                Category cat2 = new Category.Builder("category2").build();
		Event[] events = new Event[2];
		events[0] = new Atomic("event1", cat1, "Testing event 1.", new Date(0), new Timeline.Builder(name).events(events).axisLabel(3).build(false));
		events[1] = new Duration("event2", cat2, "Testing event 1.", new Date(0), new Date(10000), new Timeline.Builder(name).events(events).axisLabel(3).build(false));
		Timeline test = new Timeline.Builder(name).events(events).axisLabel(3).build(false);
		assertTrue("The event is in the timeline: ", test.contains(events[0]));
		test.removeEvent(events[0]);
		assertFalse("The event is no longer in the timeline: ", test.contains(events[0]));
	}

}
