package com.spring.boot.Service.Impl;

import com.spring.boot.BundleMessage.Exception.SystemException;
import com.spring.boot.Dto.Security.UserResponseDto;
import com.spring.boot.Dto.UserProfileResponseDto;
import com.spring.boot.Dto.UserTaskResponseDto;
import com.spring.boot.Enums.TaskStatus;
import com.spring.boot.Mapper.AdminMapper;
import com.spring.boot.Mapper.UserMapper;
import com.spring.boot.Model.Task;
import com.spring.boot.Model.User;
import com.spring.boot.Repo.TaskRepo;
import com.spring.boot.Repo.UserRepo;
import com.spring.boot.Service.ManagerService;
import com.spring.boot.ServiceHelper.CurrentUserService;
import com.spring.boot.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CurrentUserService currentUserService;
    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private ManagerService managerService;


    @Override
    public UserProfileResponseDto getProfile() {
        UserResponseDto userResponseDto = currentUserService.CurrentUser();
        User user = userRepo.findById(userResponseDto.getId())
                .orElseThrow(() -> new SystemException("assigned.user.not.found", HttpStatus.NOT_FOUND));//handle
        return UserMapper.USER_MAPPER.toUserProfileResponseDto(user);

    }

    @Override
    public List<UserTaskResponseDto> getMyTasks() {
        UserResponseDto userResponseDto = currentUserService.CurrentUser();
       List<Task>tasks = taskRepo.findAllTasksByAssignedTo_Id(userResponseDto.getId());
        return tasks.isEmpty()
                ? Collections.emptyList()
                : AdminMapper.AdminMapper.toUserTaskDtoList(tasks);

    }

    @Override
    public void updateTaskStatus(Long taskId, TaskStatus status) {
        UserResponseDto user = currentUserService.CurrentUser();
        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new SystemException("assigned.task.not.found", HttpStatus.NOT_FOUND));
        if (!task.getAssignedTo().getId().equals(user.getId())) {
            throw new SystemException("assigned.user.not.found", HttpStatus.NOT_FOUND);}
        task.setStatus(status);
        taskRepo.save(task);



    }
}
