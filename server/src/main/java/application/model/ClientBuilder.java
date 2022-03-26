package application.model;

import application.entity.TestClient;
import application.repository.ClientRepository;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ClientBuilder {
    private ClientRepository clientRepository;

    public List<TestClient> getAllClients() {
        List<TestClient> clients = new ArrayList<>();
        clientRepository
                .findAll()
                .forEach(clients::add);
        return clients;
    }

    public void addClient(@NotNull TestClient client) {
        clientRepository.save(client);
    }
}
