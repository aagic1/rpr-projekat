package ba.unsa.etf.rpr.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Java bean for User
 */
public class User implements Idable{
    private int id;
    private String username;
    private String email;
    private String password;
    private String about;


    public User() {
    }

    public User(int id, String username, String email, String password, String about) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.about = about;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(about, user.about);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, about);
    }

    @Override
    public String toString() {
        return username;
    }
}
