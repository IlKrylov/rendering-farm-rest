package com.krylov.renderfarm.conroller;

import com.krylov.renderfarm.dto.RegistrationRequestDto;
import com.krylov.renderfarm.dto.TaskDto;
import com.krylov.renderfarm.dto.UserDto;
import com.krylov.renderfarm.security.userdetails.UserDetailsImpl;
import com.krylov.renderfarm.service.TaskService;
import com.krylov.renderfarm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserRestController {

    private final UserService userService;
    private final TaskService taskService;

    @Autowired
    public UserRestController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @PostMapping("/signup")
    public ResponseEntity registerUser(@RequestBody RegistrationRequestDto registrationRequestDto) {
        UserDto userDto = userService.register(registrationRequestDto);
        return new ResponseEntity(userDto, HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity checkAuthentication() {
        return new ResponseEntity("Ok", HttpStatus.OK);
    }

    @PostMapping("/tasks")
    public ResponseEntity createTask(Authentication authentication, @RequestBody TaskDto taskDto) {
        Long userId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        taskDto = taskService.createAndRun(userId, taskDto);
        return new ResponseEntity(taskDto, HttpStatus.OK);
    }

    @GetMapping("/tasks")
    public ResponseEntity getUserTasks(Authentication authentication) {
        Long userId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        List<TaskDto> taskDtoList = taskService.findAllTasksByUserId(userId);
        return new ResponseEntity(taskDtoList, HttpStatus.OK);
    }

    @GetMapping("/tasks/{taskId}")
    public ResponseEntity getUserTasks(Authentication authentication, @PathVariable Long taskId) {
        Long userId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        TaskDto taskDto = taskService.findTaskById(userId, taskId);
        return new ResponseEntity(taskDto, HttpStatus.OK);
    }


}
