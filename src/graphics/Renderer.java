package graphics;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.sun.javafx.geom.Rectangle;
import com.sun.prism.j2d.paint.MultipleGradientPaint.CycleMethod;
import com.sun.prism.paint.LinearGradient;

import model.TimelineMaker;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineBuilder;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import entities.Atomic;
import entities.Category;
import entities.Duration;
import entities.Event;
import entities.Timeline;
import entities.Timeline.AxisLabel;

/**
 * This is the class that renders the actual timeline onto a JFXPanel using javafx.
 * The JFXPanel is then put into the GUI (JFXPanel is the link between swing and 
 * javafx). Currently this class is a little confusing (I spent a large chunk of time
 * refactoring and simplifying it, but it is still large) so I'll give an overview of 
 * the process here.
 * 
 * Currently the only public method is run. This allows the class to be run on the 
 * javafx thread, since java will actually throw an exception if any javafx stuff 
 * happens off this thread. 
 * 
 * While rendering the class initializes some variables important to the rendering 
 * process, and then renders the atomic events, axis, and duration events in that
 * order. This is so the atomic events can push the axis and duration events down the
 * screen. Detailed descriptions of this process are located in the method documentations.
 * Note that if there are no events in the database a blank screen gets rendered until the
 * user adds some events. 
 * 
 * NOTE: rendering long timelines with small units (i.e. 1500-2014 with days) will cause
 * an exception. It will also save to the database which means that you will have to change
 * the axisLabel of the timeline before it renders properly, (EditTimeline -> AxisLabel). It won't
 * crash the program, it just won't render.
 * 
 * @author Kurt Andres & Josh Wright
 * Created: Feb 10, 2014
 * Package: graphics
 * 
 * Some online examples of the Calendar class as well as making javafx graphics were used
 * in the making this class.
 */

public class Renderer implements Runnable {

	/**
	 * Used as the connection between the Swing gui and the javafx graphics
	 * (embeds in swing)
	 */
	private JFXPanel fxPanel;

	/**
	 * The model of the entire program, this is so selected events can be set
	 */
	private TimelineMaker model;

	/**
	 * The item associated with this Renderer object
	 */
	private Renderable item;

	/**
	 * The group of javafx elements to display in the scene (similar to a canvas,
	 * this gets put on the JFXPanel)
	 */
	private Group group;

	/**
	 * ArrayLists of all the events in the timeline. 
	 * Separated into durations and atomics for rendering purposes
	 */
	private ArrayList<Duration> durations;
	private ArrayList<Atomic> atomics;

	/**
	 * An ArrayList of all the TLEventLabels, used for selecting events
	 */
	private ArrayList<TLEventLabel> eventLabels;

	/**
	 * The AxisLabel that this TimelineRenderer will use when rendering the timeline.
	 * Essentially the unit by which the axis will be rendered.
	 */
	private AxisLabel axisLabel;

	/**
	 * The Final pushdown location of the last durration event that this TimelineRenderer will use when rendering the timeline.
	 * Essentially the unit by which the axis will be rendered.
	 */
	private int finalPushdown;

	/**
	 * ArrayList of all the atomic event x positions
	 */
	private ArrayList<Integer> atomicXPositions = new ArrayList<Integer>();

	/**
	 * ArrayList of all the atomic event y positions
	 */
	private ArrayList<Integer> atomicYPositions = new ArrayList<Integer>();

	/**
	 * ArrayList of all the duration event x positions
	 */
	private ArrayList<Integer> durationXPositions = new ArrayList<Integer>();

	/**
	 * ArrayList of all the duration event y positions
	 */
	private ArrayList<Integer> durationYPositions = new ArrayList<Integer>();


	/**
	 * Int value of timeline loaction after drawing timeline before making atomic connections
	 */
	private int timelineYLocation = 0;
	
	/**
	 * Int value of  final timeline location after drawing events and connections
	 */
	private int finalTimelineYLocation = 0;


	/**
	 * Use in rendering with an AxisLabel of months
	 */
	private final String[] months = {"Jan", "Feb", "March", "April",
			"May","June","July","Aug",
			"Sept","Oct","Nov","Dec"};

	/**
	 * The number of pixels each unit (definied by axisLAbel) on the axis takes
	 * up. Currently this is constant, but could be easily changed to account for
	 * different timeline sizes.
	 */
	private int unitWidth;

	/**
	 * The y location of the next element to be rendered. Everything is rendered
	 * from the top (0) down (positive y) to avoid overlaps of events.
	 */
	private int pushDown;

	/**
	 * The min and max time on the timeline, initialized in initRange() and used
	 * for determining the range at which to render the axis
	 */
	private long minTime;
	private long maxTime;

	/**
	 * The constructor for TimelineRenderer. Takes an fxPanel for putting the 
	 * scene (graphics), a TimelineMake object for updating the program state, a Timeline
	 * object to render with, and a group for putting the elements to draw on before 
	 * drawing to the fxPanel
	 * 
	 * @param fxPanel
	 * @param model
	 * @param timeline
	 * @param group
	 */
	public Renderer(JFXPanel fxPanel, TimelineMaker model, Timeline item, Group group) {
		this.model = model;
		this.item = item;
		if (item.getAxisLabel() == AxisLabel.DAYS || item.getAxisLabel() == AxisLabel.MONTHS || item.getAxisLabel() == AxisLabel.YEARS)
			this.axisLabel = item.getAxisLabel();
		else
			this.axisLabel = AxisLabel.YEARS;
		this.group = group;
		this.fxPanel = fxPanel;
		atomics = new ArrayList<Atomic>();
		durations = new ArrayList<Duration>();

		eventLabels = new ArrayList<TLEventLabel>();
	}

	/**
	 * The constructor for CategoryRenderer. Takes an fxPanel for putting the 
	 * scene (graphics), a TimelineMake object for updating the program state, a Timeline
	 * object to render with, and a group for putting the elements to draw on before 
	 * drawing to the fxPanel
	 * 
	 * @param fxPanel
	 * @param model
	 * @param timeline
	 * @param category
	 * @param group
	 */
	public Renderer(JFXPanel fxPanel, TimelineMaker model, Timeline timeline, Renderable category, Group group) {
		this.model = model;
		this.item = category;
		if (timeline.getAxisLabel() == AxisLabel.DAYS || timeline.getAxisLabel() == AxisLabel.MONTHS || timeline.getAxisLabel() == AxisLabel.YEARS)
			this.axisLabel = timeline.getAxisLabel();
		else
			this.axisLabel = AxisLabel.YEARS;
		this.group = group;
		this.fxPanel = fxPanel;
		atomics = new ArrayList<Atomic>();
		durations = new ArrayList<Duration>();

		eventLabels = new ArrayList<TLEventLabel>();
	}

	/*
	 * Initializes the minTime maxTime values, if there are not events render a blank screen
	 *  otherwise then calls init and renders the timeline.
	 * 
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		if (!initRange()){
			Scene toShow = new Scene(new Group(), 0, 0, Color.WHITE);
			fxPanel.setScene(toShow);
			return;
		}
		init();
		render();
	}

	/**
	 * Sets the unitWidth to a constant (this can be changed to depend on how many units are in the
	 * timeline) then populates the Atomic and Duration ArrayLists. While populating the lists, it
	 * also updates the minTime and maxTime. Currently it uses milliseconds, but could be modified to use
	 * the Date class's compareTo method (which I didn't know existed until after writing this).
	 */
	private void init(){
		unitWidth = 150;
		pushDown = 60;
		for(Event event : item.getEvents()){
			if(event instanceof Duration){
				durations.add((Duration)event);
				long start = ((Duration) event).getStartDate().getTime();
				long end = ((Duration) event).getEndDate().getTime();
				if(start < minTime){ minTime = start; }
				if(end > maxTime){ maxTime = end; }
			}else if(event instanceof Atomic){
				atomics.add((Atomic)event);
				long date = ((Atomic) event).getDate().getTime();
				if(date < minTime){ minTime = date; }
				if(date > maxTime){ maxTime = date; }
			}
		}
	}

	/**
	 * Initializes the minTime and maxTime to the first event. This is kind of a hack but
	 * seems to be necessary. It would not be necesssary if we used the compareTo method 
	 * for Dates, however.
	 * 
	 * @return if there are any events
	 */

	private boolean initRange() {
		if(item.getEvents() == null || item.getEvents().isEmpty()) { // Initializes the variables, super kludgy but we can make it better later if there is time
			return false; 
		} else if (item.getEvents().get(0) instanceof Duration) {
			minTime = ((Duration)item.getEvents().get(0)).getStartDate().getTime();
			maxTime = ((Duration)item.getEvents().get(0)).getEndDate().getTime();
		} else {
			minTime = ((Atomic)item.getEvents().get(0)).getDate().getTime();
			maxTime = ((Atomic)item.getEvents().get(0)).getDate().getTime();
		}
		return true;
	}

	/**
	 * Renders the timeline in order of height on the screen; atomic events,
	 * axis, duration events.
	 * 
	 * Also clears the old rendering and resets the group to remove the previous render.
	 */

	private void render() {
		group.getChildren().clear();
		group = new Group();
		renderAtomics();
		renderTime();
		renderDurations();
		renderConnections();
		renderFinalTimeAxis();
	}


	/**
	 * Renders each 'Unit' on the axis as a label with width unitWidth (uses unitLabel method). 
	 * Adds the label to the group, and when finished puts the group in a scene and displays the 
	 * scene in the fxPanel.
	 * 
	 * currently uses a second label to make each hash mark on the timeline simply a |
	 * 
	 * then lineBuilder to draw a black constant line for the timeline depending on length
	 */

	private void renderTime() {
		int diffUnit = getUnitLength();
		int xPos2 = 0;

		for(int i = 0; i <= diffUnit ; i++){
			//System.out.println(xPos2);
			Label label = unitLabel(i,xPos2+90);
			label.setTextAlignment(TextAlignment.LEFT);
			label.setAlignment(Pos.BASELINE_LEFT);
			Label lineLabel;

			//adds the dashes (|) on the timeline
			lineLabel = new Label("    |");
			lineLabel.setLayoutX(xPos2+90);
			lineLabel.setLayoutY(pushDown+23);
			lineLabel.setPrefWidth(unitWidth);
			lineLabel.setTextAlignment(TextAlignment.LEFT);
			lineLabel.setAlignment(Pos.TOP_LEFT);

			group.getChildren().add(label);
			group.getChildren().add(lineLabel);
			xPos2+=unitWidth;
		}
		//adds the actual black timeline
		Line blackLine = LineBuilder.create()
				.startX(75)
				.startY(pushDown+10)
				.endX(xPos2-5)
				.endY(pushDown+10)
				.fill(Color.BLACK)
				.strokeWidth(3.5f)
				.translateY(20)
				.build();

		group.getChildren().add(blackLine);

		timelineYLocation = pushDown+10;

		//adding the title label
		Label titleLabel = new Label(item.getName());
		titleLabel.setRotate(270);
		titleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 28));

		if (item instanceof Timeline) {
			titleLabel.setLayoutY(timelineYLocation);
		} else if (item instanceof Category) {
			titleLabel.setLayoutY(timelineYLocation-30);
		}
		titleLabel.setLayoutX(0);
		titleLabel.setLayoutY(timelineYLocation);
		//titleLabel.setStyle("-fx-border-color: black");
		titleLabel.setTextAlignment(TextAlignment.CENTER);
		titleLabel.setAlignment(Pos.TOP_LEFT);
		group.getChildren().add(titleLabel);

		Scene toShow = new Scene(group, xPos2+5, pushDown, Color.GHOSTWHITE);
		fxPanel.setScene(toShow);
	}

	/**
	 * Renders each 'Unit' on the bottom axis as a label with width unitWidth (uses unitLabel method). 
	 * Adds the label to the group, and when finished puts the group in a scene.
	 * 
	 * currently uses a second label to make each hash mark on the timeline simply a |
	 * 
	 * then lineBuilder draws a black constant line for the timeline depending on length
	 */

	private void renderFinalTimeAxis() {
		int diffUnit = getUnitLength();
		int xPos3 = 0;
		finalTimelineYLocation =  finalPushdown +20;

		if(finalTimelineYLocation > timelineYLocation ){
			for(int i = 0; i <= diffUnit ; i++){
				//no need for year
				Label label = unitLabel(i,xPos3+90);
				label.setTextAlignment(TextAlignment.LEFT);
				label.setAlignment(Pos.BASELINE_LEFT);
				label.setLayoutY(finalPushdown+32);
				group.getChildren().add(label);

				//adds the dashes (|) on the timeline
				Label lineLabel;
				lineLabel = new Label("    |");
				lineLabel.setLayoutX(xPos3+90);
				lineLabel.setLayoutY(finalPushdown + 26);
				lineLabel.setPrefWidth(unitWidth);
				lineLabel.setTextAlignment(TextAlignment.LEFT);
				lineLabel.setAlignment(Pos.TOP_LEFT);

				group.getChildren().add(lineLabel);
				xPos3+=unitWidth;
			}
			//adds the actual black timeline
			Line blackLine = LineBuilder.create()
					.startX(75)
					.startY(finalTimelineYLocation)
					.endX(xPos3 - 5)
					.endY(finalTimelineYLocation)
					.fill(Color.BLACK)
					.strokeWidth(3.5f)
					.translateY(20)
					.build();

			group.getChildren().add(blackLine);
		}
	}

	/**
	 * Helper method for creating units on the axis. Uses a Calendar object to add i units to the object,
	 * and then create a label with the correct position and text based on the unit and the current
	 * AxisLabel. Some stylistic things at the end.
	 * 
	 * @param i the number of units after the first unit on the axis (the 'x' coordinate in AxisLabel units)
	 * @param xPos2 the actual pixel starting x coordinate of the label on the scene
	 * @return the created Label to add to the Group
	 */

	private Label unitLabel(int i, int xPos2) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(getFirstDate());
		cal.add(getUnit(), i); //adds i units to the date
		Label label;

		switch(axisLabel){
		case DAYS:
			label = new Label(new Date(cal.getTime().getTime()).toString());
			break;
		case MONTHS:
			label = new Label(months[cal.get(Calendar.MONTH)]+" "+ cal.get(Calendar.YEAR));
			break;
		case YEARS:
			label = new Label(cal.get(Calendar.YEAR)+"");
			break;
		default:
			label = new Label("");
			break;
		}
		label.setLayoutX(xPos2);
		label.setLayoutY(pushDown);
		label.setPrefWidth(unitWidth);
		label.setPrefHeight(40);
		label.setAlignment(Pos.CENTER);
		//label.setStyle("-fx-border-color: black;");

		return label;
	}

	/**
	 * Uses a calndar object to calculate how long the timeline will be (rounds down to the nearest unit
	 * and then finds the number of units from there to the last event, rounded up).
	 * 
	 * @return the total units long the axis will be.
	 */
	private int getUnitLength() {
		Calendar startCalendar = new GregorianCalendar();
		startCalendar.setTime(getFirstDate());
		Calendar endCalendar = new GregorianCalendar();
		endCalendar.setTime(new Date(maxTime));

		int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
		int diffDay = diffYear * 365 +endCalendar.get(Calendar.DAY_OF_YEAR) - startCalendar.get(Calendar.DAY_OF_YEAR);

		switch(axisLabel){ // +1 to round up
		case DAYS:
			return diffDay+1;
		case MONTHS:
			return diffMonth+1;
		case YEARS:
			return diffYear+1;
		default:
			return 0;
		}
	}

	/**
	 * @return the Date of the first date in the timeline rounded down to the nearest unit. Used
	 * for drawing the axis.
	 * 
	 * i.e. if the first date was November 3, 2012, this would return January 1, 2012.
	 */
	private Date getFirstDate() {
		Date date = new Date(minTime);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Date toReturn = null;

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_YEAR);

		switch(axisLabel){
		case DAYS:
			cal.set(year, month, day);
			toReturn = new Date(cal.getTime().getTime());
			break;
		case MONTHS:
			cal.set(year, month, 1);
			toReturn = new Date(cal.getTime().getTime());
			break;
		case YEARS:
			cal.set(year, 0, 1);
			toReturn = new Date(cal.getTime().getTime());
			break;
		default:
			break;
		}
		return toReturn;
	}

	/**
	 * Creates labels out of each Atomic event to add to the Group. Calculates x position based on the date
	 * and y position based on the pushDown global value. 
	 * 
	 * Uses custom Label class
	 */
	private void renderAtomics() {
		pushDown = 30; //where to put the event ( y - axis )
		for(Atomic e : atomics){
			int xPosition = getXPos(e.getDate())+109;
			AtomicLabel label = new AtomicLabel(e, xPosition, pushDown, model, eventLabels);
			eventLabels.add(label);
			group.getChildren().add(label);
			atomicXPositions.add(xPosition);
			atomicYPositions.add(pushDown);
			pushDown += 20;
		}
	}

	/**
	 * Creates labels out of each Duration event to add to the Group. Calculates x position based on start and 
	 * end date and y position based on the pushDown global value. 
	 * 
	 * Uses custom Label class
	 */
	private void renderDurations() {
		int counter = 0;
		for(Duration e : durations){

			int xStart = getXPos(e.getStartDate())+109;
			int xEnd = getXPos(e.getEndDate())+109;
			int labelWidth = xEnd - xStart;
			DurationLabel label = new DurationLabel(e, xStart, (pushDown + 45 + counter), labelWidth, model, eventLabels);
			eventLabels.add(label);
			group.getChildren().add(label);
			durationXPositions.add(xStart);
			durationYPositions.add(pushDown);
			finalPushdown = pushDown + 45 + counter;

			//add connecting lines for start duration events
			Line blackdashedConnector = LineBuilder.create()
					.startX(xStart)
					.startY(timelineYLocation+2)
					.endX(xStart)
					.endY(pushDown + 45 + counter-7)
					.fill(Color.BLACK)
					.strokeWidth(1.5f)
					.translateY(20)
					.build();

			group.getChildren().add(blackdashedConnector);

			//add connecting line for end duration events
			Line endDurrationConnector = LineBuilder.create()
					.startX(xEnd)
					.startY(timelineYLocation+2)
					.endX(xEnd)
					.endY(pushDown + 45 + counter-7)
					.fill(Color.BLACK)
					.strokeWidth(1.5f)
					.translateY(20)
					.build();

			group.getChildren().add(endDurrationConnector);
			counter += 20;
		}
	}
	//can't draw this with atomic events because they have to wait for the timeline axis to know what to connect to
	private void renderConnections() {
		for(int i =0; i<atomicXPositions.size(); i++){
			Line blackConnector = LineBuilder.create()
					.startX(atomicXPositions.get(i)+1)
					.startY(timelineYLocation)
					.endX(atomicXPositions.get(i)+1)
					.endY(atomicYPositions.get(i)-4)
					.fill(Color.color(1.0, 0, 1))
					.strokeWidth(1.5f)
					.translateY(20)
					.build();

			group.getChildren().add(blackConnector);
		}

	}

	/**
	 * Returns the pixel x position that a date should be, based on its value and the axis
	 * 
	 * @param date the date to get the x position for
	 * @return the pixel x value that the date should be
	 */
	private int getXPos(Date date) {
		double units = getUnitsSinceStart(date);
		int xPosition = (int)(units*unitWidth); 
		//System.out.println("Event " + date.toString() + " is " +units+ " units after the start. It has an x offset of " +(int)(units*unitWidth)+ " pixels.");
		return xPosition;
	}

	/**
	 * Returns the number of units (based on axisLabel) since the first date on the timeline axis (see
	 * getFirstDate) for a Date.
	 * 
	 * i.e. if first date was January 1, 2011, date was January 1, 2012 this and axisLabel was days, this would
	 * return 366.
	 * 
	 * @param date the date to get the units for
	 * @return the units since the start date, of the date
	 */

	private double getUnitsSinceStart(Date date){
		Calendar startCalendar = new GregorianCalendar();
		startCalendar.setTime(getFirstDate());
		Calendar endCalendar = new GregorianCalendar();
		endCalendar.setTime(date);

		double diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		double diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
		double diffDay = diffYear * 365 +endCalendar.get(Calendar.DAY_OF_YEAR) - startCalendar.get(Calendar.DAY_OF_YEAR);

		double years = diffYear + (endCalendar.get(Calendar.DAY_OF_YEAR) - startCalendar.get(Calendar.DAY_OF_YEAR))/365.0;
		double months = diffMonth + (endCalendar.get(Calendar.DAY_OF_MONTH) - startCalendar.get(Calendar.DAY_OF_MONTH))/30.5;
		double days = diffDay + 0.5; //so that dates lineup nicely

		switch(axisLabel){
		case DAYS:
			return days;
		case MONTHS:
			return months;
		case YEARS:
			return years;
		default:
			return 0;
		}
	}

	/**
	 * Returns the calendar unit based on axisLabel (used in rendering the different pieces based
	 * on length and date)
	 * 
	 * @return the Calendar unit
	 */

	private int getUnit(){
		switch(axisLabel){
		case DAYS:
			return Calendar.DATE;
		case MONTHS:
			return Calendar.MONTH;
		case YEARS:
			return Calendar.YEAR;
		default:
			return 0;
		}
	}

}
