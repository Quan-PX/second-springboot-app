package com.quanpham.secondApp.repository;

import com.quanpham.secondApp.entity.Permission;
import com.quanpham.secondApp.enums.PermissionEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    Optional<Permission> findByName(String permission);
}
