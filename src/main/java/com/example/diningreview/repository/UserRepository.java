package com.example.diningreview.repository;

import com.example.diningreview.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository <User, Integer> {
    Optional<User> findUserByDisplayName(String name);
}
