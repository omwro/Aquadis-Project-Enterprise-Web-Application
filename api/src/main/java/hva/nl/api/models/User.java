package hva.nl.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name ="find_all_users", query="select u from User u"),
        @NamedQuery(name ="count_users", query="select count(u.id) from User u"),
        @NamedQuery(name ="user_data", query="select u.id, u.email, u.username, u.nickname, u.disabled from User u"),
        @NamedQuery(name = "top_5_users", query = "select u from User u WHERE u.disabled = 0 AND u.status = 0 order by u.score DESC"),
})
public class User {

    @Id
    @GeneratedValue
    private int id;
    private String email;
    private String username;
    private String password;
    private int disabled;
    private String nickname;
    private String avatar;
    private String userType;
    private String currentToken;
    private int score;
    private int status;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Message> messageList = new ArrayList<>();

    public User() {

    }

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.disabled = 0;
        this.nickname = username;
        this.avatar = null;
        this.score = 0;
        this.status = 0;
        this.userType = "user";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDisabled() {
        return disabled;
    }

    public void setDisabled(int disabled) {
        this.disabled = disabled;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("User{")
                .append("id:" + id)
                .append(",email:" + email)
                .append(",username:" + username)
                .append(",password:" + password)
                .append(",disabled:" + disabled)
                .append(",nickname:" + nickname)
                .append(",avatar:" + avatar)
                .append(",score:" + score)
                .append(",status:" + status)
                .append(",user_type:" + userType)
                .append(",current_token:" + currentToken)
                .append("}").toString();
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getCurrentToken() {
        return currentToken;
    }

    public void setCurrentToken(String currentToken) {
        this.currentToken = currentToken;
    }
}
