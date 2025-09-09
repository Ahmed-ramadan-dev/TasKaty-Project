package com.spring.boot.Mapper;

import com.spring.boot.Dto.*;
import com.spring.boot.Model.Task;
import com.spring.boot.Model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AdminMapper {
    AdminMapper AdminMapper = Mappers.getMapper(AdminMapper.class);
    List<AdminResponseDto> toAdminResponseDto(List<User> users);
    AdminResponseDto toAdminResponseDto(User user);
    List<ManagerResponseDto>toManagerResponseDtoList(List<User> users);
    ManagerResponseDto toManagerResponseDto(User user);
//
@Mapping(source = "id", target = "id")
@Mapping(source = "taskReference", target = "taskReference")
@Mapping(source = "title", target = "title")
@Mapping(source = "status", target = "status")
@Mapping(source = "createdAt", target = "createdAt")
@Mapping(source = "updatedAt", target = "updatedAt")
UserTaskResponseDto toUserTaskDto(Task task);
    List<UserTaskResponseDto> toUserTaskDtoList(List<Task> tasks);
    //
    @Mapping(target = "userName", expression = "java(user.getFirstName() + \" \" + user.getLastName())")
    @Mapping(source = "email", target = "email")
    @Mapping(target = "taskCount", expression = "java(user.getTasks() != null ? user.getTasks().size() : 0)")
    @Mapping(target = "userTaskResponseDto", expression = "java(toUserTaskDtoList(user.getTasks()))")
    UserMangerDto toUserMangerDto(User user);

    List<UserMangerDto> toUserMangerDtoList(List<User> users);
    //
    @Mapping(source = "id", target = "id")
    @Mapping(target = "mangerName", expression = "java(manager.getFirstName() + \" \" + manager.getLastName())")
    @Mapping(source = "email", target = "email")
    @Mapping(target = "userCount", expression = "java(manager.getUserManger().size())")
    @Mapping(target = "userManger", expression = "java(toUserMangerDtoList(manager.getUserManger()))")
    ManagerWithUsersResponseDto toManagerWithUsersDto(User manager);
    List<ManagerWithUsersResponseDto> toManagerWithUsersDtoList(List<User> managers);
//

}
