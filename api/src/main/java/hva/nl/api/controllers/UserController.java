package hva.nl.api.controllers;

import hva.nl.api.models.User;
import hva.nl.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping(path = "/api")
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenController tokenController;

    @GetMapping(path = "")
    public @ResponseBody
    String get() {
        return "The API is running!";
    }

    @GetMapping(path = "/users")
    public @ResponseBody
    List<User> getUsers() {
        return repository.getUsers();
    }

    @GetMapping(path = "/users/top5users")
    public @ResponseBody
    List<User> findTopFiveUsers () {
        return repository.findTopFiveUsers();
    }

    @PutMapping(path = "/users")
    public @ResponseBody
    boolean updateUser(@RequestBody User user) {
        User foundUser = repository.getUserByID(user.getId());
        foundUser.setEmail(user.getEmail());
        foundUser.setUsername(user.getUsername());
        foundUser.setPassword(user.getPassword());
        repository.setUser(foundUser);
        return true;
    }

    @PostMapping(path = "/users")
    @ResponseBody
    public String setUser(@RequestBody User user) {
        user.setPassword(PasswordController.hash(user.getPassword()));
        user.setDisabled(0);
        user.setNickname(user.getUsername());
        user.setAvatar("default");
        user.setScore(0);
        user.setStatus(0);
        user.setUserType("User");
        User newUser = repository.setUser(user);
        if (newUser == null)
            return "";
        String token = tokenController.generate(newUser);
        String res = new StringBuilder()
                .append("{\"Session\":{")
                .append("\"token\":\"" + token + "\"")
                .append(",\"userType\":\"" + user.getUserType() + "\"")
                .append(",\"email\":\"" + user.getEmail() + "\"")
                .append(",\"disabled\":\"" + user.getDisabled() + "\"")
                .append(",\"nickname\":\"" + user.getNickname() + "\"")
                .append(",\"avatar\":\"" + user.getAvatar() + "\"")
                .append(",\"score\":" + user.getScore())
                .append(",\"status\":" + user.getStatus())
                .append("}}")
                .toString();
        return res;
    }

    @GetMapping(path = "/users/{id}")
    public @ResponseBody
    User getUserById(@PathVariable int id) {
        return repository.getUserByID(id);
    }

    @GetMapping(path = "/users/token/{token}")
    public @ResponseBody
    User getUserByToken(@PathVariable String token) throws Exception {
        if (tokenController.validateToken(token)) {
            return tokenController.tokenGetUser(token);
        }
        return null;
    }

    @GetMapping(path = "/login/{username}/{password}")
    public @ResponseBody
    String getUserByLogin(@PathVariable String username, @PathVariable String password) {
        User user = repository.getUserByLogin(username, password);

        if (user == null)
            return "";

        String token = tokenController.generate(user);

        String res = new StringBuilder()
                .append("{\"Session\":{")
                .append("\"token\":\"" + token + "\"")
                .append(",\"userType\":\"" + user.getUserType() + "\"")
                .append(",\"email\":\"" + user.getEmail() + "\"")
                .append(",\"disabled\":\"" + user.getDisabled() + "\"")
                .append(",\"nickname\":\"" + user.getNickname() + "\"")
                .append(",\"avatar\":\"" + user.getAvatar() + "\"")
                .append(",\"score\":" + user.getScore())
                .append(",\"status\":" + user.getStatus())
                .append("}}")
                .toString();
        return res;
    }

    @GetMapping(path = "/logout/{token}")
    @ResponseBody
    public User logout(@PathVariable String token) throws Exception {
        if (tokenController.validateToken(token)) {
            User user = tokenController.tokenGetUser(token);
            user.setCurrentToken(null);
            return repository.setUser(user);
        }
        return null;
    }

    @GetMapping(path = "/profile/{token}")
    @ResponseBody
    public String getProfile(@PathVariable String token) throws Exception {
        if (tokenController.validateToken(token)) {
            User user = tokenController.tokenGetUser(token);
            String str = new StringBuilder()
                    .append("{\"Profile\":{")
                    .append("\"avatar\":\"" + user.getAvatar() + "\"")
                    .append(",\"nickname\":\"" + user.getNickname() + "\"")
                    .append(",\"score\":" + user.getScore())
                    .append(",\"status\":" + user.getStatus())
                    .append("}}")
                    .toString();
            return str;
        }
        return null;
    }

    @PutMapping(path = "/profile/{token}")
    @ResponseBody
    public String updateProfile(@PathVariable String token, @RequestBody User user) throws Exception {
        if (tokenController.validateToken(token)) {
            User profile = tokenController.tokenGetUser(token);
            profile.setAvatar(user.getAvatar());
            profile.setNickname(user.getNickname());
            profile.setStatus(user.getStatus());
            repository.setUser(profile);

            String str = new StringBuilder()
                    .append("{\"Profile\":{")
                    .append("\"avatar\":\"" + user.getAvatar() + "\"")
                    .append(",\"nickname\":\"" + user.getNickname() + "\"")
                    .append(",\"status\":" + user.getStatus())
                    .append("}}")
                    .toString();
            return str;
        }
        return null;
    }

    @GetMapping(path = "/profile-advanced/{token}")
    @ResponseBody
    public String getProfileAdvanced(@PathVariable String token) throws Exception {
        if (tokenController.validateToken(token)) {
            User user = tokenController.tokenGetUser(token);
            String str = new StringBuilder()
                    .append("{\"Advanced\":{")
                    .append("\"email\":\"" + user.getEmail() + "\"")
                    .append(",\"username\":\"" + user.getUsername() + "\"")
                    .append(",\"password\":\"" + user.getPassword() + "\"")
                    .append("}}")
                    .toString();
            return str;
        }
        return null;
    }

    @PutMapping(path = "/profile-advanced/{token}")
    @ResponseBody
    public String updateProfileAdvanced(@PathVariable String token, @RequestBody User user) throws Exception {
        if (tokenController.validateToken(token)) {
            User profile = tokenController.tokenGetUser(token);
            profile.setEmail(user.getEmail());
            profile.setUsername(user.getUsername());
            profile.setPassword(PasswordController.hash(user.getPassword()));
            repository.setUser(profile);

            String str = new StringBuilder()
                    .append("{\"Advanced\":{")
                    .append("\"email\":\"" + user.getEmail() + "\"")
                    .append(",\"username\":\"" + user.getUsername() + "\"")
                    .append(",\"password\":\"" + user.getPassword() + "\"")
                    .append("}}")
                    .toString();
            return str;
        }
        return null;
    }

    @PutMapping(path = "/profile/addscore/{token}")
    @ResponseBody
    public int updateProfileAddScore(@PathVariable String token, @RequestBody User user) throws Exception {
        if (tokenController.validateToken(token)) {
            User profile = tokenController.tokenGetUser(token);
            profile.setScore(profile.getScore() + user.getScore());
            repository.setUser(profile);
            return profile.getScore();
        }
        return -1;
    }

    @GetMapping(path = "/adminUsers")
    public @ResponseBody
    long getAdmin() {
        return repository.countUsers();
    }

    @GetMapping(path = "/adminData")
    public @ResponseBody
    List<User> getData() {
        return repository.getData();
    }

    @GetMapping(path = "/adminDisable/{id}")
    public @ResponseBody
    boolean disable(@PathVariable("id") int id) {
        if (repository.getUserByID(id) != null) {
            repository.disable(id);
            return true;
        } else return false;
    }

    @GetMapping(path = "/adminEnable/{id}")
    public @ResponseBody
    boolean enable(@PathVariable("id") int id) {
        repository.enable(id);
        return true;
    }

    @GetMapping(path = "/exist/{email}/{username}/{token}")
    @ResponseBody
    public String accountExist(
            @PathVariable String email,
            @PathVariable String username,
            @PathVariable String token) throws Exception {
        boolean emailBool = true;
        boolean usernameBool = true;

        if (tokenController.validateToken(token)) {
            User user = tokenController.tokenGetUser(token);

            if (user.getEmail().equals(email)) {
                emailBool = false;
            } else {
                User emailUser = repository.getUsers()
                        .stream()
                        .filter(u ->
                                (u.getEmail().equals(email) || u.getUsername().equals(email)))
                        .findFirst()
                        .orElse(null);
                emailBool = emailUser != null;
            }

            if (user.getUsername().equals(username)) {
                usernameBool = false;
            } else {
                User usernameUser = repository.getUsers()
                        .stream()
                        .filter(u ->
                                (u.getUsername().equals(username) || u.getEmail().equals(username)))
                        .findFirst()
                        .orElse(null);
                usernameBool = usernameUser != null;
            }

        }
        return "{\"Exist\":{" +
                "\"email\":" + emailBool +
                ",\"username\":" + usernameBool +
                "}}";
    }
}
