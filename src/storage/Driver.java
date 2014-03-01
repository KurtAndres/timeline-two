/**
 * 
 */
package storage;

import java.sql.Date;

import entities.Atomic;
import entities.Category;
import entities.Duration;
import entities.TLEvent;
import entities.Timeline;

/**
 * This is a driver to help set up a sample database. This can be used to reset or remake the database
 * since the JUnit testing can't make any permanent changes to the database (or else the test results will not be
 * consistent).
 * 
 * Not actually needed in the program.
 * 
 * @author Josh Wright
 * Created: Jan 28, 2014
 * Package: backend
 *
 */
public class Driver {

	public static void main(String[] args) {
		DBHelper helper = new DBHelper("databases/timeline.db");
                Category cat1 = new Category("category1");
                Category cat2 = new Category("category2");
		TLEvent event1 = new Atomic("one", cat1, new Date(((long)1000)*60*60*24));
		TLEvent event2 = new Duration("two", cat2, new Date(((long)1000)*60*60*24),new Date(((long)1000)*60*60*24*2));
		Timeline test1 = new Timeline("Test1", 0);
		Timeline test2 = new Timeline("Test2", 2);
		test1.addEvent(event1);
		test1.addEvent(event2);
		helper.removeTimeline(test1);
		helper.removeTimeline(test2);
		helper.writeTimeline(test1);
		helper.writeTimeline(test2);
		helper.removeTimeline(test1);
		helper.changeTimeline(test2, test1);
		Timeline[] timelines = helper.getTimelines();
		for(Timeline timeline : timelines){
			System.out.println("-----"+timeline.getName()+"-----");
			TLEvent[] events = timeline.getEvents();
			for(TLEvent event : events){
				System.out.println(event.getName());
			}
		}
		System.out.println("Finished!");
	}

}
