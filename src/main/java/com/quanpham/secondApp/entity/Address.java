package com.quanpham.secondApp.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tbl_address")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Address extends AbstractEntity<Long>{

    @Column(name = "building")
    String buildingNumber;

    @Column(name = "street")
    String street;

    @Column(name = "city")
    String city;

    @Column(name = "country")
    String country;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "user_id")
//    User user;
}
