package Depth_FS;

/*
* File: GUIDisplay.java
* Author: Kolger Hajati
* Date: March 7, 2019
* Purpose: holds GUI details for user menu.
*/

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class GUIDisplay extends JFrame {

	// Variables
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel topCover;
	private JPanel botCover;
	private static JTextField inputText;
	private static JTextField classText;
	private static JTextPane orderText;
	private JButton buildDG;
	private JButton topOrder;
	private JLabel inputLabel;
	private JLabel classLabel;
    private String fileN;
    private String classN;
    private GraphMethod<String> graphData;
    
	// Main and runs GUI
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				GUIDisplay frame = new GUIDisplay();
				frame.setVisible(true);
			}
		});
	}

	private GUIDisplay() {

		//Content Bounds
		setTitle("Class Dependency Graph");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 605, 325);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Top panel cover
		topCover = new JPanel();
		contentPane.add(topCover);
		topCover.setBounds(3, 5, 582, 100);
		topCover.setBorder(BorderFactory.createTitledBorder(""));
		topCover.setLayout(null);
		
		//Bottom panel cover
		botCover = new JPanel();
		botCover.setBackground(Color.WHITE);
		contentPane.add(botCover);
		botCover.setBounds(1, 107, 586, 178);
		botCover.setBorder(BorderFactory.createTitledBorder("Recompilation Order"));
		botCover.setLayout(null);
		
		//Build directed graph button
		buildDG = new JButton("Build Directed Graph");
		buildDG.setFont(new Font("Lucida Sans Unicode", Font.BOLD, 12));
		buildDG.setBounds(395, 25, 175, 23);
		topCover.add(buildDG);

		//Topological order button
		topOrder = new JButton("Topological Order");
		topOrder.setFont(new Font("Lucida Sans Unicode", Font.BOLD, 12));
		topOrder.setBounds(403, 60, 155, 23);
		topCover.add(topOrder);

		//Input file list label
		inputLabel = new JLabel("Input file name:");
		inputLabel.setFont(new Font("Lucida Sans Unicode", Font.BOLD, 12));
		inputLabel.setBounds(35, 25, 150, 20);
		topCover.add(inputLabel);

		//Class recompile list label
		classLabel = new JLabel("Class to recompile:");
		classLabel.setFont(new Font("Lucida Sans Unicode", Font.BOLD, 12));
		classLabel.setBounds(25, 60, 150, 20);
		topCover.add(classLabel);

		//Original text field
		inputText = new JTextField();
		inputText.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 12));
		inputText.setBounds(170, 25, 210, 20);
		topCover.add(inputText);
		inputText.setColumns(10);

		//Sorted text field
		classText = new JTextField();
		classText.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 12));
		classText.setBounds(170, 60, 210, 20);
		topCover.add(classText);
		classText.setColumns(10);
		
		//orderText data field
		orderText = new JTextPane();		
		StyledDocument doc = orderText.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		orderText.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 12));
		orderText.setBounds(44, 20, 500, 100);
		orderText.setEditable(false);
		botCover.add(orderText);
		
		//Action listener sent to ActionInput
		inputText.addActionListener(new ActionInput());
		classText.addActionListener(new ActionInput());
		topOrder.addActionListener(new ActionInput());
		buildDG.addActionListener(new ActionInput());
		
	}

	/*Action listener that handles input and output of GUI values
	Also handles error check*/
	private class ActionInput implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//If statement for build directed graph
			if (e.getSource() == buildDG) {
				//Instance and File getter
				graphData = new GraphMethod<>();
				fileN = inputText.getText();
				//Try for building graph data
				try {
					graphData.tokenOrg(graphData.fileToken(fileN));

					//Dialog to show success
					JOptionPane.showMessageDialog(null, "Graph Built Successfully", "Message",
							JOptionPane.INFORMATION_MESSAGE);

				} 
				//Catch file not working error
				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "File Did Not Open", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			// If statement for topological order
			else if (e.getSource() == topOrder) {
				if (classText.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter A Class!", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					classN = classText.getText();
					// Try to set data output to text pane
					try {
						orderText.setText(graphData.topOrdGeneration(classN));
					}
					// Catch invalid class input
					catch (NameExp e1) {
						JOptionPane.showMessageDialog(null, "Invalid Class Name!", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
					// Catch for cycle
					catch (CycleExp e2) {
						JOptionPane.showMessageDialog(null, "Cycle Has Been Detected", "Message",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		}
	}
}
