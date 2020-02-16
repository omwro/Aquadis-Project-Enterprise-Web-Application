package hva.nl.api.controllers;

import hva.nl.api.models.Message;
import hva.nl.api.models.User;
import hva.nl.api.repositories.MapRepository;
import hva.nl.api.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api")
public class MessageController {

    @Autowired
    private MessageRepository repository;

    @Autowired
    private MapRepository mapRepository;

    @Autowired
    private TokenController tokenController;

    @GetMapping(path = "/message")
    @ResponseBody
    public List<Message> getMSG(){
        return repository.getMessages();
    }

    @GetMapping("/currentmessage")
    @ResponseBody
    public List<Message> getCurrentMSG(){
        Date acceptedtime = Date.from(LocalDateTime.now().minusMinutes(mapRepository.getMapByKey("msg_exp_min").getValue()).atZone(ZoneId.systemDefault()).toInstant());
        return repository.getMessages()
                .stream()
                .filter(m -> (m.getMessage_Opend() == null || m.getMessage_Created().compareTo(acceptedtime) > 0) && m.isDisabled() == 0)
        .collect(Collectors.toList());
    }

    @PostMapping(path = "/message/{token}")
    @ResponseBody
    public Message saveMsg(@RequestBody Message message, @PathVariable String token) throws Exception {
        if(tokenController.validateToken(token)) {
            User omrwo = tokenController.tokenGetUser(token);
            message.setUser(omrwo);
            return repository.saveMessage(message);
        }
        return null;
    }

    @PutMapping(path = "/message/{token}")
    @ResponseBody
    public Message updateMsg(@RequestBody Message message, @PathVariable String token) throws Exception {
        if (tokenController.validateToken(token)){
            User user = tokenController.tokenGetUser(token);
            System.out.println(message.toString());
            return repository.updateMessage(message, user);
        }
        return null;
    }

    @GetMapping(path = "/adminMessages")
    @ResponseBody
    public long countMessages() {
        return repository.countMessages();
    }

    @GetMapping("/messagesData")
    @ResponseBody
    public List<Message> messageData() {
        return repository.messageData();
    }

    @GetMapping(path = "/message/{id}")
    public @ResponseBody
    Message getMessageByID(@PathVariable("id") int id) {
        return repository.getMessageByID(id);
    }

    @GetMapping(path = "/messageDisable/{id}")
    public @ResponseBody
    boolean disable(@PathVariable("id") int id) {
        if(repository.getMessageByID(id) != null) {
            repository.disableMessage(id);
            return true;
        } else return false;

    }

    @GetMapping(path = "/messageEnable/{id}")
    public @ResponseBody
    boolean enable(@PathVariable("id") int id) {
        repository.enableMessage(id);
        return true;
    }

}
