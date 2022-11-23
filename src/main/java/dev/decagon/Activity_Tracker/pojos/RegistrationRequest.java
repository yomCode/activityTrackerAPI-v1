package dev.decagon.Activity_Tracker.pojos;


import lombok.Data;


@Data
public class RegistrationRequest {

    private String username;

    private String email;

    private String password;

    private String gender;


}
