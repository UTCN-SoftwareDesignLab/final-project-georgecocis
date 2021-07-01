package com.example.demo.user.mapper;

import com.example.demo.user.model.User;
import com.example.demo.user.model.dto.UserListDTO;
import com.example.demo.user.model.dto.UserMinimalDTO;
import org.mapstruct.*;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mappings({
            @Mapping(target = "name", source = "user.username")
    })
    UserMinimalDTO userMinimalFromUser(User user);

    @Mappings({
            @Mapping(target = "name", source = "user.username"),
            @Mapping(target = "password", source = "user.password"),
            @Mapping(target = "roles", ignore = true)
    })
    UserListDTO userListDtoFromUser(User user);

    @Mappings({
            @Mapping(target = "username", source = "userListDTO.name"),
            @Mapping(target = "password", source = "userListDTO.password"),
            @Mapping(target = "roles", ignore = true)
    })
    User userFromUserListDto(UserListDTO userListDTO);

    @AfterMapping
    default void populateRoles(User user, @MappingTarget UserListDTO userListDTO) {
        userListDTO.setRoles(user.getRoles().stream().map(role -> role.getRole().name()).collect(Collectors.toSet()));
    }
}
