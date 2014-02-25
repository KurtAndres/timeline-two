package gui;

import model.*;
import entities.*;

import javax.swing.*;
import java.awt.event.*;

/**
 * TimelinePropertiesWindow.java
 * 
 * @author Andrew Thompson
 * Wheaton College, CS 335, Spring 2014
 * Project Phase 2
 * Mar 7, 2014
 */
public class TimelinePropertiesWindow extends JFrame {

	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	// Window components.
	/**
	 * The timeline title field label.
	 */
	private JLabel titleLabel;
	/**
	 * The timeline title field.
	 */
	private JTextField title;

	/**
	 * The timeline axis label dropdown label.
	 */
	private JLabel axisLabelLabel;
	/**
	 * The timeline axis label dropdown.
	 */
	private JComboBox<String> axisLabel;

	/**
	 * The timeline axis label position dropdown label.
	 */
	private JLabel axisPositionLabel;
	/**
	 * The timeline axis label position dropdown.
	 */
	private JComboBox<String> axisPosition;

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
	 * Constructor for adding a new timeline.
	 * @param model the application model
	 */
	public TimelinePropertiesWindow(final TimelineMaker model) {
		initComponents();

		// Define action for adding a timeline.
		okButton.addActionListener(new ActionListener() {
			/**
			 * Get information from the window and create a new timeline. Add it to the model.
			 */
			public void actionPerformed(ActionEvent e) {
				final String titleString = title.getText();
				final int axisLabelIndex = axisLabel.getSelectedIndex();
				new Thread(new Runnable() {
					public void run() {
						model.addTimeline(new Timeline(titleString, axisLabelIndex));
					}
				}).start();
				dispose();
			}
		});

		initLayout();
	}


	/**
	 * Constructor.
	 * Constructor for editing an existing timeline.
	 * @param model the application model
	 * @param timeline the timeline to be edited
	 */
	public TimelinePropertiesWindow(final TimelineMaker model, final Timeline timeline) {
		initComponents();

		new Thread(new Runnable() {
			/**
			 * Load information from the timeline to be edited into the window.
			 */
			public void run() {
				final String timelineTitle = timeline.getName();
				final int timelineAxisLabelIndex = timeline.getAxisLabelIndex();
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						title.setText(timelineTitle);
						axisLabel.setSelectedItem(axisLabel.getItemAt(timelineAxisLabelIndex));
					}
				});
			}
		}).start();

		// Define action for editing a timeline.
		okButton.addActionListener(new ActionListener() {
			/**
			 * Get information from the window and create a new timeline. 
			 * Replace thes existing timeline with the new one.
			 */
			public void actionPerformed(ActionEvent e) {
				final String titleString = title.getText();
				final int axisLabelIndex = axisLabel.getSelectedIndex();
				new Thread(new Runnable() {
					public void run() {
						TLEvent[] events = timeline.getEvents();
						model.editTimeline(new Timeline(titleString, events, axisLabelIndex));
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

		axisLabelLabel = new JLabel();
		axisLabel = new JComboBox<String>();

		axisPositionLabel = new JLabel();
		axisPosition = new JComboBox<String>();

		okButton = new JButton();
		cancelButton = new JButton();


		setTitle("Timeline Properties");

		titleLabel.setText("Title");

		axisLabelLabel.setText("Axis Label");
		axisLabel.setModel(new DefaultComboBoxModel<String>(new String[] { "Days", "Weeks", "Months", "Years", "Decades", "Centuries", "Millennia" }));
		axisLabel.setSelectedItem("Years");

		axisPositionLabel.setText("Axis Position");
		axisPosition.setModel(new DefaultComboBoxModel<String>(new String[] { "Top", "Center", "Bottom" }));

		okButton.setText("Ok");

		cancelButton.setText("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

	/**
	 * Initialize the layout of this window.
	 * Note: Generated code.
	 */
	private void initLayout() {
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addComponent(axisLabelLabel)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(axisLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(layout.createSequentialGroup()
												.addComponent(titleLabel)
												.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
												.addComponent(title, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
												.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
														.addComponent(axisPositionLabel)
														.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(axisPosition, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
														.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
																.addGap(0, 0, Short.MAX_VALUE)
																.addComponent(okButton)
																.addGap(18, 18, 18)
																.addComponent(cancelButton)))
																.addContainerGap())
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(titleLabel)
								.addComponent(title, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(axisLabelLabel)
										.addComponent(axisLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
												.addComponent(axisPosition, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(axisPositionLabel))
												.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
														.addComponent(cancelButton)
														.addComponent(okButton))
														.addContainerGap())
				);

		pack();
	}
}
