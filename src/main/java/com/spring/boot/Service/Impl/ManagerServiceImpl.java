package com.spring.boot.Service.Impl;

import com.spring.boot.BundleMessage.Exception.SystemException;
import com.spring.boot.Dto.*;
import com.spring.boot.Dto.Security.UserResponseDto;
import com.spring.boot.Enums.RoleName;
import com.spring.boot.Enums.TaskStatus;
import com.spring.boot.Mapper.ManagerMapper;
import com.spring.boot.Mapper.UserMapper;
import com.spring.boot.Model.Security.Role;
import com.spring.boot.Model.Task;
import com.spring.boot.Model.User;
import com.spring.boot.Repo.RoleRepo;
import com.spring.boot.Repo.TaskRepo;
import com.spring.boot.Repo.UserRepo;
import com.spring.boot.Service.AdminServiceInterface;
import com.spring.boot.ServiceHelper.CurrentUserService;
import com.spring.boot.Service.ManagerService;
import com.spring.boot.ServiceHelper.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collections;
import java.util.List;


@Service
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CurrentUserService currentUserService;
    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private AdminServiceInterface adminService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private EmailService emailService;

    @Transactional
    @Override
    public void signUpUser(UserSignUpNormalDto userSignUpNormalDto) {
        if (userRepo.findByEmail(userSignUpNormalDto.getEmail()).isPresent()) {
            throw new SystemException("email.already.registered", HttpStatus.CONFLICT);
        }

        User user = UserMapper.USER_MAPPER.touser(userSignUpNormalDto);
        user.setPassword(passwordEncoder.encode(userSignUpNormalDto.getPassword()));

        Role role = roleRepo.findByRoleName(RoleName.USER.name())
                .orElseThrow(() -> new SystemException("role.not.found", HttpStatus.NOT_FOUND));
        user.setRole(role);
        UserResponseDto userResponseDto = currentUserService.CurrentUser();
       User manager = userRepo.findById(userResponseDto.getId())
               .orElseThrow(() -> new SystemException("manager.not.found", HttpStatus.NOT_FOUND));
        user.setManager(manager);

        userRepo.save(user);
    }

    @Override
    @Transactional
    public void createTask(Long id, MangerTaskDto mangerTaskDto) {
        UserResponseDto manager = currentUserService.CurrentUser();

        User user = userRepo.findByIdAndManager_Id(id, manager.getId())
        .orElseThrow(() -> new SystemException("assigned.user.not.found", HttpStatus.NOT_FOUND));
        Task task = ManagerMapper.MANAGER_MAPPER.toTask(mangerTaskDto);
        task.setStatus(TaskStatus.PENDING);
        task.setAssignedTo(user);

        taskRepo.save(task);
        emailService.sendTaskNotification(
                user.getEmail(),              // ايميل المستلم
                task.getTitle(),              // عنوان التاسك
                task.getDescription(),        // وصف التاسك
                manager.getEmail());         // اسم المدير


    }

    @Override
    @Transactional
    public void updateTask(Long taskId, ManagerUpdateTaskDto managerUpdateTaskDto) {
        UserResponseDto manager = currentUserService.CurrentUser();


        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new SystemException("assigned.task.not.found", HttpStatus.NOT_FOUND));
        if (!task.getAssignedTo().getManager().getId().equals(manager.getId())) {
            throw new SystemException("task.update.unauthorized", HttpStatus.FORBIDDEN);
        }

        if (managerUpdateTaskDto.getTitle() != null && !managerUpdateTaskDto.getTitle().isBlank()) {
            task.setTitle(managerUpdateTaskDto.getTitle());
        }

        if (managerUpdateTaskDto.getDescription() != null && !managerUpdateTaskDto.getDescription().isBlank()) {
            task.setDescription(managerUpdateTaskDto.getDescription());
        }

        taskRepo.save(task);


    }

    @Override
    public List<ManagerGetUserDto> getUsersByManager() {
        UserResponseDto manager = currentUserService.CurrentUser();
        List<User> users =  userRepo.findAllByManager_Id(manager.getId());
        return users.isEmpty()
                ? Collections.emptyList()
                : ManagerMapper.MANAGER_MAPPER.toManagerGetUserDtoList(users);


    }

    @Override
    public ManagerGetUserDto getUserById(Long id) {
        UserResponseDto manager = currentUserService.CurrentUser();
        User user = userRepo.findByIdAndManager_Id(id, manager.getId())
                .orElseThrow(() -> new SystemException("assigned.user.not.found", HttpStatus.NOT_FOUND));
        return ManagerMapper.MANAGER_MAPPER.toManagerGetUserDto(user);


    }

    @Override
    @Transactional
    public void updateUser(Long id, AdminUpdateRequestDto dto) {
        adminService.updateAdmin(id,dto, RoleName.USER.name());
    }


    @Override
    public List<MangerTaskDto> getUserTasks(Long userId) {
        UserResponseDto manager = currentUserService.CurrentUser();
        User user  = userRepo.findByIdAndManager_Id(userId,manager.getId())
                .orElseThrow(() -> new SystemException("assigned.user.not.found", HttpStatus.NOT_FOUND));
        List<Task> tasks = taskRepo.findAllTasksByAssignedTo_IdAndAssignedTo_Manager_Id(userId, manager.getId());
        return tasks.isEmpty()
                ? Collections.emptyList()
                : ManagerMapper.MANAGER_MAPPER.toTaskDtoList(tasks);


    }

    @Override
    @Transactional
    public void updateTaskStatus(Long taskId, TaskStatus status) {
        UserResponseDto manager = currentUserService.CurrentUser();
        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new SystemException("assigned.task.not.found", HttpStatus.NOT_FOUND));
        if (!task.getAssignedTo().getManager().getId().equals(manager.getId())) {
            throw new SystemException("assigned.user.not.found", HttpStatus.NOT_FOUND);}
            task.setStatus(status);
            taskRepo.save(task);



    }

    @Override
    @Transactional
    public void deleteTask(Long userId, Long taskId) {
        UserResponseDto manager = currentUserService.CurrentUser();

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new SystemException("assigned.user.not.found", HttpStatus.NOT_FOUND));//handle
        if (!user.getManager().getId().equals(manager.getId())) {
            throw new SystemException("assigned.user.not.found", HttpStatus.NOT_FOUND);}//handle

        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new SystemException("assigned.task.not.found", HttpStatus.NOT_FOUND));//handle
        if (!task.getAssignedTo().getId().equals(userId)) {
            throw new SystemException("assigned.task.not.found", HttpStatus.NOT_FOUND);}//handle
        taskRepo.delete(task);
         }


}

