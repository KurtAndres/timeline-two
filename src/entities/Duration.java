/**
 * 
 */
package entities;
import java.sql.Date;
/**
 * Extension of class Event to represent duration (events that have a start and end date) events
 * 
 * @author Josh Wright
 * Created: Jan 29, 2014
 * Package: backend
 *
 */
public class Duration extends Event {
	
	/**
	 * the date the event starts
	 */
	private Date startDate;
	
	/**
	 * the date the event ends
	 */
	private Date endDate;
	
	/**
	 * Constructor for the name, category, startDate, and endDate
	 * 
	 * @param name the event name
	 * @param category the event category
	 * @param startDate the event startDate
	 * @param endDate the event endDate
	 */
	public Duration(String name, Category category, String details, Date startDate, Date endDate, Timeline timeline){
		super(name, category, details, timeline);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
	}
	
	/**
	 * Get the startDate
	 * 
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	
	/**
	 * Set the startDate
	 * 
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	/**
	 * Get the endDate
	 * 
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	
	/**
	 * Set the endDate
	 * 
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Override
	public String typeName() {
		return "duration";
	}
}
