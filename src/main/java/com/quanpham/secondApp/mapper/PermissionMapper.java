package com.quanpham.secondApp.mapper;


import com.quanpham.secondApp.entity.Permission;
import com.quanpham.secondApp.dto.request.PermissionRequest;
import com.quanpham.secondApp.dto.response.PermissionResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);

}
