package io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 
 * @author Grant
 * 2/12/18
 * Responsible for version control (updates)
 */
public class Version {

	//URL Path to webhost
	public static final String URL_PATH = "http://ec2-18-221-227-162.us-east-2.compute.amazonaws.com/WeatherApp/";
	
	/**
	 * Checks the webhost's version against you're personal copies
	 */
	public static void checkVersion(){
		URL oracle;
		try {
			oracle = new URL(URL_PATH + "version.txt");
			BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

			String versionFound = in.readLine();
			in.close();
			
			in = new BufferedReader(new InputStreamReader(Version.class.getResourceAsStream("version.txt")));
			String versionOnBoard = in.readLine();
			in.close();
			
			if(!versionFound.equals(versionOnBoard)) {
				updateWeatherApp();
			} else {
				Start.getUpdater().getProgressBar().setValue(100);
				Start.getUpdater().getProgressText().setText("100%");
				Start.getUpdater().getUpdateText().setText("Launching...");
				Start.getUpdater().dispose();
				Start.version = versionOnBoard;
				Start.startClock();
			}
			
		} catch (MalformedURLException e) {
			System.out.println("Failed to update, could not locate URL!");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Updates our current jar to the newest version on the webhost
	 */
	private static void updateWeatherApp() {
		try {
			File jar = new File(Version.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());

			URL url = new URL(URL_PATH + "WeatherClock.jar");
			
			URLConnection conn = url.openConnection();
			
			BufferedInputStream bufferIn = new BufferedInputStream(url.openStream());
			
			File writeJar = new File(jar.getPath());
			OutputStream out = new FileOutputStream(writeJar);
			BufferedOutputStream bufferOut = new BufferedOutputStream(out);
			byte buffer[] = new byte[1024];//1024 is a slower download speed (research into maybe 8192 or even 13-14k?)
			
			int size = conn.getContentLength();
			
			int count;
			
			double sumCount = 0.0;
			
			Start.getUpdater().getUpdateText().setText("Downloading update...");
			while((count = bufferIn.read(buffer, 0, buffer.length)) != 1) {
				if(count == -1){
					break;
				}
				bufferOut.write(buffer, 0, count);
				sumCount += count;
				
				double percentage = sumCount/(size);
				if(size > 0) {
					Start.getUpdater().getProgressBar().setToolTipText((int)(percentage*100) + "%");
					Start.getUpdater().getProgressBar().setValue((int)(percentage * 100));
					Start.getUpdater().getProgressText().setText((int)(percentage*100) + "%");
				}
			}
			
			Start.getUpdater().getUpdateText().setText("Updated... Please re-run!");
			bufferOut.flush();
			out.close();
			bufferIn.close();
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
