package application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class ClientAuthenticationResponseJWT {
    private String jwt;
}
