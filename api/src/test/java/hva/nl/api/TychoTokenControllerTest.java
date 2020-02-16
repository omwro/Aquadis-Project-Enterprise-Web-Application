package hva.nl.api;

import hva.nl.api.controllers.TokenController;
import hva.nl.api.controllers.UserController;
import hva.nl.api.models.User;
import hva.nl.api.repositories.UserRepository;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Tycho Scholtze 500807273 IS203
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TychoTokenControllerTest {
    @Mock
    @Autowired
    TokenController tokenController;

    @Mock
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    @Test
    void Setup() {
        User user = new User("foo@bar.com", "foo", "bar");
        user.setId(117);
        userRepository.setUser(user);
    }

    @Test
    void createToken() {
        User user = userRepository.getUserByID(107);

        String token = tokenController.generate(user);

        Assertions.assertNotSame(null, token, "Token should not be null");
        Assertions.assertNotSame("", token, "Token should not be empty");
    }

    @Test
    void detectValidToken() {
        User user = userRepository.getUserByID(107);

        String token = tokenController.generate(user);

        boolean tokenIsValid = tokenController.validateToken(token);

        Assertions.assertSame(true, tokenIsValid, "Token should be validated");
    }

    @Test
    void detectInvalidToken() {
        User user = userRepository.getUserByID(107);

        String token = tokenController.generate(user);

        try {
            boolean tokenIsValid = tokenController.validateToken("NotARealToken");
            Assertions.assertSame(false, tokenIsValid, "Token should be invalidated");
        } catch (Exception e) {
            Assertions.assertNotSame(null, e, "Token should be invalidated");
        }
    }

    @Test
    void detectUser() {
        User user = userRepository.getUserByID(107);

        String token = tokenController.generate(user);

        System.out.println(token);

        try {
            Assertions.assertSame(tokenController.tokenGetUser(token).getId(), user.getId(), "User should be the same as" +
                    " the user used to generate the token.");
        } catch (Exception e) {
            Assertions.assertSame(user, e, "User should exist");
        }
    }

    @Test
    void detectOldToken() {
        User user = userRepository.getUserByID(107);

        String token = tokenController.generate(user);

        user.setCurrentToken("FooBar");
        userRepository.setUser(user);

        Assertions.assertSame(false, tokenController.validateToken(token), "Token should be invalid");
    }
}
