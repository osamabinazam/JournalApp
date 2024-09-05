package pk.smartq.journalApp.cotrollers;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pk.smartq.journalApp.payloads.ErrorResponse;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/public")
public class HealthCheck {

    @GetMapping("/api/health-check")
    public ResponseEntity<?> healthCheck(HttpServletRequest request) {
        try {
            Map<String, Object> body = new HashMap<>();
            body.put("message", "System is up and running");
            body.put("statusCode", HttpStatus.OK.value());

            return  new ResponseEntity<>(body, HttpStatus.OK);

        } catch (Exception e){
            ErrorResponse body = new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());

            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }
    }
}
