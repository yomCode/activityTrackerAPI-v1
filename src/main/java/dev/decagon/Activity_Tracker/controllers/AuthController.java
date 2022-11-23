package dev.decagon.Activity_Tracker.controllers;


import dev.decagon.Activity_Tracker.pojos.ApiResponse;
import dev.decagon.Activity_Tracker.pojos.LoginRequest;
import dev.decagon.Activity_Tracker.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> userLogin(@RequestBody LoginRequest request, HttpSession session){

       return userService.userLogin(request, session);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> userLogout(HttpSession session){
       return userService.userLogout(session);
    }
}
