package dev.decagon.Activity_Tracker.controllers;


import dev.decagon.Activity_Tracker.pojos.ApiResponse;
import dev.decagon.Activity_Tracker.pojos.LoginRequest;
import dev.decagon.Activity_Tracker.services.UserService;
import dev.decagon.Activity_Tracker.utils.ResponseManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final HttpSession session;

    @PostMapping("/login")
    public ApiResponse<Object> userLogin(@RequestBody LoginRequest request){
        String response = userService.userLogin(request, session);
        return new ResponseManager().success(response, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ApiResponse<Object> userLogout(){
       String response = userService.userLogout(session);
       return new ResponseManager().success(response, HttpStatus.OK);
    }
}
