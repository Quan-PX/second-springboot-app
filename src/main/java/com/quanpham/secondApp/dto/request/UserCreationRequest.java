package com.quanpham.secondApp.dto.request;

import com.quanpham.secondApp.validator.DobConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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

    @NotBlank(message = "First name must not be left blank")
    String firstName;

    @NotBlank(message = "Last name must not be left blank")
    String lastName;

    @NotBlank(message = "Email must not be left blank")
    @Email(message = "Email should be valid")
    String email;

    @Pattern(regexp = "^(?:[0-9] ?){6,14}[0-9]$", message = "Phone number should be valid")
    String phone;

    @Pattern(regexp = "^(male|female|none)$", message = "Gender must be either male or female")
    String gender;

//    @Pattern(regexp = "^(active|inactive|none)$", message = "Status must be active, inactive, or none")
//    private String status;

     @DobConstraint(min = 18, message = "INVALID_DOB")
     LocalDate dateOfBirth;
}
