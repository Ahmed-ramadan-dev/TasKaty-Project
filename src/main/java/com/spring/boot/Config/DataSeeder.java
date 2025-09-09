package com.spring.boot.Config;

import com.spring.boot.BundleMessage.Exception.SystemException;
import com.spring.boot.Enums.RoleName;
import com.spring.boot.Model.Security.Role;
import com.spring.boot.Model.User;
import com.spring.boot.Repo.RoleRepo;
import com.spring.boot.Repo.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;

    public DataSeeder(UserRepo userRepo, PasswordEncoder passwordEncoder, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;


    }

    @Override
    public void run(String... args) throws Exception {


        List<RoleName> roles = List.of(RoleName.ADMIN, RoleName.MANAGER, RoleName.USER);

        for (RoleName roleName : roles) {
            roleRepo.findByRoleName(roleName.name())
                    .orElseGet(() -> {
                        Role role = new Role();
                        role.setRoleName(roleName.name());
                        roleRepo.save(role);
                        System.out.println("✅ Role-" + roleName + " created successfully!");
                        return role;
                    });
        }

        List<User> defaultUsers = List.of(
                createUser("Ahmed", "Ramadan", "admin.ahmed@gmail.com", "123456", RoleName.ADMIN),
                createUser("Ahmed", "Ramadan", "manager.ahmed@gmail.com", "123456", RoleName.MANAGER),
                createUser("Ahmed", "Ramadan", "ahmed.ramadan.dev.2000@gmail.com", "123456", RoleName.USER)
        );

        for (User user : defaultUsers) {
            userRepo.findByEmail(user.getEmail())
                    .orElseGet(() -> {
                        Role role = roleRepo.findByRoleName(user.getRole().getRoleName())
                                .orElseThrow(() -> new SystemException("role.not.found", HttpStatus.NOT_FOUND));
                        user.setRole(role);
                        user.setPassword(passwordEncoder.encode(user.getPassword()));
                        userRepo.save(user);
                        System.out.println("✅ " + user.getRole().getRoleName() + " account created successfully!");
                        return user;
                    });
        }
    }


    private User createUser(String firstName, String lastName, String email, String password, RoleName roleName) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        Role role = new Role();
        role.setRoleName(roleName.name());
        user.setRole(role);
        return user;
    }




}
