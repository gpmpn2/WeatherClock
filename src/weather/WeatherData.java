package weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

/**
 * 
 * @author Grant
 * Pulls, and parses all weather data.
 */
public class WeatherData {
	
	/**
	 * Given the URL of a city on accuweather.com, getWeather() will parse its data for use by us
	 * @param cityURL
	 * @return
	 */
	public static Weather getWeather(String cityURL) {
		String data = getURLSource(cityURL);
			
		if(data == null) {
			return null;
		}
			
		return parseData(data);
	}
	
	/**
	 * Parses all our data from the urlSource supplied, using accuweather.com
	 * @param urlSource
	 * @return
	 */
	private static Weather parseData(String urlSource) {
		String data = urlSource.substring(urlSource.indexOf("wrap-forecast-feed"),urlSource.indexOf("See Hourly"));
		data = data.substring(data.indexOf("large-temp"),data.indexOf("<!-- /.info --> "));
		data = data.replace("                                            ", System.getProperty("line.separator"));
		data = data.replace("                                        ", System.getProperty("line.separator"));
		data = data.substring(12,data.indexOf("</span>                                    </div>                                    "));
		data = data.replace("</span>", "");
		data = data.replace("</div>", "");
		data = data.replace("RealFeel&#174; ", "");
		data = data.replace("#176;", "");
		
		String manipulatedData[] = data.split("<span class=");
		
		int currentTemperature = Integer.parseInt(manipulatedData[0].replace("&deg;", "").replace(System.getProperty("line.separator"), ""));
		int feelsLikeTemperature = Integer.parseInt(manipulatedData[2].replace("\"realfeel\">","").replace("&", "").replace(System.getProperty("line.separator"), ""));
		boolean farenheit = manipulatedData[1].replace("\"temp-label\">", "").replace(System.getProperty("line.separator"), "").equals("F") ? true : false;
		String condensation = manipulatedData[3].replace("\"cond\">","");
		
		return new Weather(currentTemperature, feelsLikeTemperature, condensation, farenheit);
	}

	/**
	 * Gets the source code from a specified website URL
	 * 
	 * Added support for wi-fi or connection drops
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static String getURLSource(String url)
    {
        URLConnection urlConnection = null;
        
        try {
        	URL urlObject = new URL(url);
        	urlConnection = urlObject.openConnection();
        	urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        	return toString(urlConnection.getInputStream());
        } catch (UnknownHostException e) {
        	return null;
        } catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return null;
    }

	/**
	 * Parses the inputStream of data into a large string to then be parsed into the data we want to collect
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
    private static String toString(InputStream inputStream) throws IOException
    {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8")))
        {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(inputLine);
            }

            bufferedReader.close();
            
            return stringBuilder.toString();
        }
    }
}
