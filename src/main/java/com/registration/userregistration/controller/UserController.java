package com.registration.userregistration.controller;

import com.registration.userregistration.dto.UserDTO;
import com.registration.userregistration.dto.UserLoginRequest;
import com.registration.userregistration.entity.UserEntity;
import com.registration.userregistration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        System.out.println(userDTO);
        // Check if the username is already taken
        if (userService.findByEmail(userDTO.getEmail()) != null) {
            return new ResponseEntity<>("Email already taken", HttpStatus.BAD_REQUEST);
        }
        // Register the user
        userService.registerUser(userDTO);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest loginRequest) {
        return userService.userLogin(loginRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        UserEntity user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return new ResponseEntity<>("User Not Exists", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO updatedUser) {
        // Check if the user exists
        UserEntity existingUser = userService.getUserById(id);
        if (existingUser == null) {
            return new ResponseEntity<>("User Not Exists", HttpStatus.BAD_REQUEST);
        }
        return userService.updateUser(id,updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        UserEntity user = userService.getUserById(id);
        if (user != null) {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully");
        } else {
            return new ResponseEntity<>("User Not Exists", HttpStatus.BAD_REQUEST);
        }
    }
}
