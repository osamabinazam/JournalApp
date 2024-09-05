package pk.smartq.journalApp.cotrollers;

import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pk.smartq.journalApp.payloads.WeatherApiResponse;
import pk.smartq.journalApp.services.WeatherService;

@RestController
@RequestMapping("/public/api/get-weather")
@Slf4j
public class WeatherApiController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    ResponseEntity<?> getWeather(@RequestParam String city){
        try{
            WeatherApiResponse weatherApiResponse = weatherService.getWeather(city);
            if (weatherApiResponse == null) {
                return new ResponseEntity<>("Weather not found for requested locationn", HttpStatus.NOT_FOUND);
            }else{
                return new ResponseEntity<>(weatherApiResponse, HttpStatus.OK);
            }
        } catch (Exception e ){
            log.error("Error during Weather Api Call: ",e );
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
