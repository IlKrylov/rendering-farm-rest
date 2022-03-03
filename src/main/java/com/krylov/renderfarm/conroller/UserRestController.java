package com.krylov.renderfarm.conroller;

import com.krylov.renderfarm.dto.RegistrationRequestDto;
import com.krylov.renderfarm.dto.UserDto;
import com.krylov.renderfarm.service.TaskService;
import com.krylov.renderfarm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
        UserDto user = userService.register(registrationRequestDto);
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @GetMapping("/tasks")
    public ResponseEntity getUserTasks(Authentication authentication) {
        //TODO: get id from auth, run service method...
        return null;
    }
}
