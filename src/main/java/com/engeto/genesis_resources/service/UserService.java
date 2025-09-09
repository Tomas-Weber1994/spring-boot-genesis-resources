package com.engeto.genesis_resources.service;

import com.engeto.genesis_resources.dto.UserLiteDTO;
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

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonIdService personIdService;

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
        // personId validation against mocked API
        if (!personIdService.isValidPersonId(user.getPersonId())) {
            throw new InvalidPersonIdException(user.getPersonId());
        }
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

    public UserLiteDTO getUserLiteById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return new UserLiteDTO(user.getId(), user.getName(), user.getSurname());
    }

    public User updateUserById(UserLiteDTO dto) {
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
