package aibg.selekcioni23.domain;

import aibg.selekcioni23.logic.Assignment;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    private Long id;
    private String username;
    private String password;
    private int result;
    private int trueResult;
    private boolean success;
    private Assignment assignment;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", result=" + result +
                ", trueResult=" + trueResult +
                ", success=" + success +
                ", assignment=" + assignment +
                '}';
    }
}
