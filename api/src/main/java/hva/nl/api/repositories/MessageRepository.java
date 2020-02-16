package hva.nl.api.repositories;

import hva.nl.api.models.Message;
import hva.nl.api.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class MessageRepository {

    @Autowired
    private EntityManager em;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MapRepository mapRepository;

    public List<Message> getMessages() {
        return em.createQuery("select m from Message m", Message.class).getResultList();
    }

    public Message getMessageByID(int id) {
        return em.find(Message.class, id);
    }

    public Message saveMessage(Message message) {
        return em.merge(message);
    }

    public Message updateMessage(Message message, User user) {
        /*Message feMessage = object.getMessage();
        User feUser = userRepository.getUserByID(object.getUserid());
        Message dbMessage = getMessageByID(feMessage.getId());*/

        if (message.getMessage_Opend() == null) {
            message.setMessage_Opend(new Date());
        }
        if (!message.existSeenUsers(user)) {
            message.addSeenUsers(user);
            user.setScore(user.getScore() + mapRepository.getMapByKey("msg_open_score").getValue());
            userRepository.setUser(user);
        }

        return em.merge(message);
    }

    public long countMessages() {
        return (long) em.createNamedQuery("count_messages").getSingleResult();
    }

    public List<Message> messageData() {
        return em.createNamedQuery("get_messages").getResultList();
    }

    public void disableMessage(int id) {
        Message message = getMessageByID(id);
        if(message.getDisabled() == 0) {
            message.setDisabled(1);
            em.merge(message);
        }
    }

    public void enableMessage(int id) {
        Message message = getMessageByID(id);
        if(message.getDisabled() == 1) {
            message.setDisabled(0);
            em.merge(message);
        }
    }
}


