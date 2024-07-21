package com.quanpham.secondApp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequest implements Serializable {

    @NotBlank(message = "username must be not null")
    String username;

    @NotBlank(message = "password must be not blank")
    String password;
}
