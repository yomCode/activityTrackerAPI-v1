package dev.decagon.Activity_Tracker.controllers;


import dev.decagon.Activity_Tracker.entities.Task;
import dev.decagon.Activity_Tracker.entities.User;
import dev.decagon.Activity_Tracker.pojos.ApiResponse;
import dev.decagon.Activity_Tracker.pojos.TaskRequestDto;
import dev.decagon.Activity_Tracker.pojos.TaskResponseDto;
import dev.decagon.Activity_Tracker.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/task")
public class TaskController {

    private final TaskService taskService;

    //==========================CREATE TASK=======================================================

    @PostMapping("/create")
    public ResponseEntity<ApiResponse>  createTask(@RequestBody TaskRequestDto request, HttpSession session){

        return taskService.createTask(request, session);
    }


    //==========================VIEW ALL TASK=======================================================

    @GetMapping("/view-all")
    public ResponseEntity<List<TaskResponseDto>> getAllTask(HttpSession session){

        return taskService.viewAllTask(session);
    }


    //==========================VIEW TASK BY ID=======================================================

    @GetMapping("/view-task-by-id/{task_id}")
    public ResponseEntity<TaskResponseDto> viewTaskById(@PathVariable Long task_id){

        return taskService.viewTaskById(task_id);
    }


    //==========================DELETE TASK============================================================

    @DeleteMapping("/delete/{task_id}")
    public ResponseEntity<ApiResponse> deleteTask(@PathVariable Long task_id){

        return taskService.deleteTask(task_id);
    }


    //==========================VIEW TASK BY STATUS=======================================================

    @GetMapping("/view/{status}")
    public ResponseEntity<List<TaskResponseDto>> viewTaskByStatus(@PathVariable String status, HttpSession session){

        return taskService.viewTaskByStatus(status, session);
    }

    //==========================EDIT TASK TITLE=======================================================
    @PatchMapping("/edit-title/{task_id}")
    public ResponseEntity<ApiResponse> edit_taskTitle(@RequestBody TaskRequestDto request, @PathVariable Long task_id){

        return taskService.edit_taskTitle(request, task_id);
    }

    //==========================EDIT TASK DESCRIPTION=======================================================
    @PatchMapping("/edit-description/{task_id}")
    public ResponseEntity<ApiResponse> edit_taskDescription(@RequestBody TaskRequestDto request, @PathVariable Long task_id){

        return taskService.edit_taskDescription(request, task_id);
    }

    //==========================UPDATE TASK STATUS=======================================================
    @PatchMapping("/update-status/{task_id}")
    public ResponseEntity<ApiResponse> updateStatus(@RequestBody TaskRequestDto request, @PathVariable Long task_id){

        return taskService.updateTaskStatus(request, task_id);
    }

}
