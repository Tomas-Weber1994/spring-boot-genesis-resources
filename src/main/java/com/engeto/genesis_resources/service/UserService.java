package com.engeto.genesis_resources.service;

import com.engeto.genesis_resources.dto.UserLiteDTO;
import com.engeto.genesis_resources.model.User;
import com.engeto.genesis_resources.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<UserLiteDTO> getAllUsersLite() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserLiteDTO(
                        user.getId(),
                        user.getName(),
                        user.getSurname()
                ))
                .toList();
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    public UserLiteDTO getUserLiteById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
        return new UserLiteDTO(user.getId(), user.getName(), user.getSurname());
    }

    public User updateUserById(UserLiteDTO dto) {
        User user = userRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("User not found with id " + dto.getId()));
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        return userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
