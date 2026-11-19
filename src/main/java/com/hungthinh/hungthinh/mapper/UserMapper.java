package com.hungthinh.hungthinh.mapper;

import com.hungthinh.hungthinh.models.dtos.user.UserGetsResponse;
import com.hungthinh.hungthinh.models.entities.bases.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(
            target = "role",
            expression = "java(user.getRole().name())"
    )
    UserGetsResponse toResponse(User user);

    List<UserGetsResponse> toResponseList(List<User> users);
}
