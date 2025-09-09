package com.engeto.genesis_resources.service;

import com.engeto.genesis_resources.dto.UserCreateDTO;
import com.engeto.genesis_resources.dto.UserLiteResponseDTO;
import com.engeto.genesis_resources.exception.DatabaseConstraintException;
import com.engeto.genesis_resources.exception.InvalidPersonIdException;
import com.engeto.genesis_resources.exception.UserNotFoundException;
import com.engeto.genesis_resources.model.User;
import com.engeto.genesis_resources.repository.UserRepository;
import com.engeto.genesis_resources.service.validation.PersonIdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonIdService personIdService;

    public List<User> getAllUsers() {
        log.info("Retrieving all users (detailed info)");
        return userRepository.findAll();
    }

    public List<UserLiteResponseDTO> getAllUsersLite() {
        log.info("Retrieving all users (lite)");
        return userRepository.findAll()
                .stream()
                .map(user -> new UserLiteResponseDTO(
                        user.getId(),
                        user.getName(),
                        user.getSurname()
                ))
                .toList();
    }

    public void addUser(UserCreateDTO dto) {
        log.info("Trying to add new user with personId: {}...", dto.getPersonId());
        if (!personIdService.isValidPersonId(dto.getPersonId())) {
            throw new InvalidPersonIdException(dto.getPersonId());
        }

        User user = new User();
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setPersonId(dto.getPersonId());
        user.setUuid(UUID.randomUUID().toString());

        try {
            userRepository.save(user);
            log.info("User saved successfully with uuid: {}", user.getUuid());
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseConstraintException(e);
        }
    }

    public User getUserById(Long id) {
        log.info("Retrieving user by id: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public UserLiteResponseDTO getUserLiteById(Long id) {
        log.info("Retrieving user (lite) by id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return new UserLiteResponseDTO(user.getId(), user.getName(), user.getSurname());
    }

    public User updateUserById(UserLiteResponseDTO dto) {
        log.info("Updating user with id: {}", dto.getId());
        User user = userRepository.findById(dto.getId())
                .orElseThrow(() -> new UserNotFoundException(dto.getId()));
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        User updatedUser = userRepository.save(user);
        log.info("User updated successfully with id: {}", updatedUser.getId());
        return updatedUser;
    }

    public void deleteUserById(Long id) {
        log.info("Trying to delete user with id: {}...", id);
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        log.info("User with id: {} deleted successfully", id);
    }
}
