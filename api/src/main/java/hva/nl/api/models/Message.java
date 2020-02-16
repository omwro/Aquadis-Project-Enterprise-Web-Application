package hva.nl.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "find_all_messages", query = "select m from Message m"),
        @NamedQuery(name = "count_messages", query = "select count(m.id) from Message m"),
        @NamedQuery(name = "get_messages", query = "select m.id, m.disabled, m.title, m.message from Message m")
})
public class Message {

    @Id
    @GeneratedValue()
    private int id;
    private String title;
    private String message;
    private Date message_Created;
    private double longitude;
    private double latitude;
    private Date message_Opend;
    private int disabled;

    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToMany
    private List<User> seenUsers;

    public Message() {
    }

    public Message(@JsonProperty("id") int id,
                   @JsonProperty("title")String title,
                   @JsonProperty("message")String message,
                   @JsonProperty("message_Created")Date message_Created,
                   @JsonProperty("longitude")double longitude,
                   @JsonProperty("latitude")double latitude,
                   @JsonProperty("message_Opend")Date message_Opend,
                   @JsonProperty("disabled")int disabled) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.message_Created = message_Created;
        this.longitude = longitude;
        this.latitude = latitude;
        this.message_Opend = message_Opend;
        this.disabled = disabled;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessage_Created(Date message_Created) {
        this.message_Created = message_Created;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getDisabled() {
        return disabled;
    }

    public void setDisabled(int disabled) {
        this.disabled = disabled;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public Date getMessage_Created() {
        return message_Created;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public Date getMessage_Opend() {
        return message_Opend;
    }

    public void setMessage_Opend(Date message_Opend) {
        this.message_Opend = message_Opend;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int isDisabled() {
        return disabled;
    }

    public List<User> getSeenUsers() {
        return seenUsers;
    }

    public void addSeenUsers(User user) {
        this.seenUsers.add(user);
    }

    public boolean existSeenUsers(User user){
        User foundUser = seenUsers.stream()
                .filter(s -> s.getId() == (user.getId()))
                .findFirst()
                .orElse(null);
        return foundUser != null;
    }

    public void setSeenUsers(List<User> seenUsers) {
        this.seenUsers = seenUsers;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Message{")
                .append("id:" + id)
                .append(",title:" + title)
                .append(",message:" + message)
                .append(",message_Created:" + message_Created)
                .append(",longitude:" + longitude)
                .append(",latitude:" + latitude)
                .append(",message_Opend:" + message_Opend)
                .append(",disabled:" + disabled)
                .append("}").toString();
    }

//    public void setId(int id) {
//        this.id = id;
    }

//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public void setMessage_Created(Date message_Created) {
//        this.message_Created = message_Created;
//    }
//
//    public void setLongitude(double longitude) {
//        this.longitude = longitude;
//    }
//
//    public void setLatitude(double latitude) {
//        this.latitude = latitude;
//    }

