package graph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Graph extends JPanel{

	/**
	 * @author Grant
	 * Represents the graph object on the JFrame Parent
	 */
	private static final long serialVersionUID = 1L;
	
	private int height;
	private ArrayList<Integer> points;
	
	public ArrayList<Double> percentages = new ArrayList<Double>();
	
	public static final int CIRCLE_SIZE = 6;
	public static final int SPACE_OFFSET = 23;
	
	public Graph(int height) {
		this.height = height;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		removeAll();
		revalidate();
		
		int average = calculateAverageTemperature();
		
		this.setToolTipText("Average of " + average + "°F/" + calculateAverageTemperatureConversion(average) + "°C");
		Graphics2D g2 = (Graphics2D) g;
		setPercentageHeight();
		setOpaque(false);
		
		int startX = 0;
		
		if(points == null) {
			return;
		}
		
		for(int i = 0;i<percentages.size();i++){
			int drawHeight = height - (int) (height * percentages.get(i));
			
			if(drawHeight < 0) {
				drawHeight = 0;
			}
			
			g2.fillOval(startX, drawHeight, CIRCLE_SIZE, CIRCLE_SIZE);
			
			if(i < percentages.size() - 1)
				g2.drawLine(startX + (CIRCLE_SIZE/2), drawHeight + (CIRCLE_SIZE/2), startX + SPACE_OFFSET + (CIRCLE_SIZE/2), ((height - (int) (height * percentages.get(i+1))) < 0 ? 0 : (height - (int) (height * percentages.get(i+1)))) + (CIRCLE_SIZE/2));
			startX += SPACE_OFFSET;
		}
	}
	
	/**
	 * Sets the percentages of each point
	 */
	public void setPercentageHeight() {
		if(points == null) {
			return;
		}
		
		percentages.clear();
		for(int i = 0;i<points.size();i++) {
			percentages.add((double)(points.get(i)) / 100);
		}
	}
	
	/**
	 * Sets the points
	 * @param points
	 */
	public void setPoints(ArrayList<Integer> points) {
		this.points = points;
	}
	
	/**
	 * Sets the color of of the graph
	 * @param color
	 */
	public void setColor(Color color) {
		setForeground(color);
	}
	
	/**
	 * Calculates the average temperature based on the points supplied
	 * @return
	 */
	public int calculateAverageTemperature() {
		if(points == null) {
			return 0;
		}
		
		int average = 0;
		for(int i = 0;i<points.size();i++) {
			average += points.get(i);
		}
		
		return average / points.size();
	}
	
	/**
	 * Converts the average temperature to celcius
	 * @param temp
	 * @return
	 */
	public int calculateAverageTemperatureConversion(int temp) {
    	return (int)Math.round(((double)(temp - 32)) * (.5555));
    }
}
