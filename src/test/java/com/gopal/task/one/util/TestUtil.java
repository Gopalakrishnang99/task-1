package com.gopal.task.one.util;

import com.gopal.task.one.model.UserDetails;

public class TestUtil {

    public static UserDetails getTestUserDetails(Long userId){
        UserDetails user = new UserDetails();
        user.setUserId(userId);
        user.setEmail("email@email.com");
        user.setFirstName("first_name");
        user.setLastName("last_name");
        return user;
    }
}
