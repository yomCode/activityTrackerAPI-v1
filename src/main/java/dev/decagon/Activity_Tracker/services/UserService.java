package dev.decagon.Activity_Tracker.services;

import dev.decagon.Activity_Tracker.pojos.ApiResponse;
import dev.decagon.Activity_Tracker.pojos.LoginRequest;
import dev.decagon.Activity_Tracker.pojos.RegistrationRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;

public interface UserService {

    ResponseEntity<ApiResponse> createUser(RegistrationRequest request);

    ResponseEntity<ApiResponse> userLogin(LoginRequest request, HttpSession session);

    ResponseEntity<String>userLogout(HttpSession session);

}
