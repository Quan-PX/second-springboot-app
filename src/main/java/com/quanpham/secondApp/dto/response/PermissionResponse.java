package com.quanpham.secondApp.dto.response;

import com.quanpham.secondApp.dto.request.PermissionRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionResponse implements Serializable {
    String name;
    String description;
}
