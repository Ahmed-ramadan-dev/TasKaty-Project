package com.spring.boot.Dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "UserMangerDto",
        description = "DTO used to return manager's users and their tasks"
)
public class UserMangerDto {

    @Schema(
            description = "Unique ID of the user",
            example = "101"
    )
    private Long id;

    @Schema(
            description = "Full name of the user",
            example = "Ahmed Ramadan"
    )
    private String userName;

    @Schema(
            description = "Email address of the user",
            example = "ahmed.ramadan@gmail.com"
    )
    private String email;

    @Schema(
            description = "Total number of tasks assigned to the user",
            example = "5"
    )
    private int taskCount;

    @ArraySchema(
            schema = @Schema(implementation = UserTaskResponseDto.class),
            arraySchema = @Schema(description = "List of tasks assigned to this user")
    )
    private List<UserTaskResponseDto> userTaskResponseDto;
}

