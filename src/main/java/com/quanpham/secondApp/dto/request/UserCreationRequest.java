package com.quanpham.secondApp.dto.request;

import com.quanpham.secondApp.Validator.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest implements Serializable {

    @Size(min = 8, message = "USERNAME_INVALID")
     String username;

    @Size(min = 8, message = "PASSWORD_INVALID")
     String password;

     String firstName;

     String lastName;

     @DobConstraint(min = 18, message = "INVALID_DOB")
     LocalDate dob;
}
