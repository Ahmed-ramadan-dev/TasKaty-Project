package com.spring.boot.Dto.Security;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "RoleDto", description = "DTO used for creating or updating user roles")
public class RoleDto {
    @Schema(
            description = "The name of the role assigned to the user",
            example = "ADMIN",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotBlank(message = "Role.is.required")
    private String roleName;
}
