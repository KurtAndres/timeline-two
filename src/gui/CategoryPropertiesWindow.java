package gui;

import java.awt.event.*;
import java.util.ArrayList;

import model.TimelineMaker;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javafx.scene.paint.Color;
import entities.Category;
import entities.Event;

/**
 * CategoryPropertiesWindow.java
 *
 * @author Andrew Thompson
 * Wheaton College, CS 335, Spring 2014
 * Project Phase 2
 * Mar 7, 2014
 */
public class CategoryPropertiesWindow extends JFrame {

	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	// Variable declaration.
	/**
	 * The title text-field label.
	 */
	private JLabel titleLabel;
	/**
	 * The title text-field.
	 */
	private JTextField title;
	/**
	 * The color modifier label.
	 */
	private JLabel colorLabel;
	/**
	 * The red spinner label.
	 */
	private JLabel redLabel;
	/**
	 * The spinner for entering red value.
	 */
	private JSpinner redSpinner;
	/**
	 * The green spinner label.
	 */
	private JLabel greenLabel;
	/**
	 * The spinner for entering green value.
	 */
	private JSpinner greenSpinner;
	/**
	 * The blue spinner label.
	 */
	private JLabel blueLabel;
	/**
	 * The spinner for entering blue value.
	 */
	private JSpinner blueSpinner;
	/**
	 * A textfield for previewing the specified color.
	 */
	private JTextField colorPreview;
	/**
	 * The ok button.
	 */
	private JButton okButton;
	/**
	 * The canel button.
	 */
	private JButton cancelButton;

	/**
	 * Constructor.
	 * Constructor for adding a new category.
	 * @param model The TimelineMaker application model
	 */
	public CategoryPropertiesWindow(final TimelineMaker model) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Add Category");
		
		initComponents();
		
		okButton.addActionListener(new ActionListener() {
			/**
			 * Get information from data fields and create new category. Add category to model.
			 */
			public void actionPerformed(ActionEvent e) {
				final String title = CategoryPropertiesWindow.this.title.getText();
				final Color color = new Color(Double.parseDouble(redSpinner.getValue().toString())/255, 
						Double.parseDouble(greenSpinner.getValue().toString())/255, Double.parseDouble(blueSpinner.getValue().toString())/255,1);
				new Thread(new Runnable() {
					public void run() {
						model.addCategory(new Category.Builder(title).deselectColor(color).build());
					}
				}).start();
				dispose();
			}
		});
		
		initLayout();
	}
	
	/**
	 * Constructor.
	 * Constructor for editing an existing category.
	 * @param model the TimelineMaker application model
	 */
	public CategoryPropertiesWindow(final TimelineMaker model, final Category category) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Edit Category");
		
		initComponents();
		
		new Thread(new Runnable() {
			/**
			 * Load information from the timeline to be edited into the window.
			 */
			public void run() {
				final String categoryTitle = category.getName();
				final int red = (int)(category.getDeselectColor().getRed()*255);
				final int green = (int)(category.getDeselectColor().getGreen()*255);
				final int blue = (int)(category.getDeselectColor().getBlue()*255);
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						title.setText(categoryTitle);
						redSpinner.setValue(red);
						greenSpinner.setValue(green);
						blueSpinner.setValue(blue);
					}
				});
			}
		}).start();
		
		okButton.addActionListener(new ActionListener() {
			/**
			 * Get information from data fields and create new category. Add category to model.
			 */
			public void actionPerformed(ActionEvent e) {
				final String title = CategoryPropertiesWindow.this.title.getText();
				final Color color = new Color(Double.parseDouble(redSpinner.getValue().toString())/255, 
						Double.parseDouble(greenSpinner.getValue().toString())/255, Double.parseDouble(blueSpinner.getValue().toString())/255,1);
				new Thread(new Runnable() {
					public void run() {
						ArrayList<Event> events = category.getEvents();
						model.editCategory(new Category.Builder(title).deselectColor(color).events(events).build());
					}
				}).start();
				dispose();
			}
		});
		
		initLayout();
	}

	/**
	 * Initialize the window components.
	 * Add action listeners and format the window..
	 */
	private void initComponents() {

		titleLabel = new JLabel();
		title = new JTextField();
		colorLabel = new JLabel();
		blueSpinner = new JSpinner();
		blueLabel = new JLabel();
		greenLabel = new JLabel();
		redLabel = new JLabel();
		colorPreview = new JTextField();
		cancelButton = new JButton();
		okButton = new JButton();
		greenSpinner = new JSpinner();
		redSpinner = new JSpinner();

		titleLabel.setText("Title");

		colorLabel.setText("Display Color");
		redLabel.setText("R");
		redSpinner.setModel(new SpinnerNumberModel(255, 0, 255, 1));
		greenLabel.setText("G");
		greenSpinner.setModel(new SpinnerNumberModel(255, 0, 255, 1));
		blueLabel.setText("B");
		blueSpinner.setModel(new SpinnerNumberModel(255, 0, 255, 1));

		colorPreview.setText("Preview");
		colorPreview.setEditable(false);
		
		ChangeListener spinnerListener = new ChangeListener() {
			/**
			 * Update the color preview field whenever the user modifies the color spinners.
			 */
			public void stateChanged(ChangeEvent e) {
				colorPreview.setBackground(new java.awt.Color(Integer.valueOf(redSpinner.getValue().toString()), 
						Integer.valueOf(greenSpinner.getValue().toString()), Integer.valueOf(blueSpinner.getValue().toString())));
			}
		};
		
		redSpinner.addChangeListener(spinnerListener);
		greenSpinner.addChangeListener(spinnerListener);
		blueSpinner.addChangeListener(spinnerListener);

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
	                        .addComponent(titleLabel)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                        .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
	                    .addGroup(layout.createSequentialGroup()
	                        .addComponent(colorLabel)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
	                        .addComponent(redLabel)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(redSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(greenLabel)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(greenSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(blueLabel)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(blueSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
	                        .addGap(10, 135, Short.MAX_VALUE)
	                        .addComponent(colorPreview, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
	                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
	                        .addGap(0, 0, Short.MAX_VALUE)
	                        .addComponent(okButton)
	                        .addGap(18, 18, 18)
	                        .addComponent(cancelButton)))
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
	                    .addComponent(colorLabel)
	                    .addComponent(blueSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addComponent(blueLabel)
	                    .addComponent(greenLabel)
	                    .addComponent(redLabel)
	                    .addComponent(greenSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addComponent(redSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(colorPreview, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(cancelButton)
	                    .addComponent(okButton))
	                .addContainerGap())
	        );
		pack();
	}
}
