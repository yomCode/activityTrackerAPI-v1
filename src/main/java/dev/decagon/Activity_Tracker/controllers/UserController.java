package dev.decagon.Activity_Tracker.controllers;


import dev.decagon.Activity_Tracker.pojos.ApiResponse;
import dev.decagon.Activity_Tracker.pojos.RegistrationRequest;
import dev.decagon.Activity_Tracker.services.UserService;
import dev.decagon.Activity_Tracker.utils.ResponseManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ApiResponse<Object> register_user(@Valid @RequestBody RegistrationRequest request){
        String response = userService.createUser(request);
        return new ResponseManager().success(response, HttpStatus.CREATED);
    }

}
