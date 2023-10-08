package com.store.services;

import com.store.models.User;
import com.store.repos.UserRepository;
import org.apache.commons.validator.routines.IntegerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    public final UserRepository repo;

    @Autowired
    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public List<User> getAllUsers() {
        if (repo == null) return null;

        return repo.findAll();
    }

    public Optional<User> getUser(Long id) {
        if (isIdInvalid(id.toString()))
            return Optional.empty();

        return repo.findById(id);
    }

    public User createUser(User user) {
        if (isUserInvalid(user) || repo.findByName(user.getName()) != null)
            return null;

        return repo.save(user);
    }

    public User changeUser(User user) {
        if (isUserInvalid(user) || !repo.existsById(user.getId())) return null;

        return repo.save(user);
    }

    public boolean deleteUser(Long id) {
        if (isIdInvalid(id.toString()))
            return false;

        Optional<User> userToBeDeleted = repo.findById(id);

        if (userToBeDeleted.isEmpty()) return false;

        repo.delete(userToBeDeleted.get());

        return true;
    }

    public boolean isUserInvalid(User user) {
        return user == null || user.getName().isEmpty() || user.getName().isBlank() ||
                user.getPassword().isEmpty() || user.getPassword().isBlank();
    }

    public boolean isIdInvalid(String id) {
        return id == null || new IntegerValidator().validate(id) == null || Long.parseLong(id) < 1;
    }
}
