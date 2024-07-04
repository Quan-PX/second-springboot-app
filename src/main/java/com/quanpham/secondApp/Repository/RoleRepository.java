package com.quanpham.secondApp.Repository;

import com.quanpham.secondApp.Entity.Permission;
import com.quanpham.secondApp.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
}
