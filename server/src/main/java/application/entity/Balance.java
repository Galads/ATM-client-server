package application.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
public class Balance {
    @Id
    private long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client_id;// many to one
    private BigDecimal amount;
    private String name;
}
