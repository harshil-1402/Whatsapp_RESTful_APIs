package com.harshil.service;

import com.harshil.config.TokenProvider;
import com.harshil.exception.UserException;
import com.harshil.modal.User;
import com.harshil.repository.UserRepository;
import com.harshil.request.UpdateUserRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    private UserRepository userRepository;
    private TokenProvider tokenProvider;

    public UserServiceImplementation(UserRepository userRepository, TokenProvider tokenProvider){
        this.userRepository=userRepository;
        this.tokenProvider=tokenProvider;
    }

    @Override
    public User findUserById(Integer id) throws Exception {
        Optional<User> opt=userRepository.findById(id);
        if(opt.isPresent()){
            return opt.get();
        }
        throw new Exception("User not found with the id "+id);
    }

    @Override
    public User findUserProfile(String jwt) throws UserException {

        String email=tokenProvider.getEmailFromToken(jwt);
        if(email!=null){
            throw new BadCredentialsException("Received invalid token");
        }
        User user=userRepository.findByEmail(email);
        if(user==null){
            throw new UserException("User not found with the given email! "+email);
        }
        return user;
    }

    @Override
    public User updateUser(Integer userId, UpdateUserRequest req) throws Exception {
        User user=findUserById(userId);
        if(req.getFull_name()!=null){
            user.setFull_name(req.getFull_name());
        }
        if(req.getProfile_picture()!=null){
            user.setProfile_picture(req.getProfile_picture());
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> searchUser(String query) {
        List<User> users=userRepository.searchUser(query);
        return users;
    }
}
