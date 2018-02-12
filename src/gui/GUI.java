package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import clock.Time;
import io.Start;
import cities.City;

/**
 * 
 * @author Grant
 * 1/30/18
 *
 * Generates the GUI of the Application
 */
public class GUI extends JFrame{

	/**
	 * Generated Serialized ID
	 */
	private static final long serialVersionUID = 1L;
	
	public ArrayList<JLabel> locations = new ArrayList<>();
	
	private JLabel topImageLabel;
	private JLabel bottomImageLabel;
	private JLabel gif;
	private JLabel gifLower;
	private int gifCounter = -290;
	private int gifCounterLower = -290;
	
	/**
	 * Constructor for the GUI
	 * @param width
	 * @param height
	 */
	public GUI(int width, int height) {
		setTitle("Weather/Time Clock");
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
		
		try {
			Image image = ImageIO.read(getClass().getResourceAsStream("data/icon.png"));
			setIconImage(image);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		JPanel panel = (JPanel) getContentPane();
		
		for(int i = 0;i<Start.cities.size() * 5;i++) {
			JLabel current = new JLabel();
			current.setText("");
			current.setFont(new Font(customFont.getName(), Font.TRUETYPE_FONT, 15));
			
			if(i % 5 == 0) {
				current.setBounds(300, i < 5 ? 0 : 200, 200, 35);
				current.setHorizontalAlignment(SwingConstants.CENTER);
			} else if (i % 5 == 1) {
				current.setBounds(5, i < 5 ? 50 : 250, 500, 32);
			} else if (i % 5 == 2) {
				current.setFont(new Font(customFont.getName(), Font.TRUETYPE_FONT, 40));
				current.setBounds(5, i < 5 ? 5 : 205, 350, 35);
			} else if (i % 5 == 3) {
				current.setBounds(5, i < 5 ? 35 : 235, 500, 32);
			} else if (i % 5 == 4) {
				current.setBounds(5, i < 5 ? 5 + (i*15) : 205 + ((i%5)*15), 500, 32);
			}
			
			locations.add(current);
			panel.add(current);
		}
		
		URL url = getClass().getResource("data/dog.gif");
		gif = new JLabel(new ImageIcon(url));
		gif.setBounds(gifCounter, 160, 500, 50);
		panel.add(gif);
		
		gifLower = new JLabel(new ImageIcon(url));
		gifLower.setBounds(gifCounterLower, 360, 500, 50);
		panel.add(gifLower);

		try {
			BufferedImage myPicture = ImageIO.read(getClass().getResourceAsStream("data/1.png"));
			topImageLabel = new JLabel(new ImageIcon(myPicture));
			topImageLabel.setBounds(0, 0, 500, 200);
			panel.add(topImageLabel);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			BufferedImage secondImage = ImageIO.read(getClass().getResourceAsStream("data/1.png"));
			bottomImageLabel = new JLabel(new ImageIcon(secondImage));
			bottomImageLabel.setBounds(0, 200, 500, 200);
			panel.add(bottomImageLabel);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JLabel blockSet = new JLabel();
		panel.add(blockSet);

		
		refresh();
		
	}
	
	/**
	 * Refreshes the GUI's Label's and Images (Cycles on a by second basis)
	 */
	public void refresh() {
		int counter = 0;
		
		for(City city : Start.cities) {
			
			String time = city.getCityTime();
			
			int militaryTime = Time.getMilitaryTime(time);
			
			//If the background image is dark, lets make the text color light
			//Essentially from 7:00PM - 4AM we display the font in a white color
			if(militaryTime >= 19 || militaryTime <= 4) {
				setFontColor(counter,Color.WHITE);
			} else {
				setFontColor(counter,Color.BLACK);
			}
			
			try {
				setImage(counter, militaryTime);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			locations.get(counter*5).setText(city.getCityName());
			locations.get(counter*5 + 1).setText(time);
			locations.get(counter*5 + 2).setText(city.printCurrentTemperature());
			locations.get(counter*5 + 3).setText("Feels like " + city.printFeelsLikeTemperature());
			locations.get(counter*5 + 4).setText(city.printCondensation());
			
			counter++;
		}
	}

	/**
	 * Sets the background image based on the current military time
	 * @param counter
	 * @param militaryTime
	 * @throws IOException
	 */	
	private void setImage(int counter, int militaryTime) throws IOException {
		if(counter == 0) {
			BufferedImage topImage = ImageIO.read(getClass().getResourceAsStream("data/" + militaryTime + ".png"));
			topImageLabel.setIcon(new ImageIcon(topImage));
		} else {
			BufferedImage bottomImage = ImageIO.read(getClass().getResourceAsStream("data/" + militaryTime + ".png"));
			bottomImageLabel.setIcon(new ImageIcon(bottomImage));
		}
		
		if(militaryTime >= 11 && militaryTime <= 13 && counter == 0) {
			if(gifCounter >= 285) {
				gifCounter = -290;
			}
			gifCounter += 1;
			gif.setBounds(gifCounter, 160, 500, 50);
		} else if (militaryTime < 11 || militaryTime > 13 && counter == 0) {
			gifCounter = -290;
			gif.setBounds(-400, 160, 500, 50);
		}
		
		if(militaryTime >= 11 && militaryTime <= 13 && counter == 1) {
			if(gifCounterLower >= 285) {
				gifCounter = -290;
			}
			gifCounterLower += 1;
			gifLower.setBounds(gifCounterLower, 360, 500, 50);
		} else if (militaryTime < 11 || militaryTime > 13 && counter == 0) {
			gifCounterLower = -290;
			gifLower.setBounds(-400, 360, 500, 50);
		}
	}

	/**
	 * Sets the font color based on the current military time
	 * @param counter
	 * @param color
	 */
	private void setFontColor(int counter, Color color) {
		locations.get(counter*5).setForeground(color);
		locations.get(counter*5 + 1).setForeground(color);
		locations.get(counter*5 + 2).setForeground(color);
		locations.get(counter*5 + 3).setForeground(color);
		locations.get(counter*5 + 4).setForeground(color);
	}
	
	
	
}
