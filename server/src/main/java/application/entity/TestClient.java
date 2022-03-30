package application.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@AllArgsConstructor // удалить
@Entity
@Getter // обязательно иначе пустой json?
@Table(name = "testclient")
public class TestClient {
    @Id
    private long id;
    private short pin;
    private BigDecimal balance;
}