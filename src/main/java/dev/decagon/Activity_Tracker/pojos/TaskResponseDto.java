package dev.decagon.Activity_Tracker.pojos;


import dev.decagon.Activity_Tracker.enums.Status;
import lombok.Data;

@Data
public class TaskResponseDto {
    private String title;
    private String description;
    private Status status;

}
