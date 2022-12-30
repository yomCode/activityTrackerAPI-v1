package dev.decagon.Activity_Tracker.utils;


import dev.decagon.Activity_Tracker.pojos.ApiResponse;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class ResponseManager {

    public ApiResponse<Object> success(Object data){

        return new ApiResponse<>("Request successful", true, data);
    }

    public ApiResponse<Object> loginSuccess(Object request){

        return  new ApiResponse<>("Login Successful", true, request);
    }


    public ApiResponse<Object> failure(Object errorMsg){

        return new ApiResponse<>(errorMsg, false, null);
    }

}
