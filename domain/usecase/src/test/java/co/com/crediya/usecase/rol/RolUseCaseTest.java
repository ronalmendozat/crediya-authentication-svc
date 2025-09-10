package co.com.crediya.usecase.rol;

import co.com.crediya.model.rol.Rol;
import co.com.crediya.model.rol.gateways.RolRepository;
import co.com.crediya.model.user.User;
import co.com.crediya.usecase.rol.helper.RolUseCaseTestHelper;
import co.com.crediya.usecase.user.helper.UserUseCaseTestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RolUseCaseTest {

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private RolUseCase rolUseCase;

    @Test
    @DisplayName("Test find by Id OK")
    void testFindById_OK(){
        Rol rol = RolUseCaseTestHelper.crearRol();

        Mockito.when(this.rolRepository.findById(Mockito.anyLong()))
                .thenReturn(Mono.just(rol));

        Mono<Rol> result = this.rolUseCase.findById(1L);

        StepVerifier.create(result)
                .expectNextMatches(u -> u.getId().equals(1L))
                .verifyComplete();
    }
}