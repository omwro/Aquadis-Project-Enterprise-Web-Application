package hva.nl.api;

import hva.nl.api.models.User;
import hva.nl.api.repositories.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.AssertFalse;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;



@SpringBootTest
class UserRepositoryTestJoel {


    @Autowired
    @Mock
    UserRepository userRepository;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    //joel verhagen - 500760451
    @org.junit.jupiter.api.Test
    public void get_UserName_From_User_Id_From_Database(){
        //create restTemplate
        RestTemplate restTemplate = new RestTemplate();
        //retrieve data from database, with user id 4. user 4 in the database is equal to username: omer
        User user = restTemplate.getForObject("http://localhost:8080/api/users/4", User.class);
        //call getUserById
        userRepository.getUserByID(user.getId());
        //check if username from user id 4 is equal to "omer
        assertThat(user.getUsername(), equalTo("omer"));
    }

    //Joel verhagen - 500760451
    @Test
    public void Username_And_Password_User_Should_Match_Username_And_Password_User1()  {
        //create restTemplate
        RestTemplate restTemplate = new RestTemplate();
        //retrieve data from id 107 from database, this user has username and pasword: "amsterdam'
        User user = restTemplate.getForObject("http://localhost:8080/api/users/107", User.class);
        //create a new user with the same credentials.
        User user1 = new User();
        user1.setUsername("amsterdam");
        user1.setPassword("amsterdam");
        //user.username should match user1.username
        assertEquals(user.getUsername(), user1.getUsername());
        //user.password should match user1.password
        assertEquals(user.getPassword(), user.getPassword());
    }

    //joel verhagen -500760451
    @Test
    public void disable_User_By_Id() {
        //create restTemplate
        RestTemplate restTemplate = new RestTemplate();
        //retrieve data from database, user with id 107
        User user = restTemplate.getForObject("http://localhost:8080/api/users/15", User.class);
        //call disable method to disable user
        userRepository.disable(user.getId());
        //user in database is already disabled, disabled == 1
        assertNotEquals(0, user.getId());
        //because user is already disabled (1), user.getdisabled should be equal to 1
        assertThat(user.getDisabled(), equalTo(1));
    }

    //joel verhagen - 500760451
    @Test
    public void enable_User_By_Id() {
        //create restTemplate
        RestTemplate restTemplate = new RestTemplate();
        //retrieve data from database, user with id 16
        User user = restTemplate.getForObject("http://localhost:8080/api/users/16", User.class);
        //cal enable for user with id 16, this wil enable the user
        userRepository.enable(user.getId());
        //checks if the user gets enabled, 1 should go to 0
      assertEquals( 0, user.getDisabled()  );
    }

    //Joel verhagen - 500760451
    @Test
    public void error_Test_Get_User_From_Database_That_Not_Exists_With_Id () {
        //create restTemplate
        RestTemplate restTemplate = new RestTemplate();
        //retrieve data from database, user with id 3000 (does not exixt)
        User user = restTemplate.getForObject("http://localhost:8080/api/users/3000", User.class);
        //call getUserById with Id 3000
        userRepository.getUserByID(user.getId());
        //Getting a user with a non existing Id returns a 'nullPointerException' because there is no User.
        assertEquals("nullpointerException", user);
    }







}