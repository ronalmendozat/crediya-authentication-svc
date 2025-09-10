package co.com.crediya.api.config;

import co.com.crediya.api.Handler;
import co.com.crediya.api.RouterRest;
import co.com.crediya.api.mapper.UserDTORequestMapper;
import co.com.crediya.api.mapper.UserDTOResponseMapper;
import co.com.crediya.api.security.jwt.JwtProvider;
import co.com.crediya.usecase.rol.RolUseCase;
import co.com.crediya.usecase.user.UserUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;

@ContextConfiguration(classes = {RouterRest.class, Handler.class})
@WebFluxTest
@Import({CorsConfig.class, SecurityHeadersConfig.class})
class ConfigTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private UserUseCase userUseCase;

    @MockitoBean
    private UserDTORequestMapper userDTOMapper;

    @MockitoBean
    private UserDTOResponseMapper userDTOResponseMapper;
    @MockitoBean
    private PasswordEncoder passwordEncoder;
    @MockitoBean
    private JwtProvider jwtProvider;
    @MockitoBean
    private RolUseCase rolUseCase;

    @Test
    void corsConfigurationShouldAllowOrigins() {
        /*
        webTestClient.get()
                .uri("/api/usecase/path")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Security-Policy",
                        "default-src 'self'; frame-ancestors 'self'; form-action 'self'")
                .expectHeader().valueEquals("Strict-Transport-Security", "max-age=31536000;")
                .expectHeader().valueEquals("X-Content-Type-Options", "nosniff")
                .expectHeader().valueEquals("Server", "")
                .expectHeader().valueEquals("Cache-Control", "no-store")
                .expectHeader().valueEquals("Pragma", "no-cache")
                .expectHeader().valueEquals("Referrer-Policy", "strict-origin-when-cross-origin");

         */
    }

}