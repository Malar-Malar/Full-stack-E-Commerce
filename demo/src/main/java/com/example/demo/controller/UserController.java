package com.example.demo.controller;


import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/users")
@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody User user){
        User newUser = userService.signup(user);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        Map<String, String> response = new HashMap<>();
        try {
            boolean success = userService.loginByEmail(email, password);
            if (success) {
                User user = userService.getUserByEmail(email);
                response.put("id", user.getId().toString());
                response.put("username", user.getUsername());
                return ResponseEntity.ok(response);
            } else {
                response.put("error", "Invalid email or password");
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("error", "An error occurred: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
