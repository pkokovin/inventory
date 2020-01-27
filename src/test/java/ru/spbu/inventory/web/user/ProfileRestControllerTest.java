package ru.spbu.inventory.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.inventory.model.Role;
import ru.spbu.inventory.model.User;
import ru.spbu.inventory.service.UserService;
import ru.spbu.inventory.util.UserUtil;
import ru.spbu.inventory.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.spbu.inventory.UserTestData.*;
import static ru.spbu.inventory.util.exception.ErrorType.VALIDATION_ERROR;
import static ru.spbu.inventory.web.ExceptionInfoHandler.EXCEPTION_DUPLICATE_EMAIL;

class ProfileRestControllerTest extends AbstractControllerTest {

    @Autowired
    private UserService userService;

    ProfileRestControllerTest() {
        super(ProfileRestController.REST_URL);
    }

    @Test
    void get() throws Exception {
        perform(doGet().basicAuth(USER))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHERS.contentJson(USER));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(doGet())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void delete() throws Exception {
        perform(doDelete().basicAuth(USER))
                .andExpect(status().isNoContent());
        USER_MATCHERS.assertMatch(userService.getAll(), ADMIN);
    }


    @Test
    void update() throws Exception {
        User updatedUser = new User(null, "newName", "newemail@ya.ru", "newPassword", Role.ROLE_USER);
        perform(doPut().jsonBody(updatedUser).basicAuth(USER))
                .andDo(print())
                .andExpect(status().isNoContent());

        USER_MATCHERS.assertMatch(userService.get(USER_ID), UserUtil.updateFromTo(new User(USER), updatedUser));
    }

    @Test
    void updateInvalid() throws Exception {
        User updatedUser = new User(null, null, "password", null, Role.ROLE_USER);

        perform(doPut().jsonBody(updatedUser).basicAuth(USER))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        User updatedUser = new User(null, "newName", "admin@gmail.com", "newPassword", Role.ROLE_USER);

        perform(doPut().jsonBody(updatedUser).basicAuth(USER))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_EMAIL))
                .andDo(print());
    }
}