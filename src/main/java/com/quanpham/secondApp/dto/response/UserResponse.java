package com.quanpham.secondApp.dto.response;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse implements Serializable {
    String id;
    String username;
//    String password;
    String firstName;
    String lastName;
    LocalDate dob;
    Set<RoleResponse> roles;
}
