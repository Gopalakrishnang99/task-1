package com.gopal.task.one.util;

import com.gopal.task.one.dto.RoleFeatureDto;
import com.gopal.task.one.dto.UserDetailsDto;
import com.gopal.task.one.dto.UserRolesDataDto;
import com.gopal.task.one.model.Role;
import com.gopal.task.one.model.UserDetails;
import com.gopal.task.one.model.UserRole;

import java.util.Set;

public class TestUtil {

    public static UserDetailsDto getTestUserDetailsDto(Long userId){
        UserDetailsDto user = new UserDetailsDto();
        user.setUserId(userId);
        user.setEmail("email@email.com");
        user.setFirstName("first_name");
        user.setLastName("last_name");
        return user;
    }

    public static UserDetails getTestUserDetails(Long userId){
        UserDetails user = new UserDetails();
        user.setUserId(userId);
        user.setEmail("email@email.com");
        user.setFirstName("first_name");
        user.setLastName("last_name");
        return user;
    }

    public static UserRolesDataDto getUserRoleDataDto(Long userId) {
        UserRolesDataDto userRolesDataDto = new UserRolesDataDto();
        userRolesDataDto.setUser(getTestUserDetailsDto(userId));
        userRolesDataDto.setRoles(Set.of("admin","user"));
        RoleFeatureDto roleFeatureDto = new RoleFeatureDto(1L,"design");
        roleFeatureDto.getPermission().add("read");
        userRolesDataDto.setFeatures(Set.of(roleFeatureDto));
        return userRolesDataDto;
    }

    public static UserRole getUserRole(Long userId,Long roleId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        return userRole;
    }

    public static Role getRoleEntity(Long id) {
        Role role = new Role();
        role.setRoleName(id==2L?"admin":"user");
        role.setDescription("description");
        role.setRoleId(id);
        return role;
    }
}
