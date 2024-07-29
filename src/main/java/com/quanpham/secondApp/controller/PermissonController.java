package com.quanpham.secondApp.controller;

import com.quanpham.secondApp.service.PermissionService;
import com.quanpham.secondApp.dto.request.ApiResponse;
import com.quanpham.secondApp.dto.request.PermissionRequest;
import com.quanpham.secondApp.dto.response.PermissionResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissonController {
    PermissionService permissionService;

    @PostMapping
    ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest request){
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.create(request))
                .build();
    };

    @GetMapping
    ApiResponse<List<PermissionResponse>> getAll(){
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAll())
                .build();
    }

    @DeleteMapping("/permissionId")
    ApiResponse<Void> delete(@PathVariable int permission){
        permissionService.delete(permission);
        return ApiResponse.<Void>builder()
                .build();
    }
}
