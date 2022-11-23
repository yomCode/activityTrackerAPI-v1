package dev.decagon.Activity_Tracker.pojos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private Object message;
    private Boolean success;
    private T data;

}
