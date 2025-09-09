package com.spring.boot.Repo;

import com.spring.boot.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {
  Optional<Task> findTaskById(Long id);
    List<Task> findAllTasksByAssignedTo_Id(Long assignedToId);
 List<Task> findAllTasksByAssignedTo_IdAndAssignedTo_Manager_Id(Long assignedToId, Long assignedToManagerId);

}
