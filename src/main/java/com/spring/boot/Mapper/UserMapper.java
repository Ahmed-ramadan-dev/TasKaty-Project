package com.spring.boot.Mapper;

import com.spring.boot.Dto.*;
import com.spring.boot.Dto.Security.TokenUserDto;
import com.spring.boot.Dto.Security.UserResponseDto;

import com.spring.boot.Model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;



@Mapper
public interface UserMapper {
    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);
    User touser(UserSignUpNormalDto userSignUpNormalDto);
    @Mapping(source = "role",target = "roleDto")
    TokenUserDto toTokenUserDto(User user);
    @Mapping(source = "role",target = "roleDto")
    UserResponseDto toUserResponseDto(User user);

    //



//
@Mapping(target = "userName", expression = "java(user.getFirstName() + \" \" + user.getLastName())")
@Mapping(source = "email", target = "email")
@Mapping(source = "phoneNumber", target = "phoneNumber")
@Mapping(source = "avatar", target = "avatar")
@Mapping(
        target = "managerName",
        expression = "java(user.getManager() != null ? user.getManager().getFirstName() + \" \" + user.getManager().getLastName() : null)"
)

    UserProfileResponseDto toUserProfileResponseDto(User user);


}
