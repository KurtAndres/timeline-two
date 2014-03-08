/**
 * 
 */
package storage;

import com.thoughtworks.xstream.XStream;

import entities.Category;
import entities.Event;
import entities.Timeline;
import entities.Timeline.Builder;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author leanne.miller
 * 
 * A class which can save Timelines, Events and Categories. 
 * 
 * A directory called "Timelines" is created in the working directory, within which are directories for each individual timeline.
 * In each timeline's directory are two other directories and one file. 
 * The first directory holds all of the timeline's events and the other holds all of its categories and the file keeps track of the axis label.
 * Currently saving to disk. Eventually will try to save to Google Drive.
 *
 */
public class SaveMe{
	
	/**
	 * Loads all timelines found in the working directory under the folder "Timelines".
	 * If no timelines are found, it will return an empty arraylist.
	 * 
	 * @return An arraylist containing all of the timelines found in the "Timelines" folder. 
	 *
	 */
	public static ArrayList<Timeline> loadAll(){
		ArrayList<Timeline> timelines = new ArrayList<Timeline>();
		String path = System.getProperty("user.dir") + "/Timelines";
		File dir = new File(path);
		dir.mkdir();

		File[] files = dir.listFiles();
		for(File f : files){
			timelines.add(loadTimeline(f.getName()));
		}
		
		return timelines;	
		
	}

	/**
	 * Saves an instance of class Timeline.
	 * 
	 * @param timeline The timeline to save.
	 *
	 */
	public static void saveTimeline(Timeline tl){
		ArrayList<Event> events = tl.getEvents();	
		HashSet<Category> categories = tl.getCategories();
		String tlName = tl.getName();
		
		//Make dirs for the timeline's events and categories	
		new File("Timelines/" + tlName + "/events").mkdirs();
		new File("Timelines/" +tlName + "/categories").mkdirs();
		
		//Save events
		for(Event e : events){
			saveEvent(e, tlName);
		}
		
		//Save categories
		for(Category c : categories){
			saveCategory(c, tlName);
		}
		
		//Save axis label
		saveAxisLabel(tl.getAxisLabelIndex(), tlName);
		
	}
	

	/**
	 * Loads an instance of class Timeline.
	 * 
	 * @param timeLineName The name the the timeline to load.
	 * @return The timeline stored under the given name, with all of its events.
	 *
	 */
	public static Timeline loadTimeline(String timeline){		
		Builder b = new Builder(timeline);
		ArrayList<Event> events = new ArrayList<Event>();
		HashSet<Category> categories = new HashSet<Category>();

		//Load events
		String eventPath = "Timelines/" + timeline + "/events";
		File eventDir = new File(eventPath);
		File[] eventFiles = eventDir.listFiles();
		for(File f : eventFiles){
			if(f.isFile()){
				events.add(loadEvent(f.getName(), timeline));	
			}
		}
		b.events(events);

		//Load Categories
		String categoryPath = "Timelines/" + timeline + "/categories";
		File categoryDir = new File(categoryPath);
		File[] categoryFiles = categoryDir.listFiles();
		for(File f : categoryFiles){
			if(f.isFile()){
				categories.add(loadCategory(f.getName(), timeline));	
			}
		}
		b.categories(categories);
		
		//Load Axis Label
		b.axisLabel(loadAxisLabel(timeline));
	

		return b.build(true);
	}
	
	
	/**
	 * Saves an instance of class Category.
	 * 
	 * @param category The category to save.
	 * @param timeline The timeline which the category is part of.
	 *
	 */
	public static void saveCategory(Category category, String timeline) {
		XStream xstream = new XStream(); 
		String name = category.getName();
		String path = "Timelines/" + timeline + "/categories/" + name + ".xml";

		xstream.alias("entities.Category", Category.class);
		String xml = xstream.toXML(category);
		try{
			FileOutputStream out = new FileOutputStream(path);
			byte[] bytes = xml.getBytes("UTF-8");
			out.write(bytes);
			out.close();

		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Could not save.");
		}

	}

	
	/**
	 * Loads an instance of class Category.
	 * 
	 * @param filename The name of the category to load.
	 * @param timeline The name of the timeline which the category is a part of.
	 * @return The category stored at the given filePath.
	 *
	 */
	public static Category loadCategory(String filename, String timeline) {
		XStream xstream = new XStream();
		Category category = null;
		String path = System.getProperty("user.dir"); //Grab the working directory
		path = path + "/Timelines/" + timeline + "/categories/" + filename;

		try{
			File xmlFile = new File(path);
			category = (Category)xstream.fromXML(xmlFile);       
		}catch(Exception e){
			System.err.println("Error in XML Read: " + e.getMessage());
		}

		return category;
	}
	
	
	//Ref: http://stackoverflow.com/questions/13063815/save-xml-file-with-xstream
	/**
	 * Saves an event of type Event.
	 * 
	 * @param event The event to save.
	 * @param timeline The timeline which the event is part of. 
	 *
	 */
	public static void saveEvent(Event event, String timeline){
		XStream xstream = new XStream(); 
		String name = event.getName();
		String path = "Timelines/" + timeline + "/events/" + name + ".xml";

		xstream.alias(name, Event.class);
		String xml = xstream.toXML(event);
		try{
			FileOutputStream out = new FileOutputStream(path);
			byte[] bytes = xml.getBytes("UTF-8");
			out.write(bytes);
			out.close();

		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Could not save.");
		}
	}

	
	/**
	 * Loads an event of type Event.
	 * 
	 * @param filename The name of the event to load.
	 * @param timeline The name of the timeline which the event is a part of.
	 * @return The event stored under the given name.
	 *
	 */
	public static Event loadEvent(String filename, String timeline){
		XStream xstream = new XStream();
		Event event = null;
		String path = System.getProperty("user.dir"); //Grab the working dir
		path = path + "/Timelines/" + timeline + "/events/" + filename;
		
		try{
			File xmlFile = new File(path);
			event = (Event)xstream.fromXML(xmlFile);       
		}catch(Exception e){
			System.err.println("Error in XML Read: " + e.getMessage());
		}

		return event;
	}
	
	
	/**
	 * Saves a timeline's current AxisLabel.
	 * 
	 * @param axislabel The axis label to save.
	 * @param timeline The name of the timeline which the axis label belongs to.
	 *
	 */
	public static void saveAxisLabel(int axisLabel, String timeline){
		XStream xstream = new XStream(); 
		String path = "Timelines/" + timeline + "/axisLabel.xml";

		Integer aL = (Integer)axisLabel;
		xstream.alias(aL.toString(), Integer.class);
		String xml = xstream.toXML(aL);
		try{
			FileOutputStream out = new FileOutputStream(path);
			byte[] bytes = xml.getBytes("UTF-8");
			out.write(bytes);
			out.close();

		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Could not save.");
		}
	}
	
	/**
	 * Loads a timeline's AxisLabel
	 * 
	 * @param timeline The timeline whose axis label you want to load.
	 * @return The saved axis label for the given timeline.
	 *
	 */
	public static int loadAxisLabel(String timeline){
		XStream xstream = new XStream();
		int axisLabel = 0;
		//Integer aL = new Integer(0);
		String path = System.getProperty("user.dir"); //Grab the working dir
		path = path + "/Timelines/" + timeline + "/axisLabel.xml";
		
		try{
			File xmlFile = new File(path);
			axisLabel = (Integer)xstream.fromXML(xmlFile);       
		}catch(Exception e){
			System.err.println("Error in XML Read: " + e.getMessage());
		}

		return axisLabel;
	}


}
