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
        name = "AdminResponseDto",
        description = "DTO representing the response details of an Admin"
)
public class AdminResponseDto {
    @Schema(
            description = "Unique identifier of the admin",
            example = "1",
            requiredMode = Schema.RequiredMode.AUTO
    )
    private Long id;
    @Schema(
            description = "Admin's first name",
            example = "Ahmed",
            requiredMode = Schema.RequiredMode.AUTO
    )
    private String firstName;
    @Schema(
            description = "Admin's last name",
            example = "Ramadan",
            requiredMode = Schema.RequiredMode.AUTO
    )
    private String lastName;
    @Schema(
            description = "Admin's email address",
            example = "admin@gmail.com",
            requiredMode = Schema.RequiredMode.AUTO
    )
    private String email;

    @Schema(
            description = "Admin's phone number",
            example = "+201234567890",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String phoneNumber;
    @Schema(
            description = "Admin's profile picture URL",
            example = "https://example.com/avatar.png",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String avatar;
    @Schema(
            description = "Timestamp when the admin account was created",
            example = "2025-09-07T10:15:30",
            requiredMode = Schema.RequiredMode.AUTO
    )
    private LocalDateTime createdAt;
    @Schema(
            description = "Timestamp when the admin account was last updated",
            example = "2025-09-07T14:45:00",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private LocalDateTime updatedAt;
}
