package com.quanpham.secondApp.Service;

import com.quanpham.secondApp.Entity.Permission;
import com.quanpham.secondApp.Entity.Role;
import com.quanpham.secondApp.Mapper.PermissionMapper;
import com.quanpham.secondApp.Mapper.RoleMapper;
import com.quanpham.secondApp.Repository.PermissionRepository;
import com.quanpham.secondApp.Repository.RoleRepository;
import com.quanpham.secondApp.dto.request.PermissionRequest;
import com.quanpham.secondApp.dto.request.RoleRequest;
import com.quanpham.secondApp.dto.response.PermissionResponse;
import com.quanpham.secondApp.dto.response.RoleResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {

    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleResponse create(RoleRequest request){
        var role = roleMapper.toRole(request);
        var permissions = permissionRepository.findAllById(request.getPermissions());

        role.setPermissions(new HashSet<>(permissions));

        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    };

    public List<RoleResponse> getAll(){
        var role = roleRepository.findAll();
        return role.stream().map(roleMapper::toRoleResponse).toList();
    };

    public void delete(String role){
        roleRepository.deleteById(role);
    }
}
