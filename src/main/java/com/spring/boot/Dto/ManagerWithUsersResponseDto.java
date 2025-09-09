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
        name = "ManagerWithUsersResponseDto",
        description = "Response DTO containing manager details along with their assigned users"
)
public class ManagerWithUsersResponseDto {

    @Schema(
            description = "Unique ID of the manager",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Full name of the manager",
            example = "Ahmed Ramadan"
    )
    private String mangerName;

    @Schema(
            description = "Email address of the manager",
            example = "ahmed.ramadan@gmail.com"
    )
    private String email;

    @Schema(
            description = "Total number of users assigned to the manager",
            example = "5"
    )
    private int UserCount;

    @ArraySchema(
            schema = @Schema(implementation = UserMangerDto.class),
            arraySchema = @Schema(description = "List of users assigned to the manager")
    )
    private List<UserMangerDto> userManger;
}
