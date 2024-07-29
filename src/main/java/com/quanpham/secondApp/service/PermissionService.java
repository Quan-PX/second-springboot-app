package com.quanpham.secondApp.service;

import com.quanpham.secondApp.entity.Permission;
import com.quanpham.secondApp.mapper.PermissionMapper;
import com.quanpham.secondApp.repository.PermissionRepository;
import com.quanpham.secondApp.dto.request.PermissionRequest;
import com.quanpham.secondApp.dto.response.PermissionResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {

    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request){
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    };

    public List<PermissionResponse> getAll(){
        var permission = permissionRepository.findAll();
        return permission.stream().map(permissionMapper::toPermissionResponse).toList();
    };

    public void delete(int permission){
        permissionRepository.deleteById(permission);
    }
}
