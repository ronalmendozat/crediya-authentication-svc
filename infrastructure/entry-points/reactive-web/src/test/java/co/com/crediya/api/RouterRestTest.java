package co.com.crediya.api;

import co.com.crediya.api.mapper.UserDTORequestMapper;
import co.com.crediya.api.mapper.UserDTOResponseMapper;
import co.com.crediya.api.security.jwt.JwtProvider;
import co.com.crediya.usecase.rol.RolUseCase;
import co.com.crediya.usecase.user.UserUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;

@ContextConfiguration(classes = {RouterRest.class, Handler.class})
@WebFluxTest
class RouterRestTest {

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
    void testListenGETUseCase() {
        /*
        webTestClient.get()
                .uri("/api/usecase/path")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(userResponse -> {
                            Assertions.assertThat(userResponse).isEmpty();
                        }
                );

         */
    }

    @Test
    void testListenGETOtherUseCase() {
        /*
        webTestClient.get()
                .uri("/api/otherusercase/path")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(userResponse -> {
                            Assertions.assertThat(userResponse).isEmpty();
                        }
                );

         */
    }

    @Test
    void testListenPOSTUseCase() {
        /*
        webTestClient.post()
                .uri("/api/usecase/otherpath")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue("")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(userResponse -> {
                            Assertions.assertThat(userResponse).isEmpty();
                        }
                );

         */
    }
}
