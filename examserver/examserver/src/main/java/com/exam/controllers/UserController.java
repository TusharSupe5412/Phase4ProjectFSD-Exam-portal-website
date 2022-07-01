package com.exam.controllers;

import com.exam.helper.UserFoundException;
import com.exam.model.Role;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //    Creating user
    @PostMapping("/")
    public User createUser(@RequestBody User user) throws Exception {

        user.setProfile("default.png");

        //encoding password with BCryptPasswordEncoder

        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));


        Set<UserRole> roles = new HashSet<>();

        Role role = new Role();
        role.setRoleId(45l);
        role.setRoleName("NORMAL");

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        roles.add(userRole);

        return this.userService.createUser(user, roles);
    }

    //     Get user by username
    @GetMapping("/{username}")
    public User getUser(@PathVariable("username") String userName) {
        return this.userService.getUser(userName);
    }

    //    Delete User by id
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId){
        this.userService.deleteUser(userId);
    }

    //    Update user by username
    @PostMapping("/{username}")
    public User updateUser(@PathVariable("username") String userName){
        return this.userService.updateUser(userName);
    }


//    @ExceptionHandler(UserFoundException.class)
//    public ResponseEntity<?> exceptionHandler(UserFoundException ex){
//        return ResponseEntity;
//    }
}
