/**
 * 
 */
package graphics;

import java.util.ArrayList;

import model.TimelineMaker;
import entities.Duration;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * @author Kurt Andres
 *
 */
public class DurationLabel extends TLEventLabel {
	/**
	 * The event this label is associated with
	 */
	private Duration event;
	
	/**
	 * The x and y position of this event
	 */
	private int xPos;
	private int yPos;

	/**
	 * The model of the program to update selected event
	 */
	private TimelineMaker model;
	
	/**
	 * This object. Used for passing to anonymous inner classes.
	 */
	private DurationLabel label;
	
	/**
	 * ArrayList of all other eventLabels, used for clearing previous selection
	 */
	private ArrayList<TLEventLabel> eventLabels;
	
	
	/**
	 * The width in pixels of the label
	 */
	private int width;
	
	/**
	 * The tooltip hoverover text for the label
	 */
	private String tooltipText;
	
	/**
	 * The string color (i.e. #ff0000) of the label when selected
	 */
	private String selectedColor;

	/**
	 * The string color of the label when not selected
	 */
	private String deselectedColor;

	
	/**
	 * Constructor calls the super constructor with the event name, assigns instance variables,
	 * and then calls init
	 * 
	 * @param event the event this label is associated with
	 * @param xPos the xPosition on the screen
	 * @param yPos the yPosition on the screen
	 * @param width the width of the label
	 * @param model the program model
	 * @param eventLabels the list of TLEventLabels
	 */
	DurationLabel(Duration event, int xPos, int yPos, int width, TimelineMaker model, ArrayList<TLEventLabel> eventLabels){
		super(event.getName());
		this.event = event;
		this.xPos = xPos;
		this.yPos = yPos;
		this.eventLabels = eventLabels;
		this.label = this;
		this.width = width;
		this.model = model;
		this.tooltipText = setDurationTooltip(event);
		this.selectedColor = this.toStringColor((event.getCategory()).getSelectColor());
		this.deselectedColor = this.toStringColor((event.getCategory()).getDeselectColor());
		init();
	}
	
	
	
	/**
	 * Calls two other init helper methods for cleanliness
	 */
	private void init() {
		initDesign();
		initHandlers();
	}

	/**
	 * Sets up the "design" of the label. Border, position, etc.
	 */
	private void initDesign(){
		label.setPrefWidth(width);
		label.setAlignment(Pos.CENTER);
		label.setLayoutX(xPos);
		label.setLayoutY(yPos);
		label.setStyle("-fx-border-color: "+ selectedColor);
		label.setStyle("-fx-background-color: " + selectedColor );
		label.setTooltip(new Tooltip(tooltipText));
	}
	
	/**
	 * Initializes the various handlers of the label for mouse over and selection
	 */
	private void initHandlers(){
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
	 * Takes an 8-digit hex color and transforms it to a JavaFx css sheet color
	 */
	public String toStringColor(Color c){
	String colorString = ""+ c;
	String colorStringCSS = "#" + colorString.substring(2, 8);
	return colorStringCSS;
	}
	
	
	/**
	 * Controls highlight, and selection fx of timeline events
	 */
	@Override
	public void updateDesign() {
			
		if (isSelected()) {
			label.setStyle("-fx-background-color: firebrick");
		}else{	
			if (isHovered()){
				label.setStyle("-fx-border-color: #7cfc00");
				label.setStyle("-fx-background-color: #7cfc00");
			}else{
				label.setStyle("-fx-border-color: " + selectedColor);
				label.setStyle("-fx-background-color: " + selectedColor);
			}
		}
		
		
		
	}
}
