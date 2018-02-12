package cities;

import clock.Time;
import weather.Weather;
import weather.WeatherData;

/**
 * 
 * @author Grant
 * City object, holds all information that a city needs
 */
public class City{
	
	
	/*Variables*/
	private String cityName;
	private String cityURL;
	private String cityTimezone;
	
	private Weather currentWeather;
	private String currentTime;
	
	public City(String cityName, String cityURL, String cityTimezone) {
		this.cityName = cityName;
		this.cityURL = cityURL;
		this.cityTimezone = cityTimezone;
	}
	
	public String getCityName() {
		return cityName;
	}
	
	public String getCityURL() {
		return cityURL;
	}
	
	public String getCityTimezone() {
		return cityTimezone;
	}

	public Weather getCityWeather() {
		return currentWeather;
	}

	public String getCityTime() {
		return currentTime;
	}

	/**
	 * Prints the current weather in a String
	 * @return
	 */
	public String printWeather() {
		return getCityName() + "'s current temperature is: " + (getCityWeather().isFarenheit() ? getCityWeather().getCurrentTemperature() : getCityWeather().getCurrentTemperatureConversion()) + "F" + "\nIt feels like: " +  
				(getCityWeather().isFarenheit() ? getCityWeather().getFeelsLikeTemperature() : getCityWeather().getFeelsLikeTemperatureConversion()) + "F" + "\nThe current weather is: " + getCityWeather().getCondensation() + ".";
	}
	
	/**
	 * Prints the current time in a String
	 * @return
	 */
	public String printTime() {
		return getCityName() + "'s current time is: " + getCityTime();
	}

	/**
	 * Returns the current temperature in a String
	 * @return
	 */
	public String printCurrentTemperature() {
		if(getCityWeather() == null) {
			return "--°F/--°C";
		}
		return (getCityWeather().isFarenheit() ? getCityWeather().getCurrentTemperature() : getCityWeather().getCurrentTemperatureConversion()) + "°F/" +
				(getCityWeather().isFarenheit() ? getCityWeather().getCurrentTemperatureConversion() : getCityWeather().getCurrentTemperature()) + "°C";
	}

	/**
	 * Returns the current feels like temperature in a String
	 * @return
	 */
	public String printFeelsLikeTemperature() {
		if(getCityWeather() == null) {
			return "--°F/--°C";
		}
		return (getCityWeather().isFarenheit() ? getCityWeather().getFeelsLikeTemperature() : getCityWeather().getFeelsLikeTemperatureConversion()) + "°F/" +
				(getCityWeather().isFarenheit() ? getCityWeather().getFeelsLikeTemperatureConversion() : getCityWeather().getFeelsLikeTemperature()) + "°C";
	}

	/**
	 * Returns the current condensation (weather)
	 * @return
	 */
	public String printCondensation() {
		if(getCityWeather() == null) {
			return "Please reconnect to wi-fi to begin weather updates.";
		}
		return "The current weather is " + getCityWeather().getCondensation().toLowerCase();
	}

	/**
	 * Updates the weather of the City
	 */
	public void updateWeather() {
		currentWeather = WeatherData.getWeather(getCityURL());
	}

	/**
	 * Updates the Time of the current city
	 */
	public void updateTime() {
		currentTime = Time.getTime(getCityTimezone());
	}

}
