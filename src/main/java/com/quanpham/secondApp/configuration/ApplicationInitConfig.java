package com.quanpham.secondApp.configuration;

import com.quanpham.secondApp.entity.Group;
import com.quanpham.secondApp.entity.Permission;
import com.quanpham.secondApp.entity.Role;
import com.quanpham.secondApp.entity.User;
import com.quanpham.secondApp.enums.GroupEnum;
import com.quanpham.secondApp.enums.PermissionEnum;
import com.quanpham.secondApp.enums.RoleEnum;
import com.quanpham.secondApp.repository.GroupRepository;
import com.quanpham.secondApp.repository.PermissionRepository;
import com.quanpham.secondApp.repository.RoleRepository;
import com.quanpham.secondApp.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    // khi ung dung chay no se tao 1 user Admin
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, PermissionRepository permissionRepository, RoleRepository roleRepository, GroupRepository groupRepository){

        return args -> {
            if(userRepository.findByUsername("admin").isEmpty()){

                if(roleRepository.findByName(RoleEnum.ADMIN.name()).isEmpty() && roleRepository.findByName(RoleEnum.USER.name()).isEmpty()){

                    if(true) {          //permissionRepository.findByName(PermissionEnum.FULLACCESS.name()).isEmpty()

                        Permission fullAccess = Permission.builder()
                                .name(PermissionEnum.FULLACCESS.name())
                                .description("Full access with admin role")
                                .build();
                        permissionRepository.save(fullAccess);

                        Role adminRole = Role.builder()
                                .name(RoleEnum.ADMIN.name())
                                .description("Admin Role")
                                .permissions(new HashSet<>(List.of(fullAccess)))
                                .build();

                        Role userRole = Role.builder()
                                .name(RoleEnum.USER.name())
                                .description("User Role")
                                .build();

                        log.info("oke-1");


                        List<Role> listRole = new ArrayList<>();
                        listRole.add(adminRole);
                        listRole.add(userRole);
                        roleRepository.saveAll(listRole);

                        log.info("oke-2");


                        Group topGroup = new Group();
                        topGroup.setName(GroupEnum.TOP);
                        topGroup.setDescription("high permission");
                        groupRepository.save(topGroup);
                        log.info("oke-3");


                        User user = User.builder()
                                .username("admin")
                                .password(passwordEncoder.encode("admin"))
                                .firstName("quan")
                                .lastName("pham")
                                .email("quanpham@gmail.com")
                                .phone("0987654321")
                                .group(topGroup)
                                .roles(new HashSet<>(Collections.singletonList(adminRole)))
                                .build();
                        userRepository.save(user);
                    }
            }

            };
        };
    }
}
