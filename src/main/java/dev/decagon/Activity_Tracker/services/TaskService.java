package dev.decagon.Activity_Tracker.services;


import dev.decagon.Activity_Tracker.pojos.ApiResponse;
import dev.decagon.Activity_Tracker.pojos.TaskRequestDto;
import dev.decagon.Activity_Tracker.pojos.TaskResponseDto;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface TaskService {

    String createTask(TaskRequestDto request, HttpSession session);

    Object viewAllTask(HttpSession session);

    Object viewTaskById(Long task_id);

    ApiResponse<Object> deleteTask(Long task_id);

    ApiResponse<Object> edit_taskTitle(TaskRequestDto request, Long task_id);

    ApiResponse<Object> edit_taskDescription(TaskRequestDto request, Long task_id);

    ApiResponse<Object> viewTaskByStatus(String status, HttpSession session);

    ApiResponse<Object> updateTaskStatus (TaskRequestDto request, Long task_id);

}
