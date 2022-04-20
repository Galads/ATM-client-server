package application.security.filters;

import application.entity.Client;
import application.properties.ServerProperties;
import application.security.JPAUserDetails;
import application.security.jwt.JWT;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JwtRequestFilterTest {
    @MockBean
    private ServerProperties serverProperties;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    private String token;

    @BeforeAll
    public void initToken() {
        when(serverProperties.getSECRET_KEY()).thenReturn("secret");
        token = new JWT(serverProperties).generateToken(new JPAUserDetails(new Client("nikolay", "password", (short) 1234)));
    }

    @Test
    public void doFilterInternal() throws ServletException, IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        FilterChain filterChain = Mockito.mock(FilterChain.class);

        when(serverProperties.getSECRET_KEY()).thenReturn("secret");
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verifyNoMoreInteractions(filterChain);
        verify(request, times(1)).getHeader("Authorization");
    }
}