package application.security.service;

import application.repository.ClientRepository;
import application.security.JPAUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JPAUserDetailsService implements UserDetailsService {
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return clientRepository
                .findByLogin(username)
                .map(JPAUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Client with name: " + username + " not found!"));
    }
}
