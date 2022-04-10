package application.security.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
class JPAUserDetailsServiceTest {
    @Autowired
    private JPAUserDetailsService jpaUserDetailsService;

    @Test
    public void loadUserByUsernameSuccess() {
        String user = "nikolay";
        UserDetails userDetails = jpaUserDetailsService.loadUserByUsername(user);

        assertEquals(user, userDetails.getUsername());
        assertFalse(userDetails.getPassword().isEmpty());
    }

    @Test
    public void loadUserByUsernameFailed() {
        String user = "failUser";
        Exception exception = assertThrows(UsernameNotFoundException.class, () ->
                jpaUserDetailsService.loadUserByUsername(user));

        assertTrue(exception.getMessage().contains("not found!"));
    }
}