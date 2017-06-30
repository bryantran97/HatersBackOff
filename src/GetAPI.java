import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class GetAPI {

	 String weather(String city) throws IOException{
		
		// This gets user's City name and encodes it into API URL
		city = URLEncoder.encode(city, "UTF-8");
		String url = "http://api.openweathermap.org/data/2.5/weather?q="+city+"&APPID=26aa1d90a24c98fad4beaac70ddbf274";
		URL weatherURL = new URL(url);
		
        // Making the GET request
        HttpURLConnection weatherConnection = (HttpURLConnection) weatherURL.openConnection();
        weatherConnection.setRequestMethod("GET");
        
        weatherConnection.setDoOutput(true);
        weatherConnection.connect();
        
        // Obtaining String and building it
        BufferedReader reader = new BufferedReader(new InputStreamReader(weatherConnection.getInputStream()));
        StringBuilder results = new StringBuilder();
        
        // Appending string lines
        String line;
        while ((line = reader.readLine()) != null) {
            results.append(line);
        }
        
        weatherConnection.disconnect();
       
        // Parsing JSON data
    	JsonElement jelement = new JsonParser().parse(results.toString());
        JsonObject  jobject = jelement.getAsJsonObject();
        JsonObject windObject = jobject.getAsJsonObject("wind");
        JsonObject tempObject = jobject.getAsJsonObject("main");
        
        // Calculations
        double kelvin = tempObject.get("temp_max").getAsDouble();
        double temp_max = 1.8 * (kelvin - 273) + 32;       
        kelvin = tempObject.get("temp_min").getAsDouble();
        double temp_min = 1.8 * (kelvin - 273) + 32;
        
        double wspeed = windObject.get("speed").getAsDouble();
        
        // String outputs
        String output = "The temperature in " + city + " has a high of " + temp_max + " degrees fahrenheit, a low of "
        		+ temp_min + "degrees fahrenheit and a wind speed of "
        		+ wspeed + " mph.";
        return output;        
	}

}


