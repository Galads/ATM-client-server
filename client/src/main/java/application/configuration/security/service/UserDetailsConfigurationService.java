package application.configuration.security.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsConfigurationService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return new User("admin", "admin", new ArrayList<>());
    }
}
