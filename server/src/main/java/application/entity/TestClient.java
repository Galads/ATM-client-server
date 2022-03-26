package application.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "testclient")
public class TestClient {
    @Id
    private long id;
    private short pin;
    private BigDecimal balance;
}