package com.spring.boot.Service;

import com.spring.boot.Dto.UserProfileResponseDto;
import com.spring.boot.Dto.UserTaskResponseDto;
import com.spring.boot.Enums.TaskStatus;

import java.util.List;

public interface UserService {
    UserProfileResponseDto getProfile();
    List<UserTaskResponseDto> getMyTasks();
    void updateTaskStatus( Long taskId, TaskStatus status);
}
