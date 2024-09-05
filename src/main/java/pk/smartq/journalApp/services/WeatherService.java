package pk.smartq.journalApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pk.smartq.journalApp.payloads.WeatherApiResponse;

@Component
public class WeatherService {

    private static String apiKey = "9a4a86b57c2f489396c85616240509";

    @Autowired
    private RestTemplate restTemplate;

    private static final String api = "http://api.weatherapi.com/v1/current.json?key=API_KEY&q=City&aqi=no";

    public WeatherApiResponse getWeather (String location){
        if (location !=null) {
            String finalApi = api.replace("City", location).replace("API_KEY", apiKey);
            ResponseEntity<WeatherApiResponse> response =  restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherApiResponse.class);
             return response.getBody();
        }
        return null;
    }



}
