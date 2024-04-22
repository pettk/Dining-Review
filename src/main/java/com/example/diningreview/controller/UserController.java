package com.example.diningreview.controller;

import com.example.diningreview.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.diningreview.model.User;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    public UserController(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        Optional<User> userToCreate = this.userRepository.findUserByDisplayName(user.getDisplayName());
        if(userToCreate.isPresent()) {
            return null;
        }
        User newUser = this.userRepository.save(user);
        return newUser;
    }

    @PutMapping("/{displayName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public User updateUser(@PathVariable("displayName") String displayName, @RequestBody User user) {
        Optional<User> userToUpdateOptional = this.userRepository.findUserByDisplayName(displayName);
        if(!userToUpdateOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        User userToUpdate = userToUpdateOptional.get();
        if(user.getCity() != null) {
            userToUpdate.setCity(user.getCity());
        }
        if(user.getState() != null) {
            userToUpdate.setState(user.getState());
        }
        if(user.getZipCode() != null) {
            userToUpdate.setZipCode(user.getZipCode());
        }
        if(user.getPeanutInterest() != null) {
            userToUpdate.setPeanutInterest(user.getPeanutInterest());
        }
        if(user.getEggInterest() != null) {
            userToUpdate.setEggInterest(user.getEggInterest());
        }
        if(user.getDairyInterest() != null) {
            userToUpdate.setDairyInterest(user.getDairyInterest());
        }

        User updatedUser = this.userRepository.save(userToUpdate);
        return updatedUser;

    }

    @GetMapping("/{displayName}")
    public Optional<User> findUser(@PathVariable("displayName") String displayName) {
        return this.userRepository.findUserByDisplayName(displayName);
    }


}
