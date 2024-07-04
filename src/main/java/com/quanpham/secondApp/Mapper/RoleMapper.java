package com.quanpham.secondApp.Mapper;

import com.quanpham.secondApp.Entity.Role;
import com.quanpham.secondApp.dto.request.RoleRequest;
import com.quanpham.secondApp.dto.response.RoleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)     // bo qua permissions
    Role toRole(RoleRequest request);
    RoleResponse toRoleResponse(Role role);
}
