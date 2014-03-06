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
	 * The width in pixels of the label
	 */
	private int width;
	
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
	private void initDesignDuration(){
                initDesign();
		label.setPrefWidth(width);
		label.setAlignment(Pos.CENTER);
        }
}
