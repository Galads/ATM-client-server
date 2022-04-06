package application.repository;

import application.entity.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Long> {
    Optional<Client> findByLoginAndPassword(String login, String password);
    Optional<Client> findByLogin(String login);
}
