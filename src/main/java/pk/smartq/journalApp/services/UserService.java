package pk.smartq.journalApp.services;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pk.smartq.journalApp.entities.User;
import pk.smartq.journalApp.repositories.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

//    @Autowired
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User saveUser(User user){

        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User createUser (User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER"));
        return userRepository.save(user);
    }



    public Optional<User> findUserById(ObjectId id ){
        return  userRepository.findById(id);

    }

    public  Optional<User> findUserByUsername(String username){
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    public void deleteUserById(ObjectId id ){
        userRepository.deleteById(id);
    }

    public void deleteUserByUsername(String username){
        userRepository.deleteByUsername(username);
    }


}

