package io;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import cities.City;
import clock.Clock;
import gui.GUI;

/**
 * 
 * @author Grant
 * Responsible for the main application method, GUI initialization and loading of cities
 */
public class Start {

	public static ArrayList<City> cities = new ArrayList<>();
	
	private static GUI gui;
	
	public static void main(String args[]) {
		//Load our city data before we show the GUI!
		loadCities();
		System.out.println("Cities loaded, initialization of start up information loaded...");
		
		//Load up the GUI
		gui = new GUI(500,400);
		System.out.println("GUI loaded...");
		
		//Current thread to keep track of weather and time updates!
		System.out.println("Clock started...");
		Clock clock = new Clock();
		Thread clockThread = new Thread(clock);
		clockThread.start();
		
		System.out.println("Adding window listener...");
		gui.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			    if(clock != null) {
		            try {
		            	clock.close();
		            	clockThread.join();
						System.exit(0);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
			    }
			}
		});
		
		System.out.println("Opening application...");
	}
	
	/**
	 * Load up our cities
	 */
	public static void loadCities() {
		cities.add(new City("Columbia, Missouri","https://www.accuweather.com/en/us/columbia-mo/65201/weather-forecast/329434","CST"));
		cities.add(new City("Cape Town, South Africa","https://www.accuweather.com/en/za/cape-town/306633/weather-forecast/306633","Africa/Cairo"));
		
		for(City city : cities) {
			//Lets get some info before our thread kicks in so our GUI has something to display
			city.updateWeather();
			city.updateTime();
		}
	}

	/**
	 * Getter for GUI
	 * @return
	 */
	public static GUI getGUI() {
		return gui;
	}
	
}
