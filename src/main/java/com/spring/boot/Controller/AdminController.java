package com.spring.boot.Controller;

import com.spring.boot.Dto.*;
import com.spring.boot.Enums.RoleName;
import com.spring.boot.Service.AdminServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin APIs", description = "APIs for managing admins, managers, users and their tasks")
public class AdminController {
    @Autowired
    private AdminServiceInterface adminService;


    // >>>>>>>>>>ADMIN API<<<<<<<<<<<<<<
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-admin")
    @Operation(summary = "Register Admin ", description = "Creates a new Admin  account.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Admin registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "403", description = "Only Admin can create Admin "),
            @ApiResponse(responseCode = "409", description = "Email already registered"),
            @ApiResponse(responseCode = "404", description = "Role not found")
    })
    public ResponseEntity<String>SignUpAdmin(@RequestBody @Valid UserSignUpNormalDto userSignUpNormalDto) {
        adminService.signUpAdmin(userSignUpNormalDto);
        return ResponseEntity.created(URI.create("/api/admin/create-admin"))
                .body("Sign Up Successful");
    }



    @Operation(summary = "Get All Admins", description = "Fetches a list of all admins")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of admins retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No admins found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<AdminResponseDto>> getAdmins() {
        return ResponseEntity.ok(adminService.getAdmins());
    }

    @Operation(summary = "Get Admin by ID", description = "Fetches an admin by their unique ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Admin found successfully"),
            @ApiResponse(responseCode = "404", description = "Admin not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<AdminResponseDto> getAdminsByID(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getAdminsByID(id));
    }
    @Operation(summary = "Update Admin", description = "Updates an admin's information")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Admin updated successfully"),
            @ApiResponse(responseCode = "404", description = "Admin not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateAdmin(
            @PathVariable Long id,
            @Valid @RequestBody AdminUpdateRequestDto dto) {
        adminService.updateAdmin(id, dto, RoleName.ADMIN.name());
        return ResponseEntity.ok("Admin updated successfully");
    }
    @Operation(summary = "Delete Admin", description = "Deletes an admin by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Admin deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Admin not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.ok("Admin deleted successfully");
    }
    // <<<<<<<<<<<<MANAGER API>>>>>>>>>>>>>
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-manager")
    @Operation(summary = "Register Manager", description = "Creates a new Manager account.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Manager registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "403", description = "Only Admin can create Manager"),
            @ApiResponse(responseCode = "409", description = "Email already registered"),
            @ApiResponse(responseCode = "404", description = "Role not found")
    })
    public ResponseEntity<String> signUpManager(@RequestBody @Valid UserSignUpNormalDto userSignUpNormalDto) {
        adminService.signUpManager(userSignUpNormalDto);
        return ResponseEntity.created(URI.create("/auth/sign-up-for-dev"))
                .body("Sign Up Successful");
    }


    @Operation(summary = "Get All Managers", description = "Fetches a list of all managers")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of managers retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No managers found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/managers")
    public ResponseEntity<List<ManagerResponseDto>> getManagers() {
        return ResponseEntity.ok(adminService.getManagers());

    }
    @Operation(summary = "Get Manager by ID", description = "Fetches a manager by their unique ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Manager found successfully"),
            @ApiResponse(responseCode = "404", description = "Manager not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/managers/{id}")
    public ResponseEntity<ManagerResponseDto> getManagerByID(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getManagerByID(id));
    }
    @Operation(summary = "Update Manager", description = "Updates a manager's information")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Manager updated successfully"),
            @ApiResponse(responseCode = "404", description = "Manager not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/managers/{id}")
    public ResponseEntity<String> updateManager(
            @PathVariable Long id,
            @Valid @RequestBody AdminUpdateRequestDto dto) {
        adminService.updateManager(id, dto);
        return ResponseEntity.ok("Manager updated successfully");
    }


    @Operation(summary = "Delete Manager", description = "Deletes a manager by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Manager deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Manager not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/managers/{id}")
    public ResponseEntity<String> deleteManager(@PathVariable Long id) {
        adminService.deleteManager(id);
        return ResponseEntity.ok("Manager deleted successfully");
    }
    // <<<<<<<<<<<<TASK API>>>>>>>>>>>>>
    @Operation(summary = "Get All Managers with Their Users", description = "Fetches all managers with the users under their supervision")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Managers with users retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No managers found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/managers-with-users")
    public ResponseEntity<List<ManagerWithUsersResponseDto>> getManagersWithUsers() {
        return ResponseEntity.ok(adminService.getManagersWithUsers());
    }

    @Operation(summary = "Get All Tasks for a User", description = "Fetches all tasks assigned to a specific user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User or tasks not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/tasks/user/{userId}")
    public ResponseEntity<List<UserTaskResponseDto>> getTasksByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(adminService.getTasksByUser(userId));
    }
    @Operation(summary = "Get Manager with Their Users and Tasks", description = "Fetches a manager along with their assigned users and tasks")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Manager with users and tasks retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Manager not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/managers/{managerId}/users-tasks")
    public ResponseEntity<ManagerWithUsersResponseDto> getManagerWithUsersAndTasks(@PathVariable Long managerId) {
        return ResponseEntity.ok(adminService.getManagerWithUsersAndTasks(managerId));
    }
}
