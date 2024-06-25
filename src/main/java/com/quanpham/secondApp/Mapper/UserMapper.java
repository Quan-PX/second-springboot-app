package com.quanpham.secondApp.Mapper;

import com.quanpham.secondApp.Entity.User;
import com.quanpham.secondApp.dto.request.UserCreationRequest;
import com.quanpham.secondApp.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    void updateUser(@MappingTarget User user, UserCreationRequest request);
    UserResponse toUserResponse(User user);
    // @Mapping(source = "", target="") // map voi cac fields khac nhau tu source thanh target
    // @Mapping(target="", ignore=true) // map voi cac fields nhung tru fiels nay`
}
