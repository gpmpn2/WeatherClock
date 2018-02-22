package clock;

import io.Start;
import cities.City;

/**
 * 
 * @author Grant
 * Responsible for the main thread and updating of the cities information
 */
public class Clock implements Runnable{

	private static int tickCounter = 0;
	
	//Responsible for catching the thread before killing it, to properly close it
	private boolean running = true;
	
	public void close() {
		running = false;
	}
	
	@Override
	public void run() {
		//Sleep every 1 second
		while(running) {
			try {
				updateGUI();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				running = false;
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Calls for a GUI update every second
	 */
	public void updateGUI() {
		//Lets update the weather and time here so we don't have to do it in multiple places
		
		tickCounter++;
		
		for(City city : Start.cities) {
			city.updateTime();
		}
		
		
		//Only update our weather every 30 seconds, honestly could probably set it to like every 2-3 minutes, but this is fine for now
		if(tickCounter % 30 == 0) {
			
			if(tickCounter == 3600) {
				int count = 0;
				for(City city : Start.cities) {
					if(city.getPreviousTemperatures().size() == 7) {
						city.getPreviousTemperatures().remove(6);
					}
					city.getPreviousTemperatures().add(0, (city.getCityWeather().isFarenheit() ? city.getCityWeather().getCurrentTemperature() : city.getCityWeather().getCurrentTemperatureConversion()));
					Start.getGUI().updateGraph(city, count);
					count++;
				}
				tickCounter = 0;
			}
			
			//Asyncronous thread running to grab the weather as to not delay our thread that is updating our clock's
			new Thread(() -> {
				for(City city : Start.cities) {
					city.updateWeather();
				}
				return;
			}).start();
		}
		
		//Refresh our GUI to show our live updates
		Start.getGUI().refresh();
	}
	
}
