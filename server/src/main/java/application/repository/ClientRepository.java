package application.repository;

import application.entity.TestClient;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<TestClient, Long> {
}
