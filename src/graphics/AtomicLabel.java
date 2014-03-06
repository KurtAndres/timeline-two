package graphics;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.TimelineMaker;
import entities.Atomic;

/**
 * Atomic version of TLEventLabel
 * 
 * @author Kurt Andres & Josh Wright
 * February 15, 2014
 */
public class AtomicLabel extends TLEventLabel {

	/**
	 * The event this label is associated with
	 */
	private Atomic event;

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
	private AtomicLabel label;

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
	 * ArrayList of all other eventLabels, used for clearing previous selection
	 */
	private ArrayList<TLEventLabel> eventLabels;

	/**
	 * Constructor calls the super constructor with the event name, assigns instance variables,
	 * and then calls init
	 * 
	 * @param event the event this label is associated with
	 * @param xPos the xPosition on the screen
	 * @param yPos the yPosition on the screen
	 * @param model the program model
	 * @param eventLabels the list of TLEventLabels
	 */
	AtomicLabel(Atomic event, int xPos, int yPos, TimelineMaker model, ArrayList<TLEventLabel> eventLabels){
		super(event.getName());
		this.event = event;
		this.xPos = xPos;
		this.yPos = yPos;
		this.eventLabels = eventLabels;
		this.label = this;
		this.model = model;
		this.tooltipText = setAtomicTooltip(event);
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
			label.setStyle("-fx-border-color: black");
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
