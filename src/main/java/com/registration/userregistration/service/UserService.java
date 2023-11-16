package com.registration.userregistration.service;

import com.registration.userregistration.dto.UserDTO;
import com.registration.userregistration.dto.UserLoginRequest;
import com.registration.userregistration.entity.UserEntity;
import com.registration.userregistration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserEntity registerUser(UserDTO userDTO) {
        UserEntity userData = new UserEntity(userDTO);
        return userRepository.save(userData);
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public ResponseEntity<?> updateUser(Long id,UserDTO user) {
        UserEntity existingUser = getUserById(id);
        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }

        // Update user information
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        userRepository.save(existingUser);
        return ResponseEntity.ok("User updated successfully");
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public ResponseEntity<?> userLogin(UserLoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        System.out.println(email+ " "+password);
        UserEntity user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
    }

}
