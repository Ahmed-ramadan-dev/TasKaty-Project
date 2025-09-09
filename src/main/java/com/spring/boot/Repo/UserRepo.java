package com.spring.boot.Repo;

import com.spring.boot.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    List<User> findAllByRole_RoleName(String roleRoleName);
    Optional<User> findByIdAndRole_RoleName(Long id, String roleRoleName);
    boolean existsByIdAndRole_RoleName(Long id, String roleName);
    Optional<User> findByEmail(String email);
    Optional<User>findById(long id);
    Optional<User> findByIdAndManager_Id(Long id, Long managerId);
    List<User> findAllByManager_Id(Long managerId);


}
