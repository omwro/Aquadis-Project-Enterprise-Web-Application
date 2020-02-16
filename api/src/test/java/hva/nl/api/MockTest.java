package hva.nl.api;

import hva.nl.api.models.User;
import hva.nl.api.repositories.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MockTest {
    @Autowired
    UserRepository userRepository;

    @Mock
    UserRepository mockRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    // Made by Omer Erdem 500802898 IS203
    @Test
    void MockTest1_UserNotNull(){
        User u1 = new User("u1@u.u", "u1", "p1");
        when(mockRepository.getUserByID(1)).thenReturn(u1);
        Assertions.assertNotNull(mockRepository.getUserByID(1));
    }
}
