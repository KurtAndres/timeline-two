package graphics;

import entities.Atomic;
import entities.Duration;
import entities.Event;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.TimelineMaker;

/**
 * An abstract class to create labels for Atomic and Duration events to render.
 * Currently the subclasses have a decent amount of repetition so some of that could be
 * moved up here.
 * 
 * @author KurtAndres
 * February 15, 2014
 */
public abstract class TLEventLabel extends Label {

	/**
	 * The event this label is associated with
	 */
	protected Event event;

	/**
	 * The x and y position of this event
	 */
	protected int xPos;
	protected int yPos;

	/**
	 * Whether this is the selected event or not
	 */
	private boolean selected;

	/**
	 * Whether this event is hovered over or not
	 */
	private boolean hovered;

	/**
	 * This object. Used for passing to anonymous inner classes.
	 */
	protected TLEventLabel label;

	/**
	 * The model of the program to update selected event
	 */
	protected TimelineMaker model;

	/**
	 * ArrayList of all other eventLabels, used for clearing previous selection
	 */
	protected ArrayList<TLEventLabel> eventLabels;

	/**
	 * The tooltip hoverover text for the label
	 */
	protected String tooltipText;

	/**
	 * The string color (i.e. #ff0000) of the label when selected
	 */
	protected String selectedColor;

	/**
	 * The string color of the label when not selected
	 */
	protected String deselectedColor;

	/**
	 * Set the text of the label to text
	 * 
	 * @param text the label text
	 */
	TLEventLabel(String text){
		super(text);
	}

	/**
	 * Initializes the various handlers of the label for mouse over and selection
	 */
	protected void initHandlers(){
		label.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				for(TLEventLabel label : eventLabels){
					label.setSelected(false);
				}
				setSelected(true);
				new Thread(new Runnable() {
					public void run() {
						model.selectEvent(event);
					}
				}).start();
			}
		});
		label.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				for(TLEventLabel label : eventLabels){
					label.setHovered(false);
				}
				setHovered(true);
				new Thread(new Runnable() {
					public void run() {
						model.selectEvent(event);
					}
				}).start();
			}
		});
		label.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				for(TLEventLabel label : eventLabels){
					label.setHovered(false);
				}
				setHovered(false);
				new Thread(new Runnable() {
					public void run() {
						model.selectEvent(event);
					}
				}).start();
			}
		});
	}

	/**
	 * Sets up the "design" of the label. Border, position, fill, etc.
	 */
	protected void initDesign(){
		label.setLayoutX(xPos);
		label.setLayoutY(yPos);
		label.setStyle("-fx-border-color: "+ deselectedColor);
		label.setStyle("-fx-background-color: " + deselectedColor );
		label.setTooltip(new Tooltip(tooltipText));
	}

	/**
	 * Takes an 8-digit hex color and transforms it to a JavaFx css sheet color
	 * @param Color 
	 * @return colorStringCSS
	 * this color returns a string in the form "#ffffff" 
	 * which can be used in java fx with css styling, normal 8-digit with 0x cannot be used in css styling
	 */
	protected String toStringColor(Color c){
		String colorString = ""+ c;
		String colorStringCSS = "#" + colorString.substring(2, 8);
		return colorStringCSS;
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
	 * merely use to check if a particular label is hovered over by the mouse
	 */
	public boolean isHovered(){
		return hovered;
	}

	/**
	 * Setter for hovered, that updates the label if hovered
	 * 
	 * @param selected
	 * updates a labels boolean to true if mouse is over it
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
		String detailString;
		if(E.getDetails()!=null && !E.getDetails().isEmpty()){
			detailString = "Details: "+E.getDetails();	
			return "Name: "+E.getName()+"\n"+"Category: "+E.getCategory()+"\n"+dateString+"\n"+detailString;
		}else{
			return "Name: "+E.getName()+"\n"+"Category: "+E.getCategory()+"\n"+dateString;
		}


	}

	/**
	 * Setter to set text for the hover over tooltip for Atomic labels
	 * 
	 * @return String
	 */
	public String setAtomicTooltip(Atomic E){
		String detailString;
		if(E.getDetails()!=null && !E.getDetails().isEmpty()){
			detailString = "Details: "+E.getDetails();	
			return "Name: "+E.getName()+"\n"+"Category: "+E.getCategory()+"\n"+E.getDate()+"\n"+detailString;
		}else{
			return "Name: "+E.getName()+"\n"+"Category: "+E.getCategory()+"\n"+E.getDate();
		}
	}

	/**
	 * Controls highlight, and selection fx of timeline events
	 */
	public void updateDesign(){
		if (isSelected()) {
			label.setStyle("-fx-background-color: #7cfc00");
		}else{	
			if (isHovered()){
				label.setStyle("-fx-border-color: " + selectedColor);
				label.setStyle("-fx-background-color: " + selectedColor);
			}else{
				label.setStyle("-fx-border-color: " + deselectedColor);
				label.setStyle("-fx-background-color: " + deselectedColor);
			}
		}
	}
}
