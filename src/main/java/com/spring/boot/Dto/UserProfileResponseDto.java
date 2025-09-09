package com.spring.boot.Dto;

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
        name = "UserProfileResponseDto",
        description = "DTO used to return the profile information of the currently logged in user"
)
public class UserProfileResponseDto {

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
            description = "Phone number of the user",
            example = "01012345678"
    )
    private String phoneNumber;

    @Schema(
            description = "URL of the user profile avatar",
            example = "https://cdn.example.com/avatars/ahmed.png"
    )
    private String avatar;

    @Schema(
            description = "Full name of the user manager",
            example = "Mohamed Ali"
    )
    private String managerName;
}
