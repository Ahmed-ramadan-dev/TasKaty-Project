package com.spring.boot.Mapper;

import com.spring.boot.Dto.ManagerGetUserDto;
import com.spring.boot.Dto.MangerTaskDto;
import com.spring.boot.Model.Task;
import com.spring.boot.Model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ManagerMapper {
    ManagerMapper MANAGER_MAPPER = Mappers.getMapper(ManagerMapper.class);
    @Mapping(source = "taskReference", target = "taskReference")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "assignedToId", target = "assignedTo.id")
    Task toTask(MangerTaskDto mangerTaskDto);
    @Mapping(source = "taskReference", target = "taskReference")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "status", target = "status")
    @Mapping(target = "assignedToId", expression = "java(task.getAssignedTo() != null ? task.getAssignedTo().getId() : null)")
    MangerTaskDto  toMangerTaskDto(Task task);
    List<MangerTaskDto> toTaskDtoList(List<Task> tasks);

    @Mapping(target = "userName", expression = "java(user.getFirstName() + \" \" + user.getLastName())")
    ManagerGetUserDto toManagerGetUserDto(User user);
    @Mapping(target = "userName", expression = "java(user.getFirstName() + \" \" + user.getLastName())")
    List<ManagerGetUserDto> toManagerGetUserDtoList(List<User> users);

}
