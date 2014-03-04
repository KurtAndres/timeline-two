/**
 * 
 */
package entities;

/**
 * This abstract class is used to define some methods and variables needed by all events.
 * It is currently extended by the Duration and Atomic classes. Both use java.sql.Date for their dates
 * to make database writing easier. Events also have reference to a Category to which they belong.
 * 
 * @author Josh Wright
 * Created: Jan 29, 2014
 * Package: backend
 *
 */

public abstract class Event {
    
        /**
         * The comments on this event
         */
        private String comments;
	
	/**
	 * The name of the event
	 */
	private String name;
	
	/**
	 * The category of the event
	 */
	private Category category;
	
	/**
	 * A super constructor for all TLEvents, setting the name and category 
	 * 
	 * @param name the name of the event
	 * @param category the category of the event
	 */
	Event(String name, Category category) {
		this.name = name;
                try{
                    this.category = category;
                    category.addEvent(this);
                }catch(NullPointerException npe){
                    category = Category.defaultCategory;
                    System.out.println("no category added, null pointer");
                }
	}
        
	/**
	 * Get the name
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}
        
        /**
         * Sets the category of this event to the category provided. Also adds this event to the Category.
         * If this event was previously a member of a different category, this event is removed from that category.
         * 
         * @param category the category to be set
         */
        public void setCategory(Category category){
                if(this.category!=null)
                    this.category.removeEvent(this);
                category.addEvent(this);
                this.category = category;
        }
	
	/**
	 * Get the category.
         * WARNING: Main return null.
	 * 
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}
        
        /**
         * Set the comments of this event.
         * 
         * @param comments The string to which to set comments.
         */
        public void setComments(String comments){
            this.comments=comments;
        }
        
        /**
         * Retrieve the comments of this event.
         * 
         * @return The comments returned.
         */
        public String getComments(){
            return comments;
        }
	
	/**
	 * Set the name
	 * 
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Return the name of the Type (to be used when storing on the database)
	 * 
	 * @return the name of the Type
	 */
	public abstract String typeName();
}
