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
}
