package dev.decagon.Activity_Tracker.pojos;


import dev.decagon.Activity_Tracker.enums.Status;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskResponseDto {
    private String title;
    private String description;
    private Status status;

}
