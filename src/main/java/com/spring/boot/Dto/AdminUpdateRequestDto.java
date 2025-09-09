package com.spring.boot.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
        name = "AdminUpdateRequestDto",
        description = "DTO used for updating admin profile"
)
public class AdminUpdateRequestDto {

    @Schema(
            description = "Admin first name",
            example = "Ahmed",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @Size(min = 2, max = 50, message = "Admin.first.name.size")
    private String firstName;

    @Schema(
            description = "Admin last name",
            example = "Ramadan",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @Size(min = 2, max = 50, message = "Admin.last.name.size")
    private String lastName;

    @Schema(
            description = "Admin gmail address",
            example = "admin@gmail.com",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$",
            message = "Admin.email.invalid"
    )
    private String email;

    @Schema(
            description = "Admin password",
            example = "Admin@123",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @Size(min = 6, max = 30, message = "Admin.password.size")
    private String password;

    @Schema(
            description = "Admin phone number",
            example = "01012345678",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @Pattern(
            regexp = "^(01[0-9]{9})$",
            message = "Admin.phoneNumber.invalid"
    )
    private String phoneNumber;

    @Schema(
            description = "Admin avatar url",
            example = "https://example.com/avatar.png",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String avatar;
}
