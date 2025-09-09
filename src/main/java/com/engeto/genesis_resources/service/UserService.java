package com.engeto.genesis_resources.service;

import com.engeto.genesis_resources.dto.UserCreateDTO;
import com.engeto.genesis_resources.dto.UserLiteResponseDTO;
import com.engeto.genesis_resources.exception.DatabaseConstraintException;
import com.engeto.genesis_resources.exception.InvalidPersonIdException;
import com.engeto.genesis_resources.exception.UserNotFoundException;
import com.engeto.genesis_resources.model.User;
import com.engeto.genesis_resources.repository.UserRepository;
import com.engeto.genesis_resources.service.validation.PersonIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonIdService personIdService;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<UserLiteResponseDTO> getAllUsersLite() {
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
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseConstraintException(e);
        }
    }


    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public UserLiteResponseDTO getUserLiteById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return new UserLiteResponseDTO(user.getId(), user.getName(), user.getSurname());
    }

    public User updateUserById(UserLiteResponseDTO dto) {
        User user = userRepository.findById(dto.getId())
                .orElseThrow(() -> new UserNotFoundException(dto.getId()));
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        return userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}
