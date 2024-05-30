package aibg.selekcioni23.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Entity
@Table(name = "tbl_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    private String username;

    @NotEmpty
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
