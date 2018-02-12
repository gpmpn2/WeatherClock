package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

public class Updater extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8809506503296735271L;
	public static boolean finishedUpdate = false;
	
	private JLabel updateText;
	
	private JLabel progressText;
	
	private JProgressBar progressBar;
	
	public Updater(int width, int height) {
		setTitle("Updater");
		setResizable(false);
		setSize(width,height);
		
		//Forces our frame and content size into correct dimensions
		getContentPane().setPreferredSize(new Dimension(width,height));
		pack();
		
		//Center frame
		setLocationRelativeTo(null);
		
		//Set operation on close
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Open up!
		setVisible(true);
		validate();
		
		Font customFont = null;
		
		try {
		    //create the font to use. Specify the size!
		    customFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("font/HelveticaNeue-ThinExt.otf")).deriveFont(16f);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    //register the font
		    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("font/HelveticaNeue-ThinExt.otf")));
		} catch (IOException e) {
		    e.printStackTrace();
		} catch(FontFormatException e) {
		    e.printStackTrace();
		}
		
		JPanel panel = (JPanel) getContentPane();
		
		updateText = new JLabel("", SwingConstants.CENTER);
		updateText.setText("Checking for updates...");
		updateText.setBounds(125, 25, 150, 25); 
		updateText.setFont(new Font(customFont.getName(), Font.TRUETYPE_FONT, 12));
		panel.add(updateText);
		
		progressBar = new JProgressBar();
		progressBar.setToolTipText("%");
		progressBar.setBounds(0, 0, 320, 15);
		progressBar.setPreferredSize(new Dimension(320,15));
		progressBar.setValue(90);
		
		JPanel barHolder = new JPanel();
		barHolder.setBounds(100,250,200,15);
		barHolder.add(progressBar);
		
		progressText = new JLabel("", SwingConstants.CENTER);
		progressText.setText("");
		progressText.setFont(new Font(customFont.getName(), Font.TRUETYPE_FONT, 12));
		progressText.setBounds(175, 0, 50, 25);
		progressText.setForeground(Color.DARK_GRAY);
		panel.add(progressText);
		
		panel.add(barHolder);
		
		panel.repaint();
	}
	
	public JLabel getUpdateText(){
		return updateText;
	}
	
	public JLabel getProgressText() {
		return progressText;
	}
	
	public JProgressBar getProgressBar() {
		return progressBar;
	}
}
