package dev.decagon.Activity_Tracker.services;


import dev.decagon.Activity_Tracker.pojos.ApiResponse;
import dev.decagon.Activity_Tracker.pojos.TaskRequestDto;
import dev.decagon.Activity_Tracker.pojos.TaskResponseDto;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface TaskService {

    ResponseEntity<ApiResponse> createTask(TaskRequestDto request, HttpSession session);

    ResponseEntity<List<TaskResponseDto>> viewAllTask(HttpSession session);

    ResponseEntity<TaskResponseDto> viewTaskById(Long task_id);

    ResponseEntity<ApiResponse> deleteTask(Long task_id);

    ResponseEntity<ApiResponse> edit_taskTitle(TaskRequestDto request, Long task_id);

    ResponseEntity<ApiResponse> edit_taskDescription(TaskRequestDto request, Long task_id);

    ResponseEntity<List<TaskResponseDto>> viewTaskByStatus(String status, HttpSession session);

    ResponseEntity<ApiResponse> updateTaskStatus (TaskRequestDto request, Long task_id);

}
