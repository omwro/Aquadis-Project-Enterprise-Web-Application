package hva.nl.api;

import hva.nl.api.models.Message;
import hva.nl.api.models.User;
import hva.nl.api.repositories.MessageRepository;
import hva.nl.api.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MessageRepositoryTestJoel {

    @Autowired
    @InjectMocks
    private MessageRepository messageRepository;

    @Mock
    MessageRepository MessageRepo;

    @Mock
    private EntityManager em;


    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    //joel verhagen - 500760451
    @Test
    public void get_Message_Object_From_Message_Id(){
        //make message and set id and message
        Message message = new Message();
        message.setId(11);
        message.setMessage("hello");
        //return the message
        when(MessageRepo.getMessageByID(11)).thenReturn(message);
        //call getMessage
        Message message1 = MessageRepo.getMessageByID(11);
        //message body is hello
        assertThat(message1.getMessage(), equalTo(message.getMessage()));

    }

    //joel verhagen - 500760451
    @Test
    public void enable_Message() {
        //hardcoded message with id and is disabled
        Message message = new Message();
        message.setId(1);
        message.setDisabled(1);
        //return the hardcoded message
        when(messageRepository.getMessageByID(1)).thenReturn(message);
        messageRepository.enableMessage(1);
        //message should nog get enabled 1, because message is enabled
        assertNotEquals(1, message.getDisabled());

    }

    //joel verhagen - 500760451
    @Test
    public void disable_Message() {
        //hardcoded message with id and is enabled
        Message message = new Message();
        message.setId(1);
        message.setDisabled(0);
        //return the hardcoded message
        when(messageRepository.getMessageByID(1)).thenReturn(message);
        //call disable message on hardcoded message
        messageRepository.disableMessage(1);
        //message should get disabled 1, message is disabled
        assertEquals(1, message.getDisabled() );
    }

    //joel verhagen - 500760451
    @Test
    public void update_Message_Opend() {
        //create resttemplate
        RestTemplate restTemplate = new RestTemplate();
        //get a message from database
        Message message = restTemplate.getForObject("http://localhost:8080/api/message/117", Message.class);
        //get a user from database
        User user = restTemplate.getForObject("http://localhost:8080/api/users/107", User.class);
        //cal update message
        messageRepository.updateMessage(message, user);
        //message should be updated when message is opened
        assertNotNull(message.getMessage_Opend().toString(), "Message opened");
    }


    //joel verhagen - 500760451
    @Test
    public void get_Message_By_Id_When_Message_Is_Empty() {
        //make message and set message null
        Message message = new Message();
        message.setId(1);
        message.setMessage(null);
        //return message
        when(MessageRepo.getMessageByID(1)).thenReturn(message);
        //call get messageById
        MessageRepo.getMessageByID(1);
        //Message = null, message.getMessage == null
        assertNull(null, message.getMessage());
    }

    //joel verhagen - 500760451
   @Test
    public void error_Test_Get_Message_with_Non_Existing_Id() {
        //create restTemplate
        RestTemplate restTemplate = new RestTemplate();
        //retrieve message data from database with non existing id 3000
        Message message = restTemplate.getForObject("http://localhost:8080/api/message/3000", Message.class);
        //call getMessageById
        messageRepository.getMessageByID(message.getId());
        //getting the message by Id with non existing Id 3000, should give a nullPointerException.
        assertEquals("nullPointerException", message.getMessage());

    }

}




