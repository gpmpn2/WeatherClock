package weather;

/**
 * 
 * @author Grant
 * Holds the current weather for a specific city
 */
public class Weather {

	private int currentTemperature;
	private int feelsLikeTemperature;
	private String condensation;
	private boolean farenheit;
	private int currentTemperatureConversion;
	private int feelsLikeTemperatureConversion;
	
	public Weather(int currentTemperature, int feelsLikeTemperature, String condensation, boolean farenheit) {
		this.setCurrentTemperature(currentTemperature);
		this.setFeelsLikeTemperature(feelsLikeTemperature);
		this.setCondensation(condensation);
		this.setFarenheit(farenheit);
		setCurrentTemperatureConversion(setConversion(currentTemperature));
		setFeelsLikeTemperatureConversion(setConversion(feelsLikeTemperature));
	}
	
	public int celciusToFarenheit(int temp) {
    	return (int)((double)(temp * (1.8)) + 32);
    }
	
	public int farenheitToCelcius(int temp) {
    	return (int)Math.round(((double)(temp - 32)) * (.5555));
    }
	
	public int setConversion(int temp) {
		if(farenheit)
			return farenheitToCelcius(temp);
		return celciusToFarenheit(temp);
	}

	public boolean isFarenheit() {
		return farenheit;
	}

	public void setFarenheit(boolean farenheit) {
		this.farenheit = farenheit;
	}

	public String getCondensation() {
		return condensation;
	}

	public void setCondensation(String condensation) {
		this.condensation = condensation;
	}

	public int getFeelsLikeTemperature() {
		return feelsLikeTemperature;
	}

	public void setFeelsLikeTemperature(int feelsLikeTemperature) {
		this.feelsLikeTemperature = feelsLikeTemperature;
	}

	public int getCurrentTemperature() {
		return currentTemperature;
	}

	public void setCurrentTemperature(int currentTemperature) {
		this.currentTemperature = currentTemperature;
	}

	public int getCurrentTemperatureConversion() {
		return currentTemperatureConversion;
	}

	public void setCurrentTemperatureConversion(int currentTemperatureConversion) {
		this.currentTemperatureConversion = currentTemperatureConversion;
	}

	public int getFeelsLikeTemperatureConversion() {
		return feelsLikeTemperatureConversion;
	}

	public void setFeelsLikeTemperatureConversion(int feelsLikeTemperatureConversion) {
		this.feelsLikeTemperatureConversion = feelsLikeTemperatureConversion;
	}
}
