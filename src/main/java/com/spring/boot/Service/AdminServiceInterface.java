package com.spring.boot.Service;

import com.spring.boot.Dto.*;

import java.util.List;

public interface AdminServiceInterface {
    void signUpAdmin( UserSignUpNormalDto userSignUpNormalDto);
    List<AdminResponseDto> getAdmins();
    AdminResponseDto  getAdminsByID( Long id);
    void updateAdmin( Long id, AdminUpdateRequestDto dto,String roleName);
    void deleteAdmin( Long id);
    void signUpManager( UserSignUpNormalDto userSignUpNormalDto);
    List<ManagerResponseDto> getManagers();
    ManagerResponseDto getManagerByID( Long id);
    void updateManager(  Long id,  AdminUpdateRequestDto dto);
    void deleteManager( Long id);
    List<ManagerWithUsersResponseDto> getManagersWithUsers();
    List<UserTaskResponseDto> getTasksByUser(Long userId);
    ManagerWithUsersResponseDto getManagerWithUsersAndTasks(Long managerId);

}
