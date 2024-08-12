package com.quanpham.secondApp.entity;

import com.quanpham.secondApp.enums.Gender;
import com.quanpham.secondApp.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "tbl_user")
public class User extends AbstractEntity<Long> implements UserDetails {

    @Column(name = "username")
    String username;

    @Column(name = "password")
    String password;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "email")
    String email;

    @Column(name = "phone")
    String phone;

    @Column(name = "date_of_birth")
//    @Temporal(TemporalType.DATE)    // chi nhan kieu ngay, ko nhan gio phut giay
    LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
//    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "status")
    UserStatus status = UserStatus.ACTIVE;

    @Enumerated(EnumType.STRING)    // enum nay kieu string se duoc add vao column trong kieu table user
//    @JdbcTypeCode(SqlTypes.NAMED_ENUM)  // chuyen doi du lieu tu enum sang column
    @Column(name = "gender")
    Gender gender = Gender.NONE;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")   // the hien moi quan he voi user, user co quan he 1 nhieu voi address
//    Set<Address> addresses = new HashSet<>();

     @ManyToMany(fetch = FetchType.EAGER)
     @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
     Set<Role> roles;

     @ManyToOne
     @JoinColumn(name = "group_id", nullable=true)     //, nullable=false
     Group group;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;              // UserStatus.ACTIVE.equals(status);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
