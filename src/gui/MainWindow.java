package gui;

import model.*;
import entities.*;
import graphics.TimelineGraphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * MainWindow.java
 * 
 * The main GUI window for the TimelineMaker application - responsible for all user interaction.
 * 
 * @author Andrew Thompson
 * Wheaton College, CS 335, Spring 2014
 * Project Phase 2
 * Mar 7, 2014
 */
public class MainWindow extends JFrame {

	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * TimelineMaker model for this window.
	 */
	private TimelineMaker model;

	// Window components:
	/**
	 * The add event button.
	 */
	private JButton addEventButton;
	/**
	 * The add timeline button.
	 */
	private JButton addTimelineButton;
	/**
	 * The add event button.
	 */
	private JButton deleteEventButton;
	/**
	 * The delete button in the edit menu. Unused.
	 */
	private JMenuItem deleteMenuItem;
	/**
	 * The delete timeline button.
	 */
	private JButton deleteTimelineButton;
	/**
	 * The edit event button.
	 */
	private JButton editEventButton;
	/**
	 * The edit menu.
	 */
	private JMenu editMenu;
	/**
	 * A separator in the edit menu.
	 */
	private JPopupMenu.Separator editMenuSeparator1;
	/**
	 * The edit timelineButton.
	 */
	private JButton editTimelineButton;
	/**
	 * The edit-view button in the view menu. Unused.
	 */
	private JMenuItem editViewMenuItem;
	/**
	 * The event edit toolbar label.
	 */
	private JLabel eventsEditLabel;
	/**
	 * The exit button in the file menu.
	 */
	private JMenuItem exitMenuItem;
	/**
	 * The file menu.
	 */
	private JMenu fileMenu;
	/**
	 * A separator in the file menu.
	 */
	private JPopupMenu.Separator fileMenuSeparator1;
	/**
	 * A separator in the file menu.
	 */
	private JPopupMenu.Separator fileMenuSeparator2;
	/**
	 * The insert menu.
	 */
	private JMenu insertMenu;
	/**
	 * The display pane for graphics.
	 */
	private DisplayPane displayPane;
	/**
	 * The main split pane for the window.
	 */
	private JSplitPane mainSplitPane;
	/**
	 * The menu bar of the window.
	 */
	private JMenuBar menuBar;
	/**
	 * The multi-view button in the view menu.
	 */
	private JMenuItem multiViewMenuItem;
	/**
	 * The new-event button in the insert menu.
	 */
	private JMenuItem newEventMenuItem;
	/**
	 * The new-timeline button in the file menu.
	 */
	private JMenuItem newTimelineMenuItem;
	/**
	 * The redo button in the edit menu. Unused.
	 */
	private JMenuItem redoMenuItem;
	/**
	 * The save button in the file menu. Unused.
	 */
	private JMenuItem saveTimelineMenuItem;
	/**
	 * The dropdown of timelineSelector.
	 */
	private JComboBox<String> timelineSelector;
	/**
	 * The timelineSelector edit toolbar label.
	 */
	private JLabel timelinesEditLabel;
	/**
	 * The toolbar of the window.
	 */
	private JPanel toolbar;
	/**
	 * The toolbar label.
	 */
	private JLabel toolbarLabel;
	/**
	 * A separator in the toolbar.
	 */
	private JSeparator toolbarSeparator1;
	/**
	 * A separator in the toolbar.
	 */
	private JSeparator toolbarSeparator2;
	/**
	 * The undo button in the edit menu. Unused.
	 */
	private JMenuItem undoMenuItem;
	/**
	 * The view menu.
	 */
	private JMenu viewMenu;


	private JLabel categoriesEditLabel;
	private JComboBox<String> categorySelector;
	private JButton addCategoryButton;
	private JButton editCategoryButton;
	private JButton deleteCategoryButton;

	private JMenu helpMenu;
	private JMenuItem aboutMenuItem;
	private JSeparator toolbarSeparator3;

	/**
	 * Constructor.
	 * Creates a new main window for this application.
	 * @param model the application model
	 * @param graphics the graphics object for timeline display
	 */
	public MainWindow(TimelineMaker model, TimelineGraphics graphics) {
		this.model = model;
		initComponents(graphics);
		initActionListeners();
	}

	/**
	 * Initialize all window components.
	 * Most of this code was generated using NetBeans IDE.
	 */
	private void initComponents(TimelineGraphics graphics) {
		// Instantiate all components.
		mainSplitPane = new JSplitPane();

		toolbar = new JPanel();
		toolbarLabel = new JLabel();
		toolbarSeparator1 = new JSeparator();

		timelinesEditLabel = new JLabel();
		timelineSelector = new JComboBox<String>();
		addTimelineButton = new JButton();
		deleteTimelineButton = new JButton();
		editTimelineButton = new JButton();
		toolbarSeparator2 = new JSeparator();

		categoriesEditLabel = new JLabel();
		categorySelector = new JComboBox<String>();
		addCategoryButton = new JButton();
		editCategoryButton = new JButton();
		deleteCategoryButton = new JButton();
		toolbarSeparator3 = new JSeparator();

		eventsEditLabel = new JLabel();
		addEventButton = new JButton();
		deleteEventButton = new JButton();
		editEventButton = new JButton();

		displayPane = new DisplayPane(graphics);
		menuBar = new JMenuBar();
		fileMenu = new JMenu();
		newTimelineMenuItem = new JMenuItem();
		fileMenuSeparator1 = new JPopupMenu.Separator();
		saveTimelineMenuItem = new JMenuItem();
		fileMenuSeparator2 = new JPopupMenu.Separator();
		exitMenuItem = new JMenuItem();
		editMenu = new JMenu();
		undoMenuItem = new JMenuItem();
		redoMenuItem = new JMenuItem();
		editMenuSeparator1 = new JPopupMenu.Separator();
		deleteMenuItem = new JMenuItem();
		viewMenu = new JMenu();
		editViewMenuItem = new JMenuItem();
		multiViewMenuItem = new JMenuItem();
		insertMenu = new JMenu();
		newEventMenuItem = new JMenuItem();
		helpMenu = new JMenu();
		aboutMenuItem = new JMenuItem();

		// Set default close operation and title of this window.
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Timelord - Create, edit, and view timelineSelector!");

		// Set location of the main divider.
		mainSplitPane.setDividerLocation(140);


		// Set up the toolbar:
		toolbarLabel.setFont(new Font("Tahoma", 0, 12));
		toolbarLabel.setText("Toolbar");

		timelinesEditLabel.setText("Timelines");
		addTimelineButton.setText("Add Timeline");
		deleteTimelineButton.setText("Delete Timeline");
		editTimelineButton.setText("Edit Timeline");

		categoriesEditLabel.setText("Categories");
		addCategoryButton.setText("Add Category");
		editCategoryButton.setText("Edit Category");
		deleteCategoryButton.setText("Delete Category");

		eventsEditLabel.setText("Events");
		addEventButton.setText("Add Event");
		deleteEventButton.setText("Delete Event");
		editEventButton.setText("Edit Event");

		// Define the format for the toolbar. Generated code:
		GroupLayout toolbarLayout = new GroupLayout(toolbar);
		toolbar.setLayout(toolbarLayout);
		toolbarLayout.setHorizontalGroup(
				toolbarLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(toolbarSeparator1)
				.addComponent(toolbarSeparator2)
				.addComponent(toolbarSeparator3)
				.addGroup(toolbarLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(toolbarLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(categoriesEditLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(toolbarLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(editTimelineButton, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(deleteTimelineButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(addTimelineButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(timelineSelector, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(timelinesEditLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(categorySelector, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(addCategoryButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(editCategoryButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(deleteCategoryButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(addEventButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(editEventButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(toolbarLayout.createSequentialGroup()
										.addComponent(eventsEditLabel)
										.addGap(0, 0, Short.MAX_VALUE))
										.addComponent(deleteEventButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addContainerGap())
				);
		toolbarLayout.setVerticalGroup(
				toolbarLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(toolbarLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(toolbarLabel)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(toolbarSeparator1, GroupLayout.PREFERRED_SIZE, 5, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(timelinesEditLabel, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
						.addGap(4, 4, 4)
						.addComponent(timelineSelector, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(addTimelineButton)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(editTimelineButton)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(deleteTimelineButton)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(toolbarSeparator2, GroupLayout.PREFERRED_SIZE, 5, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(categoriesEditLabel, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(categorySelector, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(addCategoryButton)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(editCategoryButton)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(deleteCategoryButton)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(toolbarSeparator3, GroupLayout.PREFERRED_SIZE, 5, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(eventsEditLabel)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(addEventButton)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(editEventButton)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(deleteEventButton)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);

		mainSplitPane.setLeftComponent(toolbar);
		mainSplitPane.setRightComponent(displayPane);

		// Set up the menu bar:
		fileMenu.setText("File");
		newTimelineMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		newTimelineMenuItem.setText("New");
		fileMenu.add(newTimelineMenuItem);
		fileMenu.add(fileMenuSeparator1);
		saveTimelineMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		saveTimelineMenuItem.setText("Save");
		fileMenu.add(saveTimelineMenuItem);
		fileMenu.add(fileMenuSeparator2);
		exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
		exitMenuItem.setText("Exit");
		fileMenu.add(exitMenuItem);
		menuBar.add(fileMenu);

		editMenu.setText("Edit");
		undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
		undoMenuItem.setText("Undo");
		editMenu.add(undoMenuItem);
		redoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
		redoMenuItem.setText("Redo");
		editMenu.add(redoMenuItem);
		editMenu.add(editMenuSeparator1);
		deleteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		deleteMenuItem.setText("Delete");
		editMenu.add(deleteMenuItem);
		menuBar.add(editMenu);

		viewMenu.setText("View");
		editViewMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, InputEvent.CTRL_MASK));
		editViewMenuItem.setText("Edit View");
		viewMenu.add(editViewMenuItem);
		multiViewMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_MASK));
		multiViewMenuItem.setText("Multi View");
		viewMenu.add(multiViewMenuItem);
		menuBar.add(viewMenu);

		insertMenu.setText("Insert");
		newEventMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
		newEventMenuItem.setText("New Event");
		insertMenu.add(newEventMenuItem);
		menuBar.add(insertMenu);

		helpMenu.setText("Help");
		aboutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		aboutMenuItem.setText("About");
		helpMenu.add(aboutMenuItem);
		menuBar.add(helpMenu);

		setJMenuBar(menuBar);

		// Define the layout for the main pane:
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(mainSplitPane, GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(mainSplitPane)
				);

		// Pack the window.
		pack();
	}

	/**
	 * Initialize action listeners for all interactive buttons and shortcuts.
	 */
	private void initActionListeners() {
		//TODO Define action listeners for category tools.
		// Set up event toolbar listeners.
		addEventButton.addActionListener(new ActionListener() {
			/**
			 * Create a new EventPropertiesWindow for event addition.
			 */
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					public void run() {
						if (model.getSelectedTimeline() != null)
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									new EventPropertiesWindow(MainWindow.this.model).setVisible(true);
								}
							});
					}
				}).start();
			}
		});
		editEventButton.addActionListener(new ActionListener() {
			/**
			 * Create a new EventPropertiesWindow for event editing.
			 */
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					public void run() {
						final TLEvent selectedEvent = model.getSelectedEvent();
						if (selectedEvent != null && model.getSelectedTimeline() != null)
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									new EventPropertiesWindow(MainWindow.this.model, selectedEvent).setVisible(true);
								}
							});
					}
				}).start();
			}
		});
		deleteEventButton.addActionListener(new ActionListener() {
			/**
			 * Delete the selected event in the model.
			 */
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					public void run() {
						model.deleteEvent();
					}
				}).start();
			}
		});

		// Set up timeline toolbar listeners.
		addTimelineButton.addActionListener(new ActionListener() {
			/**
			 * Create a new TimelinePropertiesWindow for timeline addition.
			 */
			public void actionPerformed(ActionEvent e) {
				new TimelinePropertiesWindow(model).setVisible(true);
			}
		});
		editTimelineButton.addActionListener(new ActionListener() {
			/**
			 * Create a new TimelinePropertiesWindow for timeline editing.
			 */
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					public void run() {
						final Timeline selectedTimeline = model.getSelectedTimeline();
						if (selectedTimeline != null)
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									new TimelinePropertiesWindow(model, selectedTimeline).setVisible(true);
								}
							});
					}
				}).start();
			}
		});
		deleteTimelineButton.addActionListener(new ActionListener() {
			/**
			 * Delete the selected timeline in the model.
			 */
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					public void run() {
						model.deleteTimeline();
					}
				}).start();
			}
		});

		// Set up timeline-selection dropdown listener.
		timelineSelector.addActionListener(new ActionListener() {
			/**
			 * Update the selected event in the model.
			 */
			public void actionPerformed(ActionEvent e) {
				final String selectedTimeline = (String)timelineSelector.getSelectedItem();
				new Thread(new Runnable() {
					public void run(){
						model.selectTimeline(selectedTimeline);
					}
				}).start();
			}
		});

		// Set up menu item listeners.
		newEventMenuItem.addActionListener(new ActionListener() {
			/**
			 * Create a new EventPropertiesWindow for event addition.
			 */
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					public void run() {
						if (model.getSelectedTimeline() != null)
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									new EventPropertiesWindow(MainWindow.this.model).setVisible(true);
								}
							});
					}
				}).start();
			}
		});
		newTimelineMenuItem.addActionListener(new ActionListener() {
			/**
			 * Create a new TimelinePropertiesWindow for timeline addition.
			 */
			public void actionPerformed(ActionEvent e) {
				new TimelinePropertiesWindow(model).setVisible(true);
			}
		});
		saveTimelineMenuItem.addActionListener(new ActionListener() {
			/**
			 * Unimplemented.
			 */
			public void actionPerformed(ActionEvent e) {
				// TODO implement.
			}
		});
		exitMenuItem.addActionListener(new ActionListener() {
			/**
			 * Exit the application.
			 */
			public void actionPerformed(ActionEvent e) {
				System.exit(0); // TODO Save!
			}
		});
	}

	/**
	 * Update the timelineSelector from TimelineMaker model into GUI window.
	 * Get a list of timeline titles from model. Then populate a default list model for the JList of timelineSelector with those titles.
	 */
	public void updateTimelines(final ArrayList<String> timelineTitles, final String selectedTimelineName) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				timelineSelector.removeAllItems();
				for (String s : timelineTitles)
					timelineSelector.addItem(s);
				if (selectedTimelineName != null && !selectedTimelineName.isEmpty())
					timelineSelector.setSelectedItem(selectedTimelineName);
			}
		});

	}
}
