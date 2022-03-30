package application.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class Client {
    @Id
    private long id;
    private String login;
    private String password;
    private short pin;

    @OneToMany(mappedBy = "client_id")
    private Set<Balance> balance;
}
