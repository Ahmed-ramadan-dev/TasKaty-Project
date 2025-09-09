    package com.spring.boot.Service.Impl;

    import com.spring.boot.BundleMessage.Exception.SystemException;
    import com.spring.boot.Dto.*;
    import com.spring.boot.Enums.RoleName;
    import com.spring.boot.Mapper.AdminMapper;
    import com.spring.boot.Mapper.UserMapper;
    import com.spring.boot.Model.Security.Role;
    import com.spring.boot.Model.Task;
    import com.spring.boot.Model.User;
    import com.spring.boot.Repo.RoleRepo;
    import com.spring.boot.Repo.TaskRepo;
    import com.spring.boot.Repo.UserRepo;
    import com.spring.boot.Service.AdminServiceInterface;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    import java.util.Collections;
    import java.util.List;
    import java.util.Optional;

    @Service
    public class AdminServiceImplement implements AdminServiceInterface {
        @Autowired
        private UserRepo userRepo;
        @Autowired
        private PasswordEncoder passwordEncoder;
        @Autowired
        private TaskRepo taskRepo;
        @Autowired
        private RoleRepo roleRepo;

        // >>>>>>>>>>ADMIN API<<<<<<<<<<<<<<
        @Override
        @Transactional
        public void signUpAdmin(UserSignUpNormalDto userSignUpNormalDto) {
            if (userRepo.findByEmail(userSignUpNormalDto.getEmail()).isPresent()) {
                throw new SystemException("email.already.registered", HttpStatus.CONFLICT);
            }

            User user = UserMapper.USER_MAPPER.touser(userSignUpNormalDto);
            user.setPassword(passwordEncoder.encode(userSignUpNormalDto.getPassword()));

            Role role = roleRepo.findByRoleName(RoleName.ADMIN.name())
                    .orElseThrow(() -> new SystemException("role.not.found", HttpStatus.NOT_FOUND));
            user.setRole(role);

            userRepo.save(user);
        }



        @Override
        public List<AdminResponseDto> getAdmins() {
            List<User> admins = userRepo.findAllByRole_RoleName(RoleName.ADMIN.name());
            return admins.isEmpty()
                    ? Collections.emptyList()
                    : AdminMapper.AdminMapper.toAdminResponseDto(admins);

        }

        @Override
        public AdminResponseDto getAdminsByID(Long id) {
            User admin =  userRepo.findByIdAndRole_RoleName(id,RoleName.ADMIN.name())
                    .orElseThrow(() -> new SystemException("admin.not.found", HttpStatus.NOT_FOUND));
            return AdminMapper.AdminMapper.toAdminResponseDto(admin);
        }

        @Override
        @Transactional
        public void updateAdmin(Long id, AdminUpdateRequestDto dto, String roleName) {
            User admin = userRepo.findByIdAndRole_RoleName(id,roleName)
                    .orElseThrow(() -> new SystemException(roleName.toLowerCase()+".not.found", HttpStatus.NOT_FOUND));
            Optional.ofNullable(dto.getFirstName()).ifPresent(admin::setFirstName);
            Optional.ofNullable(dto.getLastName()).ifPresent(admin::setLastName);
            Optional.ofNullable(dto.getEmail()).ifPresent(admin::setEmail);
            Optional.ofNullable(dto.getPassword())
                    .ifPresent(password -> admin.setPassword(passwordEncoder.encode(password)));
            Optional.ofNullable(dto.getPhoneNumber()).ifPresent(admin::setPhoneNumber);
            Optional.ofNullable(dto.getAvatar()).ifPresent(admin::setAvatar);

            userRepo.save(admin);

        }

        @Override
        @Transactional
        public void deleteAdmin(Long id) {
            boolean exists = userRepo.existsByIdAndRole_RoleName(id, RoleName.ADMIN.name());

            if (!exists) {
                throw new SystemException("admin.not.found", HttpStatus.NOT_FOUND);
            }

            userRepo.deleteById(id);
        }


        // <<<<<<<<<<<<MANAGER API>>>>>>>>>>>>>
        @Transactional
        @Override
        public void signUpManager(UserSignUpNormalDto userSignUpNormalDto) {
            if (userRepo.findByEmail(userSignUpNormalDto.getEmail()).isPresent()) {
                throw new SystemException("email.already.registered", HttpStatus.CONFLICT);
            }

            User user = UserMapper.USER_MAPPER.touser(userSignUpNormalDto);
            user.setPassword(passwordEncoder.encode(userSignUpNormalDto.getPassword()));

            Role role = roleRepo.findByRoleName(RoleName.MANAGER.name())
                    .orElseThrow(() -> new SystemException("role.not.found", HttpStatus.NOT_FOUND));
            user.setRole(role);

            userRepo.save(user);
        }

        @Override
        public List<ManagerResponseDto> getManagers() {
            List<User> managers = userRepo.findAllByRole_RoleName(RoleName.MANAGER.name());
            return managers.isEmpty()
                    ? Collections.emptyList()
                    : AdminMapper.AdminMapper.toManagerResponseDtoList(managers);
        }

        @Override
        public ManagerResponseDto getManagerByID(Long id) {
            User manager = userRepo.findByIdAndRole_RoleName(id, RoleName.MANAGER.name())
                    .orElseThrow(() -> new SystemException("manager.not.found", HttpStatus.NOT_FOUND));
            return AdminMapper.AdminMapper.toManagerResponseDto(manager);

        }
        @Override
        @Transactional
        public void updateManager(Long id, AdminUpdateRequestDto dto) {
        updateAdmin(id,dto,RoleName.MANAGER.name());



        }

        @Override
        @Transactional
        public void deleteManager(Long id) {
            boolean exists = userRepo.existsByIdAndRole_RoleName(id, RoleName.MANAGER.name());

            if (!exists) {
                throw new SystemException("manager.not.found", HttpStatus.NOT_FOUND);
            }

            userRepo.deleteById(id);
        }

        @Override
        public List<ManagerWithUsersResponseDto> getManagersWithUsers() {
            List<User> managerAndUsers = userRepo.findAllByRole_RoleName(RoleName.MANAGER.name());
            return managerAndUsers.isEmpty()
                    ? Collections.emptyList()
                    : AdminMapper.AdminMapper.toManagerWithUsersDtoList(managerAndUsers);

        }




        @Override
        public List<UserTaskResponseDto> getTasksByUser(Long userId) {
            boolean userExists = userRepo.existsById(userId);

            if (!userExists) {
                throw new SystemException("user.not.found", HttpStatus.NOT_FOUND);
            }

            List<Task> userTasks = taskRepo.findAllTasksByAssignedTo_Id(userId);

            return userTasks.isEmpty()
                    ? Collections.emptyList()
                    : AdminMapper.AdminMapper.toUserTaskDtoList(userTasks);
        }


        @Override
        public ManagerWithUsersResponseDto getManagerWithUsersAndTasks(Long managerId) {

            User manager = userRepo.findByIdAndRole_RoleName(managerId, RoleName.MANAGER.name())
                    .orElseThrow(() -> new SystemException("manager.not.found", HttpStatus.NOT_FOUND));

            if (manager.getUserManger() == null || manager.getUserManger().isEmpty()) {
                throw new SystemException("manager.has.no.users", HttpStatus.NOT_FOUND);
            }

            return AdminMapper.AdminMapper.toManagerWithUsersDto(manager);
        }




    }





