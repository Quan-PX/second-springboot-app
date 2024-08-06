package com.quanpham.secondApp.mapper;


import com.quanpham.secondApp.entity.Permission;
import com.quanpham.secondApp.dto.request.PermissionRequest;
import com.quanpham.secondApp.dto.response.PermissionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
//    @Mapping(target="name", ignore=true)
    Permission toPermission(PermissionRequest request);

//    @Mapping(target="name", ignore=true)
    PermissionResponse toPermissionResponse(Permission permission);
}
