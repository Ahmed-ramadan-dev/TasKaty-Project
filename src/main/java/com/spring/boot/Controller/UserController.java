package com.spring.boot.Controller;

import com.spring.boot.Dto.UserProfileResponseDto;
import com.spring.boot.Dto.UserTaskResponseDto;
import com.spring.boot.Enums.TaskStatus;
import com.spring.boot.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User APIs", description = "Operations related to user profile and tasks")
public class UserController {
    @Autowired
    UserService userService;
    @Operation(summary = "Get current user profile", description = "Retrieve the profile details of the currently logged-in user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me")

    public ResponseEntity<UserProfileResponseDto> getProfile() {
        UserProfileResponseDto profile = userService.getProfile();
        return ResponseEntity.ok(profile);
    }
    @Operation(summary = "Get my tasks", description = "Retrieve all tasks assigned to the currently logged-in user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No tasks found")
    })
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me/tasks")
    public ResponseEntity<List<UserTaskResponseDto>> getMyTasks() {
        List<UserTaskResponseDto> tasks = userService.getMyTasks();
        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tasks);
    }
    @Operation(summary = "Update task status", description = "Update the status of a specific task assigned to the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task status updated successfully"),

            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "400", description = "Invalid status value")
    })
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/me/tasks/{taskId}/status")
    public ResponseEntity<String> updateTaskStatus(
            @PathVariable Long taskId,
            @RequestParam TaskStatus status) {

        userService.updateTaskStatus(taskId, status);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Task status updated successfully");
    }
}
