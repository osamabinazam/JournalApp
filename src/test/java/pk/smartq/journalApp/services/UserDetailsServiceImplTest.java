package pk.smartq.journalApp.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import pk.smartq.journalApp.entities.User;
import pk.smartq.journalApp.repositories.UserRepository;

import java.util.List;

import  static  org.mockito.Mockito.*;

//@SpringBootTest
public class UserDetailsServiceImplTest {

//    @Autowired
    @InjectMocks
    private  UserDetailsServiceImpl userDetailsServiceImpl;

    @Mock
    private UserRepository userRepository;


    // It initialize the userRepository and Inject the Mock Object to service implementation
    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadByUsernameTest(){
        when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(User.builder().username("admin").password("toor").roles(List.of("ADMIN")).build());
        UserDetails user = userDetailsServiceImpl.loadUserByUsername("admin");
        Assertions.assertNotNull(user);
    }
}
