package com.quanpham.secondApp.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponse implements Serializable {
    String accessToken;
    String refreshToken;
    String userId;
    boolean authenticated;
}
