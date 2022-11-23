package dev.decagon.Activity_Tracker.serviceImpl;

import dev.decagon.Activity_Tracker.entities.User;
import dev.decagon.Activity_Tracker.enums.Gender;
import dev.decagon.Activity_Tracker.pojos.LoginRequest;
import dev.decagon.Activity_Tracker.repositories.UserRepository;
import dev.decagon.Activity_Tracker.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;

    @Test
    void createUser() {
        String username = "yo";
        String email = "yo@gmail.com";
        String password = "1234";
        Gender gender = Gender.MALE;

        userRepository.save(new User(username, email, password, gender));
        User user = userRepository.findUserByUsernameAndPassword(username, password);
        Boolean expected = false;
        Boolean actual = user == null;

        assertEquals(expected, actual);
    }



    @Test
    void userLogin() {
        LoginRequest request = new LoginRequest();
        request.setUsername("yom");
        request.setPassword("1234");

        userService.userLogin(request, session);

        User user = (User) session.getAttribute("currUser");

        assertEquals(true, user != null);
    }

    @Test
    void userLogout(){
        LoginRequest request = new LoginRequest();
        request.setUsername("yom");
        request.setPassword("1234");

        userService.userLogin(request, session);
        userService.userLogout(session);
        User user = (User) session.getAttribute("currUser");

        assertEquals(true, user == null);
    }

}