/**
 * 
 */
package storage;

import java.io.File;
import java.util.ArrayList;

import model.TimelineMaker;

import entities.Category;
import entities.Event;
import entities.Timeline;


/**
 * A class which can delete Timelines, Categories and Events.
 * Deleting a category will delete all events in that category as well.
 * 
 * @author leanne.miller
 *
 */
public class DeleteMe {

	/**
	 * Deletes the given timeline.
	 * 
	 * @param tl The timeline to delete.
	 * 
	 */
	public static void deleteTimeline(Timeline tl){
		String path = System.getProperty("user.dir") + "/Timelines/" + tl.getName() + "/";

		File dir = new File(path);
		deleteDir(dir);	

	}


	/**
	 * Deletes all files and all sub-directories in a given directory.
	 * 
	 * @param dir The directory to delete.
	 */
	private static void deleteDir(File dir){
		File[] files = dir.listFiles();
		if(files != null){
			for(File f : files){
				if(f.isDirectory()){
					deleteDir(f);
				}
				f.delete();
			}
		}
		dir.delete();
	}

	/**
	 * Deletes a category from within a given timeline. The category should have no associated events.
	 * 
	 * @param category The category to delete.
	 * @param timeline The timeline to which the category belongs.
	 */
	public static void deleteCategory(Category category, String timeline){
		String path = System.getProperty("user.dir"); //Grab the working dir
		path = path + "/Timelines/" + timeline + "/categories/" + category.getName() + ".xml";

		deleteFile(path);
	}

	/**
	 * Deletes an event from a given timeline.
	 * 
	 * @param event The event to delete.
	 * @param timeline The timeline which the event is a part of.
	 */
	public static void deleteEvent(Event event, String timeline){
		String path = System.getProperty("user.dir"); //Grab the working dir
		path = path + "/Timelines/" + timeline + "/events/" + event.getName() + ".xml";

		deleteFile(path);
	}

	/**
	 * Deletes a file found at the given path.
	 * 
	 * @param path The path to the file to delete.
	 */
	private static void deleteFile(String path){
		try{
			File toDelete = new File(path);
			toDelete.delete();

		}catch(Exception e){
			System.err.println("Could not delete.");
		}
	}

}
