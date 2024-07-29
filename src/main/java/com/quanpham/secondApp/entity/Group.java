package com.quanpham.secondApp.entity;


import com.quanpham.secondApp.enums.GroupEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "tbl_group")
public class Group extends AbstractEntity<Integer>{

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    GroupEnum name;

    @Column(name = "description")
    String description;

//    @OneToOne
//    Role role;

    @OneToMany(mappedBy = "group")
    Set<User> users = new HashSet<>();
}
