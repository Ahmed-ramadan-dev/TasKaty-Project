package com.spring.boot.Service;

import com.spring.boot.Dto.*;
import com.spring.boot.Enums.TaskStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ManagerService {
    void signUpUser(UserSignUpNormalDto userSignUpNormalDto);

    void createTask(Long id,MangerTaskDto mangerTaskDto);
    void updateTask(Long taskId, ManagerUpdateTaskDto managerUpdateTaskDto);
    List<ManagerGetUserDto> getUsersByManager();
    ManagerGetUserDto getUserById(Long id);
    void updateUser( Long id, AdminUpdateRequestDto dto);
    List<MangerTaskDto> getUserTasks( Long userId);
    void updateTaskStatus( Long taskId,  TaskStatus status);
    void deleteTask( Long userId,  Long taskId);
}
