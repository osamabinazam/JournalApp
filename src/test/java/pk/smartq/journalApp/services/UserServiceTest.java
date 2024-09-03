package pk.smartq.journalApp.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pk.smartq.journalApp.entities.User;
import pk.smartq.journalApp.providers.UserArgumentsProvider;
import pk.smartq.journalApp.repositories.UserRepository;

import java.util.List;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  UserService userService;


//    @Disabled
//    @ParameterizedTest
//    @CsvSource({
//            "osama,toor,USER",
//            "falak,toor2,USER",
//            "admin,admin,ADMIN",
//            "hanood,toor3,USER"
//    })
//    public void testCreateUser(String username, String password, String role) {
//        assertNotNull(userService.createUser(User.builder()
//                .username(username)
//                .password(password)
//                .roles(List.of(role))
//                .build()));
//    }

    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testCreateUser(User user) {
        assertNotNull(userService.createUser(user), "Cannot create user" + user);
    }


    @ParameterizedTest
    @CsvSource({
        "osama",
        "falak",
        "admin",
        "hanood"
    })
    public void testFindUserByUsername(String username) {
        assertNotNull(userRepository.findByUsername(username));
    }


    @ParameterizedTest
    @CsvSource({
        "osama",
        "falak",
        "admin",
        "hanood"
    })
    public void testDeleteByUsername(String username) {
        assertTrue(userService.deleteUserByUsername(username));
    }
    
}
