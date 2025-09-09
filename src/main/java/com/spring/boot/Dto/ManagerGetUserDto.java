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
        name = "ManagerGetUserDto",
        description = "DTO used for returning user details managed by a specific manager"
)
public class ManagerGetUserDto {

    @Schema(
            description = "Unique ID of the user",
            example = "101",
            requiredMode = Schema.RequiredMode.AUTO
    )
    private Long id;

    @Schema(
            description = "Full name of the user",
            example = "Ahmed Ramadan",
            requiredMode = Schema.RequiredMode.AUTO
    )
    private String userName;

    @Schema(
            description = "User's email address",
            example = "ahmed.ramadan@gmail.com",
            requiredMode = Schema.RequiredMode.AUTO
    )
    private String email;

    @Schema(
            description = "User's phone number (Egyptian format)",
            example = "01012345678",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String phoneNumber;

    @Schema(
            description = "User's profile picture URL",
            example = "https://example.com/avatars/user101.png",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String avatar;
}
