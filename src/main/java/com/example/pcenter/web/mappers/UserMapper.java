package com.example.pcenter.web.mappers;

import com.example.pcenter.domain.user.User;
import com.example.pcenter.web.dto.user.UserDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto userDto);

    List<UserDto> toDto (List<User> users);

}
