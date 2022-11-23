package dev.decagon.Activity_Tracker.serviceImpl;

import dev.decagon.Activity_Tracker.entities.Task;
import dev.decagon.Activity_Tracker.entities.User;
import dev.decagon.Activity_Tracker.enums.Status;
import dev.decagon.Activity_Tracker.pojos.LoginRequest;
import dev.decagon.Activity_Tracker.pojos.TaskRequestDto;
import dev.decagon.Activity_Tracker.pojos.TaskResponseDto;
import dev.decagon.Activity_Tracker.repositories.TaskRepository;
import dev.decagon.Activity_Tracker.repositories.UserRepository;
import dev.decagon.Activity_Tracker.services.TaskService;
import dev.decagon.Activity_Tracker.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskServiceImplTest {

    @Autowired
    TaskRepository taskRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    TaskService taskService;
    @Autowired
    HttpSession session;


    @Test
    void createTask() {
        LoginRequest request = new LoginRequest();
        request.setUsername("musty");
        request.setPassword("1234");
        userService.userLogin(request, session);

        TaskRequestDto taskRequestDto = new TaskRequestDto();
        taskRequestDto.setTitle("Yom");
        taskRequestDto.setDescription("Have a talk with him");
        taskService.createTask(taskRequestDto, session);

        Task task = taskRepository.findById(14L).get();

        assertEquals("Yom", task.getTitle());
    }

    @Test
    void viewAllTask() {
        LoginRequest request = new LoginRequest();
        request.setUsername("musty");
        request.setPassword("1234");
        userService.userLogin(request, session);

        User user = (User) session.getAttribute("currUser");
        List<Task> tasks = user.getTasks();

        assertEquals(true, tasks.size() > 0);
    }

    @Test
    void viewTaskById() {
      ResponseEntity<TaskResponseDto> response = taskService.viewTaskById(2L);

      assertEquals(true, response != null);
    }

    @Test
    void edit_taskTitle() {
        TaskRequestDto request = new TaskRequestDto();
        request.setTitle("My new title");
        taskService.edit_taskTitle(request, 2L);

        Task task = taskRepository.findById(2L).get();

        assertEquals("My new title", task.getTitle());
    }

    @Test
    void edit_taskDescription() {
        TaskRequestDto request = new TaskRequestDto();
        request.setDescription("My new description");
        taskService.edit_taskDescription(request, 2L);

        Task task = taskRepository.findById(2L).get();

        assertEquals("My new description", task.getDescription());
    }



    @Test
    void viewTaskByStatus() {
        LoginRequest request = new LoginRequest();
        request.setUsername("musty");
        request.setPassword("1234");
        userService.userLogin(request, session);

        String status = "PENDING";
        ResponseEntity<List<TaskResponseDto>> task = taskService.viewTaskByStatus(status, session);

        assertEquals(true, task != null);

    }

    @Test
    void updateTaskStatus() {
        TaskRequestDto request = new TaskRequestDto();
        request.setStatus("In_progress");
        taskService.updateTaskStatus(request, 2L);

        Task task = taskRepository.findById(2L).get();

        assertEquals(Status.IN_PROGRESS, task.getStatus());
    }

    @Test
    void deleteTask() {
        taskService.deleteTask(14L);
        Task task = taskRepository.findById(14L).orElse(null);

        assertEquals(true, task == null);

    }
}