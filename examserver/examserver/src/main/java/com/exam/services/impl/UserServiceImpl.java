package com.exam.services.impl;

import com.exam.helper.UserFoundException;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.repositories.RoleRepository;
import com.exam.repositories.UserRepository;
import com.exam.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User createUser(User user, Set<UserRole> userRoles) throws Exception {

        User local = this.userRepository.findByUserName(user.getUserName());
        if (local != null) {
            System.out.println("User is already there!!!");
            throw new UserFoundException();
        } else {
            //            Create user
            for (UserRole ur : userRoles) {
                roleRepository.save(ur.getRole());
            }

            user.getUserRoles().addAll(userRoles);
            local = this.userRepository.save(user);

        }

        return local;
    }

    // Getting user by username
    @Override
    public User getUser(String userName) {
        return this.userRepository.findByUserName(userName);
    }

    //  Delete user by id
    @Override
    public void deleteUser(Long userId) {
        this.userRepository.deleteById(userId);
    }

    @Override
    public User updateUser(String userName) {
        return this.userRepository.findByUserName(userName);
    }
}
