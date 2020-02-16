package hva.nl.api;

import hva.nl.api.controllers.UserController;
import hva.nl.api.models.User;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * @author Omer Erdem 500802898 IS203
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OmerUserControllerTest {

    @Mock
    @Autowired
    UserController userController;

    @Autowired
    private TestRestTemplate testRestTemplate;


    private User testUser;

    @BeforeEach
    @Test
    void login() throws Exception {
        String user = userController.getUserByLogin("aaa", "aaa");
        Assertions.assertNotNull(user);
        assertThat(user, notNullValue());
        JSONObject obj = new JSONObject(user);
        testUser = userController.getUserByToken(obj.getJSONObject("Session").getString("token"));
    }

    @Test
    void getProfile() throws Exception {
        String user = userController.getProfile(testUser.getCurrentToken());
        JSONObject obj = new JSONObject(user);
        String expectedNickname = "aaa12";
        String actualNickname = obj.getJSONObject("Profile").getString("nickname");

        Assertions.assertEquals(expectedNickname, actualNickname);
        assertThat(expectedNickname, is(actualNickname));
    }

    @Test
    void updateProfile() throws Exception {
        testUser.setAvatar("boy-1");
        testUser.setNickname(testUser.getNickname());
        testUser.setStatus(testUser.getStatus());
        userController.updateProfile(testUser.getCurrentToken(), testUser);

        String user = userController.getProfile(testUser.getCurrentToken());
        JSONObject obj = new JSONObject(user);
        String expectedAvatar = "boy-1";
        String actualAvatar = obj.getJSONObject("Profile").getString("avatar");

        Assertions.assertEquals(expectedAvatar, actualAvatar);
        assertThat(expectedAvatar, equalTo(actualAvatar));

        testUser.setAvatar("boy-4");
        userController.updateProfile(testUser.getCurrentToken(), testUser);
    }

    @Test
    void getProfileAdvanced() throws Exception {
        String user = userController.getProfileAdvanced(testUser.getCurrentToken());
        JSONObject obj = new JSONObject(user);
        String expectedEmail = "a@a.a";
        String actualEmail = obj.getJSONObject("Advanced").getString("email");

        Assertions.assertEquals(expectedEmail, actualEmail);
        assertThat(expectedEmail, is(actualEmail));
    }

    @Test
    void updateProfileAdvanced() throws Exception {
        testUser.setEmail("abc@a.a");
        testUser.setNickname(testUser.getNickname());
        testUser.setPassword("aaa");
        userController.updateProfileAdvanced(testUser.getCurrentToken(), testUser);

        String user = userController.getProfileAdvanced(testUser.getCurrentToken());
        JSONObject obj = new JSONObject(user);
        String expectedEmail = "abc@a.a";
        String actualEmail = obj.getJSONObject("Advanced").getString("email");

        Assertions.assertEquals(expectedEmail, actualEmail);
        assertThat(expectedEmail, is(equalTo(actualEmail)));

        testUser.setEmail("a@a.a");
        userController.updateProfileAdvanced(testUser.getCurrentToken(), testUser);
    }

    @Test
    void accountEmailExist() throws Exception {
        String user = userController.accountExist(testUser.getEmail(), testUser.getUsername(), testUser.getCurrentToken());
        JSONObject obj = new JSONObject(user);
        String actualEmail = obj.getJSONObject("Exist").getString("email");
        System.out.println(actualEmail);

        Assertions.assertFalse(Boolean.parseBoolean(actualEmail));
        assertThat(Boolean.parseBoolean(actualEmail), is(not(true)) );
    }

    @Test
    void accountUsernameExist() throws Exception {
        String user = userController.accountExist(testUser.getEmail(), testUser.getUsername(), testUser.getCurrentToken());
        JSONObject obj = new JSONObject(user);
        String actualUsername = obj.getJSONObject("Exist").getString("username");
        System.out.println(actualUsername);

        Assertions.assertFalse(Boolean.parseBoolean(actualUsername));
        assertThat(Boolean.parseBoolean(actualUsername), is(false) );
    }

    @Test
    void getUserByToken() throws Exception {
        User user = userController.getUserByToken(testUser.getCurrentToken());

        Assertions.assertNotSame(testUser, user);
        assertThat(testUser, is(not(samePropertyValuesAs(user))));
    }

    @Test
    void logout() throws Exception {
        String oldToken = testUser.getCurrentToken();
        User user = userController.logout(testUser.getCurrentToken());

        Assertions.assertNotEquals(user.getCurrentToken(), oldToken);
        assertThat(user.getCurrentToken(), is(not(oldToken)));
    }

    /*
    Dear Tester, I would like to notify your about the usage of this test. The ApiApplication should be running in an
    other state, because it will try to make a connection through a link and is not fully run when starting this test.
    You could first run the ApiApplication and then this test in another state to get the result.
     */
    @Test
    void forced_error() {
        ResponseEntity<User> responseEntity = testRestTemplate.getForEntity("http://localhost:8080/api/users/token/"+
                "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiItOTIyMzM3MjAzNjg1NDc3NTgwOCIsImlhdCI6MTU3ODg0NTMyMywic3ViIjoiMTA3IiwiaXNzIjoidXNlciIsImV4cCI6MTU3ODg0ODkyM30.IMf6msnVvQN9dPMrVun8OiO1gMHUcXgB0jVebP8K_Ks",
                User.class);
        assertThat(responseEntity.getStatusCode().value(), isA(Integer.class));
        assertThat(500, is(responseEntity.getStatusCode().value()));
    }
}
