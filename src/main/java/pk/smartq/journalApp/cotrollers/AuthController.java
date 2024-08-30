package pk.smartq.journalApp.cotrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pk.smartq.journalApp.entities.User;
import pk.smartq.journalApp.services.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/public/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;
     @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {

        try {
            Optional<User> userInDb = userService.findUserByUsername(user.getUsername());
            if (userInDb.isPresent()) {
                return new ResponseEntity<>("username is already present in the database", HttpStatus.OK);
            }
            user.setRoles(List.of("USER"));
            userService.createUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
