package com.spring.boot.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "ManagerResponseDto",
        description = "DTO used for returning manager details"
)
public class ManagerResponseDto {

    @Schema(
            description = "Unique ID of the manager",
            example = "10",
            requiredMode = Schema.RequiredMode.AUTO
    )
    private Long id;

    @Schema(
            description = "Manager's first name",
            example = "Ahmed",
            requiredMode = Schema.RequiredMode.AUTO
    )
    private String firstName;

    @Schema(
            description = "Manager's last name",
            example = "Ramadan",
            requiredMode = Schema.RequiredMode.AUTO
    )
    private String lastName;

    @Schema(
            description = "Manager's email address",
            example = "ahmed.ramadan@gmail.com",
            requiredMode = Schema.RequiredMode.AUTO
    )
    private String email;

    @Schema(
            description = "Manager's phone number (Egyptian format)",
            example = "01012345678",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String phoneNumber;

    @Schema(
            description = "Manager's profile picture URL",
            example = "https://example.com/avatars/manager10.png",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String avatar;

    @Schema(
            description = "Date and time when the manager account was created",
            example = "2025-09-07T15:30:00",
            requiredMode = Schema.RequiredMode.AUTO
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "Date and time when the manager account was last updated",
            example = "2025-09-07T18:45:00",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private LocalDateTime updatedAt;
}
