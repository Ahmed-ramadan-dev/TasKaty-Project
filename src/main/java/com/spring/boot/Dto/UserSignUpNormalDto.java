package com.spring.boot.Dto;

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
        name = "UserSignUpNormalDto",
        description = "DTO used for normal user registration"
)
public class UserSignUpNormalDto {

    @Schema(
            description = "User first name",
            example = "Ahmed"
    )
    @NotBlank(message = "User.firstName.notBlank")
    @Size(min = 2, max = 50, message = "User.firstName.size")
    private String firstName;

    @Schema(
            description = "User last name",
            example = "Ramadan"
    )
    @NotBlank(message = "User.lastName.notBlank")
    @Size(min = 2, max = 50, message = "User.lastName.size")
    private String lastName;

    @Schema(
            description = "User email address",
            example = "ahmed.ramadan@gmail.com"
    )
    @NotBlank(message = "User.email.notBlank")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$",
            message = "User.email.invalid"
    )
    private String email;

    @Schema(
            description = "User password",
            example = "Ahmed@12345"
    )
    @NotBlank(message = "User.password.notBlank")
    @Size(min = 6, max = 30, message = "User.password.size")
    private String password;

    @Schema(
            description = "User phone number",
            example = "01012345678"
    )
    @NotBlank(message = "User.phoneNumber.notBlank")
    @Pattern(
            regexp = "^(01[0-9]{9})$",
            message = "User.phoneNumber.invalid"
    )
    private String phoneNumber;

    @Schema(
            description = "User avatar url",
            example = "https://cdn.example.com/avatars/ahmed.png"
    )
    @NotBlank(message = "User.avatar.notBlank")
    private String avatar;
}
