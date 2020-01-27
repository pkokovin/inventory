package ru.spbu.inventory.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import ru.spbu.inventory.model.User;

public class UserUtil {

    public static User updateFromTo(User user, User updatedUser) {
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail().toLowerCase());
        user.setPassword(updatedUser.getPassword());
        return user;
    }

    public static User prepareToSave(User user, PasswordEncoder passwordEncoder) {
        String password = user.getPassword();
        user.setPassword(StringUtils.hasText(password) ? passwordEncoder.encode(password) : password);
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}
