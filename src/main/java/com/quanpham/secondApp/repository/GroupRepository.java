package com.quanpham.secondApp.repository;

import com.quanpham.secondApp.entity.Group;
import com.quanpham.secondApp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
    Optional<Group> findByName(String group);

}
