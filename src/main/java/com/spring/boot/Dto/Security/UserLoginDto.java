package com.spring.boot.Dto.Security;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "UserLoginDto",
        description = "DTO used for user login"
)
public class UserLoginDto {

    @Schema(
            description = "User Gmail address for login",
            example = "ahmed123@gmail.com",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Email.is.required")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$",
            message = "Email.must.be.valid.Gmail"
    )
    private String email;

    @Schema(
            description = "User password for login",
            example = "MyStrongPass123",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Password.is.required")
    @Size(
            min = 6,
            max = 30,
            message = "Password.must.be.between.6.and.30.characters"
    )
    private String password;
}
