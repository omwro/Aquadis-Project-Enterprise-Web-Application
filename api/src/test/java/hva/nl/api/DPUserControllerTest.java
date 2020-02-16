package hva.nl.api;


import hva.nl.api.controllers.UserController;
import hva.nl.api.models.User;


import org.junit.Before;
import org.junit.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DPUserControllerTest {




    @Autowired
    TestRestTemplate testRestTemplate;


    @Autowired
    UserController userController;


    User[] topFiveUsers;
    User theChampion;

    //making a list of users for test purpose
    @Before
    public void setUp(){
        topFiveUsers = testRestTemplate.getForObject("/api/users/top5users", User[].class);
    }

    /**
     * This test gets the user by id number.
     *
     * @Author Danny Prodanovic
     */
    @Test
    public void getUserById(){
        User user = this.testRestTemplate.getForObject("/api/users/107", User.class);
        assertThat(user, notNullValue());
    }

    /**
     * This test gets 5 players who has points to be in the list.
     *
     * @Author Danny Prodanovic
     */
    @Test
    public void topFiveNeedsToBeFive(){
        setUp();
        assertThat(topFiveUsers, arrayWithSize(5));
    }

    /**
     * This test shows that the first player in the array has more points than the next player
     *
     * @Author Danny Prodanovic
     */
    @Test
    public void firstPlayerNeedsMorePoints(){
        User[] users = testRestTemplate.getForObject("/api/users/top5users", User[].class);
        assertThat(users[0].getScore(), greaterThan(users[1].getScore()));
    }

    /**
     * This test shows that if an player is disabled he wont be in the top 5 player list.
     *
     *
     * @Author Danny Prodanovic
     */
    @Test
    public void hidePlayerIfDisabled(){
        setUp();
        this.theChampion = topFiveUsers[0];


        userController.disable(theChampion.getId());

        User[] newTopList = testRestTemplate.getForObject("/api/users/top5users", User[].class);
        assertThat(newTopList[0], not(equalTo(theChampion)));

    }

    /**
     * Test after a user is disabled and enabled again, the user needs to be in the list again.
     *
     * @Author Danny Prodanovic
     */
    @Test
    public void showPlayerAgainAfterDisable(){
        //disabled user
        theChampion = this.testRestTemplate.getForObject("/api/users/77", User.class);

        System.out.println(topFiveUsers[0].getUsername() + " The temp new top player..");

        userController.enable(theChampion.getId());
        setUp();

        System.out.println("The top player is back in the game.");

        assertThat(topFiveUsers[0].getUsername(), startsWith(theChampion.getUsername()));

    }

}
