package dev.decagon.Activity_Tracker.serviceImpl;

import dev.decagon.Activity_Tracker.entities.Task;
import dev.decagon.Activity_Tracker.entities.User;
import dev.decagon.Activity_Tracker.enums.Status;
import dev.decagon.Activity_Tracker.exceptions.UserNotFoundException;
import dev.decagon.Activity_Tracker.exceptions.ResourceNotFoundException;
import dev.decagon.Activity_Tracker.pojos.ApiResponse;
import dev.decagon.Activity_Tracker.pojos.TaskRequestDto;
import dev.decagon.Activity_Tracker.pojos.TaskResponseDto;
import dev.decagon.Activity_Tracker.repositories.TaskRepository;
import dev.decagon.Activity_Tracker.services.TaskService;
import dev.decagon.Activity_Tracker.utils.ResponseManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ResponseManager responseManager;


//    ===========================CREATE TASK=============================================
    @Override
    public String createTask(TaskRequestDto request, HttpSession session) {

        User user = (User) session.getAttribute("currUser");

        if(user != null) {
            Task task = Task.builder()
                    .user(user)
                    .title(request.getTitle())
                    .description(request.getDescription())
                    .status(Status.valueOf("PENDING")).build();

            taskRepository.save(task);
            return "Task created successfully";
        }
        throw new UserNotFoundException("Login to create a tasks", "No user in session");
    }


    //    ===========================VIEW ALL TASK=============================================
    @Override
    public Object viewAllTask(HttpSession session) {

        User user = (User) session.getAttribute("currUser");

        if(user != null) {
            List<Task> tasks = user.getTasks();
            List<TaskResponseDto> taskList = new ArrayList<>();
            tasks.forEach(task -> {

                TaskResponseDto response = TaskResponseDto.builder()
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .status(task.getStatus())
                        .build();
                taskList.add(response);
            });
            return taskList;
        }
        throw new UserNotFoundException("Login to view your tasks", "No user in session");
    }

    //    ===========================VIEW TASK BY ID=============================================
    @Override
    public Object viewTaskById(Long task_id) {

        Task task =  taskRepository.findById(task_id)
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Task not found", "Provide a valid taskId"));

        return TaskResponseDto.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .build();
    }

//    ===========================EDIT TASK TITLE=============================================

    public ApiResponse<Object> edit_taskTitle(TaskRequestDto request, Long task_id){

        Task task = taskRepository.findById(task_id)
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Task not found", "Provide a valid task Id"));

        task.setTitle(request.getTitle());
        taskRepository.save(task);

        TaskResponseDto response = TaskResponseDto.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .build();

        return responseManager.success(response);
    }


    //    ===========================EDIT TASK DESCRIPTION=============================================

    public ApiResponse<Object> edit_taskDescription(TaskRequestDto request, Long task_id){

        Task task = taskRepository.findById(task_id)
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Task not found", "Provide a valid task Id"));

        task.setDescription(request.getDescription());
        taskRepository.save(task);
        TaskResponseDto response = TaskResponseDto.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .build();

        return responseManager.success(response);
    }


    //    ===========================DELETE TASK=============================================

    @Override
    public ApiResponse<Object> deleteTask(Long task_id) {
        Task task = taskRepository.findById(task_id)
                        .orElseThrow(()-> new ResourceNotFoundException(
                                "Task not found", "Provide a valid task Id"));

        taskRepository.delete(task);

        return responseManager.success("Task deleted successfully!");
    }


    //===========================VIEW TASK BY STATUS=============================================

    public ApiResponse<Object> viewTaskByStatus(String status, HttpSession session){
        User user = (User) session.getAttribute("currUser");
        List<Task> tasks = user.getTasks();

        List<TaskResponseDto> responses = new ArrayList<>();
        tasks.forEach(task ->{
            if(task.getStatus().equals(Status.valueOf(status.toUpperCase()))){
                TaskResponseDto response = TaskResponseDto.builder()
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .status(task.getStatus())
                        .build();
                responses.add(response);
            }
        });
        return responseManager.success(responses);
    }


    //    ===========================UPDATE TASK STATUS=============================================
    @Override
    public ApiResponse<Object> updateTaskStatus(TaskRequestDto request, Long task_id) {

        Task task = taskRepository.findById(task_id)
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Task not found", "Provide a valid taskId"));

        task.setStatus(Status.valueOf(request.getStatus().toUpperCase()));
        if(request.getStatus().equalsIgnoreCase("done")){
            task.setCompletedAt(new Date());
        }
        taskRepository.save(task);

        TaskResponseDto response = TaskResponseDto.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .build();

        return responseManager.success(response);
    }
}
