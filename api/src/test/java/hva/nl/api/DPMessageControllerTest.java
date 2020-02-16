package hva.nl.api;

import hva.nl.api.controllers.UserController;
import hva.nl.api.models.Message;
import hva.nl.api.models.User;
import hva.nl.api.repositories.MessageRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DPMessageControllerTest {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserController userController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Mock
    MessageRepository mockMessage;



    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }


    /**
     * This test is build with a mock. A fake message is created and is pickedup bij getMessageId().
     * Mockito is used for this test.
     *
     * @Author Danny Prodanovic
     */
    @Test
    public void getMessageById(){

        Message fakeMessage = new Message();
        fakeMessage.setId(1);
        fakeMessage.setMessage("Message created for test purpose");
        fakeMessage.setLongitude(52.324234);
        fakeMessage.setLatitude(4.124234);
        fakeMessage.setTitle("Test message");

       when(mockMessage.getMessageByID(1)).thenReturn(fakeMessage);
       Message message = mockMessage.getMessageByID(1);

       String messageCountains = "Message created for test purpose";
       assertEquals(messageCountains, message.getMessage(), "Get the message by id works!");
    }

    /**
     * This test saves a message on the database IF the token is correct and valid. Otherwise the message wont be saved.
     *
     * @Author Danny Prodanovic
     */
    @Test
    public void saveMessage() throws JSONException {
        Message message = new Message();

        message.setMessage("This message is saved with a token");
        message.setTitle("Title of test");
        message.setLatitude(42.34242342);
        message.setLongitude(54.21423423);

        HttpEntity<Message> request = new HttpEntity<>(message);


        //login in to get a token that is working.
        String user = userController.getUserByLogin("aaa", "aaa");
        JSONObject obj = new JSONObject(user);
        String token = obj.getJSONObject("Session").getString("token");


        //Please make sure that the token is up to date, if the token is expired the test will fail!
        //To get a new token you need to log in
        ResponseEntity<Message> responseEntity = testRestTemplate.postForEntity("http://localhost:8080/api/message/"+ token,
                request, Message.class);

        if (responseEntity.getStatusCode().value() == 500){
            fail("Token is not right, or expired!");
        } else
            assertTrue(true, "Message has been saved");
        System.out.println(responseEntity.getBody().getId());
    }

    /**
     * This test updates the data when the message is opend. User is neccesary for knowing who opened it.
     *
     * @Author Danny Prodanovic
     */

    @Test
    public void updateMessageDateOpend(){
        Message message = this.testRestTemplate.getForObject("/api/message/117", Message.class);
        User user = this.testRestTemplate.getForObject("/api/users/107", User.class);

        messageRepository.updateMessage(message, user);

        assertNotNull(message.getMessage_Opend(), "Message has been opened");

    }

    /**
     * This test shows that disable a message works.
     * If the message.getdisabled = 1 the message wont we shown in the map.
     *
     * @Author Danny Prodanovic
     */
    @Test
    public void disableMessage(){
        Message message = this.testRestTemplate.getForObject("/api/message/117", Message.class);
        System.out.println("message is enabled now "+message.getDisabled());

        messageRepository.disableMessage(message.getId());

        //Its also possible to run the test with testRestTemplate. Only if the messagecontroller is a GetMapping
        //function
        //testRestTemplate.put("/api/messageDisable/" + message.getId(), Message.class);
        Message disabledNow = this.testRestTemplate.getForObject("/api/message/117", Message.class);

        assertTrue(disabledNow.getDisabled() == 1, "Message is disabled");

        System.out.println("message is disabled now "+disabledNow.getDisabled());
    }

    /**
     * This test enables a disabled message again
     *
     * @Author Danny Prodanovic
     */
    @Test
    public void enableDisabledMessage(){
        Message message = this.testRestTemplate.getForObject("/api/message/117", Message.class);
        System.out.println("message is enabled now "+message.getDisabled());
        messageRepository.disableMessage(message.getId());
        Message disabledNow = this.testRestTemplate.getForObject("/api/message/117", Message.class);

        System.out.println("message is disabled now "+disabledNow.getDisabled());

        messageRepository.enableMessage(message.getId());
        //Its also possible to run the test with testRestTemplate. Only if the messagecontroller is a GetMapping
        //function
        //testRestTemplate.put("/api/messageEnable/" + message.getId(), Message.class);
        Message enableMsg = this.testRestTemplate.getForObject("/api/message/117", Message.class);
        assertTrue(enableMsg.getDisabled() == 0, "Message is enabled again");
    }

}
