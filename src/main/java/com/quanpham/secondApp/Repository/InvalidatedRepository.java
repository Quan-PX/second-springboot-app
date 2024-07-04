package com.quanpham.secondApp.Repository;

import com.quanpham.secondApp.Entity.InvalidatedToken;
import com.quanpham.secondApp.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidatedRepository extends JpaRepository<InvalidatedToken, String> {
}
