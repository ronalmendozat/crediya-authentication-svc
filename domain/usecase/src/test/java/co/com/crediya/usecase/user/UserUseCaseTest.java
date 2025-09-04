package co.com.crediya.usecase.user;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
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

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserUseCase userUseCase;

    @Test
    @DisplayName("Test save user OK")
    void testSaveUser_OK() {

        User userSave = UserUseCaseTestHelper.crearUsuario();

        Mockito.when(this.userRepository.findByEmail(Mockito.anyString()))
                .thenReturn(Mono.empty());
        Mockito.when(this.userRepository.save(Mockito.any(User.class)))
                .thenReturn(Mono.just(userSave));

        Mono<User> result = userUseCase.saveUser(userSave);

        StepVerifier.create(result)
                .expectNextMatches(u -> u.getEmail().equals(userSave.getEmail()))
                .verifyComplete();
    }

    @Test
    @DisplayName("Test save user KO")
    void testSaveUser_KO() {
        User userExist = UserUseCaseTestHelper.crearUsuario();
        User userSave = UserUseCaseTestHelper.crearUsuario();

        Mockito.when(this.userRepository.findByEmail(Mockito.anyString()))
                .thenReturn(Mono.just(userExist));

        Mono<User> result = userUseCase.saveUser(userSave);

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof IllegalStateException &&
                                throwable.getMessage().equals("El usuario con email ronal@gmail.com ya existe"))
                .verify();
    }

    @Test
    @DisplayName("Test exist user by identity document OK")
    void testExistUserByIdentityDocument_OK(){
        User user = UserUseCaseTestHelper.crearUsuario();

        Mockito.when(this.userRepository.findByIdentityDocument(Mockito.anyString()))
                .thenReturn(Mono.just(user));

        Mono<Boolean> result = this.userUseCase.existUserByIdentityDocument("documento-test");

        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    @DisplayName("Test exist user by identity document KO")
    void testExistUserByIdentityDocument_KO(){

        Mockito.when(this.userRepository.findByIdentityDocument(Mockito.anyString()))
                .thenReturn(Mono.empty());

        Mono<Boolean> result = this.userUseCase.existUserByIdentityDocument("documento-test");

        StepVerifier.create(result)
                .expectNext(false)
                .verifyComplete();
    }

}