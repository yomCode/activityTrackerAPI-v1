package dev.decagon.Activity_Tracker.serviceImpl;

import dev.decagon.Activity_Tracker.entities.Task;
import dev.decagon.Activity_Tracker.entities.User;
import dev.decagon.Activity_Tracker.enums.Status;
import dev.decagon.Activity_Tracker.exceptions.NullUserException;
import dev.decagon.Activity_Tracker.exceptions.ResourceNotFoundException;
import dev.decagon.Activity_Tracker.pojos.ApiResponse;
import dev.decagon.Activity_Tracker.pojos.TaskRequestDto;
import dev.decagon.Activity_Tracker.pojos.TaskResponseDto;
import dev.decagon.Activity_Tracker.repositories.TaskRepository;
import dev.decagon.Activity_Tracker.services.TaskService;
import dev.decagon.Activity_Tracker.utils.ResponseManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse> createTask(TaskRequestDto request, HttpSession session) {

        User user = (User) session.getAttribute("currUser");

        if(user != null) {
            Task task = new Task();

            task.setUser(user);
            task.setTitle(request.getTitle());
            task.setDescription(request.getDescription());
            task.setStatus(Status.valueOf("PENDING"));

            taskRepository.save(task);
            return new ResponseEntity<>(responseManager.success(task), HttpStatus.CREATED);
        }
        throw new NullUserException("Login to create a tasks", "No user in session");
    }


    //    ===========================VIEW ALL TASK=============================================
    @Override
    public ResponseEntity<List<TaskResponseDto>> viewAllTask(HttpSession session) {

        User user = (User) session.getAttribute("currUser");

        if(user != null) {
            List<Task> tasks = user.getTasks();
            List<TaskResponseDto> taskList = new ArrayList<>();
            tasks.forEach(task -> {
                TaskResponseDto response = new TaskResponseDto();
                response.setTitle(task.getTitle());
                response.setDescription(task.getDescription());
                response.setStatus(task.getStatus());
                taskList.add(response);
            });
            return new ResponseEntity<>(taskList, HttpStatus.FOUND);
        }
        throw new NullUserException("Login to view your tasks", "No user in session");
    }

    //    ===========================VIEW TASK BY ID=============================================
    @Override
    public ResponseEntity<TaskResponseDto> viewTaskById(Long task_id) {

        Task task =  taskRepository.findById(task_id)
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Task not found", "Provide a valid taskId"));

        TaskResponseDto response = new TaskResponseDto();
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());

        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

//    ===========================EDIT TASK TITLE=============================================

    public ResponseEntity<ApiResponse> edit_taskTitle(TaskRequestDto request, Long task_id){

        Task task = taskRepository.findById(task_id)
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Task not found", "Provide a valid task Id"));

        task.setTitle(request.getTitle());
        taskRepository.save(task);
        TaskResponseDto response = new TaskResponseDto();
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());

        return new ResponseEntity<>(responseManager.success(task), HttpStatus.ACCEPTED) ;
    }


    //    ===========================EDIT TASK DESCRIPTION=============================================

    public ResponseEntity<ApiResponse> edit_taskDescription(TaskRequestDto request, Long task_id){

        Task task = taskRepository.findById(task_id)
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Task not found", "Provide a valid task Id"));

        task.setDescription(request.getDescription());
        taskRepository.save(task);
        TaskResponseDto response = new TaskResponseDto();
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());

        return new ResponseEntity<>(responseManager.success(response), HttpStatus.ACCEPTED) ;
    }


    //    ===========================DELETE TASK=============================================

    @Override
    public ResponseEntity<ApiResponse> deleteTask(Long task_id) {
        Task task = taskRepository.findById(task_id)
                        .orElseThrow(()-> new ResourceNotFoundException(
                                "Task not found", "Provide a valid task Id"));

        taskRepository.delete(task);
        return new ResponseEntity<>(responseManager.success("Task deleted successfully!"), HttpStatus.ACCEPTED);
    }


    //    ===========================VIEW TASK BY STATUS=============================================

    public ResponseEntity<List<TaskResponseDto>> viewTaskByStatus(String status, HttpSession session){
        User user = (User) session.getAttribute("currUser");
        List<Task> tasks = user.getTasks();

        List<TaskResponseDto> responses = new ArrayList<>();
        tasks.forEach(task ->{
            if(task.getStatus().equals(Status.valueOf(status.toUpperCase()))){
                TaskResponseDto response = new TaskResponseDto();
                response.setTitle(task.getTitle());
                response.setDescription(task.getDescription());
                response.setStatus(task.getStatus());
                responses.add(response);
            }
        });
        return new ResponseEntity<>(responses, HttpStatus.FOUND) ;
    }


    //    ===========================UPDATE TASK STATUS=============================================

    @Override
    public ResponseEntity<ApiResponse> updateTaskStatus(TaskRequestDto request, Long task_id) {

        Task task = taskRepository.findById(task_id)
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Task not found", "Provide a valid taskId"));

        TaskResponseDto response = new TaskResponseDto();

        task.setStatus(Status.valueOf(request.getStatus().toUpperCase()));
        if(request.getStatus().equalsIgnoreCase("done")){
            task.setCompletedAt(new Date());
        }
        taskRepository.save(task);

        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());

        return new ResponseEntity<>(responseManager.success(response), HttpStatus.FOUND);
    }
}
