package com.quanpham.secondApp.repository;

import com.quanpham.secondApp.entity.Role;
import com.quanpham.secondApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String username);

//    @Query(value = "select r from Role r inner join UserRole ur on r.id = ur.user.id where ur.user.id=: userId")
//    List<Role> getAllByUserId(Integer userId);
}
