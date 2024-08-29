package pk.smartq.journalApp.cotrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pk.smartq.journalApp.entities.User;
import pk.smartq.journalApp.services.UserService;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/user")

public class UserController {
    @Autowired
    private UserService userService;



    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            Optional<User> userInDb = userService.findUserByUsername(username);
            if (userInDb.isPresent()) {
                userInDb.get().setUsername(user.getUsername());
                userInDb.get().setPassword(user.getPassword());
                userService.createUser(userInDb.get());

                return new ResponseEntity<>(userInDb, HttpStatus.OK);
            }

            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            Optional<User> userInDb = userService.findUserByUsername(username);
            if (userInDb.isPresent()) {
                userService.deleteUserByUsername(username);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsername(@RequestParam String username) {

        try {
            Optional<User> user = userService.findUserByUsername(username);
            if (user.isPresent()) {
                return new ResponseEntity<>(user.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

}
