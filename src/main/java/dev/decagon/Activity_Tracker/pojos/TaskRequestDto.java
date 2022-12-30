package dev.decagon.Activity_Tracker.pojos;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskRequestDto {

    private String title;
    private String description;
    private Long id;
    private String status;

}
