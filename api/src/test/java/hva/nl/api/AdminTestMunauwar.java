package hva.nl.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import hva.nl.api.controllers.MessageController;
import hva.nl.api.controllers.UserController;
import hva.nl.api.models.Message;
import hva.nl.api.models.User;
import hva.nl.api.repositories.MessageRepository;
import hva.nl.api.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasToString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

/*
    @author: Munauwar Mughal 500802553
 */

@SpringBootTest
public class AdminTestMunauwar {
    private MockMvc mockMvc;
    private MockMvc mockMvcMessage;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    UserController userController;

    @InjectMocks
    MessageController messageController;

    private JacksonTester<User> jsonUser;

    private User user1 = new User("user1@hotmail.com", "user1", "password");
    private User user2 = new User("user2@hotmail.com", "user2", "password");
    private User user3 = new User("user2@hotmail.com", "user3", "password");
    private List<User> mockUsers;

    @Before
    public void setUp() {
        JacksonTester.initFields (this, new ObjectMapper());
        MockitoAnnotations.initMocks(this);
        mockUsers = List.of(this.user1, this.user2, this.user3);
        mockMvc = MockMvcBuilders.standaloneSetup (userController).build();
        mockMvcMessage = MockMvcBuilders.standaloneSetup (messageController).build();
    }

    @Test
    public void getUsersTest() {
        when(userRepository.getUsers()).thenReturn(mockUsers);
        Assert.assertEquals(3, userRepository.getUsers().size());
    }

    @Test
    public void disableUser() throws Exception {
        given(userRepository.getUserByID(2))
                .willReturn(new User("user2@hotmail.com", "user2", "password"));

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/api/adminDisable/2")
        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assert.assertEquals(response.getContentAsString(), "true");
    }

    @Test
    public void cantDisableUserThatdoenstExits() throws Exception {
        given(userRepository.getUserByID(2))
                .willReturn(new User("user1@hotmail.com", "user1", "password"));

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/api/adminDisable/999")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assert.assertFalse(response.getContentAsString().equals("true"));
    }

    @Test
    public void enableUser() throws Exception {
        given(userRepository.getUserByID(2))
                .willReturn(new User("user2@hotmail.com", "user2", "password"));
        userRepository.disable(2);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/api/adminEnable/2")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assert.assertTrue(response.getContentAsString().equals("true"));
    }

    @Test
    public void cantEnableUserThatDoesntExist() throws Exception {
        given(userRepository.getUserByID(2))
                .willReturn(new User("user1@hotmail.com", "user1", "password"));

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/api/adminDisable/999")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assert.assertNotEquals(response.getContentAsString(), userRepository.getUserByID(2));

    }

//    Message Admin test ------------------
    @Test
    public void getMessageId() throws Exception {
        given(messageRepository.getMessageByID(2))
                .willReturn(new Message(2, "testTitle", "hello", new Date(), 10, 10, new Date(), 1));

        MockHttpServletResponse response = mockMvcMessage.perform(MockMvcRequestBuilders.get("/api/message/2")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assert.assertThat (response.getStatus (), is (HttpStatus.OK.value ()));
    }

    @Test
    public void enableMessage() throws Exception {
        given(messageRepository.getMessageByID(2))
                .willReturn(new Message(2, "testTitle", "hello", new Date(), 10, 10, new Date(), 1));

        MockHttpServletResponse response = mockMvcMessage.perform(MockMvcRequestBuilders.get("/api/messageEnable/2")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assert.assertThat(response.getContentAsString(), equalTo("true"));
    }

    @Test
    public void cantEnableMessageThatDoesntExits() throws Exception {
        given(messageRepository.getMessageByID(2))
                .willReturn(new Message(2, "testTitle", "hello", new Date(), 10, 10, new Date(), 1));

        MockHttpServletResponse response = mockMvcMessage.perform(MockMvcRequestBuilders.get("/api/messageEnable/999")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assert.assertThat(response.getContentAsString(), hasToString("true"));
    }

    @Test
    public void disableMessage() throws Exception {
        given(messageRepository.getMessageByID(2))
                .willReturn(new Message(2, "testTitle", "hello", new Date(), 10, 10, new Date(), 0));

        MockHttpServletResponse response = mockMvcMessage.perform(MockMvcRequestBuilders.get("/api/messageDisable/2")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assert.assertThat(response.getContentAsString(), notNullValue());
        Assert.assertThat(response.getContentAsString(), equalTo("true"));
    }

    @Test
    public void cantDisableMessageThatDoesntExist() throws Exception {
        given(messageRepository.getMessageByID(2))
                .willReturn(new Message(2, "testTitle", "hello", new Date(), 10, 10, new Date(), 1));

        MockHttpServletResponse response = mockMvcMessage.perform(MockMvcRequestBuilders.get("/api/messageDisable/999")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assert.assertThat(response.getStatus(), is(HttpStatus.OK.value()));
    }
}
