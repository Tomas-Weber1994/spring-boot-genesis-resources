package com.engeto.genesis_resources.controller;

import com.engeto.genesis_resources.dto.UserCreateDTO;
import com.engeto.genesis_resources.dto.UserLiteResponseDTO;
import com.engeto.genesis_resources.model.User;
import com.engeto.genesis_resources.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody UserCreateDTO user) {
        userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                        "message", "User created successfully",
                        "user", user
                ));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getUsers(
            @RequestParam(required = false, defaultValue = "false") boolean detail) {
        if (detail) {
            List<User> users = userService.getAllUsers();
            return ResponseEntity.ok(Map.of(
                    "message", "Users (detailed info) retrieved successfully",
                    "users", users
            ));
        } else {
            List<UserLiteResponseDTO> usersLite = userService.getAllUsersLite();
            return ResponseEntity.ok(Map.of(
                    "message", "Users retrieved successfully",
                    "users", usersLite
            ));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUser(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "false") boolean detail) {
        if (detail) {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(Map.of(
                    "message", "User (detailed info) retrieved successfully",
                    "user", user
            ));
        } else {
            UserLiteResponseDTO userLite = userService.getUserLiteById(id);
            return ResponseEntity.ok(Map.of(
                    "message", "User retrieved successfully",
                    "user", userLite
            ));
        }
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> updateUser(@RequestBody UserLiteResponseDTO dto) {
        User updatedUser = userService.updateUserById(dto);
        UserLiteResponseDTO response = new UserLiteResponseDTO(updatedUser.getId(), updatedUser.getName(), updatedUser.getSurname());
        return ResponseEntity.ok(Map.of(
                "message", "User updated successfully",
                "user", response
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok(Map.of(
                "message", "User with ID " + id + " deleted successfully"
        ));
    }
}
