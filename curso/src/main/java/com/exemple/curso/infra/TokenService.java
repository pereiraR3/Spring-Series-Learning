package com.exemple.curso.infra;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.exemple.curso.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario) {
        if (usuario == null || usuario.getLogin() == null) {
            throw new IllegalArgumentException("Usuário ou login não pode ser nulo");
        }
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("Remedios_api1") // quem criou
                    .withSubject(usuario.getLogin()) // Para identificar o usuario
                    .withClaim("id", usuario.getId()) // Pegar o identificador do usuario
                    .withExpiresAt(dataExpiracao())
                    .sign(algorithm); // assinatura
        } catch (JWTCreationException exception) {
            logger.error("Erro ao gerar token", exception);
            throw new TokenCreationException("Erro ao gerar token", exception);
        }
    }

    public String getSubject(String tokenJWT){
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("Remedios_api1")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception){
            logger.error("Token inválido ou expirado", exception);
            throw new TokenVerificationException("Token inválido ou expirado", exception);
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    // Exceção personalizada para criação de token
    public static class TokenCreationException extends RuntimeException {
        public TokenCreationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    // Exceção personalizada para verificação de token
    public static class TokenVerificationException extends RuntimeException {
        public TokenVerificationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
