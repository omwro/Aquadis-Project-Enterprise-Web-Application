package hva.nl.api;

import hva.nl.api.controllers.TokenController;
import hva.nl.api.controllers.UserController;
import hva.nl.api.models.User;
import hva.nl.api.repositories.UserRepository;
import org.hibernate.usertype.UserCollectionType;
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
public class TychoUserControllerTest {

    @Mock
    @Autowired
    UserController userController;

    @Mock
    @Autowired
    TokenController tokenController;

    @Mock
    @Autowired
    UserRepository userRepository;

    User user;

    @BeforeEach
    @Test
    public void login() throws Exception {
        String userString = userController.getUserByLogin("aaa", "aaa");

        Assertions.assertNotNull(userString, "Should be a valid login");

        user = userController.getUserByToken((new JSONObject(userString)).getJSONObject("Session").getString("token"));
    }

    @Test
    void checkInvalidUserDoesNotExist() {
        try {
            Assertions.assertEquals(null, userController.accountExist("", "", ""), "User should not exist");
        } catch (Exception e) {
            Assertions.assertNotEquals(null, e, "User should not exist");
        }
    }

    @Test
    void checkValidUserDoesExist() {
        try {
            Assertions.assertNotEquals(null, userController.accountExist("aaa", "aaa", user.getCurrentToken()), "User should exist");
        } catch (Exception e) {
            Assertions.assertEquals(null, e, "User should exist");
        }
    }

    @Test
    void logoutSuccessFull() {
        try{
            String token = user.getCurrentToken();
            userController.logout(token);

            Assertions.assertEquals(false, tokenController.validateToken(token), "User should exists");

        }
        catch(Exception e){
            Assertions.assertEquals(null, e, "User should exists");
        }
    }

    @Test
    void updateProfile() throws Exception {
        user.setAvatar("boy-3");
        user.setNickname(user.getNickname());
        user.setStatus(user.getStatus());

        userController.updateProfile(user.getCurrentToken(), user);

        String userString = userController.getProfile(user.getCurrentToken());
        JSONObject userJson = new JSONObject(userString);

        String expectedAvatar = "boy-3";
        String actualAvatar = userJson.getJSONObject("Profile").getString("avatar");

        Assertions.assertEquals(expectedAvatar, actualAvatar);

        user.setAvatar("boy-4");
        userController.updateProfile(user.getCurrentToken(), user);
    }

    @Test
    void updateProfileScore() throws Exception {
        int originalScore = user.getScore();
        user.setScore(44);

        userController.updateProfile(user.getCurrentToken(), user);

        String userString = userController.getProfile(user.getCurrentToken());
        JSONObject userJson = new JSONObject(userString);

        int expectedScore = 441;
        int actualScore = userJson.getJSONObject("Profile").getInt("score");

        Assertions.assertEquals(expectedScore, actualScore);

        user.setScore(originalScore);
        userController.updateProfile(user.getCurrentToken(), user);
    }
}
