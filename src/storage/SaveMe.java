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
		
		//Make dirs for the timeline's events and categories, making a dir for the timeline itself in the process	
		/*File dir = new File(tlName);
		dir.mkdir(); */
		new File(tlName + "\\events").mkdirs();
		new File(tlName + "\\categories").mkdirs();
		
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
	public Timeline loadTimeline(String timelineName){		
		Builder b = new Builder(timelineName);
		ArrayList<Event> events = new ArrayList<Event>();
		HashSet<Category> categories = new HashSet<Category>();

		//Load events
		String eventPath = timelineName + "\\events";
		File eventDir = new File(eventPath);
		File[] eventFiles = eventDir.listFiles();
		for(File f : eventFiles){
			if(f.isFile()){
				events.add(loadEvent(eventPath + f.getName()));	
			}
		}
		b.events(events);

		//Load Categories
		String categoryPath = timelineName + "\\categories";
		File categoryDir = new File(categoryPath);
		File[] categoryFiles = categoryDir.listFiles();
		for(File f : categoryFiles){
			if(f.isFile()){
				categories.add(loadCategory(categoryPath + f.getName()));	
			}
		}
		b.categories(categories);

		return b.build();
	}

	@Override
	public void saveCategory(Category category, String timeline) {
		XStream xstream = new XStream(); 
		String path = timeline + "\\categories\\";
		String name = category.getName();

		xstream.alias(name, Category.class);
		String xml = xstream.toXML(category);
		try{
			FileOutputStream out = new FileOutputStream(path + name + ".xml");
			byte[] bytes = xml.getBytes("UTF-8");
			out.write(bytes);
			out.close();

		}catch(Exception e){ //Refine?
			e.printStackTrace();
			System.out.println("Could not save.");
		}

	}

	@Override
	public Category loadCategory(String filePath) {
		XStream xstream = new XStream();
		Category category = null;
		String path = System.getProperty("user.dir"); //Grab the working directory

		try{
			File xmlFile = new File(path + "\\" + filePath);
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
		String path = timeline + "\\events\\";
		String name = event.getName();

		xstream.alias(name, Event.class);
		String xml = xstream.toXML(event);
		try{
			FileOutputStream out = new FileOutputStream(path + name + ".xml");
			byte[] bytes = xml.getBytes("UTF-8");
			out.write(bytes);
			out.close();

		}catch(Exception e){ //Refine?
			e.printStackTrace();
			System.out.println("Could not save.");
		}
	}

	@Override
	public Event loadEvent(String filePath){
		XStream xstream = new XStream();
		Event event = null;
		String path = System.getProperty("user.dir"); //Grab the working directory

		try{
			File xmlFile = new File(path + "\\" + filePath);
			event = (Event)xstream.fromXML(xmlFile);       
		}catch(Exception e){
			System.err.println("Error in XML Read: " + e.getMessage());
		}

		return event;
	}


}
