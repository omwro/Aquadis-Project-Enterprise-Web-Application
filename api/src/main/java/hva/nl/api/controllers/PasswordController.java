package hva.nl.api.controllers;

public class PasswordController {

    public static String hash(String password) {
        return org.mindrot.jbcrypt.BCrypt.hashpw(password, org.mindrot.jbcrypt.BCrypt.gensalt());
    }

    public static boolean check(String password, String hashed) {
        return org.mindrot.jbcrypt.BCrypt.checkpw(password, hashed);
    }
}
