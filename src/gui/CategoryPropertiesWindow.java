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
	private JLabel titleLabel;
	private JTextField title;
	
	private JLabel colorLabel;
	private JLabel redLabel;
	private JSpinner redSpinner;
	private JLabel greenLabel;
	private JSpinner greenSpinner;
	private JLabel blueLabel;
	private JSpinner blueSpinner;
	
	private JLabel colorPreviewLabel;
	private JTextField colorPreview;
	
	private JButton okButton;
	private JButton cancelButton;

	/**
	 * Constructor.
	 * Constructor for adding a new category.
	 * @param model the TimelineMaker application model
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
						model.addCategory(new Category.Builder(title).selectColor(color).deselectColor(color).build());
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
				final int red = (int)(category.getSelectColor().getRed()*255);
				final int green = (int)(category.getSelectColor().getGreen()*255);
				final int blue = (int)(category.getSelectColor().getBlue()*255);
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
						model.editCategory(new Category.Builder(title).selectColor(color).deselectColor(color).events(events).build());
					}
				}).start();
				dispose();
			}
		});
		
		initLayout();
	}

	/**
	 * Initialize the window components.
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
		colorPreviewLabel = new JLabel();
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

		colorPreviewLabel.setText("Preview");
		colorPreview.setText("Preview");
		colorPreview.setEditable(false);
		
		ChangeListener spinnerListener = new ChangeListener() {
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
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addComponent(titleLabel)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(title, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
										.addGroup(layout.createSequentialGroup()
												.addComponent(colorLabel)
												.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
												.addComponent(redLabel)
												.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(redSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(greenLabel)
												.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(greenSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(blueLabel)
												.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(blueSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
														.addGap(10, 10, 10)
														.addComponent(colorPreviewLabel)
														.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(colorPreview, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
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
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(colorLabel)
										.addComponent(blueSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(blueLabel)
										.addComponent(greenLabel)
										.addComponent(redLabel)
										.addComponent(greenSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(redSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
												.addComponent(colorPreview, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(colorPreviewLabel))
												.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
														.addComponent(cancelButton)
														.addComponent(okButton))
														.addContainerGap())
				);

		pack();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
		/* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
		 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
		 */
		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(CategoryPropertiesWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(CategoryPropertiesWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(CategoryPropertiesWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(CategoryPropertiesWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CategoryPropertiesWindow(null).setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify                     

	// End of variables declaration                   
}
