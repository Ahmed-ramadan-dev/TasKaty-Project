package com.spring.boot.Dto.Security;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "UserResponseDto",
        description = "DTO representing user response"
)
public class UserResponseDto {

    @Schema(
            description = "User unique identifier",
            example = "101",
            requiredMode = Schema.RequiredMode.AUTO
    )
    private Long id;

    @Schema(
            description = "User registered email",
            example = "ahmed123@gmail.com",
            requiredMode = Schema.RequiredMode.AUTO
    )
    private String email;

    @Schema(
            description = "User encrypted password",
            example = "$2a$10$7w3z...encrypted_password",
            requiredMode = Schema.RequiredMode.AUTO
    )
    private String password;

    @Schema(
            description = "User assigned role details",
            implementation = RoleDto.class,
            requiredMode = Schema.RequiredMode.AUTO
    )
    private RoleDto roleDto;
}
