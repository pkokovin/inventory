package ru.spbu.inventory.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.spbu.inventory.AuthorizedUser;
import ru.spbu.inventory.model.User;

import java.util.List;

public interface UserService {
    User create(User user);

    void delete(int id);

    User get(int id);

    User getByEmail(String email);

    List<User> getAll();

    void update(User user);

    void enable(int id, boolean enabled);
}
