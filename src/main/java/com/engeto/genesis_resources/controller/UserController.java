package com.engeto.genesis_resources.controller;

import com.engeto.genesis_resources.dto.UserLiteDTO;
import com.engeto.genesis_resources.model.User;
import com.engeto.genesis_resources.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public void createUser(@RequestBody User user) {
        userService.addUser(user);
    }

    @GetMapping
    public Object getUsers(
            @RequestParam(required = false, defaultValue = "false") Boolean detail) {
        if (detail) {
            return userService.getAllUsers();
        } else {
            return userService.getAllUsersLite();
        }
    }

    @GetMapping("/{id}")
    public Object getUser(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "false") Boolean detail) {
        if (detail) {
            return userService.getUserById(id);
        } else {
            return userService.getUserLiteById(id);
        }
    }

    @PutMapping
    public UserLiteDTO updateUser(@RequestBody UserLiteDTO dto) {
        User updatedUser = userService.updateUserById(dto);
        return new UserLiteDTO(updatedUser.getId(), updatedUser.getName(), updatedUser.getSurname());
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}
