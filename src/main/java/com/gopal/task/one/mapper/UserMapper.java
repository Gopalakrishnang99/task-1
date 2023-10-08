package com.gopal.task.one.mapper;

import com.gopal.task.one.dto.UserDetailsDto;
import com.gopal.task.one.model.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Mapper utilities for user and user related data
 */
@Component
public class UserMapper {

    public UserDetailsDto userEntityToDto(UserDetails user){
        UserDetailsDto dto = new UserDetailsDto();
        dto.setUserId(user.getUserId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        return dto;
    }
}
