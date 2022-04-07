package application.model;

import application.security.jwt.JWT;
import application.security.service.JPAUserDetailsService;
import dto.AuthenticationResponse;
import dto.ClientRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private AuthenticationManager authenticationManager;
    private JPAUserDetailsService userDetailsService;
    private JWT jwtCreator;

    public AuthenticationResponse authenticate(ClientRequest clientRequest) {
        UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(
                clientRequest.getLogin(),
                clientRequest.getPassword()
        );

        try {
            authenticationManager.authenticate(userToken);
        } catch (BadCredentialsException ex) {
            return new AuthenticationResponse("");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(clientRequest.getLogin());

        return new AuthenticationResponse(jwtCreator.generateToken(userDetails));
    }
}
