package com.spring.boot.Dto;

import com.spring.boot.Enums.TaskStatus;
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
        name = "UserTaskResponseDto",
        description = "DTO used to return task details assigned to a user."
)
public class UserTaskResponseDto {

    @Schema(
            description = "Unique identifier of the task",
            example = "101"
    )
    private Long id;

    @Schema(
            description = "Unique reference code for the task",
            example = "TASK-2025-001"
    )
    private String taskReference;

    @Schema(
            description = "Title or name of the task",
            example = "Prepare Monthly Sales Report"
    )
    private String title;

    @Schema(
            description = "Current status of the task",
            example = "IN_PROGRESS"
    )
    private TaskStatus status;

    @Schema(
            description = "Date and time when the task was created",
            example = "2025-09-07T10:30:00"
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "Date and time when the task was last updated",
            example = "2025-09-07T15:45:00"
    )
    private LocalDateTime updatedAt;
}
