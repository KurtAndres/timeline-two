package graphics;

import entities.Atomic;
import entities.Duration;
import javafx.scene.control.Label;

/**
 * An abstract class to create labels for Atomic and Duration events to render.
 * Currently the subclasses have a decent amount of repetition so some of that could be
 * moved up here.
 * 
 * @author KurtAndres & Josh Wright
 * February 15, 2014
 */
public abstract class TLEventLabel extends Label {
	
	/**
	 * Whether this is the selected event or not
	 */
	private boolean selected;
	
	/**
	 * Whether this event is hovered over or not
	 */
	private boolean hovered;
	
	/**
	 * Set the text of the label to text
	 * 
	 * @param text the label text
	 */
	TLEventLabel(String text){
		super(text);
	}

	/**
	 * Getter for selected
	 * 
	 * @return selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * Setter for selected, that updates the label in accordance with the selection value
	 * 
	 * @param selected
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
		updateDesign();
	}
	/**
	 * Getter for hovered
	 * 
	 * @return hovered
	 */
	public boolean isHovered(){
		return hovered;
	}
	
	/**
	 * Setter for hovered, that updates the label if hovered
	 * 
	 * @param selected
	 */
	public void setHovered(boolean hovered) {
		this.hovered = hovered;
		updateDesign();
	}
	
	/**
	 * Setter to set text for the hover over tooltip for Duration labels
	 * 
	 * @return String
	 */
	public String setDurationTooltip(Duration E){
		String dateString;
		dateString = E.getStartDate()+" --> "+E.getEndDate();
		return "Name: "+E.getName()+"\n"+"Category: "+E.getCategory()+"\n"+dateString;
		
	}
	
	/**
	 * Setter to set text for the hover over tooltip for Atomic labels
	 * 
	 * @return String
	 */
	public String setAtomicTooltip(Atomic E){
		
		return "Name: "+E.getName()+"\n"+"Category: "+E.getCategory()+"\n"+E.getDate();
		
	}

	/**
	 * How the label will update itself
	 */
	public abstract void updateDesign();
}
