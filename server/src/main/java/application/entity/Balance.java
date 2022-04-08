package application.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
public class Balance {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client_id;
    private BigDecimal amount;
    private String name;
}
