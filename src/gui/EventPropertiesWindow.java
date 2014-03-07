package gui;

import model.*;
import entities.*;

import javax.swing.*;

import java.awt.Color;
import java.awt.event.*;
import java.sql.Date;

/**
 * EventPropertiesWindow.java
 * 
 * @author Andrew Thompson
 * Wheaton College, CS 335, Spring 2014
 * Project Phase 2
 * Mar 7, 2014
 */
public class EventPropertiesWindow extends JFrame {

	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	// Window components.
	/**
	 * The event title field label.
	 */
	private JLabel titleLabel;
	/**
	 * The event title field.
	 */
	private JTextField title;

	/**
	 * The event type dropdown label.
	 */
	private JLabel typeLabel;
	/**
	 * The event type dropdown.
	 */
	private JComboBox<String> type;

	/**
	 * The event date field label.
	 */
	private JLabel dateLabel;
	/**
	 * The event start date field.
	 */
	private JTextField startDate;
	/**
	 * The "to" label.
	 */
	private JLabel toLabel;
	/**
	 * The event end date field.
	 */
	private JTextField endDate;

	/**
	 * The category dropdown label.
	 */
	private JLabel categoryLabel;
	/**
	 * The category dropdown.
	 */
	private JComboBox<String> category;

	/**
	 * The details field label.
	 */
	private JLabel detailsLabel;
	/**
	 * The details scrollable pane.
	 */
	private JScrollPane detailsPane;
	/**
	 * The details text area.
	 */
	private JTextArea detailsArea;

	/**
	 * The ok button.
	 */
	private JButton okButton;
	/**
	 * The cancel button.
	 */
	private JButton cancelButton;

	/**
	 * Constructor.
	 * Constructor for adding a new event.
	 * @param model the TimelineMaker application model
	 */
	public EventPropertiesWindow(final TimelineMaker model) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Add Event");

		initComponents();

		new Thread(new Runnable() {
			/**
			 * Load information from the event to be edited into the window.
			 */
			public void run() {
				try{
					for (Category c : model.getSelectedTimeline().getCategories())
						category.addItem(c.getName());
				}catch(NullPointerException npe){
					System.out.println("No categories added, null pointer");
				}
			}
		}).start();

		// Define action for adding a new event.
		okButton.addActionListener(new ActionListener() {
			/**
			 * Get information from data fields and create new event. Add event to model.
			 */
			public void actionPerformed(ActionEvent e) {
				final String title = EventPropertiesWindow.this.title.getText();
				final String type = EventPropertiesWindow.this.type.getSelectedItem().toString();
				final String startDate = EventPropertiesWindow.this.startDate.getText();
				final String endDate = EventPropertiesWindow.this.endDate.getText();
				final String category = (String) EventPropertiesWindow.this.category.getSelectedItem();
				final String details = EventPropertiesWindow.this.detailsArea.getText();
				new Thread(new Runnable() {
					public void run() {
						if (type.equals("Atomic"))
							model.addEvent(new Atomic(title, model.getCategory(category), details, Date.valueOf(startDate), model.getSelectedTimeline()));
						else if (type.equals("Duration"))
							model.addEvent(new Duration(title, model.getCategory(category), details, Date.valueOf(startDate), Date.valueOf(endDate), model.getSelectedTimeline()));
					}
				}).start();
				dispose();
			}
		});

		initLayout();	
	}

	/**
	 * Constructor.
	 * Constructor for editing an existing event.
	 * @param model the TimelineMaker application model
	 * @param event the event to edit
	 */
	public EventPropertiesWindow(final TimelineMaker model, final Event event) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Edit Event Properties");

		initComponents();

		new Thread(new Runnable() {
			/**
			 * Load information from the event to be edited into the window.
			 */
			public void run() {
				for (Category c : model.getSelectedTimeline().getCategories())
					category.addItem(c.getName());
				final String categoryName = event.getCategory().getName();
				final String eventName = event.getName();
				final String details = event.getDetails();
				if (event instanceof Atomic) {
					final String date = ((Atomic)event).getDate().toString();
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							title.setText(eventName);
							type.setSelectedItem("Atomic");
							startDate.setText(date);
							category.setSelectedItem(categoryName);
							detailsArea.setText(details);
						}
					});
				} else if (event instanceof Duration) {
					final String startDateString = ((Duration)event).getStartDate().toString();
					final String endDateString = ((Duration)event).getEndDate().toString();
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							title.setText(eventName);
							type.setSelectedItem("Duration");
							startDate.setText(startDateString);
							endDate.setText(endDateString);
							category.setSelectedItem(categoryName);
							detailsArea.setText(details);
						}
					});
				}
			}
		}).start();

		// Define action for editing an existing event.
		okButton.addActionListener(new ActionListener() {
			/**
			 * Get information from data fields and create a new event. Replace the existing event
			 * with the new one.
			 */
			public void actionPerformed(ActionEvent e) {
				final String title = EventPropertiesWindow.this.title.getText();
				final String type = EventPropertiesWindow.this.type.getSelectedItem().toString();
				final String startDate = EventPropertiesWindow.this.startDate.getText();
				final String endDate = EventPropertiesWindow.this.endDate.getText();
				final String category = (String) EventPropertiesWindow.this.category.getSelectedItem();
				final String details = EventPropertiesWindow.this.detailsArea.getText();
				new Thread(new Runnable() {
					public void run() {
						if (type.equals("Atomic"))
							model.editEvent(new Atomic(title,  model.getCategory(category), details, Date.valueOf(startDate), model.getSelectedTimeline()));
						else if (type.equals("Duration"))
							model.editEvent(new Duration(title,  model.getCategory(category), details, Date.valueOf(startDate), Date.valueOf(endDate), model.getSelectedTimeline()));
					}
				}).start();
				dispose();
			}
		});

		initLayout();
	}

	/**
	 * Initialize window components.
	 */
	private void initComponents() {

		titleLabel = new JLabel();
		title = new JTextField();

		typeLabel = new JLabel();
		type = new JComboBox<String>();

		dateLabel = new JLabel();
		startDate = new JTextField(10);
		toLabel = new JLabel();
		endDate = new JTextField(10);

		categoryLabel = new JLabel();
		category = new JComboBox<String>();

		detailsLabel = new JLabel();
		detailsPane = new JScrollPane();
		detailsArea = new JTextArea();

		okButton = new JButton();
		cancelButton = new JButton();


		titleLabel.setText("Title");

		typeLabel.setText("Type");
		type.setModel(new DefaultComboBoxModel<String>(new String[] { "Duration", "Atomic" }));
		type.addActionListener(new ActionListener() {
			/**
			 * Toggle the event type.
			 */
			public void actionPerformed(ActionEvent e) {
				if (type.getSelectedItem().equals("Atomic")) {
					endDate.setVisible(false);
					toLabel.setVisible(false);
				} else {
					endDate.setVisible(true);
					toLabel.setVisible(true);
				}
			}
		});
		
		dateLabel.setText("Date");
		startDate.setText("yyyy-mm-dd");
		startDate.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				startDate.setForeground(Color.GRAY);
				startDate.setText("");
			}

			public void focusLost(FocusEvent e) {
				try {
					Date.valueOf(startDate.getText());
				} catch (Exception nfe) {
					startDate.setForeground(Color.RED);
					startDate.setText("yyyy-mm-dd");
				}
			}
		});
		toLabel.setText("to");
		endDate.setText("yyyy-mm-dd");
		endDate.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				endDate.setForeground(Color.GRAY);
				endDate.setText("");
			}

			public void focusLost(FocusEvent e) {
				try {
					Date.valueOf(endDate.getText());
				} catch (Exception nfe) {
					endDate.setForeground(Color.RED);
					endDate.setText("yyyy-mm-dd");
				}
			}
		});

		categoryLabel.setText("Category");

		detailsLabel.setText("Event Details:");
		detailsArea.setColumns(20);
		detailsArea.setRows(5);
		detailsPane.setViewportView(detailsArea);

		okButton.setText("Ok");

		cancelButton.setText("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			/**
			 * Dispose this window.
			 */
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

	/**
	 * Initialize the layout of the window.
	 * Note: Generated code.
	 */
	private void initLayout() {
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addComponent(dateLabel)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
										.addComponent(startDate, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(toLabel)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(endDate, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addComponent(detailsPane)
										.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
												.addGap(0, 0, Short.MAX_VALUE)
												.addComponent(okButton)
												.addGap(18, 18, 18)
												.addComponent(cancelButton))
												.addGroup(layout.createSequentialGroup()
														.addComponent(typeLabel)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(layout.createSequentialGroup()
																.addComponent(titleLabel)
																.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																.addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
																.addGroup(layout.createSequentialGroup()
																		.addComponent(detailsLabel)
																		.addGap(0, 0, Short.MAX_VALUE))
																		.addGroup(layout.createSequentialGroup()
																				.addComponent(categoryLabel)
																				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																				.addComponent(category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
																				.addContainerGap())
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(titleLabel)
								.addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(typeLabel))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(endDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(dateLabel)
												.addComponent(toLabel)
												.addComponent(startDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(categoryLabel)
														.addComponent(category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(detailsLabel)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(detailsPane, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
																.addComponent(cancelButton)
																.addComponent(okButton))
																.addContainerGap())
				);
		pack();
	}
}