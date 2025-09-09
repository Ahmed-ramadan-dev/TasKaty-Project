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
        name = "TokenUserDto",
        description = "DTO representing the user information contained in the authentication token"
)
public class TokenUserDto {

    @Schema(
            description = "Unique ID of the user",
            example = "101",
            requiredMode = Schema.RequiredMode.AUTO
    )
    private Long userId;

    @Schema(
            description = "Email of the user",
            example = "john.doe@example.com",
            requiredMode = Schema.RequiredMode.AUTO
    )
    private String email;

    @Schema(
            description = "Role assigned to the user",
            implementation = RoleDto.class,
            requiredMode = Schema.RequiredMode.AUTO
    )
    private RoleDto roleDto;
}
