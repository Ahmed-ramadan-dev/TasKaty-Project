package com.spring.boot.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.boot.Enums.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
        name = "ManagerTaskDto",
        description = "DTO used by manager to create or update task"
)
public class MangerTaskDto {

    @Schema(
            description = "Task unique reference code",
            example = "TASK-2025-001"
    )
    @NotBlank(message = "Task.reference.notBlank")
    @Size(max = 50, message = "Task.reference.size")
    private String taskReference;

    @Schema(
            description = "Task title",
            example = "Develop Spring Boot REST API"
    )
    @NotBlank(message = "Task.title.notBlank")
    @Size(max = 100, message = "Task.title.size")
    private String title;

    @Schema(
            description = "Task description",
            example = "Create a Spring Boot REST API with authentication and role-based access control."
    )
    @NotBlank(message = "Task.description.notBlank")
    @Size(max = 500, message = "Task.description.size")
    private String description;

    @Schema(
            description = "Task status",
            example = "PENDING",
            allowableValues = {"PENDING", "IN_PROGRESS", "COMPLETED"}
    )
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)

    private TaskStatus status;

    @Schema(
            description = "Task assigned user id",
            example = "101"
    )
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)

    private Long assignedToId;
}
