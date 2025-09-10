package co.com.crediya.usecase.user;

import co.com.crediya.model.user.RoleEnum;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepository;

    private static final int MAX_INTENTOS = 3;

    public Mono<User> saveUser(User user) {
        return userRepository.findByEmail(user.getEmail())
                .hasElement()
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        return Mono.error(new IllegalStateException(
                                "El usuario con email " + user.getEmail() + " ya existe"
                        ));
                    }
                    user.setRolId(RoleEnum.USER.getId());
                    user.setActive(Boolean.TRUE);
                    return userRepository.save(user);
                });

    }

    public Mono<Boolean> existUserByIdentityDocument(String identityDocument) {
        return userRepository.findByIdentityDocument(identityDocument)
                .map(exist -> true)
                .defaultIfEmpty(false);
    }

    public Mono<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Mono<User> findUserByDocument(String document){
        return userRepository.findByIdentityDocument(document);
    }
}
