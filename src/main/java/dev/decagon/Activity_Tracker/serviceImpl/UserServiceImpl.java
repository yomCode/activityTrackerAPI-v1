package dev.decagon.Activity_Tracker.serviceImpl;


import dev.decagon.Activity_Tracker.entities.User;
import dev.decagon.Activity_Tracker.enums.Gender;
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
    public ResponseEntity<ApiResponse> createUser(RegistrationRequest request) {

        if(request.getUsername() == null)
            return new ResponseEntity<>(responseManager.failure("Username is required"), HttpStatus.BAD_REQUEST);
        if (request.getUsername().length() < 4)
            return new ResponseEntity<>(responseManager
                    .failure("Enter a valid username, username must be atleast 4 letters long"), HttpStatus.BAD_REQUEST);

        if(request.getEmail() == null)
            return new ResponseEntity<>(responseManager.failure("Email is required!"), HttpStatus.BAD_REQUEST);
        if(!request.getEmail().contains("@"))
            return new ResponseEntity<>(responseManager.failure("Enter a valid email address"), HttpStatus.BAD_REQUEST);

        if(request.getGender() == null)
            return new ResponseEntity<>(responseManager.failure("Kindly specify your gender"), HttpStatus.BAD_REQUEST);


        if(request.getPassword() == null)
            return new ResponseEntity<>(responseManager.failure("password is required"), HttpStatus.BAD_REQUEST);

        Boolean existsByEmail = userRepository.existsByEmail(request.getEmail().toLowerCase());
        Boolean existsByUsername = userRepository.existsByUsername(request.getUsername().toLowerCase());

        if(existsByUsername)
            return new ResponseEntity<>(responseManager.failure("Username already in use"), HttpStatus.IM_USED);

        if(existsByEmail)
            return new ResponseEntity<>(responseManager.failure("A User with this email already exist!"), HttpStatus.IM_USED);

        User user = new User();
        user.setUsername(request.getUsername().toLowerCase());
        user.setEmail(request.getEmail().toLowerCase());
        user.setGender(Gender.valueOf(request.getGender().toUpperCase()));
        user.setPassword(request.getPassword());

        userRepository.save(user);
        return new ResponseEntity<>(responseManager.success(user), HttpStatus.CREATED);

    }


    @Override
    public ResponseEntity<ApiResponse> userLogin(LoginRequest request, HttpSession session) {

        User user = userRepository.findUserByUsernameAndPassword(request.getUsername(), request.getPassword());

        if(user != null){
            session.setAttribute("currUser", user);
            return new ResponseEntity<>(responseManager.loginSuccess(user), HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>(responseManager.failure("wrong username or password"), HttpStatus.BAD_REQUEST);

    }

    @Override
    public ResponseEntity<String> userLogout(HttpSession session){
        session.invalidate();
        return new  ResponseEntity("User signed out successfully", HttpStatus.ACCEPTED);
    }
}
