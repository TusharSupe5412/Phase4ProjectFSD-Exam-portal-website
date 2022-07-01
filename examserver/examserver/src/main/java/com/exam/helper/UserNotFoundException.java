package com.exam.helper;

public class UserNotFoundException extends Exception{

    public UserNotFoundException(){
        super("User with this UserName is not found in DB !!");
    }

    public UserNotFoundException(String msg){
        super(msg);
    }
}
