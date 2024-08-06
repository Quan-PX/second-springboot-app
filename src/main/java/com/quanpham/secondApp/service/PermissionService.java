package com.quanpham.secondApp.service;

import com.quanpham.secondApp.entity.Permission;
import com.quanpham.secondApp.enums.PermissionEnum;
import com.quanpham.secondApp.exception.AppException;
import com.quanpham.secondApp.exception.ErrorCode;
import com.quanpham.secondApp.mapper.PermissionMapper;
import com.quanpham.secondApp.repository.PermissionRepository;
import com.quanpham.secondApp.dto.request.PermissionRequest;
import com.quanpham.secondApp.dto.response.PermissionResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {

    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request){
        Permission permission = permissionMapper.toPermission(request);
        var permissionName = request.getName().toUpperCase();
        if(Objects.equals(permissionName, PermissionEnum.valueOf(request.getName().toUpperCase()).toString())){
            permission.setName(PermissionEnum.valueOf(request.getName().toUpperCase()).toString());
        } else {
            throw new AppException(ErrorCode.INVALID_ENUM_TYPE);
        }
        permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    };

    public List<PermissionResponse> getAll(){
        var permission = permissionRepository.findAll();
        permission.forEach(ele -> {
            String name = ele.getName();
            ele.setName(PermissionEnum.valueOf(name).toString());
        });
        return permission.stream().map(permissionMapper::toPermissionResponse).toList();
    };

    public void delete(int permission){
        permissionRepository.deleteById(permission);
    }

    public PermissionResponse getPermission(int id){
        Permission permission = permissionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        permission.setName(PermissionEnum.valueOf(permission.getName()).toString());
        return permissionMapper.toPermissionResponse(permission);
    }

}
