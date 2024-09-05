package pk.smartq.journalApp.services;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pk.smartq.journalApp.entities.User;
import pk.smartq.journalApp.repositories.UserRepository;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

//    @Autowired
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User saveUser(User user){

        try {
            return userRepository.save(user);
        } catch (Exception e){
            log.error("Error occurred when creating user {}" , user.getUsername(), e);
            return null;
        }
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User createUser (User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    public boolean deleteUserByUsername(String username){

        try {
            User user = userRepository.findByUsername(username);
            if (user!=null){
                userRepository.deleteByUsername(username);
                return true;


            }
        } catch (Exception e ){
            log.error("Error occurred when deleting user {}", username, e );
            return  false;
        }
        return false;
    }


}

