package com.gopal.task.one.util;

import com.gopal.task.one.dto.RoleFeatureDto;
import com.gopal.task.one.dto.UserDetailsDto;
import com.gopal.task.one.dto.UserRolesDataDto;
import com.gopal.task.one.model.UserDetails;

import java.util.Set;

public class TestUtil {

    public static UserDetailsDto getTestUserDetails(Long userId){
        UserDetailsDto user = new UserDetailsDto();
        user.setUserId(userId);
        user.setEmail("email@email.com");
        user.setFirstName("first_name");
        user.setLastName("last_name");
        return user;
    }

    public static UserRolesDataDto getUserRoleDataDto(Long userId) {
        UserRolesDataDto userRolesDataDto = new UserRolesDataDto();
        userRolesDataDto.setUser(getTestUserDetails(userId));
        userRolesDataDto.setRoles(Set.of("admin","user"));
        RoleFeatureDto roleFeatureDto = new RoleFeatureDto(1L,"design");
        roleFeatureDto.getPermission().add("read");
        userRolesDataDto.setFeatures(Set.of(roleFeatureDto));
        return userRolesDataDto;
    }
}
