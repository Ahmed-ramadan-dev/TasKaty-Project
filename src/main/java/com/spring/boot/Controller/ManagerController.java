package com.spring.boot.Controller;

import com.spring.boot.Dto.*;
import com.spring.boot.Enums.TaskStatus;
import com.spring.boot.Service.ManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/manager")
@Tag(name = "Manager APIs", description = "Operations related to managers, tasks, and users")
public class ManagerController {
    @Autowired
    private ManagerService managerService;
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/create/user")
    @Operation(summary = "Register User", description = "Creates a new normal user account.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Email already registered")
    })
    public ResponseEntity<String> signUpUser(@RequestBody @Valid UserSignUpNormalDto userSignUpNormalDto) {
        managerService.signUpUser(userSignUpNormalDto);
        return ResponseEntity.created(URI.create("/api/manager/create/user"))
                .body("Sign Up Successful");
    }










    @Operation(summary = "Create Task", description = "Creates a new task for a specific user by manager")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created successfully"),
            @ApiResponse(responseCode = "404", description = "Assigned user not found"),
            @ApiResponse(responseCode = "400", description = "Invalid task data")
    })
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/tasks/{id}")
    public ResponseEntity<String> createTask(
            @PathVariable Long id,
           @Valid @RequestBody MangerTaskDto mangerTaskDto) {
        managerService.createTask(id, mangerTaskDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Task created successfully");
    }
    @Operation(summary = "Update Task", description = "Update the details of a specific task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "403", description = "You are not allowed to update this task")
    })
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<String> updateTask(
            @PathVariable Long taskId,
            @Valid @RequestBody ManagerUpdateTaskDto managerUpdateTaskDto) {
        managerService.updateTask(taskId, managerUpdateTaskDto);
        return ResponseEntity.ok("Task updated successfully");
    }
    @Operation(summary = "Get Manager Users", description = "Retrieve all users assigned to the logged-in manager")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No users found")
    })
    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/users")
    public ResponseEntity<List<ManagerGetUserDto>> getUsersByManager() {
        List<ManagerGetUserDto> users = managerService.getUsersByManager();
        return users.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(users);
    }
    @Operation(summary = "Get User by ID", description = "Retrieve a specific user assigned to the logged-in manager")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/users/{id}")
    public ResponseEntity<ManagerGetUserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(managerService.getUserById(id));
    }
    @Operation(summary = "Update User", description = "Update user details assigned to the logged-in manager")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/user/{id}")
    public ResponseEntity<String> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody AdminUpdateRequestDto dto) {
        managerService.updateUser(id, dto);
        return ResponseEntity.ok("User updated successfully");
    }
    @Operation(summary = "Get User Tasks", description = "Retrieve all tasks for a specific user assigned to the logged-in manager")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No tasks found for this user")
    })
    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/users/{userId}/tasks")
    public ResponseEntity<List<MangerTaskDto>> getUserTasks(@PathVariable Long userId) {
        List<MangerTaskDto> tasks = managerService.getUserTasks(userId);
        return tasks.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(tasks);
    }
    @Operation(summary = "Update Task Status", description = "Update the status of a specific task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/tasks/{taskId}/status")
    public ResponseEntity<String> updateTaskStatus(
            @PathVariable Long taskId,
            @RequestParam TaskStatus status) {
        managerService.updateTaskStatus(taskId, status);
        return ResponseEntity.ok("Task status updated successfully");
    }
    @Operation(summary = "Delete Task", description = "Delete a specific task assigned to a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Task or user not found")
    })
    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/users/{userId}/tasks/{taskId}")
    public ResponseEntity<String> deleteTask(
            @PathVariable Long userId,
            @PathVariable Long taskId) {
        managerService.deleteTask(userId, taskId);
        return ResponseEntity.ok("Task deleted successfully");
    }

}
