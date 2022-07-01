package com.exam.services;

import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;

import java.util.Set;

public interface UserService {

    //    Creating user
    public User createUser(User user, Set<UserRole> userRoles) throws Exception;

    //    Get user by username
    public User getUser(String userName);

    //    Delete user by id

    public void deleteUser(Long userId);

    //    Update user by username
    public User updateUser(String userName);
}
