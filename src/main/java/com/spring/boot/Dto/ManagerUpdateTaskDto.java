package com.spring.boot.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
        name = "ManagerUpdateTaskDto",
        description = "DTO used by manager to update task"
)
public class ManagerUpdateTaskDto {

    @Schema(
            description = "Task unique reference code",
            example = "TASK-2025-001",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @Size(max = 50, message = "Task.reference.size")
    private String taskReference;

    @Schema(
            description = "Task title",
            example = "Finalize project documentation",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @Size(max = 100, message = "Task.title.size")
    private String title;

    @Schema(
            description = "Task description",
            example = "Prepare the final project documentation and upload it to the portal before the deadline.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @Size(max = 500, message = "Task.description.size")
    private String description;
}
