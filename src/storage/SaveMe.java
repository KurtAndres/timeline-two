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
 * Class which can save Timelines, Events and Categories. 
 * Creates a directory for the timeline, within which are two other directories, one holding all of the events and the other holding all of the categories.
 * Currently saving to disk. Eventually will try to save to Google Drive.
 *
 */
public class SaveMe implements SaveMeAPI{

	@Override
	public void saveTimeline(Timeline tl){
		ArrayList<Event> events = tl.getEvents();	
		HashSet<Category> categories = tl.getCategories();
		String tlName = tl.getName();
		
		//Make dirs for the timeline's events and categories	
		//File dir = new File("Timelines");
		//dir.mkdir(); 
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
	}

	@Override
	public Timeline loadTimeline(String timeline){		
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

		return b.build();
	}

	@Override
	public void saveCategory(Category category, String timeline) {
		XStream xstream = new XStream(); 
		String name = category.getName();
		String path = "Timelines/" + timeline + "/categories/" + name + ".xml";

		xstream.alias(name, Category.class);
		String xml = xstream.toXML(category);
		try{
			FileOutputStream out = new FileOutputStream(path);
			byte[] bytes = xml.getBytes("UTF-8");
			out.write(bytes);
			out.close();

		}catch(Exception e){ //Refine?
			e.printStackTrace();
			System.out.println("Could not save.");
		}

	}

	@Override
	public Category loadCategory(String filename, String timeline) {
		XStream xstream = new XStream();
		Category category = null;
		String path = System.getProperty("user.dir"); //Grab the working directory
		path = path + "Timelines/" + timeline + "/categories/" + filename;

		try{
			File xmlFile = new File(path);
			category = (Category)xstream.fromXML(xmlFile);       
		}catch(Exception e){
			System.err.println("Error in XML Read: " + e.getMessage());
		}

		return category;
	}

	//Ref: http://stackoverflow.com/questions/13063815/save-xml-file-with-xstream
	@Override
	public void saveEvent(Event event, String timeline){
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

		}catch(Exception e){ //Refine?
			e.printStackTrace();
			System.out.println("Could not save.");
		}
	}

	@Override
	public Event loadEvent(String filename, String timeline){
		XStream xstream = new XStream();
		Event event = null;
		String path = System.getProperty("user.dir"); //Grab the working dir
		path = path + "Timelines/" + timeline + "/events/" + filename;
		
		try{
			File xmlFile = new File(path);
			event = (Event)xstream.fromXML(xmlFile);       
		}catch(Exception e){
			System.err.println("Error in XML Read: " + e.getMessage());
		}

		return event;
	}


}
