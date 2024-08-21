package pk.smartq.journalApp.cotrollers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pk.smartq.journalApp.entities.User;
import pk.smartq.journalApp.services.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")

public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        try {
             Optional<List<User>> users = Optional.ofNullable(userService.getAllUsers());
             if (users.isPresent()){
                 return new ResponseEntity<>(users, HttpStatus.OK);
             }

            return new ResponseEntity<>("",HttpStatus.OK);

        }
        catch (Exception e ){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try{
               Optional<User> userInDb = userService.findUserByUsername(user.getUsername());
        if (userInDb.isPresent()){
            return new ResponseEntity<>("username is already present in the database", HttpStatus.OK);
        }
        userService.createUser(user);
        return new ResponseEntity<>(user,HttpStatus.CREATED);

        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("{username}")
    public  ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String username){
        try {
            Optional<User> userInDb = userService.findUserByUsername(username);
            if (userInDb.isPresent()){
                userInDb.get().setUsername(user.getUsername());
                userInDb.get().setPassword(user.getPassword());
                userService.saveUser(userInDb.get());

                return  new ResponseEntity<>(userInDb, HttpStatus.OK);
            }

            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);

        }catch (Exception e ){
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
