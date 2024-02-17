package com.harshil.controller;

import com.harshil.exception.UserException;
import com.harshil.modal.User;
import com.harshil.request.UpdateUserRequest;
import com.harshil.response.ApiResponse;
import com.harshil.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    UserService userService;
    public UserController(UserService userService){
        this.userService=userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String token) throws UserException {
        User user=userService.findUserProfile(token);
        return new ResponseEntity<User>(user, HttpStatus.ACCEPTED);
    }
    @GetMapping("/{query}")
    public ResponseEntity<List<User>> searchUserHandler(@PathVariable("query") String query) {

        List<User> users = this.userService.searchUser(query);
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateUserHandler(@RequestBody UpdateUserRequest req,
                                                         @RequestHeader("Authorization") String token) throws Exception {

        User user = userService.findUserProfile(token);
        userService.updateUser(user.getId(), req);

        ApiResponse response = new ApiResponse("User updated successfully", true);
        response.setMessage("User updated Successfully");
        response.setStatus(true);

        return new ResponseEntity<ApiResponse>(response, HttpStatus.ACCEPTED);
    }
}
