package com.harshil.service;
import java.util.*;

import com.harshil.exception.UserException;
import com.harshil.modal.User;
import com.harshil.request.UpdateUserRequest;
import jdk.jshell.spi.ExecutionControl;

public interface UserService {

    public User findUserById(Integer id) throws Exception;
    public User findUserProfile(String jwt) throws UserException;
    public User updateUser(Integer userId, UpdateUserRequest req) throws Exception;
    public List<User> searchUser(String query);
}
