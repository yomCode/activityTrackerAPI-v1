package dev.decagon.Activity_Tracker.serviceImpl;


import dev.decagon.Activity_Tracker.entities.User;
import dev.decagon.Activity_Tracker.enums.Gender;
import dev.decagon.Activity_Tracker.exceptions.UserNotFoundException;
import dev.decagon.Activity_Tracker.pojos.ApiResponse;
import dev.decagon.Activity_Tracker.pojos.LoginRequest;
import dev.decagon.Activity_Tracker.pojos.RegistrationRequest;
import dev.decagon.Activity_Tracker.repositories.UserRepository;
import dev.decagon.Activity_Tracker.services.UserService;
import dev.decagon.Activity_Tracker.utils.ResponseManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ResponseManager responseManager;

    @Override
    public String createUser(RegistrationRequest request) {

        Boolean existsByEmail = userRepository.existsByEmail(request.getEmail().toLowerCase());
        Boolean existsByUsername = userRepository.existsByUsername(request.getUsername().toLowerCase());

        if(existsByUsername)
            return "Username taken!";

        if(existsByEmail)
            return "A User with this email already exist!";

        User user = User.builder()
                .username(request.getUsername().toLowerCase())
                .email(request.getEmail().toLowerCase())
                .gender(Gender.valueOf(request.getGender().toUpperCase()))
                .password(request.getPassword())
                .build();
        userRepository.save(user);
        return "Registration successful";
    }


    @Override
    public String userLogin(LoginRequest request, HttpSession session) {

        User user = userRepository.findUserByUsernameAndPassword(request.getUsername().toLowerCase(),
                        request.getPassword()).orElseThrow(()-> new UserNotFoundException("Wrong email or password"));

        session.setAttribute("currUser", user);
        return "Login successful";
    }

    @Override
    public String userLogout(HttpSession session){
        session.invalidate();
        return "You have been logged out, hope to see you again soon";
    }
}
