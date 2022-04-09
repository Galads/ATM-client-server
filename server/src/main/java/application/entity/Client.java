package application.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String login;
    private String password;
    private short pin;

    @OneToMany(mappedBy = "client_id")
    private Set<Balance> balance;

    public Client(String login, String password, short pin) {
        this.login = login;
        this.password = password;
        this.pin = pin;
    }
}
